/*
* 所有从配置文件读出来的数据都从SimSettings实体的属性里获得
* 这些数据再作为参数传入需要的各个实体类里
* */

package edu.boun.edgecloudsim.core;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class SimSettings {

    private static SimSettings instance = null;
    private Document edgeDevicesDoc = null;

    private String edgeDevicesFile = "ECSim/EdgeCloudSim/scripts/sample_app1/config/edge_devices.xml";

    public int EdgeDeviceNum;



    public static SimSettings getInstance(){
        if(instance == null) {
            instance = new SimSettings();
        }
        return instance;
    }

    public void init(String edgeDevicesFile){
        parseEdgeDevicesXML(edgeDevicesFile);
    }

    public Document getEdgeDevicesDocument(){
        return edgeDevicesDoc;
    }

    public void parseEdgeDevicesXML(String filePath) {
        try {
            File devicesFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            edgeDevicesDoc = dBuilder.parse(devicesFile);
            edgeDevicesDoc.getDocumentElement().normalize();

            NodeList datacenterList = edgeDevicesDoc.getElementsByTagName("datacenter");
            EdgeDeviceNum = datacenterList.getLength();
        } catch (Exception e) {
            System.out.println("Edge Devices XML cannot be parsed! Terminating simulation...");
            e.printStackTrace();
            System.exit(1);
        }

    }
}
