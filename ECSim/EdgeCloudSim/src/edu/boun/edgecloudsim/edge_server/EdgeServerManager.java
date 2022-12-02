/*
* EdgeServerManager可以获得全局EdgeServer的信息
* 每个EdgeServer有一个DataCenter
* DataCenter用来操作Host和VM
*/
package edu.boun.edgecloudsim.edge_server;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public abstract class EdgeServerManager {
    protected List<EdgeDataCenter> localDatacenters;
    protected List<List<EdgeVM>> vmList;

    public EdgeServerManager() {
        this.localDatacenters=new ArrayList<EdgeDataCenter>();
        this.vmList = new ArrayList<List<EdgeVM>>();
    }

    //根据HostID在列表中找到对应位置的VMList
    public List<EdgeVM> getVmList(int hostId){
        return vmList.get(hostId);
    }

    public List<EdgeDataCenter> getDatacenterList(){
        return localDatacenters;
    }

    public abstract void initialize();

    public abstract void startDatacenters() throws Exception;

    public abstract void terminateDatacenters();

    public abstract List<EdgeHost> createAllHosts(Element datacenterElement);

    public abstract void createVmListForAllHosts(int hostId);


}
