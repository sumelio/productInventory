<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />

<title>Telintel SAS Test Product Inventory....</title>
<link href='./css/style.css' rel="stylesheet" type="text/css"></link>
<link rel="stylesheet" href="./css/bootstrap.min.css">

<script src="libs/angular/angular.min.js"></script>
<script src="libs/lodash/dist/lodash.min.js"></script>
<script src="libs/sockjs/sockjs.min.js" type="text/javascript"></script>
<script src="libs/stomp-websocket/lib/stomp.min.js" type="text/javascript"></script>
<script src="app/inventory_apptelintel.js" type="text/javascript"></script>
<script src="app/inventory_controllerstelintel.js" type="text/javascript"></script>
<script src="app/inventory_servicestelintel.js" type="text/javascript"></script>
</head>
<body onload='document.loginForm.username.focus();' class="login">

	<div class="topnav" id="myTopnav">
		<a href="javascript:void(0);" style="font-size: 15px;" class="icon" onclick="myFunction()">&#9776;</a>
		<a href="?language=en" class="language"><spring:message code="msg.languageEn" /> </a> <a
			href="?language=es" class="language"><spring:message code="msg.languageEs" /> </a>
	</div>

	<div class="contain">

		<div>
			<h1>
				<spring:message code="msg.title" />
			</h1>

			<h3></h3>

			<c:if test="${not empty msg}">
				<div class="validateMsg">
					<spring:message code="msg.loginErrorLabel" />
				</div>
			</c:if>
			<c:if test="${not empty error}">
				<div class="validateMsg">
					<spring:message code="msg.loginOKLabel" />
				</div>
			</c:if>


		</div>
		<div align="center">

			<form name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST'>

				<table class="login">
					<caption>
						<spring:message code="msg.login" />
					</caption>
					<tr>
						<td><spring:message code="msg.user" />:</td>
					</tr>
					<tr>
						<td><input type='text' name='username'></td>
					</tr>
					<tr>
						<td><spring:message code="msg.pass" />:</td>
					</tr>
					<tr>
						<td><input type='password' name='password' /></td>
					</tr>

				</table>

				<button onclick="javascript:submit();" class="button1">
					<spring:message code="msg.submitLabel" />
				</button>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

			</form>
		</div>
	</div>
</body>
</html>