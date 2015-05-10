<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path +
            "/";
%>
<html>
<head>
    <title>TimeSheetCheck</title>
    <link rel="stylesheet" href="resources/css/style.css"/>
    <script src="resources/js/match.js"></script>
</head>
<body>
<div class="contain">
    <div class="main">
        <h2>TimesheetComparator-1.2-Beta1</h2><br>
        <form enctype="multipart/form-data" onsubmit="return checkFileExtName();" action="<%=basePath%>match" method="post">
            <div class="file">TW Timesheet File：</div>
            <input class="file_path" id="twFilePath" type="file" name="twFilePath"><br>
            <div class="file">Telstra Timesheet File：</div>
            <input class="file_path" id="telstraFilePath" type="file" name="telstraFilePath">
            <div><input type="submit" value="提交"></div>
            <div><span id="errorMsg">请选择正确格式的文件，以.xlsx结尾</span></div>
        </form>
        <div class="tips"><h3>Tips:Please make sure the sheet storing data of your excel files ranks firstly.</h3></div>
    </div>
</div>
</body>
</html>
