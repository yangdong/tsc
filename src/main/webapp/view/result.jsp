<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>TimeSheetCheck</title>
    <link rel="stylesheet" href="<%=basePath%>resources/css/result.css"/>
</head>
<body>
<div class="contain">
    <div class="main">
        <h1>Timesheets Compared Result</h1><br>
        <c:forEach items="${result}" var="project_names">
            <c:forEach items="${project_names.value}" var="name_mismatchs">
                <div class="name">
                    <h3>${name_mismatchs.key} [${project_names.key} ]</h3>
                    <c:forEach items="${name_mismatchs.value}" var="mismatch">
                        <div class="mismatch">
                            <c:if test="${mismatch.hoursInTelstra == -1.0}">
                                <span class="nonexist">${mismatch}</span>
                            </c:if>
                            <c:if test="${mismatch.hoursInTelstra != -1.0}">
                                <span class="exist">${mismatch}</span>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
                <br>
            </c:forEach>
            <br><br>
        </c:forEach>
    </div>
</div>
</body>
</html>
