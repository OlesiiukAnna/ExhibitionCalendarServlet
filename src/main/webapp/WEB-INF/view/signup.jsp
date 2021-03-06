<%--
  Created by IntelliJ IDEA.
  User: olesiiuk
  Date: 20.05.20
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${param.lang}">
<head>
    <title>Exhibition Calendar | Sign Up</title>
    <jsp:include page="parts/head-bootstrap.jsp"/>
</head>
<body>
<header>
    <jsp:include page="parts/header.jsp"/>
</header>
<div class="container">
    <div class="row">
        <div class="col-md-offset-3 col-md-6">
            <h1><fmt:message key="headline.signup"/></h1>
            <c:if test="${requestScope.isUserExists}">
                <p class="text-danger" role="alert">
                    <fmt:message key="message.userIsExists"/>
                </p>
            </c:if>

            <c:if test="${requestScope.isInvalidData}">
                <p class="text-danger" role="alert">
                    <fmt:message key="message.dataIsInvalid"/>
                </p>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/signup" role="form" class="form-horizontal">
                <div class="form-group">
                    <label for="InputEmail" class="control-label"><fmt:message key="form.email"/></label>
                    <input type="email" name="email" class="form-control" id="InputEmail"
                           placeholder="<fmt:message key="placeholder.email"/>">
                </div>
                <div class="form-group">
                    <label for="InputPassword" class="control-label"><fmt:message key="form.password"/></label>
                    <input type="password" name="password" class="form-control" id="InputPassword"
                           placeholder="<fmt:message key="placeholder.password"/>">
                </div>
                <div class="form-group">
                    <label for="InputFirstName" class="control-label"><fmt:message key="form.firstName"/></label>
                    <input type="text" name="first-name" class="form-control" id="InputFirstName"
                           placeholder="<fmt:message key="placeholder.firstName"/>">
                </div>
                <div class="form-group">
                    <label for="InputLastName" class="control-label"><fmt:message key="form.lastName"/></label>
                    <input type="text" name="last-name" class="form-control" id="InputLastName"
                           placeholder="<fmt:message key="placeholder.lastName"/>">
                </div>
                <div class="form-group">
                    <label for="InputPhone" class="control-label"><fmt:message key="form.phone"/></label>
                    <input type="text" name="phone" class="form-control" id="InputPhone"
                           placeholder="<fmt:message key="placeholder.phone"/>">
                </div>
                <input type="hidden" name="role" value="VISITOR">
                <button type="submit" class="btn btn-primary"><fmt:message key="ref.submit"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
