package com.ssm.test;

import com.ssm.activiti.ApplayWorkFlow;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 蓝鸥科技有限公司  www.lanou3g.com.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-*.xml"})
public class MainTest {
    @Autowired
    private ApplayWorkFlow applayWorkFlow;

    /*测试请假申请*/
    @Test
    public void employeeApply() {
        Map<String, Object> map = new HashMap<>();

        map.put("applyName", "王文");
        map.put("nextApprovalName", "王湃");
        map.put("day", "3");
        map.put("content", "不想上课");

        applayWorkFlow.employeeApply(map);

    }

    /*根据名字查询任务列表*/
    @Test
    public void getTaskListByName() {
        List<Task> taskList = applayWorkFlow
                .getTaskListByName("王湃");

        for (Task task : taskList) {
            System.out.println(task);
        }

    }

    @Test
    public void history() {
        applayWorkFlow.history("王湃");
    }


}
