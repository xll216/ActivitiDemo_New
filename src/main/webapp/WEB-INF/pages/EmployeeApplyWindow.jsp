<%--
  Created by 蓝鸥科技有限公司  www.lanou3g.com.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>请假申请</title>

    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link href="../../css/demo.css" rel="stylesheet" type="text/css"/>

    <script src="../../scripts/boot.js" type="text/javascript"></script>
</head>
<body>

<form id="form1" action="/employeeApply" method="post">
    申请人：<input type="text" name="applyName" value="张三">
    <br><br>
    审批人：<input type="text" name="nextApprovalName" value="李四">
    <br><br>
    请假天数：<input type="text" name="day" value="3">
    <br><br>
    请假原因：<input type="text" name="content" value="生病">
    <br><br>
    <input id="apply" type="button" value="申请">
    <input type="reset" value="重置">
</form>

<script>

    mini.parse();

    $("#apply").click(function () {
        CloseWindow();
        $("#form1").submit();
    });

    function CloseWindow(action) {
        if (window.CloseOwnerWindow)
            return window.CloseOwnerWindow(action);
        else window.close();
    }


</script>

</body>
</html>
