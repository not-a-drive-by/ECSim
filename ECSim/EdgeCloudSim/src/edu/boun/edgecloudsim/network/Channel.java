package edu.boun.edgecloudsim.network;

import edu.boun.edgecloudsim.edge_client.MobileDevice;
import edu.boun.edgecloudsim.edge_server.EdgeDataCenter;
import edu.boun.edgecloudsim.utils.Variable;

public class Channel {
    private MobileDevice mobileDevice;
    private EdgeDataCenter edgeServer;

    public double ratio;
    public boolean usedFlag;

    public Channel( MobileDevice mobileDevice, EdgeDataCenter edgeServer){
        this.mobileDevice = mobileDevice;
        this.edgeServer = edgeServer;

        updateRatio();
        this.usedFlag = false;
    }

    private double getDistance(){
        double dx = mobileDevice.getX()-edgeServer.getX();
        double dy = mobileDevice.getY()-edgeServer.getY();
        return Math.sqrt( Math.pow(dx,2) + Math.pow(dy,2) );
    }

    //更新信道传输速率
    public void updateRatio(){
        double dis = getDistance();
        double channelGain = Variable.rand(1)*Math.pow(dis,-2);
        double SINR = channelGain*mobileDevice.getPower()/Math.pow(10,-9);
        this.ratio = Math.log(1+SINR)/Math.log(2);
    }

    //一些没啥用的函数
    public MobileDevice getMobileDevice() {    return mobileDevice;    }
    public EdgeDataCenter getEdgeServer() {      return edgeServer;    }
    public void setRatio(double newRatio) {  this.ratio = newRatio;  }

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
