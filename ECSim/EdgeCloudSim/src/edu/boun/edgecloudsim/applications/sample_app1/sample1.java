package edu.boun.edgecloudsim.applications.sample_app1;

import edu.boun.edgecloudsim.core.SimSettings;
import edu.boun.edgecloudsim.edge_client.MobileDeviceManager;

public class sample1 {
    public static void main(String[] args) throws Exception {
        //在这里开始仿真
        SimSettings SS = SimSettings.getInstance();
        SS.init("ECSim/EdgeCloudSim/scripts/sample_app1/config/mobile_devices.xml");
        MobileDeviceManager mobileDeviceManager = new MobileDeviceManager();//实体类应该放到simmanager
        mobileDeviceManager.initMobileDevice();
        System.out.println(mobileDeviceManager.mobileDevicesList);

    }
}
