/*
* Task任务类
*/

package edu.boun.edgecloudsim.task_generator;


//public class Task extends Cloudlet {

import edu.boun.edgecloudsim.edge_server.EdgeDataCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
   private List<EdgeDataCenter> preferenceList = new ArrayList<EdgeDataCenter>();
   //目标服务器ID
   private EdgeDataCenter targetServer = null;
   //比较器
   //private ServerPreferenceComparator serverPreferenceComparator = new ServerPreferenceComparator();

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

   public void sortPreferenceList(){
      Collections.sort( preferenceList, new ServerPreferenceComparator() );
   }

   public class ServerPreferenceComparator implements Comparator<EdgeDataCenter>
   {
      public int compare(EdgeDataCenter s1, EdgeDataCenter s2)
      {
         return (s1.getId() - s2.getId());
      }
   }




   //无聊函数
   public List<EdgeDataCenter> getPreferenceList() {     return preferenceList;   }
   public void setPreferenceList(List<EdgeDataCenter> preferenceList) {
      //这里注意不能浅拷贝
      this.preferenceList.clear();
      this.preferenceList.addAll(preferenceList);
//      for(EdgeDataCenter edgeDataCenter:preferenceList){
//         this.preferenceList.add(edgeDataCenter);
//      }
   }
   public EdgeDataCenter getTargetServer() {    return targetServer;   }
   public void setTargetServer(EdgeDataCenter targetServer) {    this.targetServer = targetServer;   }
   public double getDataSize() {    return dataSize;  }
   public void setDataSize(double dataSize) {     this.dataSize = dataSize;   }
   public int getArrivalTime() {  return arrivalTime;  }
   public int getLength() {   return length;  }
   public void setFinishTime(int finishTime) {   this.finishTime = finishTime;   }

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
//              ", aim at:" + targetServer+
              '}' + "\r\n";
   }
}
