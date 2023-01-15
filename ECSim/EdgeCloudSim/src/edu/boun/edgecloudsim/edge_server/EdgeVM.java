/*
* 边缘服务器虚拟机类
* */
package edu.boun.edgecloudsim.edge_server;


import edu.boun.edgecloudsim.task_generator.Task;

public class EdgeVM {
    private Task processingTask;
    private int CPU;
    private int RAM;
    private int storage;

    private int onTime;//开始工作的时间
    private int offTime;//关闭时间

    public EdgeVM(Task task, int time){
        this.processingTask = task;
        this.CPU = task.CPU;
        this.RAM = task.RAM;
        this.storage = task.storage;
        this.onTime = time;
        this.offTime = time + task.getLength();

    }

    public int getCPU() {   return CPU;   }

    public int getRAM() {    return RAM;   }

    public int getStorage() {   return storage;    }

    public int getOnTime() {   return onTime;    }

    public int getOffTime() {    return offTime;    }

    @Override
    public String toString() {
        return "EdgeVM{" +
                "processingTask=" + processingTask +
                ", offTime=" + offTime +
                '}';
    }
}
