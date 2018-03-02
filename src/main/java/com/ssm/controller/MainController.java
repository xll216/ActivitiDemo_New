package com.ssm.controller;

import com.ssm.domain.ApplyTask;
import com.ssm.util.BaseResult;
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

    @RequestMapping(value = {"", "/", "/home"})
    public String home() {
        return "Home";
    }

    @RequestMapping("/employeeApplyWindow")
    public String employeeApplyWindow() {
        return "EmployeeApplyWindow";
    }


    @ResponseBody
    @RequestMapping("/getTaskListByName")
    public BaseResult<ApplyTask> getTaskList(
            @RequestParam(name = "username", required = false)
                    String username) {
        System.out.println(username);
        BaseResult<ApplyTask> result = new BaseResult<>();

        List<ApplyTask> taskList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ApplyTask task = new ApplyTask();
            task.setId(i + "");
            task.setTaskName("请假申请-" + i);
            task.setProcessInstanceId("流程实例-" + i);
            task.setProcessDefinitionId("流程定义-" + i);
            task.setProcessName("请假申请-" + i);
            taskList.add(task);
        }
        result.setTotal(taskList.size());
        result.setData(taskList);

        return result;
    }

    @RequestMapping("/employeeApply")
    public String employeeApply(ApplyTask applyTask) {
        Map<String, Object> map = new HashMap<>();
        map.put("applyName", applyTask.getApplyName());
        map.put("nextApprovalName", applyTask.getNextApprovalName());
        map.put("day", applyTask.getDay());
        map.put("content", applyTask.getContent());

        System.out.println(map);

        return "Home";
    }

    @ResponseBody
    @RequestMapping("/processComplete")
    public String processComplete(String processInstanceId) {
        System.out.println(processInstanceId);

        return "suc";
    }
}
