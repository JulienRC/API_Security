<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>

<!-- Access the bootstrap Css like this,
        Spring boot wiInformationsll handle the resource mapping automcatically -->
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/4.4.1/css/bootstrap.min.css" />

<!--
    <spring:url value="/css/main.css" var="springCss"/>
    <link href="${springCss}" rel="stylesheet"/>
     -->
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

</head>
<body>

	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<ul class="navbar-nav">
			<li class="nav-item active"><a class="nav-link" href="/">App
					Security API</a></li>
			<c:choose>
				<c:when test="${!isConnected}">
					<li class="nav-item"><a class="nav-link"
				href="oauth2/authorization/google">Login</a></li>
				</c:when>
				<c:otherwise>
					<li class="nav-item"><a class="nav-link"
				href="logout">Logout</a></li>
				</c:otherwise>
			</c:choose>
			
			<li class="nav-item"><a class="nav-link" href="index">Index</a></li>
			
			<li class="nav-item"><a class="nav-link" href="/">Resource 1</a></li>
			
			<li class="nav-item"><a class="nav-link" href="/">Resource 2</a></li>
			
			<li class="nav-item"><a class="nav-link" href="log">Log</a></li>

		</ul>
	</nav>
	<div class="container">

		<div class="starter-template">