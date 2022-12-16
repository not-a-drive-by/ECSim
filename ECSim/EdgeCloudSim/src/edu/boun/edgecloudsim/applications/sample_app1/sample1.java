package edu.boun.edgecloudsim.applications.sample_app1;

import edu.boun.edgecloudsim.core.SimSettings;

public class sample1 {
    public static void main(String[] args) {
        //在这里开始仿真
        SimSettings SS = SimSettings.getInstance();
        SS.parseEdgeDevicesXML("ECSim/EdgeCloudSim/scripts/sample_app1/config/edge_devices.xml");
        System.out.println(SS.EdgeDeviceNum);

    }
}
