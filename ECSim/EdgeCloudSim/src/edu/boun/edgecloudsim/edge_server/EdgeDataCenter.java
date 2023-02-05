/*
* 一个DataCenter里的任务调度
* 节点内李雅普诺夫
**/

package edu.boun.edgecloudsim.edge_server;

import edu.boun.edgecloudsim.edge_client.Queue;
import edu.boun.edgecloudsim.statistic.Data;
import edu.boun.edgecloudsim.task_generator.Task;
import edu.boun.edgecloudsim.utils.StaticfinalTags;
import org.w3c.dom.Element;

import java.util.*;

public class EdgeDataCenter {

    //自身属性
    private int id;
    private int CPU;
    private int RAM;
    private int storage;
    private double x_pos;
    private double y_pos;
    private int quota;

    private List<Task> queue1 = new ArrayList<Task>();
    private List<Task> queue2 = new ArrayList<Task>();
    private List<Task> queue3 = new ArrayList<Task>();
    private List<List<Task>> queue = new ArrayList<List<Task>>();

    private List<Task> receiveReqFromTasks = new ArrayList<Task>();
    private List<EdgeVM> activeVM = new ArrayList<EdgeVM>();

    //比较器实例
    TaskPreferenceComparator taskPreferenceComparator = new TaskPreferenceComparator();
    TaskLengthComparator taskLengthComparator = new TaskLengthComparator();

    public EdgeDataCenter(int _id, int _CPU, int _RAM, int _storage, double x, double y){
        this.id = _id;
        this.CPU = _CPU;
        this.RAM = _RAM;
        this.storage = _storage;
        this.x_pos = x;
        this.y_pos = y;

        this.queue.add(queue1);
        this.queue.add(queue2);
        this.queue.add(queue3);
    }



    public void updateServerQuota(){
        quota = 2;
    }

    //更新偏好序列
    public boolean updatePreference(){
        Collections.sort(receiveReqFromTasks, taskPreferenceComparator);
        List<Task> rejectedReqTask;
        //超过限额部分的任务要拒绝
        //被拒绝的任务目标服务器应当清空 发起请求的时候已经从preferenceList中删掉了
        if( receiveReqFromTasks.size() > quota){
            rejectedReqTask = receiveReqFromTasks.subList(quota, receiveReqFromTasks.size());
            receiveReqFromTasks = receiveReqFromTasks.subList(0, quota);
            for(Task t : rejectedReqTask){
                t.setTargetServer(null);
            }
            return true;
        }
        return false;
    }



    //接受卸载的任务
    public void receiveOffloadTasks(Task task){
        int taskType = task.getType();
        if( taskType == 1 ){
            queue1.add(task);
        }else if( taskType == 2 ){
            queue2.add(task);
        }else{
            queue3.add(task);
        }
    }

    //剩余资源
    public int[] returnRemainResource(){
        int remainResource[]=new int[3];  //初始化资源总数
        int Resource[]={CPU, RAM, storage};
        /*剩余资源=当前总资源-正在执行任务所占资源*/
        int totalResource[]=new int[3];// 当前所占总资源
        for( EdgeVM vm : activeVM){
            totalResource[0] += vm.getCPU();
            totalResource[1] += vm.getRAM();
            totalResource[2] += vm.getStorage();
        }

        remainResource[0]=Resource[0]-totalResource[0];
        remainResource[1]=Resource[1]-totalResource[1];
        remainResource[2]=Resource[2]-totalResource[2];

        return remainResource;
    }

    //根据系统剩余资源产生可行虚拟机矩阵
    public ArrayList<int[]> createMatrix(){

        int N[] = new int[3];
        int remain[]=returnRemainResource();
        int R[][]={{8,32,1690},{4,30,420},{4,7,1690}};
        int Nv = 0;

        for(int v=0; v<3; v++){
            int min = (int) Math.floor( remain[0]/R[v][0] ) ;
            int tmp;
            for(int k=1; k<3; k++){
                tmp = (int) Math.floor( remain[k]/R[v][k] );
                min = tmp<min ? tmp : min;
            }
            N[v] = min;
            Nv = min>Nv ? min : Nv;
        }

        int[] tmpselect=new int[3];
        boolean tmptotal[]=new boolean[3];
        ArrayList<int[]> select=new ArrayList<int[]>();
        int total;


        for(int v1=0; v1<N[0]+1; v1++){
            for(int v2=0; v2<N[1]+1; v2++){
                for(int v3=0; v3<N[2]+1; v3++){
                    if( v1+v2+v3 < Nv+1 ){
                        tmpselect[0]=v1;
                        tmpselect[1]=v2;
                        tmpselect[2]=v3;

                        for(int k=0;k<3;k++)
                        {	total=0;
                            for(int v=0;v<3;v++)
                            {
                                total += tmpselect[v]*R[v][k];
                            }

                            tmptotal[k] = total<=remain[k] ? true : false;

                        }

                        if(tmptotal[0]&&tmptotal[1]&&tmptotal[2])
                        {
                            int[] ttmpselect = tmpselect.clone();
                            select.add(ttmpselect);

                        }

                    }
                }
            }
        }
        return select;
    }

    //根据李亚算法在矩阵中选择行向量
    public int[] selectPolicy(ArrayList<int[]> matrix, double time){

        int policy[];
        int queuelength[]=new int[3];


        double avedpp[]=new double[matrix.size()];//记录每种虚拟机组合下的平均工作时间

        int num[]=new int[3];//每种VM组合的虚拟机台数
        int tmpnum[]=new int[3];//暂存此时刻后三个队列处理到的任务id
        double sum[]=new double[matrix.size()];//记录一种VM组合下至今为止三种工作的总时间
        int sumnum=0;//记录一种VM组合下至今为止三种工作的总数量

        queuelength[0]=queue1.size();
        queuelength[1]=queue2.size();
        queuelength[2]=queue3.size();

        //找最小
        for(int i=0;i<matrix.size();i++)//矩阵中的每一行
        {
            double pen[] = new double[3];//惩罚项
            int qmw[] = new int[3];
            sumnum=0;
            tmpnum[0]=tmpnum[1]=tmpnum[2]=0;
            num[0]=matrix.get(i)[0];//第i行组合对应的虚拟机台数
            num[1]=matrix.get(i)[1];
            num[2]=matrix.get(i)[2];

            //返回理论虚拟机台数和队列长度的较小值
            tmpnum[0] = (num[0]>queuelength[0]) ? queuelength[0] : num[0];
            tmpnum[1] = (num[1]>queuelength[1]) ? queuelength[1] : num[1];
            tmpnum[2] = (num[2]>queuelength[2]) ? queuelength[2] : num[2];

            sumnum = tmpnum[0]+tmpnum[1]+tmpnum[2];

            qmw[0] = (queuelength[0] - num[0]) * tmpnum[0];
            qmw[1] = (queuelength[1] - num[1]) * tmpnum[1];
            qmw[2] = (queuelength[2] - num[2]) * tmpnum[2];
            //System.out.println();
            //System.out.print("处理数量："+tmpnum[0]+"&"+tmpnum[1]+"&"+tmpnum[2]);System.out.println();
            for(int m=0;m<tmpnum[0];m++)//第一类任务在当前时刻完成的总时长和任务数
            {
                pen[0] += ( time - queue1.get(m).getArrivalTime() + queue1.get(m).getLength() )/ (3*tmpnum[0]);
                //如果tmpnum[0]=0，不会执行语句，也不会除法报错,penalty还是为0
            }
            for(int p=0;p<tmpnum[1];p++)//第二类任务在当前时刻完成的总时长和任务数
            {
                pen[1] += ( time - queue2.get(p).getArrivalTime() + queue2.get(p).getLength()) / (3*tmpnum[1]);

            }
            for(int q=0;q<tmpnum[2];q++)//第三类任务在当前时刻完成的总时长和任务数
            {
                pen[2] += ( time - queue3.get(q).getArrivalTime() + queue3.get(q).getLength()) / (3*tmpnum[2]);

            }
            if(sumnum!=0){
                avedpp[i] = -(qmw[0]+qmw[1]+qmw[2])+ StaticfinalTags.alpha*(pen[0]+pen[1]+pen[2]);
            }else{
                avedpp[i] = Double.POSITIVE_INFINITY;
            }
        }

        double tmpave=avedpp[0];
        int aveminid=0;
        for(int i=1;i<matrix.size();i++)
        {
            if(tmpave>avedpp[i])
            {
                tmpave=avedpp[i];//找最小
                aveminid=i;
            }
        }
        policy = matrix.get(aveminid);
        return policy;
    }

    //根据选择虚拟机组合创建虚拟机
    public void createVMs(int[] policy, double time){
        for(int i=0; i<3; i++){
            if( queue.get(i).size() == 0) break;
            int taskNum = policy[i];
            for(int j=0; j<taskNum; j++){
                Task tmptask = queue.get(i).get(0);
                tmptask.setFinishTime( time + tmptask.length );
                activeVM.add( new EdgeVM(tmptask, time) );
                queue.get(i).remove(0);
            }

        }

    }

    //关闭所有任务已经处理完的VM
    public void terminateVMS(double time){
        Iterator<EdgeVM> iteratorVM = activeVM.iterator();
        while(iteratorVM.hasNext()){
            EdgeVM VM = iteratorVM.next();
            if( VM.getOffTime() <= time ) { //等于还是小于等于取决于循环细粒度
                //把处理完的任务加入数据统计集合
                Data.addFinishedTasks(VM.getProcessingTask());
                //离队
                iteratorVM.remove();
            }
        }

    }

    //资源调度总函数
    public void processTask(double time){
        //所有任务按照长度排序
        for( List<Task> que : queue ){
            Collections.sort(que, taskLengthComparator);
        }
        ArrayList<int[]> feasableMatrix = createMatrix();
        int[] selectedPolicy = selectPolicy( feasableMatrix, time);
        createVMs(selectedPolicy, time);
        terminateVMS(time);
    }

    //关闭所有的Host和VM
    public void shutdownEntity(){

    }

    //比较器构造方法
    public class TaskPreferenceComparator implements Comparator<Task>
    {
        public int compare(Task t1, Task t2)
        {
//            return (t1.taskID - t2.taskID);
            double qmw1 = (queue.get(t1.getType()-1).size()-1) * Math.min(queue.get(t1.getType()-1).size(),1);
            double qmw2 = (queue.get(t2.getType()-1).size()-1) * Math.min(queue.get(t2.getType()-1).size(),1);

//            pen[0] += ( time - queue1.get(m).getArrivalTime() + queue1.get(m).getLength() )/ (3*tmpnum[0]);

            double pen1 = StaticfinalTags.curTime - t1.arrivalTime + t1.length;
            double pen2 = StaticfinalTags.curTime - t2.arrivalTime + t2.length;

            double score1 = qmw1 + StaticfinalTags.alpha*pen1;
            double score2 = qmw2 + StaticfinalTags.alpha*pen2;

            if( score1 <= score2){
                return -1;
            }else{
                return 1;
            }

        }
    }
    public class TaskLengthComparator implements Comparator<Task>{
        public int compare(Task t1, Task t2)
        {
            double tmp = t1.length - t2.length;
            if( tmp <= 0 ){
                return 0;
            }else{
                return 1;
            }
        }
    }


    //一些没什么用的方法
    public double getX() {   return x_pos;    }
    public void setX(double x_pos) {    this.x_pos = x_pos;    }
    public double getY() {    return y_pos;    }
    public void setY(double y_pos) {    this.y_pos = y_pos;    }
    public int getId(){ return id;}
    public List<Task> getReceiveReqFromTasks() {    return receiveReqFromTasks;   }
    public List<List<Task>> getQueue() {  return queue;  }

    @Override
    public String toString() {
        return "EdgeDataCenter{" +
                "id=" + id +
                ", CPU=" + CPU +
                ", RAM=" + RAM +
                ", storage=" + storage +
                ", x_pos=" + x_pos +
                ", y_pos=" + y_pos + "\r\n" +
                "avtiveVM" + activeVM +
                '}' + "\r\n";
    }
}
