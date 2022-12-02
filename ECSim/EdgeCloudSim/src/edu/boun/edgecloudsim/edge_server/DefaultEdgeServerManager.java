package edu.boun.edgecloudsim.edge_server;

import edu.boun.edgecloudsim.core.SimSettings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class DefaultEdgeServerManager extends EdgeServerManager{
    @Override
    public void initialize() {

    }

    @Override
    //对所有的Host创建虚拟机 但我们的虚拟机不是从配置文件中创建的
    public void createVmListForAllHosts(int hostId) {

    }

    @Override
    //根据xml文件创建所有的Host
    public List<EdgeHost> createAllHosts(Element datacenterElement){
        return new ArrayList<EdgeHost>();
    }


    //创建单个datacenter
    public EdgeDataCenter createDatacenter(int id, Element datacenterElement){
        //datacenterElement可以提供其他相关的构造参数

        List<EdgeHost> hostList=createAllHosts(datacenterElement);
        for(EdgeHost edgeHost:hostList){
            createVmListForAllHosts(edgeHost.getId());
        }
        return new EdgeDataCenter(id);
    }

    //创建所有datacenters时调用创建VM、Host、单个datacenter
    @Override
    //创建xml文件中所有的Datacenters
    public void startDatacenters() throws Exception {
        Document doc = SimSettings.getInstance().getEdgeDevicesDocument();
        NodeList datacenterList = doc.getElementsByTagName("datacenter");
        for (int i = 0; i < datacenterList.getLength(); i++) {
            Node datacenterNode = datacenterList.item(i);
            Element datacenterElement = (Element) datacenterNode;
            localDatacenters.add(createDatacenter(i, datacenterElement));
        }
    }



    @Override
    //关闭localDatacenters中所有的Datacenter
    public void terminateDatacenters() {
        for(EdgeDataCenter dataCenter: localDatacenters){
            dataCenter.shutdownEntity();
        }
    }


}
