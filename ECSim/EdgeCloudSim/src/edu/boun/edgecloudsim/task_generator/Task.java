/*
* Task任务类
*/

package edu.boun.edgecloudsim.task_generator;


//public class Task extends Cloudlet {

import java.io.Serializable;

public class Task implements Serializable {//序列化后才能从文件读出
   //自身属性
   private int length;
   private int RAM;
   private int CPU;
   private int storage;

   //由LoadGenerator设置
   private int taskID;
   private int deviceID;
   private int arrivalTime;

   //处理完后设置
   private int finishTime=-1;//若为-1则未完成

   //偏好列表
   public double[] preferenceList;

   public Task(int length, int RAM, int CPU, int storage) {
      this.length = length;
      this.RAM = RAM;
      this.CPU = CPU;
      this.storage = storage;
   }

   public Task(int length, int RAM, int CPU, int storage, int taskID) {
      this.length = length;
      this.RAM = RAM;
      this.CPU = CPU;
      this.storage = storage;
      this.taskID = taskID;
   }

   public int getLength() {      return length;   }

   public void setLength(int length) {      this.length = length;   }

   public int getRAM() {      return RAM;   }

   public void setRAM(int RAM) {      this.RAM = RAM;   }

   public int getCPU() {      return CPU;   }

   public void setCPU(int CPU) {      this.CPU = CPU;   }

   public int getStorage() {      return storage;   }

   public void setStorage(int storage) {      this.storage = storage;   }

   public int getTaskID() {      return taskID;   }

   public void setTaskID(int taskID) {      this.taskID = taskID;   }

   public int getDeviceID() {      return deviceID;   }

   public void setDeviceID(int deviceID) {      this.deviceID = deviceID;   }

   public int getArrivalTime() {      return arrivalTime;   }

   public void setArrivalTime(int arrivalTime) {      this.arrivalTime = arrivalTime;   }

   public int getFinishTime() {      return finishTime;   }

   public void setFinishTime(int finishTime) {      this.finishTime = finishTime;   }

   @Override
   public String toString() {
      return "Task{" +
              "length=" + length +
              ", RAM=" + RAM +
              ", CPU=" + CPU +
              ", storage=" + storage +
              '}';
   }
}
