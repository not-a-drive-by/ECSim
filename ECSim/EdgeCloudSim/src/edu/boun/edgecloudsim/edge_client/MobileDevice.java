package edu.boun.edgecloudsim.edge_client;

import edu.boun.edgecloudsim.task_generator.IdelActiveGeneratorModel;
import edu.boun.edgecloudsim.task_generator.LoadGeneratorModel;
import edu.boun.edgecloudsim.task_generator.Task;

import java.lang.reflect.Array;

public class MobileDevice {
    //每个设备有一个产生任务的对象
    private IdelActiveGeneratorModel taskGenerator;

    //设备自身状态信息
    private int x_pos;
    private int y_pos;
    private int power;//发射功率

    //任务队列 假设都是三种
    public Task[] queue1;
    public Task[] queue2;
    public Task[] queue3;

    private int[] quota;



    public void MobileDevice(int _x, int _y, int[] _quota) {
        //初始化参数
        this.x_pos = _x;
        this.y_pos = _y;
        this.quota = _quota;

        //每个设备有一个产生任务的对象
        taskGenerator = new IdelActiveGeneratorModel();
    }
}
