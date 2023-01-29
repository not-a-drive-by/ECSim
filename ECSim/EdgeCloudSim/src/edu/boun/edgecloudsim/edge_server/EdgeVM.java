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

    private double onTime;//开始工作的时间
    private double offTime;//关闭时间

    public EdgeVM(Task task, double time){
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

    public double getOnTime() {   return onTime;    }

    public double getOffTime() {    return offTime;    }

    public Task getProcessingTask(){  return processingTask;  }

    @Override
    public String toString() {
        return "EdgeVM{" +
                "processingTask=" + processingTask +
                ", offTime=" + offTime +
                '}';
    }
}
