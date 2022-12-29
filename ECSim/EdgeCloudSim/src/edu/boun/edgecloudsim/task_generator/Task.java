/*
* Task任务类
*/

package edu.boun.edgecloudsim.task_generator;


//public class Task extends Cloudlet {

import java.io.Serializable;

public class Task implements Serializable {//序列化后才能从文件读出
   //自身属性
   public int length;
   public int RAM;
   public int CPU;
   public int storage;
   public double dataSize;

   //由初始化设置
   public int taskID;
   public int deviceID;
   public int arrivalTime;

   //处理完后设置
   public int finishTime=-1;//若为-1则未完成

   //偏好列表
   public double[] preferenceList;
   //目标服务器ID
   public int serverID;

   public Task(int length, int RAM, int CPU, int storage) {
      this.length = length;
      this.RAM = RAM;
      this.CPU = CPU;
      this.storage = storage;
   }

   public Task(int length, int RAM, int CPU, int storage, int taskID, double dataSize, int arrivalTime) {
      this.length = length;
      this.RAM = RAM;
      this.CPU = CPU;
      this.storage = storage;
      this.taskID = taskID;
      this.dataSize = dataSize;
      this.arrivalTime = arrivalTime;
   }

   public int getType(){
      if( RAM==32 && CPU==2 && storage==1690 ){
         return 1;
      }else if( RAM==30 && CPU==2 && storage==420 ){
         return 2;
      }else{
         return 3;
      }

   }

   @Override
   public String toString() {
      return "Task{" +
              "length=" + length +
              ", RAM=" + RAM +
              ", CPU=" + CPU +
              ", storage=" + storage +
              ", taskID=" + taskID +
              ", dataSize=" + dataSize +
              ", arrive at:" + arrivalTime +
              '}' + "\r\n";
   }
}
