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
    }
}
