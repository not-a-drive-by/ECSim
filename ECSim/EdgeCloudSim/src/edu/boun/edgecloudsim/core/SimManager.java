/*
* 各种实体类的集合*/

package edu.boun.edgecloudsim.core;

import edu.boun.edgecloudsim.edge_client.MobileDeviceManager;
import edu.boun.edgecloudsim.edge_orchestrator.EdgeOrchestrator;
import edu.boun.edgecloudsim.edge_server.EdgeServerManager;
import edu.boun.edgecloudsim.network.NetworkModel;

public class SimManager {
    private String simScenario;
    private ScenarioFactory scenarioFactory;
    private EdgeOrchestrator edgeOrchestrator;
    private EdgeServerManager edgeServerManager;
    private MobileDeviceManager mobileDeviceManager;
    private NetworkModel networkModel;

    private static SimManager instance = null;
    public static SimManager getInstance(){
        return instance;
    }

    public SimManager(ScenarioFactory _scenarioFactory, int _numOfMobileDevice, String _simScenario, String _orchestratorPolicy) throws Exception {
        simScenario = _simScenario;
        scenarioFactory = _scenarioFactory;

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
        System.out.println( networkModel.getChannelsList() );

    }

    public void updateQueues(int t, ScenarioFactory scenarioFactory){
        //1.更新移动设备的待处理队列
        mobileDeviceManager.updateUnprocessedQueues(t);
        System.out.println("在时刻"+ t + "更新后:" + "\r\n");
        System.out.println(mobileDeviceManager.getMobileDevicesList());

        //2.更新移动设备待传输队列
        mobileDeviceManager.updateUntransQueues(networkModel);

    }

    public void shutdownEntity(){
        edgeServerManager.terminateDatacenters();
        mobileDeviceManager.terminateDatacenters();
    }

    //无用函数
    public EdgeServerManager getEdgeServerManager() {   return edgeServerManager;    }
    public MobileDeviceManager getMobileDeviceManager() {   return mobileDeviceManager;   }
}
