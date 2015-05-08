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

        span{
            display: none;
            font-size: 12px;
            color:red;
        }

        .tips{
            margin-top: 10px;
        }
    </style>
    <script>
        function checkFileExtName(){
            var twFile = document.getElementById("twFilePath").value;
            if(twFile.trim() == '' || twFile.substring(twFile.indexOf("."))!=".xlsx"){
                document.getElementById("errorMsg").style.display = "block";
                return false;
            }
            var telstraFile = document.getElementById("telstraFilePath").value;
            if(telstraFile.trim() == '' || telstraFile.substring(telstraFile.indexOf("."))!=".xlsx"){
                document.getElementById("errorMsg").style.display = "block";
                return false;
            }
            document.getElementById("errorMsg").style.display = "none";
            return true;
        }
    </script>
</head>
<body>
<div class="contain">
    <div class="main">
        <h2>TimesheetComparator-1.1-Beta1</h2><br>

        <form enctype="multipart/form-data" onsubmit="return checkFileExtName();" action="<%=basePath%>match" method="post">
            <div class="file">TW：</div>
            <input class="file_path" id="twFilePath" type="file" name="twFilePath"><br>

            <div class="file">Telstra：</div>
            <input class="file_path" id="telstraFilePath" type="file" name="telstraFilePath">
            <div><input type="submit" value="提交"></div>
            <div><span id="errorMsg">请选择正确格式的文件，以.xlsx结尾</span></div>
        </form>
        <div class="tips"><h3>Tips:Please make sure the sheet storing data of your excel files ranks firstly.</h3></div>
    </div>
</div>
</body>
</html>
