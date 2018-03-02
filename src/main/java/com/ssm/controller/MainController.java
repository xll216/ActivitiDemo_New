package com.ssm.controller;

import com.ssm.activiti.ApplayWorkFlow;
import com.ssm.domain.ApplyTask;
import com.ssm.util.BaseResult;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 蓝鸥科技有限公司  www.lanou3g.com.
 */
@Controller
public class MainController {
    @Autowired
    private ApplayWorkFlow applayWorkFlow;

    @RequestMapping(value = {"", "/", "/home"})
    public String home() {
        return "Home";
    }

    @RequestMapping("/employeeApplyWindow")
    public String employeeApplyWindow() {
        return "EmployeeApplyWindow";
    }


    /**
     * 任务列表数据获取
     **/
    @ResponseBody
    @RequestMapping("/getTaskListByName")
    public BaseResult<ApplyTask> getTaskList(
            @RequestParam(name = "username", required = false)
                    String username) {
        System.out.println(username);
        BaseResult<ApplyTask> result = new BaseResult<>();

        List<ApplyTask> taskList = new ArrayList<>();

        if (StringUtils.isBlank(username)) {
            username = "张三";
        }

        //通过工作流对象获取某人名下的任务列表
        List<Task> list = applayWorkFlow
                .getTaskListByName(username);

        for (Task t : list) {
            ApplyTask task = new ApplyTask();
            task.setId(t.getId());//任务id

            //任务名称
            task.setTaskName("请假申请");

            //流程名称
            task.setProcessName(t.getName());

            //流程实例ID
            task.setProcessInstanceId(
                    t.getProcessInstanceId());

            //流程定义ID
            task.setProcessDefinitionId(
                    t.getProcessDefinitionId());

            //申请人
            Map<String, Object> map = t
                    .getProcessVariables();

            task.setApplyName((String) map
                    .get("applyName"));

            taskList.add(task);
        }
        result.setTotal(taskList.size());
        result.setData(taskList);

        return result;
    }

    /**
     * 员工提交请假申请
     **/
    @RequestMapping("/employeeApply")
    public String employeeApply(ApplyTask applyTask) {
        Map<String, Object> map = new HashMap<>();
        map.put("applyName", applyTask.getApplyName());
        map.put("nextApprovalName", applyTask.getNextApprovalName());
        map.put("day", applyTask.getDay());
        map.put("content", applyTask.getContent());

        System.out.println(map);

        //请假申请
        applayWorkFlow.employeeApply(map);

        return "Home";
    }

    /**
     * 处理某个任务
     **/
    @ResponseBody
    @RequestMapping("/processComplete")
    public String processComplete(
            String processInstanceId,
            String taskId) {

        System.out.println(processInstanceId);

        //处理该流程
        applayWorkFlow.processComplete(
                processInstanceId,
                taskId, "注意安全");

        return "suc";
    }
}
