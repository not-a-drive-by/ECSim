/*
* 全局一个MobileDeviceManager
* MobileDevice注册中心 得到所有的mobile device实体
* 负责绑定Task、提交任务
* */

package edu.boun.edgecloudsim.edge_client;

import edu.boun.edgecloudsim.core.SimSettings;
import edu.boun.edgecloudsim.task_generator.DeviceTaskStatic;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class MobileDeviceManager {

    public List<MobileDevice> mobileDevicesList = new ArrayList<MobileDevice>();

    public void initMobileDevice() throws Exception {
        ArrayList<DeviceTaskStatic> mobileDeviceStatics= SimSettings.getInstance().getMobileDeviceStatic();
        for (int i = 0; i < mobileDeviceStatics.size(); i++) {
            DeviceTaskStatic mobileDeviceStatic = mobileDeviceStatics.get(i);
            mobileDevicesList.add(createMobileDevice(i, mobileDeviceStatic));
        }
    }

    private MobileDevice createMobileDevice(int mobileID, DeviceTaskStatic mobileDeviceStatic){
        return new MobileDevice(mobileDeviceStatic.x_pos, mobileDeviceStatic.y_pos, mobileID);
    }



}
