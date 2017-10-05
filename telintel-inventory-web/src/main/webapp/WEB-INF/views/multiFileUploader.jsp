<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>

<html>
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
 
<link rel="stylesheet" href='./css/style.css'>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  -->
<link rel="stylesheet" href="./css/blueimp-gallery.min.css">
<link rel="stylesheet" href="./css/jquery.fileupload.css">
<link rel="stylesheet" href="./css/jquery.fileupload-ui.css">
<link rel="stylesheet" href="./css/ngToast.min.css">
<link rel="stylesheet" href="./css/ngToast-animations.min.css">


<script src="libs/jquery/jquery.min.js"></script>
<script src="libs/angular/angular.min.js"></script>
<script src="libs/angular-sanitize/angular-sanitize.js"></script>
<script src="libs/toast/toast.js"></script>
<script src="libs/lodash/dist/lodash.min.js"></script>
<script src="app/inventory_apptelintel.js"></script>

</head>
<body>

	<div class="uploadPage" align="center">
		<form id="fileupload" method="POST" enctype="multipart/form-data"
			data-ng-app="multiUploadTelintel"
			data-ng-controller="MultiUploadTelintel" data-file-upload="options"
			data-ng-class="{'fileupload-processing': processing() || loadingFiles}">


			<div class="row fileupload-buttonbar">
				<div class="col-lg-7">
					<span class="button1 btn btn-success fileinput-button"
						ng-disabled="queue.length >= maxFiles"
						ng-class="{disabled: disabled}" ng-hide="queue.length >= maxFiles">
						<i class="glyphicon glyphicon-plus"></i> <span><spring:message
								code="msg.label.addFiles" /> ...{{queue.length}} <spring:message
								code="msg.label.file" /></span> <input type="file" name="files"
						multiple="multiple"   ng-disabled="queue.length >= maxFiles">
					</span>
					<button  type="button" class="button1 btn btn-primary start"
						data-ng-click="submit()" ng-disabled="queue.length > maxFiles">
						<i class="glyphicon glyphicon-upload"></i> <span><spring:message
								code="msg.label.startUpload" /> </span>
					</button>
					<button type="button" class="button1 btn btn-warning cancel"
						data-ng-click="cancelAll()">
						<i class="glyphicon glyphicon-ban-circle"></i> <span><spring:message
								code="msg.label.cancelUpload" /> </span>
					</button>
					<button type="button" class="button1 btn btn-primary eject"
						ng-click="executeProccesFile()"
						ng-disabled="queue.length < maxFiles">
						<i class="glyphicon glyphicon-expand"></i> <span><spring:message
								code="msg.label.executeProcess" /></span>
					</button>

					<!-- The global file processing state -->
					<span class="fileupload-process"></span>
				</div>
				<div align="center">
					<toast></toast>
				</div>
				<!-- The global progress state -->
				<div class="col-lg-5 fade" data-ng-class="{in: active()}">
					<!-- The global progress bar -->
					<div class="progress progress-striped active"
						data-file-upload-progress="progress()">
						<div class="progress-bar progress-bar-success"
							data-ng-style="{width: num + '%'}"></div>
					</div>
					<!-- The extended global progress state -->
					<div class="progress-extended">&nbsp;</div>
				</div>
			</div>
			<div>
				<spring:message code="msg.label.maxFiles" />
				<input type="number" id="maxFiles" name="maxFiles"
					ng-model="maxFiles" min="1" max="10" />

			</div>
			<table class="tableInventary" >
				<caption></caption>
				<tr>
					<th><spring:message code="msg.label.name" /></th>
					<th><spring:message code="msg.label.nesame" /></th>
					<th  colspan="3" ><spring:message code="msg.label.detail" /></th>
					
				</tr>
				<tr  data-ng-repeat="file in queue"
					data-ng-class="{'processing': file.$processing()}">
					<!-- name -->
					<td data-label='<spring:message code="msg.label.name" />' >
						<p class="name" data-ng-switch data-on="!!file.url">
							<span data-ng-switch-when="true" data-ng-switch
								data-on="!!file.thumbnailUrl"> {{file.name}}</a>
							</span> <span data-ng-switch-default>{{file.name}}</span>
						</p>


					</td>

					<!-- new name -->
					<td data-label='<spring:message code="msg.label.nesame" />' >
						<p class="name" data-ng-switch data-on="!!file.nameNew">
							<span data-ng-switch-when="true" data-ng-switch
								data-on="!!file.nameNew"> {{file.nameNew}} </span> <span
								data-ng-switch-default> {{file.deleteUrl}}</span>
						</p>
					</td>


					<!-- detail -->

					<td data-label='<spring:message code="msg.label.detail" />'  >
						<p class="name" data-ng-switch data-on="!!file.contentType ">
							<span data-ng-switch-when="true" data-ng-switch
								data-on="!!file.contentType"> {{file.contentType}}
								{{file.detail}} </span> <span data-ng-switch-default>{{file.type}}
								{{file.detail}} </span>
						</p>
					</td>


					<td data-label='<spring:message code="msg.label.detail" />' >
						<p class="size">{{file.size | formatFileSize}}</p>
						<div class="progress progress-striped active fade"
							data-ng-class="{pending: 'in'}[file.$state()]"
							data-file-upload-progress="file.$progress()">
							<div class="progress-bar progress-bar-success"
								data-ng-style="{width: num + '%'}"></div>
						</div>
					</td>


					<td data-label='<spring:message code="msg.label.detail" />' >

						<button type="button" class="btn btn-warning cancel"
							data-ng-click="file.$cancel()"
							data-ng-hide="!file.$cancel || file.error">
							<i class="glyphicon glyphicon-ban-circle"></i> <span>Cancel</span>
						</button> <strong data-ng-show="file.error" class="error text-danger">{{file.error}}</strong>


					</td>
				</tr>
			</table>
		</form>
	</div>


	<script src="libs/jquery/jquery.ui.widget.js"></script>
	<script src="libs/jquery/jquery.fileupload.js"></script>
	<script src="libs/jquery/jquery.fileupload-process.js"></script>
	<script src="libs/jquery/jquery.fileupload-angular.js"></script>
	<script src="app/multiFileUploader_appTelintel.js"></script>


</body>
</html>


