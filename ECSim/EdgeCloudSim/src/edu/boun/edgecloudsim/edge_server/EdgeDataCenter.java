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

    //自身属性
    public int id;
    public int CPU;
    public int RAM;
    public int storage;
    public double x_pos;
    public double y_pos;

    private List<Task> queue1;
    private List<Task> queue2;
    private List<Task> queue3;
    private int[] pref;

    public EdgeDataCenter(int _id, int _CPU, int _RAM, int _storage, double x, double y){
        this.id = _id;
        this.CPU = _CPU;
        this.RAM = _RAM;
        this.storage = _storage;
        this.x_pos = x;
        this.y_pos = y;
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

    @Override
    public String toString() {
        return "EdgeDataCenter{" +
                "id=" + id +
                ", CPU=" + CPU +
                ", RAM=" + RAM +
                ", storage=" + storage +
                ", x_pos=" + x_pos +
                ", y_pos=" + y_pos +
                '}' + "\r\n";
    }
}
