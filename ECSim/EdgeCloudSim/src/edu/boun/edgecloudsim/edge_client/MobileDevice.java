package edu.boun.edgecloudsim.edge_client;

import edu.boun.edgecloudsim.task_generator.IdelActiveGeneratorModel;
import edu.boun.edgecloudsim.task_generator.LoadGeneratorModel;

import java.lang.reflect.Array;

public class MobileDevice {
    private int x_pos;
    private int y_pos;

    private int[][] preList;
    private int quota;

    private IdelActiveGeneratorModel taskGenerator;

    public void MobileDevice(int _x, int _y, int _quota) {
        //初始化参数
        this.x_pos = _x;
        this.y_pos = _y;
        this.quota = _quota;

        //每个设备有一个产生任务的对象
        taskGenerator = new IdelActiveGeneratorModel();
    }
}
