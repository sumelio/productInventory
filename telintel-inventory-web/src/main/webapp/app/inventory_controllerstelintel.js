//*** This code is copyright 2002-2016 by Gavin Kistner, !@phrogz.net
//*** It is covered under the license viewable at http://phrogz.net/JS/_ReuseLicense.txt
Date.prototype.customFormat = function(formatString){
  var YYYY,YY,MMMM,MMM,MM,M,DDDD,DDD,DD,D,hhhh,hhh,hh,h,mm,m,ss,s,ampm,AMPM,dMod,th;
  YY = ((YYYY=this.getFullYear())+"").slice(-2);
  MM = (M=this.getMonth()+1)<10?('0'+M):M;
  MMM = (MMMM=["January","February","March","April","May","June","July","August","September","October","November","December"][M-1]).substring(0,3);
  DD = (D=this.getDate())<10?('0'+D):D;
  DDD = (DDDD=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"][this.getDay()]).substring(0,3);
  th=(D>=10&&D<=20)?'th':((dMod=D%10)==1)?'st':(dMod==2)?'nd':(dMod==3)?'rd':'th';
  formatString = formatString.replace("#YYYY#",YYYY).replace("#YY#",YY).replace("#MMMM#",MMMM).replace("#MMM#",MMM).replace("#MM#",MM).replace("#M#",M).replace("#DDDD#",DDDD).replace("#DDD#",DDD).replace("#DD#",DD).replace("#D#",D).replace("#th#",th);
  h=(hhh=this.getHours());
  if (h==0) h=24;
  if (h>12) h-=12;
  hh = h<10?('0'+h):h;
  hhhh = hhh<10?('0'+hhh):hhh;
  AMPM=(ampm=hhh<12?'am':'pm').toUpperCase();
  mm=(m=this.getMinutes())<10?('0'+m):m;
  ss=(s=this.getSeconds())<10?('0'+s):s;
  return formatString.replace("#hhhh#",hhhh).replace("#hhh#",hhh).replace("#hh#",hh).replace("#h#",h).replace("#mm#",mm).replace("#m#",m).replace("#ss#",ss).replace("#s#",s).replace("#ampm#",ampm).replace("#AMPM#",AMPM);
};



/**
 * Author: Freddy Lemus Date: 25/09/2017 Inventoy Controller Description: This
 * Controller contains WebSocket functions and http functios
 * 
 */

// External var in order to use...
var scopeHolder;

(function(angular) {
	angular
			.module("telintelApp.controllers")
			.controller(
					"TelintelCtrl",
					function($scope, $http, TelintelService, ngToast) {

						// ======================================================
						// Init parameters
						// =====================================================
						$http.defaults.headers.common["Accept-Language"] = "es";

						// hide form add and update producto
						// show form product list
						$scope.toggle = true;
						$scope.productId = "_";
						$scope.productTypeSelect = 1;

						var urlBase = "/telintel-inventory-api-rest";
						var vm = this;
						$scope.currentPage = 1;
						$scope.pageSize = 3;
						$scope.pages = [];
						$scope.processFiles = null;
						
						

						// ====================================================
						// WebSOcket and NOtify
						// ==================================================
						// Create template notify
						vm.getNoityInfo = function(notify) {
							return {
								content : notify,
								className : 'info', // 'success', 'info',
													// 'warning', 'danger'
								classes : 'telintelClassNotify',
								dismissOnTimeout : true,
								timeout : 4000,
								dismissButton : false,
								dismissButtonHtml : '&times',
								dismissOnClick : true,
								compileContent : true
							};
						};

						 
						
						//Init 
 
						  
						
						// This function allows sending a stomp message
						// (WebSocket)
						$scope.addMessage = function addMessage(totalStock) {
							if ($scope.message != null)
								TelintelService.send($scope.message,
										totalStock, $scope.products);
							$scope.message = "";

						};

						// This function allows receiving a stomp message
						// (WebSOcket) and shall be notified to final user
						TelintelService
								.receive()
								.then(
										null,
										null,
										function(data) {
											$scope.count = data.count;

											if (data.message != null) {
                                               notify = data.message;
                                               
												if (notify.includes('[update]')) {
													label = 'update';
												}else if (notify.includes('[remove]')) {
													label = 'remove';
												}else if (notify.includes('[batch]')) {
													label = 'batch';
												}else{
													label = 'create';
												}

												if (notify.includes('[batch]')) {
													notify = formatMessageBatchNofity(
															label,
															data.message,
															data.count);
												} else {
													notify = formatMessageNofity(
															label,
															data.message,
															data.count);
												}

												title = document
														.getElementById('msg.notify.'
																+ label
																+ '.title').value;

												// Create notify style angular
												// with ngToast
												ngToast.create(vm
														.getNoityInfo(notify));

												// Create notify style HTML
												notifyMe(notify, title);
											}

										});

						// =========================================================================
						// function paint page
						// ===================================================
						// get all ProductTypes and display initially in Combo
						$http.get(urlBase + '/product/types').success(
								function(data) {
									$scope.productTypes = data;

								});

						// New Product
						$scope.addProductInventory = function addProductInventory() { 
							
							var value = document
									.getElementById('parameterName').value;
							var key = document.getElementById('parameterName').name;

						 
								$http(
										{
											method : "post",
											url : urlBase
													+ '/product/inventory/name/'
													+ $scope.productName
													+ '/description/'
													+ $scope.productDesc
													+ '/price/'
													+ $scope.productPrice
													+ '/type/'
													+ $scope.productType,
											headers : {
												'Content-Type' : 'application/x-www-form-urlencoded'
											},
											transformRequest : function() {
												return key + "=" + value;
											},
											data : {}

										}).success(
										function(data) {
											
											$scope.message = "[create]"
													+ $scope.productName; 
											$scope.showAllInventory();
											$scope.addMessage(data.totalStock);
											
										});

							 
						};

						// GET all Products
						$scope.findProductInventory = function findProductInventory() {

							/*$http.get(
									urlBase + '/products?typeId='
											+ $scope.productTypeSelect)
									.success(function(data) {
										$scope.products = data;
									});
							*/
							$scope.getProducts(); 
						};

						

						// DELETE one product
						$scope.removeSelection = function removeSelection(
								product) {
							console.log("$scope.removeSelection");
							
							//$scope.products.remove(product);
							
							 var index = $scope.products.indexOf(product);
							 $scope.products.splice(index, 1);     
							  

							var value = document.getElementById('parameterName').value;
							var key = document.getElementById('parameterName').name;

							if (product.id == "") {
								alert("Insufficient Data! Please provide values for productId");
							} else {
								$http(
										{
											method : "delete",
											url : urlBase
													+ '/product/inventory/id/'
													+ product.id,
											headers : {
												'Content-Type' : 'application/x-www-form-urlencoded'
											},
											transformRequest : function() {
												return key + "=" + value;
											},
											data : {}

										}).success(function(data) {
									$scope.message = "[remove]" + product.name;
									$scope.addMessage(data.totalStock);
								});
							}

						};// End $scope.removeSelection 
						

						// Edit product
						$scope.editSelection = function editSelection(product) {
							console.log("$scope.editSelection");
							$scope.productId = product.id;
							$scope.productName = product.name;
							$scope.productDesc = product.description;
							$scope.productType = product.productType.id;
							$scope.productPrice = product.price;
							$scope.toggle = false;
						}

						// Show all list
						$scope.showAllInventory = function showAllInventory() {
							console.log("$scope.showAllInventory");
							cleanForm();
							$scope.toggle = true;
							$scope.getProducts();
						}

						// PUT Product inventory
						$scope.updateProductInventory = function updateProductInventory() {
						 

							var value = document
									.getElementById('parameterName').value;
							var key = document.getElementById('parameterName').name;

							if ($scope.productName == ""
									|| $scope.productDesc == ""
									|| $scope.productPrice == ""
									|| $scope.productType == "") {
								alert("Insufficient Data! Please provide values for product name, description, type and price");
							} else {
								$http(
										{
											method : "put",
											url : urlBase
													+ '/product/inventory/id/'
													+ $scope.productId
													+ '/name/'
													+ $scope.productName
													+ '/description/'
													+ $scope.productDesc
													+ '/price/'
													+ $scope.productPrice
													+ '/type/'
													+ $scope.productType,
											headers : {
												'Content-Type' : 'application/x-www-form-urlencoded'
											},
											transformRequest : function() {
												return key + "=" + value;
											},
											data : {}

										}).success(
										function(data) {

											$scope.toggle = '!toggle';
											$scope.message = "[update]"
													+ $scope.productName;
											$scope.getProducts();
											$scope.addMessage(data.totalStock);
											cleanForm();
											

										});
							}
						}

				

						// ===================================================
						// Paginación
						// ===================================================
						$scope.setPage = function(index) {

							$scope.currentPage = index - 1; 
							$scope.getProducts();
						};
						
						
						$scope.getProducts = function(){
							if($scope.currentPage  < 0){
								$scope.currentPage = 0;
							}
							if($scope.productTypeSelect  < 0){
								$scope.productTypeSelect = 1;
							}
							$http.get(urlBase + '/products?typeId=' + $scope.productTypeSelect 
							          + '&page='+ $scope.currentPage 
							          + "&pageSize="+ $scope.pageSize
						).success(
							function(data) {
								$scope.products = data.products;
								$scope.totalProduct = data.totalProduct; 
								$scope.configPages($scope.totalProduct );
							});
						}

						$scope.configPages = function(totalItem) {
							$scope.pages.length = 0;
							var ini = $scope.currentPage - 2;
							var fin = $scope.currentPage + 3;
							if (ini < 1) {
								ini = 1;
								if (Math.ceil(totalItem
										/ $scope.pageSize) > 10)
									fin = 10;
								else
									fin = Math.ceil(totalItem
											/ $scope.pageSize);
							} else {
								if (ini >= Math.ceil(totalItem
										/ $scope.pageSize) - 10) {
									ini = Math.ceil(totalItem
											/ $scope.pageSize) - 10;
									fin = Math.ceil(totalItem
											/ $scope.pageSize);
								}
							}
							if (ini < 1)
								ini = 1;
							for (var i = ini; i <= fin; i++) {
								$scope.pages.push({
									no : i
								});
							}

							if ($scope.currentPage >= $scope.pages.length)
								$scope.currentPage = $scope.pages.length - 1;
						};
						
						
						
						
						
						
						
			
						
						
			 
						//====================== tab Show process status ===================
						
						
						
						//Send names files
						$scope.getProcessFile = function getProccesFile() {
					 
							$http.get("/telintel-inventory-file/processFiles")
									.success(
											function(data) { 
									 
												
												 $scope.processFiles = data; 
												 for(var i = 0 ; i < $scope.processFiles.length; i ++){
													 var _date =  new Date($scope.processFiles[i].start);
													
													 $scope.processFiles[i]._date  =  _date.customFormat( "#YYYY#/#MM#/#DD#-#hh#:#mm#:#ss#"); 
													 
												    console.log(_date + " " + $scope.processFiles[i].start );	 
												 }
												 
												 
												// var _date = $filter('date')(new Date(input), 'dd/MM/yyyy');
												 
											})
									.error(
											function(data) {
												 
												ngToast
														.danger("Error consult process");
											 
											});
						}//End $scope.executeProccesFile 
					  
						
						scopeHolder = $scope.getProcessFile;
						
						// Clean all field of the product form
						function cleanForm() {
							console.log("cleanForm()");
							$scope.productId = "_";
							$scope.productName = "";
							$scope.productDesc = "";
							$scope.productPrice = "";
							$scope.productType = "";

						}
						
						$scope.getProducts();
						
						

					})/*. filter('startFromGrid', function() {
				return function(input, start) {
					start = +start;
					if(input != null){
					  return input.slice(start);
					}else{
					  return 0;	
					}
				}
			}) */;

})(angular);




