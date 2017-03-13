/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.baifendian.swordfish.webserver.service.master;

import com.baifendian.swordfish.dao.FlowDao;
import com.baifendian.swordfish.dao.mysql.model.ExecutionFlow;
import com.baifendian.swordfish.webserver.ExecutorServerInfo;
import com.baifendian.swordfish.webserver.ExecutorServerManager;
import parquet.org.slf4j.Logger;
import parquet.org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * executor server 容错处理服务线程
 * @author : liujin
 * @date : 2017-03-13 10:04
 */
public class ExecutorCheckThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(ExecutorCheckThread.class);

    private ExecutorServerManager executorServerManager;

    private int timeoutInterval;

    private FlowDao flowDao;

    private BlockingQueue<ExecutionFlow> executionFlowBlockingQueue;

    public ExecutorCheckThread(ExecutorServerManager executorServerManager, int timeoutInterval,
                               BlockingQueue<ExecutionFlow> executionFlowBlockingQueue, FlowDao flowDao){
        this.executorServerManager = executorServerManager;
        this.timeoutInterval = timeoutInterval;
        this.executionFlowBlockingQueue = executionFlowBlockingQueue;
        this.flowDao = flowDao;
    }

    @Override
    public void run(){
        logger.debug("blocking queue size:{}, {}", executionFlowBlockingQueue.size(),executionFlowBlockingQueue);
        List<ExecutorServerInfo> faultServers = executorServerManager.checkTimeoutServer(timeoutInterval);
        if(faultServers != null){
            for(ExecutorServerInfo executorServerInfo: faultServers){
                if (executorServerInfo.getHeartBeatData() != null && executorServerInfo.getHeartBeatData().getExecIdsSize() > 0) {
                    for(Long execId: executorServerInfo.getHeartBeatData().getExecIds()){
                        ExecutionFlow executionFlow = flowDao.queryExecutionFlow(execId);
                        if(executionFlow != null){
                            if(!executionFlow.getStatus().typeIsFinished()) {
                                logger.info("executor server fault reschedule workflow execId:{}", execId);
                                executionFlowBlockingQueue.add(executionFlow);
                            }
                        } else {
                            logger.warn("executor server fault reschedule workflow execId:{} not exists", execId);
                        }
                    }
                }
            }
        }
    }
}
