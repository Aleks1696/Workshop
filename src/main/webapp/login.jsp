<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${sessionScope.get('language')}" />
<fmt:setBundle basename="messages"/>
<html>
    <head>
        <title>Authorization</title>
    </head>

    <body>
        <form>
            Choose language:
            <select id="language" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}>Українська</option>
            </select>
        </form>

        <h2>Enter login and password:</h2>
        <form action="${pageContext.request.contextPath}/login" method="post">
            Login: <input type="text" name="login" required="required"/>
            Password: <input type="text" name="password" required="required"/>
            <input type="submit" value="Log in"/>
        </form>
        <div>
<%--            <jstl:if test="${not empty requestScope.login_error_message}">--%>
<%--                <p><fmt:message key=""/></p>--%>
<%--            </jstl:if>--%>
            <c:set var="response" scope="request" value="${requestScope.login_error_message}"/>
            <c:if test="${response != null}">
                <fmt:message key="${response}"/>
            </c:if>
        </div>

    </body>
</html>
