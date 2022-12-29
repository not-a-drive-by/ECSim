/*
* 各种实体类的集合*/

package edu.boun.edgecloudsim.core;

import edu.boun.edgecloudsim.edge_client.MobileDeviceManager;
import edu.boun.edgecloudsim.edge_orchestrator.EdgeOrchestrator;
import edu.boun.edgecloudsim.edge_server.EdgeServerManager;

public class SimManager {
    private String simScenario;
    private ScenarioFactory scenarioFactory;
    private EdgeOrchestrator edgeOrchestrator;
    private EdgeServerManager edgeServerManager;
    private MobileDeviceManager mobileDeviceManager;

    private static SimManager instance = null;
    public static SimManager getInstance(){
        return instance;
    }

    public SimManager(ScenarioFactory _scenarioFactory, int _numOfMobileDevice, String _simScenario, String _orchestratorPolicy) throws Exception {
        simScenario = _simScenario;
        scenarioFactory = _scenarioFactory;

        //创建服务器
        System.out.println("Init Edge Servers" + "\r\n");
        edgeServerManager = scenarioFactory.getEdgeServerManager();
        edgeServerManager.init();
        System.out.println(edgeServerManager.edgeServersList);

        //创建移动设备
        System.out.println("Init Mobile Devices" + "\r\n");
        mobileDeviceManager = scenarioFactory.getMobileDeviceManager();
        mobileDeviceManager.initMobileDevice();
        System.out.println(mobileDeviceManager.mobileDevicesList);
    }

    public void updateQueues(int t){
        //1.更新移动设备的待处理队列
        mobileDeviceManager.updateUnprocessedQueues(t);
        System.out.println("在时刻"+ t + "更新后:" + "\r\n");
        System.out.println(mobileDeviceManager.mobileDevicesList);

    }

    public void shutdownEntity(){
        edgeServerManager.terminateDatacenters();
        mobileDeviceManager.terminateDatacenters();
    }
}
