<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>


<!DOCTYPE html>
<html ng-app="telintelApp">
<head lang="en">
<meta charset="UTF-8">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />

<title>Telintel SAS Test Product Inventory.....</title>

<link href='./css/style.css' rel="stylesheet" type="text/css"></link>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/ngToast.min.css">
<link rel="stylesheet" href="./css/ngToast-animations.min.css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="libs/jquery/jquery.min.js"></script>
<script src="libs/bootstrap/bootstrap.min.js"></script>

<script src="libs/angular/angular.min.js"></script>
<script src="libs/angular-sanitize/angular-sanitize.js"></script>
<script src="libs/toast/toast.js"></script>
<script src="libs/lodash/dist/lodash.min.js"></script>
<script src="libs/sockjs/sockjs.min.js" type="text/javascript"></script>

<script src="libs/stomp-websocket/lib/stomp.min.js" type="text/javascript">	</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/blazy/1.8.2/blazy.min.js" type="text/javascript">
</script>


<script src="app/inventory_apptelintel.js" type="text/javascript"></script>
<script src="app/inventory_controllerstelintel.js" type="text/javascript"></script>
<script src="app/inventory_servicestelintel.js" type="text/javascript"></script>

</head>
<body>
	<div ng-controller="TelintelCtrl">
		<div class="title">
			<h1>${pageContext.request.userPrincipal.name}</h1>

		</div>
		<div>
			<c:url value="/j_spring_security_logout" var="logoutUrl" />
			<form action="${logoutUrl}" method="post" id="logoutForm">
				<input type="hidden" id="parameterName" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>

		<div class="topnav" id="myTopnav">

			<a class="language" href="javascript:document.getElementById('logoutForm').submit();"> <spring:message
					code="msg.logoutLabel" /> <a href="javascript:void(0);" style="font-size: 15px;" class="icon"
				onclick="myFunction()">&#9776;</a> <a href="?language=en" class="language"> <spring:message
						code="msg.languageEn" />
			</a> <a href="?language=es" class="language"><spring:message code="msg.languageEs" /></a>
		</div>

		<div align="center">
			<toast></toast>
		</div>
		<div class="container">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#home"><spring:message
							code="msg.label.inventory" /></a></li>
				<c:if test="${isUserInRole}">
					<li><a data-toggle="tab" href="#Uploadfiles"><spring:message code="msg.label.upload" /></a></li>
				</c:if>
				<li><a data-toggle="tab" href="#ProcessFile">[Process Status]</a></li>



			</ul>

			<div class="tab-content">
				<div id="home" class="tab-pane fade in active">
					<div align="center" ng-show="toggle">
						<div>

							<div>
								<h3>
									<spring:message code="msg.productTYpeLabel" />
								</h3>
								<div>
									<select ng-model="productTypeSelect" class="button1 btn btn-primary ">
										<option ng-repeat="productType in productTypes" value="{{productType.id}}"> {{productType.name}}</option>
									</select>
									<button ng-click="findProductInventory()" class="button1 btn btn-primary star">
										<spring:message code="msg.findLabel" />
									</button>

								</div>
							</div>
						</div>
						<div align="center">


							<table class="tableInventary">

								<caption>
									Page size {{pageSize}} <select ng-model="pageSize" class="button1 btn btn-primary ">
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
									</select>
								</caption>
								<thead>
									<tr>
										<th>ID</th>
										<th><spring:message code="msg.nameProductLabel" /></th>
										<th><spring:message code="msg.descriptionLabel" /></th>
										<th><spring:message code="msg.priceUnitLabel" /></th>
										<th><spring:message code="msg.typeLabel" /></th>
										<c:if test="${isUserInRole}">
											<th><spring:message code="msg.removeEditLabel" /></th>
										</c:if>
									</tr>
								</thead>
								<tr ng-repeat="product in products ">
									<td data-label="ID">{{product.id}}</td>
									<td data-label="<spring:message code="msg.nameProductLabel" />">{{product.name}}</td>
									<td data-label="<spring:message code="msg.descriptionLabel" />">{{product.description}}</td>
									<td data-label="<spring:message code="msg.priceUnitLabel" />">$ {{product.price}}</td>
									<td data-label="<spring:message code="msg.typeLabel" />">{{product.productType.name}}</td>
									<c:if test="${isUserInRole}">
										<td>
											<button ng-click="removeSelection(product)" class="button1 btn btn-danger destroy">
												<i class="glyphicon glyphicon-trash"></i>
												<spring:message code="msg.removeLabel" />
											</button>
											<button ng-click="editSelection(product)" class="button1  btn btn-primary star"
												class="btn-panel-big">
												<i class="glyphicon glyphicon-edit"></i>
												<spring:message code="msg.editLabel" />
											</button>
										</td>
									</c:if>
								</tr>

							</table>

							<div class='btn-group'>
								<button type='button' class='btn btn-default' ng-disabled='currentPage == 0'
									ng-click='currentPage = currentPage - 1'>&laquo;</button>
								<button type='button' class='btn btn-default' ng-disabled='currentPage == page.no - 1'
									ng-click='setPage(page.no)' ng-repeat='page in pages'>{{page.no}}</button>
								<button type='button' class='btn btn-default'
									ng-disabled='currentPage >= products.length/pageSize - 1'
									, ng-click='currentPage = currentPage + 1'>&raquo;</button>
							</div>

						</div>
						<div>
							<c:if test="${isUserInRole}">
								<button ng-click="toggle = !toggle" class="button1 btn btn-primary star">
									<spring:message code="msg.addNew" />
								</button>
							</c:if>
						</div>
					</div>
				</div>
				<div class="contain">
					<div class="contain" id="add-task-panel" ng-hide="toggle">


						<div class="contain">
							<div>

								<div class="panel-heading-controls">
									<button ng-click="showAllInventory()" class="button1 btn btn-primary star">
										<spring:message code="msg.showAllInventoryLabel" />
									</button>
								</div>
							</div>
							<div align="center">

								<div>
									<form name="formEditUpdate">
										<table class="edit" width="10">
											<caption>
												<div class="contain" ng-if="productId == '_' ">
													<span class="panel-title"><spring:message code="msg.addNew" /></span>
												</div>
												<div class="contain" ng-if="productId != '_' ">
													<span class="panel-title"><spring:message code="msg.updateProductLabel" /></span>
												</div>
											</caption>
											<tr>
												<td><b>Id</b></td>
												<td>{{productId}}</td>
											</tr>
											<tr>
												<td><b><spring:message code="msg.nameProductLabel" /></b></td>
												<td>
													<div class="validateMsg" ng-hide="formEditUpdate.productName.$valid">
														<spring:message code="msg.validate.required" />
													</div> <input type="text" id="productName" name="productName" name="productName"
													ng-model="productName" required />
												</td>
											</tr>
											<tr>
												<td><b><spring:message code="msg.descriptionLabel" /></b></td>
												<td>

													<div class="validateMsg" ng-hide="formEditUpdate.productDesc.$valid">
														<spring:message code="msg.validate.required" />
													</div> <input type="text" ng-model="productDesc" name="productDesc" id="productDesc" required />
												</td>
											</tr>
											<tr>
												<td><b><spring:message code="msg.typeLabel" /></b></td>
												<td><div class="validateMsg" ng-hide="formEditUpdate.productType.$valid">
														<spring:message code="msg.validate.required" />
													</div> <select ng-model="productType" id="productType" name="productType" required
													class="select1">
														<option value="">-- Select --</option>
														<option ng-repeat="productType in productTypes" value="{{productType.id}}"> {{productType.name}}</option>
												</select></td>
											</tr>
											<tr>
												<td><b><spring:message code="msg.priceUnitLabel" /></b></td>
												<td><div class="validateMsg" ng-hide="formEditUpdate.productPrice.$valid">
														<spring:message code="msg.validate.required" />
													</div> $ <input type="number" id="productPrice" name="productPrice" ng-model="productPrice"
													min="1" max="999999999" required /></td>
											</tr>

										</table>
										</form>
 				<div ng-if="productId == '_' ">

											<button ng-click="addProductInventory()" class="button1 btn btn-primary star"
												ng-disabled="! formEditUpdate.productPrice.$valid  ||  ! formEditUpdate.productType.$valid || ! formEditUpdate.productDesc.$valid || ! formEditUpdate.productName.$valid">
												<spring:message code="msg.addNew" />
											</button>
										</div>
										<div ng-if="productId != '_' ">
											<button ng-click="updateProductInventory()" class="button1 btn btn-primary star"
												ng-disabled="! formEditUpdate.productPrice.$valid  ||  ! formEditUpdate.productType.$valid  || ! formEditUpdate.productDesc.$valid || ! formEditUpdate.productName.$valid">
												<spring:message code="msg.updateProductLabel" />
											</button>
 				</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /////////////////////////////////////////// tab Uploadfiles ////////////////////////////////////////////////77 -->
				<div id="Uploadfiles" class="tab-pane fade">
					<h3>
						<spring:message code="msg.label.upload" />
					</h3>
					<p>
					<div id="updateIframe">
						<iframe class="uploadPage" src="/telintel-inventory-web/multiUpload" frameborder="0"
							allowfullscreen>
							<p>Your browser does not support iframes.</p>
						</iframe>
					</div>
					</p>
				</div>
				<!-- ///////////////////////////////////////// Tab ProcessFile  //////////////////////////////////////////////////77 -->
				<div id="ProcessFile" class="tab-pane fade">
					<h3>
						<spring:message code="msg.label.process" />
					</h3>
					<p>
					<div id="ProcessFile">
						<table class="tableInventary">
							<caption>

								<img alt="refresh" data-src="img/refresh.png" class="refresh" onclick="scopeHolder()">

							</caption>
							<thead>
								<tr>
									<th>nameThreal</th>
									<th>count</th>
									<th>Start</th>
									<th>Update</th>
									<th><spring:message code="msg.nameProductLabel" /></th>

								</tr>
							</thead>
							<tr ng-repeat="process in processFiles ">
								<td data-label="nameThreal">{{process.nameThreal}}</td>
								<td data-label="count">{{process.linesFile}}</td>
								<td data-label="start">{{process.startTime}}</td>
								<td data-label="<spring:message code="msg.nameProductLabel" />">{{process.endTime}}</td>
								<td data-label="status">{{process.status}}</td>
							</tr>

						</table>
					</div>
				</div>
			</div>
		</div>


		<div class="messageNotify">
			<input type="hidden" value="<spring:message code='msg.notify.create.title' />"
				id="msg.notify.create.title" /> <input type="hidden"
				value="<spring:message code='msg.notify.create.msg' />" id="msg.notify.create.msg" /> <input
				type="hidden" value="<spring:message code='msg.notify.update.title' />"
				id="msg.notify.update.title" /> <input type="hidden"
				value="<spring:message code='msg.notify.update.msg' />" id="msg.notify.update.msg" /> <input
				type="hidden" value="<spring:message code='msg.notify.remove.title' />"
				id="msg.notify.remove.title" /> <input type="hidden"
				value="<spring:message code='msg.notify.remove.msg' />" id="msg.notify.remove.msg" /> <input
				type="hidden" value="<spring:message code='msg.notify.remove.title' />"
				id="msg.notify.batch.title" /> <input type="hidden"
				value="<spring:message code='msg.notify.batch.msg' />" id="msg.notify.batch.msg" />


      
		</div>
<script type="text/javascript">

var blazy = new Blazy({
    selector: 'img';
    });
</script>
</body>
</html>
