<%--
  Created by 蓝鸥科技有限公司  www.lanou3g.com.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>首页</title>

    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link href="../../css/demo.css" rel="stylesheet" type="text/css"/>

    <script src="../../scripts/boot.js" type="text/javascript"></script>
</head>
<body>
<h1>DataGrid 数据表格</h1>

<div style="width:800px;">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-add" onclick="add()">请假申请</a>
                </td>
                <td style="white-space:nowrap;">
                    <input id="key" class="mini-textbox" emptyText="请输入姓名" style="width:150px;" onenter="onKeyEnter"/>
                    <a class="mini-button" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:800px;height:280px;" allowResize="true"
     url="/getTaskListByName"
     idField="id" multiSelect="true">

    <div property="columns">
        <div type="indexcolumn"></div>
        <div field="taskName" width="120" headerAlign="center" allowSort="true">任务名</div>
        <div field="applyName" width="120" headerAlign="center" allowSort="true">申请人</div>
        <div field="processDefinitionId" width="120" headerAlign="center" allowSort="true">流程定义ID</div>
        <div field="processInstanceId" width="120" headerAlign="center" allowSort="true">流程实例ID</div>
        <div field="processName" width="120" headerAlign="center" allowSort="true">流程名称</div>
        <div name="action" width="120" headerAlign="center" align="center"
             renderer="onActionRenderer" cellStyle="padding:0;">操作
        </div>
    </div>
</div>


<script type="text/javascript">
    mini.parse();

    var grid = mini.get("datagrid1");
    grid.load();


    function add() {

        mini.open({
            url: "/employeeApplyWindow",
            title: "请假申请", width: 600, height: 400,
            onload: function () {

            },
            ondestroy: function (action) {

                grid.reload();
            }
        });
    }

    function search() {
        var key = mini.get("key").getValue();
        grid.load({username: key});
    }
    function onKeyEnter(e) {
        search();
    }

    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;

        var s = '<a class="New_Button" href="javascript:processComplete(\'' + record.processInstanceId + '\',\'' + record.id + '\')">办理</a>';

        return s;
    }

    function processComplete(processInstanceId, taskId) {
        console.log("办理按钮" + processInstanceId + "  " + taskId)

        $.ajax({
         url: "/processComplete",
         data: {
         processInstanceId: processInstanceId,
         taskId: taskId
         },
         success: function () {
         grid.reload();
         }
         })

    }

</script>

</body>
</html>
