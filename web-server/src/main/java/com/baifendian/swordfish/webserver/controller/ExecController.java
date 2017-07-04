/*
 * Copyright (C) 2017 Baifendian Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baifendian.swordfish.webserver.controller;

import com.baifendian.swordfish.dao.enums.ExecType;
import com.baifendian.swordfish.dao.enums.NodeDepType;
import com.baifendian.swordfish.dao.enums.NotifyType;
import com.baifendian.swordfish.dao.model.User;
import com.baifendian.swordfish.webserver.dto.*;
import com.baifendian.swordfish.webserver.exception.BadRequestException;
import com.baifendian.swordfish.webserver.service.ExecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 执行任务的服务入口
 */
@RestController
@RequestMapping("/executors")
public class ExecController {

  private static Logger logger = LoggerFactory.getLogger(ExecController.class.getName());

  @Autowired
  private ExecService execService;

  /**
   * 执行一个已经创建的工作流
   *
   * @param operator
   * @param projectName
   * @param workflowName
   * @param schedule
   * @param nodeName
   * @param nodeDep
   * @param notifyType
   * @param notifyMails
   * @param timeout
   * @return
   */
  @PostMapping(value = "")
  @ResponseStatus(HttpStatus.CREATED)
  public ExecutorIdsDto execExistWorkflow(@RequestAttribute(value = "session.user") User operator,
                                          @RequestParam(value = "projectName") String projectName,
                                          @RequestParam(value = "workflowName") String workflowName,
                                          @RequestParam(value = "schedule", required = false) String schedule,
                                          @RequestParam(value = "execType", required = false) ExecType execType,
                                          @RequestParam(value = "nodeName", required = false) String nodeName,
                                          @RequestParam(value = "nodeDep", required = false) NodeDepType nodeDep,
                                          @RequestParam(value = "notifyType", required = false) NotifyType notifyType,
                                          @RequestParam(value = "notifyMails", required = false) String notifyMails,
                                          @RequestParam(value = "timeout", required = false, defaultValue = "43200") int timeout) {
    logger.info("Operator user {}, exec workflow, project name: {}, workflow name: {}, schedule: {}, node name: {}, node dep: {}, notify type: {}, " +
                    "notify mails: {}, timeout: {}",
            operator.getName(), projectName, workflowName, schedule, nodeName, nodeDep, notifyType, notifyMails, timeout);

    // timeout 的限制
    if (timeout <= 0 || timeout > 43200) {
      throw new BadRequestException("Argument is not valid, timeout must be between (0, 43200]");
    }

    return execService.postExecWorkflow(operator, projectName, workflowName, schedule, execType, nodeName, nodeDep, notifyType, notifyMails, timeout);
  }

  /**
   * 直接执行一个工作流, 必须是不存在的
   *
   * @param operator
   * @param projectName
   * @param workflowName
   * @param proxyUser
   * @param queue
   * @param desc
   * @param data
   * @param file
   * @param notifyType
   * @param notifyMails
   * @param timeout
   * @param extras
   * @return
   */
  @PostMapping(value = "/direct")
  public ExecutorIdDto execWorkflowDirect(@RequestAttribute(value = "session.user") User operator,
                                          @RequestParam(value = "projectName") String projectName,
                                          @RequestParam(value = "workflowName") String workflowName,
                                          @RequestParam(value = "proxyUser") String proxyUser,
                                          @RequestParam(value = "queue") String queue,
                                          @RequestParam(value = "desc", required = false) String desc,
                                          @RequestParam(value = "data", required = false) String data,
                                          @RequestParam(value = "file", required = false) MultipartFile file,
                                          @RequestParam(value = "notifyType", required = false) NotifyType notifyType,
                                          @RequestParam(value = "notifyMails", required = false) String notifyMails,
                                          @RequestParam(value = "timeout", required = false, defaultValue = "43200") int timeout,
                                          @RequestParam(value = "extras", required = false) String extras) {
    logger.info("Operator user {}, exec workflow, project name: {}, workflow name: {}, proxy user: {}, queue: {}, data: {}, file: {}," +
            "notify type: {}, notify mails: {}, timeout: {}, extras: {}", operator.getName(), projectName, workflowName, proxyUser, queue, data, file.getName(), notifyType, notifyMails, timeout, extras);

    // timeout 的限制
    if (timeout <= 0 || timeout > 43200) {
      throw new BadRequestException("Argument is not valid, timeout must be between (0, 43200]");
    }

    return execService.postExecWorkflowDirect(operator, projectName, workflowName, desc, proxyUser, queue, data, file, notifyType, notifyMails, timeout, extras);
  }

  /**
   * 查询工作流列表信息
   *
   * @param operator
   * @param startDate
   * @param endDate
   * @param projectName
   * @param workflowName
   * @param status
   * @param from
   * @param size
   * @return
   */
  @GetMapping(value = "")
  public ExecWorkflowsDto queryExecs(@RequestAttribute(value = "session.user") User operator,
                                     @RequestParam(value = "startDate", required = false) Long startDate,
                                     @RequestParam(value = "endDate", required = false) Long endDate,
                                     @RequestParam(value = "projectName") String projectName,
                                     @RequestParam(value = "workflowName", required = false) String workflowName,
                                     @RequestParam(value = "status", required = false) String status,
                                     @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                     @RequestParam(value = "size", required = false, defaultValue = "100") int size) {
    logger.info("Operator user {}, query exec list, start date: {}, end date: {}, project name: {}, workflow name: {}, status: {}, from: {}, size: {}",
            operator.getName(), startDate, endDate, projectName, workflowName, status, from, size);

    // from 的限制
    if (from < 0) {
      throw new BadRequestException("Argument is not valid, from must be equal or more than zero");
    }

    // size 的限制
    if (size <= 0 || size > 1000) {
      throw new BadRequestException("Argument is not valid, size must be between (0, 1000]");
    }

    Date startTime = null;
    if (startDate != null) {
      startTime = new Date(startDate);
    }

    Date endTime = null;
    if (endDate != null) {
      endTime = new Date(endDate);
    }

    return execService.getExecWorkflow(operator, projectName, workflowName, startTime, endTime, status, from, size);
  }

  /**
   * 查询工作流的执行详情
   *
   * @param operator
   * @param execId
   * @return
   */
  @GetMapping(value = "/{execId}")
  public ExecutionFlowDto queryExecDetail(@RequestAttribute(value = "session.user") User operator,
                                          @PathVariable(value = "execId") int execId) {
    logger.info("Operator user {}, query exec detail, exec id: {}",
            operator.getName(), execId);

    return execService.getExecWorkflow(operator, execId);
  }

  /**
   * 查询日志
   *
   * @param operator
   * @param jobId
   * @param from
   * @param size
   * @return
   */
  @GetMapping(value = "/{jobId}/logs")
  public LogResult queryLogs(@RequestAttribute(value = "session.user") User operator,
                             @PathVariable(value = "jobId") String jobId,
                             @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                             @RequestParam(value = "size", required = false, defaultValue = "100") int size) {
    logger.info("Operator user {}, query log, job id: {}, from: {}, size: {}",
            operator.getName(), jobId, from, size);

    // from 的限制
    if (from < 0) {
      throw new BadRequestException("Argument is not valid, from must be equal or more than zero");
    }

    // size 的限制
    if (size <= 0 || size > 1000) {
      throw new BadRequestException("Argument is not valid, size must be between (0, 1000]");
    }

    return execService.getEexcWorkflowLog(operator, jobId, from, size);
  }

  /**
   * kill 某个运行的任务
   *
   * @param operator
   * @param execId
   */
  @PostMapping(value = "/{execId}/kill")
  public void killExec(@RequestAttribute(value = "session.user") User operator,
                       @PathVariable int execId) {
    logger.info("Operator user {}, kill exec, exec id: {}",
            operator.getName(), execId);

    execService.postKillWorkflow(operator, execId);
  }
}
