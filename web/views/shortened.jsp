<%--
  Created by IntelliJ IDEA.
  User: linuxmachine
  Date: 2021-07-19
  Time: 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
<%--      <c:if test="${not empty param.error}" >--%>
      <c:if test="${param.error != null}">
          <h2>error happened</h2>
      </c:if>
      <c:if test="${param.error == null}">
          <h2>display: <c:out value="${shortenedURL}"/></h2>
      </c:if>
  </body>
</html>
