/*
*
* */

package edu.boun.edgecloudsim.applications.sample_app1;

import edu.boun.edgecloudsim.core.ScenarioFactory;
import edu.boun.edgecloudsim.edge_client.MobileDeviceManager;
import edu.boun.edgecloudsim.edge_orchestrator.DefaultEdgeOrchestrator;
import edu.boun.edgecloudsim.edge_orchestrator.EdgeOrchestrator;
import edu.boun.edgecloudsim.edge_server.EdgeServerManager;
import edu.boun.edgecloudsim.network.NetworkModel;

public class SampleScenarioFactory implements ScenarioFactory {

    private int numOfMobileDevice;
    private double simulationTime;
    private String orchestratorPolicy;
    private String simScenario;

    SampleScenarioFactory(){

    }

    SampleScenarioFactory(int _numOfMobileDevice,
                          double _simulationTime,
                          String _orchestratorPolicy,
                          String _simScenario){
        orchestratorPolicy = _orchestratorPolicy;
        numOfMobileDevice = _numOfMobileDevice;
        simulationTime = _simulationTime;
        simScenario = _simScenario;
    }



    @Override
    public EdgeOrchestrator getEdgeOrchestrator() {
        return new DefaultEdgeOrchestrator(orchestratorPolicy, simScenario);
    }

//    @Override
//    public MobilityModel getMobilityModel() {
//        return new NomadicMobility(numOfMobileDevice,simulationTime);
//    }
//
    @Override
    public NetworkModel getNetworkModel() {
        return new NetworkModel();
    }

    @Override
    public EdgeServerManager getEdgeServerManager() {
        return new EdgeServerManager();
    }

    @Override
    public MobileDeviceManager getMobileDeviceManager() throws Exception {
        return new MobileDeviceManager();
    }


}
