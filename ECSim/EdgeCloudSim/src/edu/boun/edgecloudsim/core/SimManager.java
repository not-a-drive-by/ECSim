/*
* 各种实体类的集合*/

package edu.boun.edgecloudsim.core;

import edu.boun.edgecloudsim.edge_client.MobileDeviceManager;
import edu.boun.edgecloudsim.edge_orchestrator.DefaultEdgeOrchestrator;
import edu.boun.edgecloudsim.edge_orchestrator.EdgeOrchestrator;
import edu.boun.edgecloudsim.edge_server.EdgeServerManager;
import edu.boun.edgecloudsim.mobility.MobilityModel;
import edu.boun.edgecloudsim.network.Channel;
import edu.boun.edgecloudsim.network.NetworkModel;
import edu.boun.edgecloudsim.utils.StaticfinalTags;

public class SimManager {
    private String simScenario;
    private String orchestratorPolicy;
    private ScenarioFactory scenarioFactory;
    private EdgeOrchestrator edgeOrchestrator;
    private EdgeServerManager edgeServerManager;
    private MobileDeviceManager mobileDeviceManager;
    private NetworkModel networkModel;
    private MobilityModel mobilityModel;

    private static SimManager instance = null;
    public static SimManager getInstance(){
        return instance;
    }

    public SimManager(ScenarioFactory _scenarioFactory, int _numOfMobileDevice, String _simScenario, String _orchestratorPolicy) throws Exception {
        simScenario = _simScenario;
        scenarioFactory = _scenarioFactory;
        orchestratorPolicy = _orchestratorPolicy;

        //创建服务器
        System.out.println("\r\n" + "Init Edge Servers" );
        edgeServerManager = scenarioFactory.getEdgeServerManager();
        edgeServerManager.init();
        System.out.println(edgeServerManager.getEdgeServersList());

        //创建移动设备
        System.out.println("\r\n" + "Init Mobile Devices" );
        mobileDeviceManager = scenarioFactory.getMobileDeviceManager();
        mobileDeviceManager.initMobileDevice();
        System.out.println(mobileDeviceManager.getMobileDevicesList());

        //产生信道信息
        System.out.println("\r\n" + "Init Network Model" );
        networkModel = scenarioFactory.getNetworkModel();
        networkModel.init(mobileDeviceManager.getMobileDevicesList(), edgeServerManager.getEdgeServersList());
        mobileDeviceManager.bindTaskNetworkModel(networkModel);
        System.out.println( networkModel.getChannelsList() );

        //产生编排器
        System.out.println("\r\n" + "Init Matching Edge Orchestrator" );
        edgeOrchestrator = scenarioFactory.getEdgeOrchestrator(edgeServerManager);

        //产生位移器
        mobilityModel = scenarioFactory.getMobilityModel(mobileDeviceManager);

    }

    public void updateQueues(int t, ScenarioFactory scenarioFactory){
        //1.更新移动设备的待处理队列
        mobileDeviceManager.updateUnprocessedQueues(t);
//        System.out.println("在时刻"+ t + "更新队列后:" + "\r\n");
//        System.out.println(mobileDeviceManager.getMobileDevicesList());

    }

    public void updateChannel(){
        //每个时隙信道重置为未使用过
        for(Channel channel : networkModel.getChannelsList()){
            if(StaticfinalTags.curTime % StaticfinalTags.ratioItv == 0){
                channel.updateRatio();
            }
            channel.usedFlag = false;
        }
    }

    public void generateQuota(){
        edgeOrchestrator.clearPrematchTasks();//清除编排器待匹配队列
        if(orchestratorPolicy.equals("Matching")){
            mobileDeviceManager.updateQuotas(networkModel, edgeOrchestrator);//产生quota并将对应任务添加到编排器
            edgeServerManager.updateServerQuota();
        }else {
            mobileDeviceManager.updateAll(edgeServerManager, edgeOrchestrator);
        }
//        System.out.println("待匹配任务集合"+edgeOrchestrator.getPreMatchTasks());
    }


    public void transmitteTasks(){
        //更新移动设备待传输队列
        if(orchestratorPolicy.equals("Matching")){
            mobileDeviceManager.updateTransQueue_Match(networkModel);
        }else {
            mobileDeviceManager.updateTransQueue(networkModel);
        }

//        System.out.println("更新传输队列后"+mobileDeviceManager.getMobileDevicesList());

    }

    public void processTask(double time){
        if(orchestratorPolicy.equals("Matching")){
            edgeServerManager.processTasks_Lyap(time);
        }else if(orchestratorPolicy.equals("Random")){
            edgeServerManager.processTasks_FCFS(time);
        }else if(orchestratorPolicy.equals("MILP")){
            edgeServerManager.processTasks_MILP(time);
        }else if(orchestratorPolicy.equals("SUAC")){
            edgeServerManager.processTasks_FCFS(time);
        }
//        System.out.println("节点内资源调度后"+edgeServerManager.getEdgeServersList());
    }

    public void shutdownEntity(){
        edgeServerManager.terminateDatacenters();
        mobileDeviceManager.terminateDatacenters();
    }

    //无聊函数
    public EdgeServerManager getEdgeServerManager() {   return edgeServerManager;    }
    public MobileDeviceManager getMobileDeviceManager() {   return mobileDeviceManager;   }
    public EdgeOrchestrator getEdgeOrchestrator() {    return edgeOrchestrator;    }
    public MobilityModel getMobilityModel() {  return mobilityModel;  }
    public NetworkModel getNetworkModel(){ return networkModel; }
}
