package edu.boun.edgecloudsim.applications.sample_app1;

import edu.boun.edgecloudsim.core.ScenarioFactory;
import edu.boun.edgecloudsim.core.SimManager;
import edu.boun.edgecloudsim.core.SimSettings;
import edu.boun.edgecloudsim.edge_client.MobileDeviceManager;

public class sample1 {
    public static void main(String[] args) throws Exception {
        //在这里开始仿真

        //读取配置文件
        SimSettings SS = SimSettings.getInstance();
        SS.init("ECSim/EdgeCloudSim/scripts/sample_app1/config/mobile_devices.xml",
                "ECSim/EdgeCloudSim/scripts/sample_app1/config/edge_servers.xml");

        //创建实体类和实体类管理类
        ScenarioFactory scenarioFactory = new SampleScenarioFactory();
        SimManager simManager = new SimManager(scenarioFactory, 3, "xiaxiede","zheshaya");


    }
}
