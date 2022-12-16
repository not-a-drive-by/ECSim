/*
* 一个DataCenter里的任务调度
* 节点内李雅普诺夫
**/

package edu.boun.edgecloudsim.edge_server;

import edu.boun.edgecloudsim.task_generator.Task;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class EdgeDataCenter {

    private int id;
    private List<EdgeHost> localHosts;
    private List<EdgeVM> localVM;
    private List<List<Task>> queue;
    private int[] pref;

    public EdgeDataCenter(int _id){
        this.id = _id;
        this.localVM = new ArrayList<EdgeVM>();
        //localHosts要根据Element datacenterElement产生
        //queue要根据xml确定支持的虚拟机种类
    }

    //接收移动设备提交的Task，放入queue并排序
    public void preProcessTask(List<Task> receiveTask){

    }

    //根据系统剩余资源产生可行虚拟机矩阵
    public int[][] createMatrix(){
        int[][] vmConfig = {{1,2,1},{1,0,2}};
        return vmConfig;
    }

    //根据李亚算法在矩阵中选择
    public int[] selectVM(int[][] matric){
        int[] select = {1,2,1};
        return select;
    }

    //根据选择虚拟机组合创建虚拟机
    public void createVMs(int[] selectedVM){

    }

    //关闭所有任务已经处理完的VM
    public void terminateVMS(){

    }

    public void processTask(List<Task> receiveTask){
        preProcessTask(receiveTask);
        int[][] feasableMatrix = createMatrix();
        int[] selectedVM = selectVM(feasableMatrix);
        createVMs(selectedVM);

    }

    //更新偏好序列
    public void updatePreference(){

    }

    //关闭所有的Host和VM
    public void shutdownEntity(){

    }
}
