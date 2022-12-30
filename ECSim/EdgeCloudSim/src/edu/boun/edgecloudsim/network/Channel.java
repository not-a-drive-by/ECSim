package edu.boun.edgecloudsim.network;

import edu.boun.edgecloudsim.edge_client.MobileDevice;
import edu.boun.edgecloudsim.edge_server.EdgeDataCenter;

public class Channel {
    private MobileDevice mobileDevice;
    private EdgeDataCenter edgeServer;

    public double ratio;
    public boolean usedFlag;

    public Channel( MobileDevice mobileDevice, EdgeDataCenter edgeServer){
        this.mobileDevice = mobileDevice;
        this.edgeServer = edgeServer;

        this.ratio = updateRatio();
        this.usedFlag = false;
    }

    private double getDistance(){
        double dx = mobileDevice.getX()-edgeServer.getX();
        double dy = mobileDevice.getY()-edgeServer.getY();
        return Math.sqrt( Math.pow(dx,2) + Math.pow(dy,2) );
    }

    //更新信道传输速率
    private double updateRatio(){
        double dis = getDistance();
        double SINR = Math.pow(dis,-3)*mobileDevice.getPower()/(10+15);
        return 30*Math.log(1+SINR)/Math.log(2);
    }

    //一些没啥用的函数
    public MobileDevice getMobileDevice() {    return mobileDevice;    }
    public EdgeDataCenter getEdgeServer() {      return edgeServer;    }

    @Override
    public String toString() {
        return "Channel{" +
                "mobileDevice=" + mobileDevice.getMobileID() +
                ", edgeServer=" + edgeServer.getId() +
                ", ratio=" + ratio +
                ", usedFlag=" + usedFlag +
                '}' + "\r\n";
    }
}
