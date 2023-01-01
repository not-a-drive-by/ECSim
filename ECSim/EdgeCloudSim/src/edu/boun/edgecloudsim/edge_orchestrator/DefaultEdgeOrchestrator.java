package edu.boun.edgecloudsim.edge_orchestrator;

import edu.boun.edgecloudsim.edge_server.EdgeDataCenter;
import edu.boun.edgecloudsim.edge_server.EdgeServerManager;
import edu.boun.edgecloudsim.task_generator.Task;

import java.util.ArrayList;
import java.util.List;

public class DefaultEdgeOrchestrator extends EdgeOrchestrator{
    private List<Task> preMatchTasks = new ArrayList<Task>();
    private List<EdgeDataCenter> EdgeServers;

    public DefaultEdgeOrchestrator(String orchestratorPolicy, String simScenario, EdgeServerManager edgeServerManager){
        this.EdgeServers = edgeServerManager.getEdgeServersList();
    }

    public void clearPrematchTasks(){
        preMatchTasks.clear();
    }

    public void Matching(){
        boolean sendRequestFlag = true;
        boolean rejectRequestFlag = false;
        while( sendRequestFlag==true && rejectRequestFlag==false ){
            sendRequestFlag = false;
            //任务发起请求前先清空所有服务器的偏好序列
            for(EdgeDataCenter edgeDataCenter : EdgeServers){
                edgeDataCenter.getReceiveReqFromTasks().clear();
            }
            //任务发起请求
            for( Task task : preMatchTasks){
                if( task.getPreferenceList().size() != 0 ){
                    sendRequestFlag = sendRequestFlag ? sendRequestFlag : false;
                    EdgeDataCenter edgeServer = task.getPreferenceList().get(0);
                    edgeServer.getReceiveReqFromTasks().add(task);
                    task.setTargetServer(edgeServer);
                    task.getPreferenceList().remove(0);
                }
            }
            //服务器处理请求
            for(EdgeDataCenter edgeServer : EdgeServers){
                rejectRequestFlag = rejectRequestFlag || edgeServer.updatePreference();
            }
        }
    }

    //无聊函数
    public List<Task> getPreMatchTasks() {   return preMatchTasks;   }
    public void setPreMatchTasks(List<Task> preMatchTasks) {    this.preMatchTasks = preMatchTasks;   }
}
