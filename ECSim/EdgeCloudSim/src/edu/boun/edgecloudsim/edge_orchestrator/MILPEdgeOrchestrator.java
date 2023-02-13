package edu.boun.edgecloudsim.edge_orchestrator;

import edu.boun.edgecloudsim.edge_server.EdgeDataCenter;
import edu.boun.edgecloudsim.edge_server.EdgeServerManager;
import edu.boun.edgecloudsim.network.Channel;
import edu.boun.edgecloudsim.network.NetworkModel;
import edu.boun.edgecloudsim.task_generator.Task;

import java.util.List;

public class MILPEdgeOrchestrator extends EdgeOrchestrator{

    public MILPEdgeOrchestrator(EdgeServerManager edgeServerManager){
        this.EdgeServers = edgeServerManager.getEdgeServersList();
    }

    //最小传输时延 即找最大传输速率的信道
    @Override
    public void Matching(NetworkModel networkModel){
        for ( Task task : preMatchTasks ){
//            System.out.println("任务id是"+task.taskID);
            List<EdgeDataCenter> acceptableServers = task.getPreferenceList();
            if( acceptableServers.size()!=0 ){
                double min_dis = Double.POSITIVE_INFINITY;
                for( EdgeDataCenter server : acceptableServers ){
                   Channel cha = networkModel.serachChannelByDeviceandServer(
                           task.getMobileDevice().getMobileID(), server.getId() );
//                    System.out.print("距离"+cha.distance);
                   if( min_dis > cha.distance ){
                       min_dis = cha.distance;
                       task.setTargetServer(server);
                   }
                }
//                System.out.print("最后选择了"+min_dis);

            }
        }
    }


    //无聊函数
    public List<Task> getPreMatchTasks() {   return preMatchTasks;   }
    public void setPreMatchTasks(List<Task> preMatchTasks) {    this.preMatchTasks = preMatchTasks;   }
}
