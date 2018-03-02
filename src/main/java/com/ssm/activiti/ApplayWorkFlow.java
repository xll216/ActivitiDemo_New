package com.ssm.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by 蓝鸥科技有限公司  www.lanou3g.com.
 */
@Component
public class ApplayWorkFlow {
    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    /**
     * 员工请假申请
     *
     * @param map 包含请假申请的相关信息
     *            其中必须要有的是申请人，审批人，以及请假天数
     **/
    public void employeeApply(Map<String, Object> map) {
        try {
            String applyName = (String) map
                    .get("applyName");//申请人

            String nextApprovalName = (String) map
                    .get("nextApprovalName");//审批人

            //1.启动请求流程
            ProcessInstance instance = runtimeService
                    .startProcessInstanceByKey(
                            "leaveProcess", map);

            //2.处理请求申请的这个环节
            /*2.1 根据流程实例ID获取接下来的请求申请任务，
            即该流程的第一环*/
            String taskId = taskService.createTaskQuery()
                    .processInstanceId(
                            instance.getProcessInstanceId())
                    .singleResult()
                    .getId();

            /*2.2 将申请的流程办理人设置为map中的申请人*/
            taskService.setAssignee(taskId, applyName);

            /*2.3 将填写申请的这个流程完成*/
            taskService.complete(taskId, map);

            //3 当流程流转到项目申请审批时，需要将办理人设置
            //为具体某一个人，即设置为map中的nextApprovalName
            /*3.1 重新查询一下当前流程的顶层任务*/
            taskId = taskService.createTaskQuery()
                    .processInstanceId(
                            instance.getProcessInstanceId())
                    .singleResult().getId();

            /*3.2 设置审批人名称*/
            taskService.setAssignee(taskId, nextApprovalName);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据名字查询需要处理的任务列表
     *
     * @param assignee 代办人/处理人
     **/
    public List<Task> getTaskListByName(
            String assignee) {

        return taskService.createTaskQuery()
                .taskAssignee(assignee)//处理人
                .includeProcessVariables()//附加信息
                .list();//符合条件的所有任务对象
    }

    /**
     * 处理某个流程任务
     *
     * @param processInstanceId 要处理的实例ID
     * @param taskId            要处理的任务ID
     * @param msg               要添加的额外数据
     **/
    public void processComplete(
            String processInstanceId,
            String taskId, String msg) {

        //先查询要处理任务对象 返回结果需要包含额外数据的map
        Task task = taskService.createTaskQuery()
                .taskId(taskId)//任务ID
                .includeProcessVariables()//结果包含map
                .singleResult();//返回单条数据

        //得到map集合对象
        Map<String, Object> map = task
                .getProcessVariables();
        map.put("msg", msg);

        //处理任务
        taskService.complete(taskId, map);

        //如果流程的环节多的话  应该继续设置下一个处理人
        Task nextTask = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .includeProcessVariables()
                .singleResult();

        if (nextTask != null) {
            //获取下一个处理人名称
            String nextApprovalName = (String) nextTask
                    .getProcessVariables()
                    .get("nextApprovalName");
            //设置下一个处理人
            taskService.setAssignee(nextTask.getId(),
                    nextApprovalName);
        }
    }


    public void history(String username) {

        List<HistoricTaskInstance> taskInstances =
                historyService
                        .createHistoricTaskInstanceQuery()
                        .includeProcessVariables()
                        .taskAssignee(username)
                        .list();

        for (HistoricTaskInstance instance : taskInstances) {
            System.out.println(instance.getName());
            System.out.println(instance.getStartTime());
            System.out.println(instance.getEndTime());
            System.out.println(instance.getProcessVariables());
        }

    }


}
