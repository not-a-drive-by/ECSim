package edu.boun.edgecloudsim.edge_client;

import edu.boun.edgecloudsim.task_generator.TaskGeneratorModel;
import edu.boun.edgecloudsim.task_generator.Task;

import java.util.List;

public class MobileDevice {
    //每个设备有一个产生任务的对象
//    private TaskGeneratorModel taskGenerator;

    //设备自身状态信息
    private int mobileID;
    private double x_pos;
    private double y_pos;
    private int power;//发射功率

    //任务队列 假设都是三种
    public List<Task> queue1;
    public List<Task> queue2;
    public List<Task> queue3;

    //所有任务先放在预处理队列中
    public List<Task> preQueue;

    private int[] quota;

    public MobileDevice(double _x, double _y, int _mobileID, List<Task> tasks) {
        //初始化参数
        this.x_pos = _x;
        this.y_pos = _y;
        this.mobileID = _mobileID;
        this.preQueue = tasks;
        //每个设备有一个产生任务的对象
//        taskGenerator = new TaskGeneratorModel();
    }

    @Override
    public String toString() {
        return "MobileDevice{" +
                "mobileID=" + mobileID +
                ", x_pos=" + x_pos +
                ", y_pos=" + y_pos + "\r\n" +
                ", AllTasks=" + preQueue +
                '}' + "\r\n";
    }
}
