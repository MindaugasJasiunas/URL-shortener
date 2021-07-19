<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
  <head>
    <title>URL shortener</title>
    <style>
       .error{color:red;}
    </style>
  </head>
  <body>
      <form:form modelAttribute="urlLink" action="shortenURL" method="POST">
          Link you want to shorten:
          <br/>
          <form:input path="fullLink"/>
          <form:errors path="fullLink" cssClass="error"/>
          <br/>
          <input type="submit" value="Shorten!">
      </form:form>
  </body>
</html>
