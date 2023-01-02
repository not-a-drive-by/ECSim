/*
* 一个DataCenter里的任务调度
* 节点内李雅普诺夫
**/

package edu.boun.edgecloudsim.edge_server;

import edu.boun.edgecloudsim.edge_client.Queue;
import edu.boun.edgecloudsim.task_generator.Task;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EdgeDataCenter {

    //自身属性
    private int id;
    private int CPU;
    private int RAM;
    private int storage;
    private double x_pos;
    private double y_pos;
    private int quota;

    private List<Task> queue1;
    private List<Task> queue2;
    private List<Task> queue3;
    private List<Task> receiveReqFromTasks = new ArrayList<Task>();

    //比较器实例
    TaskPreferenceComparator taskPreferenceComparator = new TaskPreferenceComparator();

    public EdgeDataCenter(int _id, int _CPU, int _RAM, int _storage, double x, double y){
        this.id = _id;
        this.CPU = _CPU;
        this.RAM = _RAM;
        this.storage = _storage;
        this.x_pos = x;
        this.y_pos = y;
    }


    //根据系统剩余资源产生可行虚拟机矩阵
    public int[][] createMatrix(){
        int[][] vmConfig = {{1,2,1},{1,0,2}};
        return vmConfig;
    }

    //根据李亚算法在矩阵中选择
    public int[] selectVM(int[][] matric){
        int[] select = {1,2,1};
        return select;
    }

    //根据选择虚拟机组合创建虚拟机
    public void createVMs(int[] selectedVM){

    }

    //关闭所有任务已经处理完的VM
    public void terminateVMS(){

    }

    public void processTask(List<Task> receiveTask){
        int[][] feasableMatrix = createMatrix();
        int[] selectedVM = selectVM(feasableMatrix);
        createVMs(selectedVM);

    }

    public void updateServerQuota(){
        quota = 1;
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

    public class TaskPreferenceComparator implements Comparator<Task>
    {
        public int compare(Task t1, Task t2)
        {
            return (t1.taskID - t2.taskID);
        }
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

    //关闭所有的Host和VM
    public void shutdownEntity(){

    }

    //一些没什么用的方法
    public double getX() {   return x_pos;    }
    public void setX(double x_pos) {    this.x_pos = x_pos;    }
    public double getY() {    return y_pos;    }
    public void setY(double y_pos) {    this.y_pos = y_pos;    }
    public int getId(){ return id;}
    public List<Task> getReceiveReqFromTasks() {    return receiveReqFromTasks;   }

    @Override
    public String toString() {
        return "EdgeDataCenter{" +
                "id=" + id +
                ", CPU=" + CPU +
                ", RAM=" + RAM +
                ", storage=" + storage +
                ", x_pos=" + x_pos +
                ", y_pos=" + y_pos +
                '}' + "\r\n";
    }
}
