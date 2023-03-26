/*
* Task任务类
*/

package edu.boun.edgecloudsim.task_generator;


//public class Task extends Cloudlet {

import edu.boun.edgecloudsim.edge_client.MobileDevice;
import edu.boun.edgecloudsim.edge_server.EdgeDataCenter;
import edu.boun.edgecloudsim.network.Channel;
import edu.boun.edgecloudsim.network.NetworkModel;
import edu.boun.edgecloudsim.utils.StaticfinalTags;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Task implements Serializable {//序列化后才能从文件读出
   //自身属性
   public double length;
   public int RAM;
   public int CPU;
   public int storage;
   public double dataSize;


   //由初始化设置
   public int taskID;
   public MobileDevice device;
   public int arrivalTime;
   public int arriveClTime = -1;
   public double transRatio;

   //处理完后设置
   public double finishTime = -1;//若为-1则未完成

   //偏好列表
   private List<EdgeDataCenter> preferenceList = new ArrayList<EdgeDataCenter>();
   //目标服务器ID
   private EdgeDataCenter targetServer = null;
   //查看信道情况
   private NetworkModel networkModel = null;
   //比较器
   //private ServerPreferenceComparator serverPreferenceComparator = new ServerPreferenceComparator();

   public Task(int length, int RAM, int CPU, int storage) {
      this.length = length;
      this.RAM = RAM;
      this.CPU = CPU;
      this.storage = storage;
   }

   public Task(int length, int RAM, int CPU, int storage, int taskID, double dataSize) {
      this.length = length;
      this.RAM = RAM;
      this.CPU = CPU;
      this.storage = storage;
      this.taskID = taskID;
      this.dataSize = dataSize;
   }

   public int getType(){
      if( RAM==32 && CPU==8 && storage==1690 ){
         return 1;
      }else if( RAM==30 && CPU==4 && storage==420 ){
         return 2;
      }else{
         return 3;
      }

   }

   public void sortPreferenceList(){
      Collections.sort( preferenceList, new ServerPreferenceComparator() );
   }


   //对服务器按偏好排序
   public class ServerPreferenceComparator implements Comparator<EdgeDataCenter>
   {

      public int compare(EdgeDataCenter s1, EdgeDataCenter s2)
      {
         Channel cha1 = networkModel.serachChannelByDeviceandServer(device.getMobileID(), s1.getId());
         Channel cha2 = networkModel.serachChannelByDeviceandServer(device.getMobileID(), s2.getId());

         /**传输的时延*/
         double transQueTime_1 = 0;
         double transQueTime_2 = 0;
         for(Task t : device.transQueue ){
            if( t.arriveClTime!=-1 && t.targetServer==s1){
               transQueTime_1 += t.arriveClTime - StaticfinalTags.curTime;
//               transQueTime_1 += 1;
            }
         }
         for(Task t : device.transQueue ){
            if( t.arriveClTime!=-1 && t.targetServer==s2){
               transQueTime_2 += t.arriveClTime - StaticfinalTags.curTime;
//               transQueTime_2 += 1;
            }
         }
         double transTime_1 = dataSize/cha1.ratio;
         double transTime_2 = dataSize/cha2.ratio;


         /**服务器端的时延*/
         double queuingTime_1 = 0;
         double queuingTime_2 = 0;
         List<Task> correspQue1 = s1.getQueue().get(getType()-1);
         List<Task> correspQue2 = s2.getQueue().get(getType()-1);
         for(Task t : correspQue1){
            queuingTime_1 += t.length;
         }
         for(Task t : correspQue2){
            queuingTime_2 += t.length;
         }

         /**服务器端的资源影响*/

         double score1 = transQueTime_1 + transTime_1 + queuingTime_1;
         double score2 = transQueTime_2 + transTime_2 + queuingTime_2;

         //越小越好
         if(score1 <= score2 ) {
            return -1;
         }else{
            return 0;
         }


//         int[] remainresource1 = s1.returnRemainResource();
//         int[] remainresource2 = s2.returnRemainResource();
//         int resource1 = 0;
//         for(int i=0; i<3; i++){
//            resource1 += remainresource1[i];
//         }
//         int resource2 = 0;
//         for(int i=0; i<3; i++){
//            resource2 += remainresource2[i];
//         }
//         double len1 = s1.getQueue().get(getType()-1).size()==0 ? 0.5 : s1.getQueue().get(getType()-1).size();
//         double len2 = s2.getQueue().get(getType()-1).size()==0 ? 0.5 :s2.getQueue().get(getType()-1).size();
//         double score1 = cha1.ratio*resource1/len1;
//         double score2 = cha2.ratio*resource2/len2;
//
//         if(score1 >= score2 ) {
//            return -1;
//         }else{
//            return 0;
//         }
      }
   }




   //无聊函数
   public List<EdgeDataCenter> getPreferenceList() {     return preferenceList;   }
   public void setPreferenceList(List<EdgeDataCenter> preferenceList) {
      //这里注意不能浅拷贝 不能this.preferenceList=preferenceList
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
   public void setArrivalTime(int time) {  this.arrivalTime = time; }
   public int getArrivalTime() {  return arrivalTime;  }
   public double getLength() {   return length;  }
   public void setFinishTime(double finishTime) {   this.finishTime = finishTime;   }
   public void setDevice( MobileDevice mobileDevice){ this.device = mobileDevice; }
   public void setNetworkModel( NetworkModel networkModel ){ this.networkModel = networkModel; }
   public MobileDevice getMobileDevice(){ return device; }

   @Override
   public String toString() {
      return "Task{" +
              "l=" + length +
              ", daSize=" + dataSize +
              ", arrive:" + arrivalTime +
              ", ClTime:" + arriveClTime +
              ", ratio：" + transRatio +
              ", aim:" + targetServer+
              '}' + "\r\n";
   }
}
