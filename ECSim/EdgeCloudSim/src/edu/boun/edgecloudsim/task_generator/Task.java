/*
* Task任务类
*/

package edu.boun.edgecloudsim.task_generator;


//public class Task extends Cloudlet {

public class Task {
   //自身属性
   private int length;
   private int memo;
   private int CPU;
   private int storage;

   //由LoadGenerator设置
   private int taskID;
   private int deviceID;
   private int arrivalTime;

   //处理完后设置
   private int finishTime=-1;//若为-1则未完成

   public Task(int length, int memo, int CPU, int storage, int taskID) {
      this.length = length;
      this.memo = memo;
      this.CPU = CPU;
      this.storage = storage;
      this.taskID = taskID;
   }

   public int getLength() {      return length;   }

   public void setLength(int length) {      this.length = length;   }

   public int getMemo() {      return memo;   }

   public void setMemo(int memo) {      this.memo = memo;   }

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
}
