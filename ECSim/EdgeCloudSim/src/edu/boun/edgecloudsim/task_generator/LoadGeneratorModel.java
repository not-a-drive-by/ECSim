/*
* 抽象类：把产生的Task分配到Mobile Device上
* 通过继承该类实现不同的分配方式
*/

package edu.boun.edgecloudsim.task_generator;

import edu.boun.edgecloudsim.utils.StaticfinalTags;

import java.util.List;

public abstract class LoadGeneratorModel {
    private static int TaskNum = StaticfinalTags.CloudletNum;
    private static List<Task> taskList;



}
