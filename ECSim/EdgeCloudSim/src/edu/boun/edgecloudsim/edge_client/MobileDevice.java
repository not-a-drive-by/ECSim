package edu.boun.edgecloudsim.edge_client;

import edu.boun.edgecloudsim.task_generator.TaskGeneratorModel;
import edu.boun.edgecloudsim.task_generator.Task;

import java.util.ArrayList;
import java.util.List;

public class MobileDevice {
    //每个设备有一个产生任务的对象
//    private TaskGeneratorModel taskGenerator;

    //设备自身状态信息
    private int mobileID;
    private double x_pos;
    private double y_pos;
    private int power;//发射功率

    //待处理任务队列 假设都是三种
    public List<Task> queue1 = new ArrayList<Task>();
    public List<Task> queue2 = new ArrayList<Task>();
    public List<Task> queue3 = new ArrayList<Task>();

    //待发送任务队列 不分种类
    public List<Task> transQueue = new ArrayList<Task>();

    //所有任务先放在预处理队列中
    public List<Task> preQueue;

    private int[] quota;

    public MobileDevice(double _x, double _y, int _mobileID, List<Task> tasks) {
        //初始化参数
        this.x_pos = _x;
        this.y_pos = _y;
        this.mobileID = _mobileID;
        this.preQueue = tasks;

    }

    //更新待处理队列
    public void updateDeviceQueue(int t){
        if(preQueue.size()==0) return;

        Task tmpTask = preQueue.get(0);
        while(tmpTask.arrivalTime <= t ){
            if( tmpTask.getType()==1 ){
                queue1.add(tmpTask);
            }else if( tmpTask.getType()==2 ){
                queue2.add(tmpTask);
            }else{
                queue3.add(tmpTask);
            }
            preQueue.remove(tmpTask);
            if(preQueue.size()==0) break;
            tmpTask = preQueue.get(0);
        }
    }

    //更新待发送队列
    public void updateTransQueue(int t){

    }

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
