<%--
  Created by IntelliJ IDEA.
  User: sjyuan
  Date: 5/5/15
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>TimeSheetCheck</title>
    <%--<link rel="stylesheet" href="css/style.css"/>--%>
    <style type="text/css">
        *, body {
            margin: 0;
        }

        .contain {
            background-color: bisque;
            width: 100%;
            margin: auto;
            height: 1000px;
        }

        .main {
            width: 500px;
            height: 100%;
            margin: auto;
        }

        .file {
            margin-top: 10px;
            display: inline-block;
            width: 80px;
        }

        .file_path {

            width: 300px;
            height: 25px;
        }

    </style>
</head>
<body>
<div class="contain">
    <div class="main">
        <h2>Timesheet 在线校验v1.1</h2><br>

        <form enctype="multipart/form-data" action="<%=basePath%>upload" method="post">
            <div class="file">TW：</div>
            <input class="file_path" type="file" name="twFilePath"><br>

            <div class="file">Telstra：</div>
            <input class="file_path" type="file" name="telstraFilePath">

            <div><input type="submit" value="提交"></div>
        </form>
    </div>
</div>
</body>
</html>
