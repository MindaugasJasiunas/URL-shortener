<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<form> <!-- used for formatting -->
    <img class="mb-4" src="resources/link.svg" alt="" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal">Your shortened URL</h1>
    <p class="mt-5 mb-3"><a href="<c:out value="${shortenedURL}"/>"><c:out value="${shortenedURL}"/></a></p>
</form>
</body>
</html>