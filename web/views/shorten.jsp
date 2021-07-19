<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>URL shortener</title>
    <style>
        .error{color:red;}
    </style>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="resources/signin.css" rel="stylesheet">
</head>
<body class="text-center">
<form:form modelAttribute="urlLink" action="shortenURL" method="POST" class="form-signin">
    <img class="mb-4" src="resources/link.svg" alt="" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal">Enter URL you want to shorten</h1>
    <form:input path="fullLink" placeholder="http://www.example.com/"></form:input><br/>
    <div class="checkbox mb-3">
        <form:errors path="fullLink" cssClass="error"></form:errors>
    </div>
    <input type="submit" value="Shorten URL!" class="btn btn-lg btn-primary btn-block">
</form:form>
</body>
</html>