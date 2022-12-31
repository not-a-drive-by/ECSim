package edu.boun.edgecloudsim.edge_client;

import edu.boun.edgecloudsim.core.ScenarioFactory;
import edu.boun.edgecloudsim.network.Channel;
import edu.boun.edgecloudsim.network.NetworkModel;
import edu.boun.edgecloudsim.task_generator.TaskGeneratorModel;
import edu.boun.edgecloudsim.task_generator.Task;

import java.lang.reflect.Array;
import java.util.*;

public class MobileDevice {
    //每个设备有一个产生任务的对象
//    private TaskGeneratorModel taskGenerator;

    //设备自身状态信息
    private int mobileID;
    private double x_pos;
    private double y_pos;
    private int power;//发射功率

    //待处理任务队列 假设都是三种
    public Queue queue1 = new Queue();
    public Queue queue2 = new Queue();
    public Queue queue3 = new Queue();
    public List<Queue> queue = new ArrayList<Queue>();

    //待发送任务队列 不分种类
    public List<Task> transQueue = new ArrayList<Task>();

    //所有任务先放在预处理队列中
    public List<Task> preQueue;

    //工具类比较器
    Comparator<Queue> queueComparatorByLen = new QueueComparatorByLen();
    Comparator<Queue> queueComparatorByLQValue = new QueueComparatorByQValue();

    public MobileDevice(double _x, double _y, int _mobileID, List<Task> tasks) {
        //初始化参数
        this.x_pos = _x;
        this.y_pos = _y;
        this.mobileID = _mobileID;
        this.preQueue = tasks;
        this.power = 20;

    }

    //更新待处理队列
    public void updateDeviceQueue(int t){
        if(preQueue.size()==0) return;

        Task tmpTask = preQueue.get(0);
        while(tmpTask.arrivalTime <= t ){
            if( tmpTask.getType()==1 ){
                queue1.getTaskQueues().add(tmpTask);
            }else if( tmpTask.getType()==2 ){
                queue2.getTaskQueues().add(tmpTask);
            }else{
                queue3.getTaskQueues().add(tmpTask);
            }
            preQueue.remove(tmpTask);
            if(preQueue.size()==0) break;
            tmpTask = preQueue.get(0);
        }
    }

    //更新待发送队列
    public void updateTransQueue(NetworkModel networkModel){
//        Channel cha = networkModel.serachChannelByDeviceandServer( mobileID, 8001);
        //每个任务减去该时隙传输的内容大小
        //如果传输完了 就调用对应服务器的接收函数 还没写
    }

    //更新quota
    public void updateQuota(NetworkModel networkModel){
        //1.先清除所有队列的quota
        queue.add(queue1);
        queue.add(queue2);
        queue.add(queue3);
        for(Queue que : queue){
            que.setQuota(0);
        }
        clearBlankQueue();
        //2.找出对于当前设备而言的合格信道
        List<Channel> channels = networkModel.serachChannelByDevice(mobileID);
        Iterator<Channel> iteratorChannel = channels.iterator();
        while(iteratorChannel.hasNext()){
            Channel chan = iteratorChannel.next();
            if( chan.ratio < 3E-4) iteratorChannel.remove();
        }
//        System.out.println("更新信道信息后"+channels.size());
        //没有合格信道结束
        if(channels.size() == 0) return;

        //3.否则开始Q值迭代
        int quotaSum = channels.size();
        if( quotaSum < queue.size() ){//3.1：quota不够所有队列分配一个 对队长排序后分配
            Collections.sort(queue, queueComparatorByLen);
            int index = 0;
            while(quotaSum != 0){
                queue.get(index).setQuota(1);
                index++;
                quotaSum--;
            }
            return;
        }else if( quotaSum==queue.size() ){//3.2: 每个队列quota一个
            for(Queue que : queue){      que.setQuota(1);     }
            return;
        }else{
            quotaSum -= queue.size();
            for(Queue que : queue){      que.setQuota(1);     }
            while( quotaSum!=0 ){
                Collections.sort(queue, queueComparatorByLQValue);
                int tmp = queue.get(0).getQuota();
                queue.get(0).setQuota( tmp+1 );
                quotaSum--;
            }
            return;
        }

    }

    //queue清除空白队列
    private void clearBlankQueue(){
//        for(Queue que : queue){
//            if(que.getTaskQueues().size()==0) queue.remove(que);
//        }
        Iterator<Queue> iteratorQue = queue.iterator();
        while(iteratorQue.hasNext()){
            Queue que = iteratorQue.next();
            if( que.getTaskQueues().size()==0 ) iteratorQue.remove();
        }
    }


    //Queue比较器函数
    public class QueueComparatorByLen implements Comparator<Queue>
    {
        public int compare(Queue q1, Queue q2)
        {
            return (q1.getTaskQueues().size() - q2.getTaskQueues().size());
        }
    }
    public class QueueComparatorByQValue implements Comparator<Queue>
    {
        public int compare(Queue q1, Queue q2)
        {
            double Q1 =  q1.getTaskQueues().size()/(q1.getQuota()*(q1.getQuota()+1));
            double Q2 =  q2.getTaskQueues().size()/(q2.getQuota()*(q2.getQuota()+1));
            int res;
            if( Q1 > Q2 ){
                return (int) Math.ceil(Q1-Q2);
            }else if(Q1 < Q2){
                return (int) Math.floor(Q1-Q2);
            }else{
                return 0;
            }
        }
    }


    //一些无聊的函数
    public double getX() {  return x_pos;    }
    public void setX(double x_pos) {  this.x_pos = x_pos;    }
    public double getY() {    return y_pos;    }
    public void setY(double y_pos) {    this.y_pos = y_pos;    }
    public int getPower(){ return power;}
    public int getMobileID(){   return mobileID; }

    @Override
    public String toString() {
        return "MobileDevice{" +
                "mobileID=" + mobileID +
                ", x_pos=" + x_pos +
                ", y_pos=" + y_pos + "\r\n" +
                ", preProcessedTasks=" + preQueue + "\r\n" +
                ", queue1" + queue1 +
                '}' + "\r\n";
    }
}
