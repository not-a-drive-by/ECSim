/*
* 全局一个MobileDeviceManager
* MobileDevice注册中心 得到所有的mobile device实体
* 负责绑定Task、提交任务
* */

package edu.boun.edgecloudsim.edge_client;

import edu.boun.edgecloudsim.core.SimSettings;
import edu.boun.edgecloudsim.task_generator.DeviceTaskStatic;
import edu.boun.edgecloudsim.task_generator.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class MobileDeviceManager {

    public List<MobileDevice> mobileDevicesList = new ArrayList<MobileDevice>();
    public SimSettings SS = SimSettings.getInstance();
    private ArrayList<List<Task>> taskList;

    //初始化移动设备
    public void initMobileDevice() throws Exception {
        int MobileDeviceNum = SS.MobileDeviceNum;
        //读出任务
        List<Task> t = null;
        FileInputStream fi=new FileInputStream("TaskInformation.txt");
        ObjectInputStream si=new ObjectInputStream(fi);
        taskList = new ArrayList<List<Task>>();
        try
        {
            taskList.clear();
            for(int i=0;i<MobileDeviceNum;i++)
            {
                t=(List<Task>)si.readObject();
                taskList.add(t);
            }
            si.close();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        //创建移动设备 并添加任务
        ArrayList<DeviceTaskStatic> mobileDeviceStatics = SS.getMobileDeviceStatic();
        for (int i = 0; i < mobileDeviceStatics.size(); i++) {
            DeviceTaskStatic mobileDeviceStatic = mobileDeviceStatics.get(i);
            List<Task> tasks = taskList.get(i);
            mobileDevicesList.add(createMobileDevice(mobileDeviceStatic,tasks));
        }

    }

    private MobileDevice createMobileDevice(DeviceTaskStatic mobileDeviceStatic, List<Task> tasks){
        return new MobileDevice(mobileDeviceStatic.x_pos, mobileDeviceStatic.y_pos,
                mobileDeviceStatic.deviceID, tasks);
    }

    //更新待处理任务
    public void updateUnprocessedQueues(int t){
        for( MobileDevice mobileDevice : mobileDevicesList ){
            mobileDevice.updateDeviceQueue(t);
        }
    }

    //更新待卸载任务
    public void updateUntransQueues(int t){
        for( MobileDevice mobileDevice : mobileDevicesList ){
            mobileDevice.updateTransQueue(t);
        }
    }

    public void terminateDatacenters() {
        //local computation is not supported in default Mobile Device Manager
    }



}
