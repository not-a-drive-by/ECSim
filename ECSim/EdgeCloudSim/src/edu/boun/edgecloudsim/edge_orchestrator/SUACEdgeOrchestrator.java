package edu.boun.edgecloudsim.edge_orchestrator;

import edu.boun.edgecloudsim.edge_server.EdgeDataCenter;
import edu.boun.edgecloudsim.edge_server.EdgeServerManager;
import edu.boun.edgecloudsim.network.Channel;
import edu.boun.edgecloudsim.network.NetworkModel;
import edu.boun.edgecloudsim.task_generator.Task;
import ilog.concert.IloException;

import java.util.ArrayList;
import java.util.List;
import ilog.concert.*;
import ilog.cplex.IloCplex;

public class SUACEdgeOrchestrator extends EdgeOrchestrator{

    public SUACEdgeOrchestrator( EdgeServerManager edgeServerManager ){
        this.EdgeServers = edgeServerManager.getEdgeServersList();
    }

    @Override
    public void Matching(NetworkModel networkModel){
        int m = preMatchTasks.size();
        int c = EdgeServers.size();

        if( m != 0 && c!= 0) {
            try {
                System.out.print("进来了"+m+" "+c);
                /** 声明cplex优化模型 */
                IloCplex cplex = new IloCplex();

                /** 设定变量及上下限 */
                IloIntVar[][] x = new IloIntVar[m][];
                for (int i = 0; i < m; i++) {
                    x[i] = cplex.intVarArray(c, 0, 1);
                }

                /** 设定目标函数 */
                IloNumExpr cs1 = cplex.numExpr(); //最小化Lyapunov函数
                IloNumExpr cs_as; //每个服务器所有抉择变量之和

                for (int i = 0; i < c; i++) { //server
                    cs_as = cplex.numExpr();
                    for (int j = 0; j < m; j++) { //task
                        cs_as = cplex.sum(cs_as, x[j][i]);
                    }
                    EdgeDataCenter server = EdgeServers.get(i);

                    double K = 7*20/2/server.N_max; //session length不知道咋定
                    double Ms =server.activeVM.size() - server.N_max + server.getQueueLength();
                    double ps = server.getQueueLength() + 0.3*( K*( 2*Ms + 1) - 300);

                    cs1 = cplex.sum( cs1, cplex.prod(cs_as, ps));
                    cs_as = cplex.prod(cs_as, cs_as);
                    cs1 = cplex.sum( cs1, cplex.prod(cs_as, 0.3*K) );

                }

                cplex.addMinimize(cs1);

                /** 设定限制条件 */
                //限制任务必须卸载至一个服务器
                IloNumExpr cs3 ;
                for (int i = 0; i < m; i++) { //task
                    cs3 = cplex.numExpr();
                    for (int j = 0; j < c; j++) { //server

                        cs3 = cplex.sum(cs3, x[i][j]);
                    }
                    cplex.addEq(cs3, 1);
                }

                /** 模型求解 */
                double[][] val = new double[m][c];
                if (cplex.solve()) {
                    for (int i = 0; i < m; i++) {
                        val[i] = cplex.getValues(x[i]);
                    }
                }

                /** 退出优化模型 */
                cplex.end();

                /** 根据结果处理 */
                for (int i = 0; i < m; i++) { //task
                    for (int j = 0; j < c; j++) { //server
                        System.out.print("x" +(i+1)+ (j+1) + "  = " + val[i][j]+"  ");
                        if (val[i][j] == 1.0) {
                            preMatchTasks.get(i).setTargetServer(EdgeServers.get(j));
                        }
                    }
                    System.out.println(" ");
                }


            } catch (IloException e) {
                System.err.println("Concert exception caught: " + e);
            }
        }
    }
}
