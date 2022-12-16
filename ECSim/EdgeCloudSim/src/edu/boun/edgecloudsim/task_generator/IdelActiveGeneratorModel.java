/*
* 任务要能够在仿真之前先产生并写入文件
* 所以这里的main要能够独立运行 元素很多static
* */

package edu.boun.edgecloudsim.task_generator;

import edu.boun.edgecloudsim.core.SimSettings;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class IdelActiveGeneratorModel extends LoadGeneratorModel{

    //配置文件数据来源
    static SimSettings SS = SimSettings.getInstance();

    //文件路径信息
    private static String edgeDevicesFile = "ECSim/EdgeCloudSim/scripts/sample_app1/config/edge_devices.xml";

    private static List<Task> taskList; //表示是一个关于 Task类的列表

    private static Document edgeDevicesDoc = null;
    private static int EdgeDeviceNum;

    public static void main(String[] args) {
        SS.init(edgeDevicesFile);
        EdgeDeviceNum = SS.EdgeDeviceNum;
        System.out.println(EdgeDeviceNum);
    }

    private static List<Task> produceTask(int tasknum){
        List<Task> tList;
        tList=new ArrayList<Task>();
        return tList;
    }

    //根据到达时间升序排序任务，需要导入包java.util.Comparator
    public static class TaskComparatorByTime implements Comparator<Task>
    {
        public int compare(Task tl1, Task tl2)
        {
            return (tl1.getArrivalTime() - tl2.getArrivalTime());
        }
    }


}
