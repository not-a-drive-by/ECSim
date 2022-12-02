package edu.boun.edgecloudsim.core;

import org.w3c.dom.Document;

public class SimSettings {

    private static SimSettings instance = null;
    private Document edgeDevicesDoc = null;

    public static SimSettings getInstance(){
        if(instance == null) {
            instance = new SimSettings();
        }
        return instance;
    }

    public Document getEdgeDevicesDocument(){
        return edgeDevicesDoc;
    }

    private void parseEdgeDevicesXML(String filePath) {

    }
}
