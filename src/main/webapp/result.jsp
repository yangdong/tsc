<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
        *,body{
            margin: 0;
        }
        .contain {
            background-color: bisque;
            width: 100%;
            margin:auto;
            height: 2000px;
        }
        .main{
            width: auto;
            height: 100%;
            margin-left: 10px;

        }
        .mismatch{
            margin-top: 5px;
            margin-bottom: 10px;
        }
        h3{
            text-decoration: underline;
        }
        .mismatch span {

            border-bottom: 1px dashed chocolate;
        }
        .name{
            margin-top: 5px;
        }
    </style>
</head>
<body>
<div class="contain">
    <div class="main">
        <h2>Timesheet比较结果</h2><br>
        <c:forEach items="${result}" var="project_names">
            <c:forEach items="${project_names.value}" var="name_mismatchs">
                <div class="name">
                <h3>${name_mismatchs.key} [${project_names.key} ]</h3>
                <c:forEach items="${name_mismatchs.value}" var="mismatch">
                    <div class="mismatch">
                        <span>${mismatch}</span>
                    </div>
                </c:forEach>
                </div><br>
            </c:forEach>
            <br><br>
        </c:forEach>
    </div>
</div>
</body>
</html>
