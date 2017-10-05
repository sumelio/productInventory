/*
 * jQuery File Upload Plugin Angular JS Example
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2013, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * https://opensource.org/licenses/MIT
 *
 * Modified by Freddy lEMUS
 * Date update 25-09-2017
 * Description: New function in order to send newName files and process them
 */

/* jshint nomen:false */
/* global window, angular */

;
(function() {
	'use strict';

	var isLocalhost = window.location.hostname === 'localhost', url = isLocalhost ? '/telintel-inventory-file/upload/files'
			: '/telintel-inventory-file/upload/files';

	console.log("url " + url);

	angular
			.module('multiUploadTelintel', [ 'blueimp.fileupload', 'ngToast' ])
			.config(
					[
							'$httpProvider',
							'fileUploadProvider',
							function($httpProvider, fileUploadProvider) {
								delete $httpProvider.defaults.headers.common['X-Requested-With'];
								fileUploadProvider.defaults.redirect = window.location.href
										.replace(/\/[^\/]*$/,
												'/cors/result.html?%s');

							} ])

			.controller(
					'MultiUploadTelintel',
					[
							'$scope',
							'$http',
							'$filter',
							'$window',
							'ngToast',
							function($scope, $http, $filter, $window, ngToast) {
								//==================================== init parameters =================================
								console.log("url " + url);
								var urlBase = "/telintel-inventory-file";
                                $scope.maxFiles = 4; 
                                $scope.processFiles = null;
								//Check up
								//alert(ngToast);
        						
                                // ======================================================
        						// Init parameters
        						// =====================================================
        						$http.defaults.headers.common["Accept-Language"] = "es";
        						
        						
								//Send names files
								$scope.executeProccesFile = function executeProccesFile() {
									var paths = "";
									
									
									// Valiate llength files
									if ($scope.queue.length > 0) {
										
										var list = this.queue.slice(0),
				                        i,
				                        file;
				                    for (i = 0; i < list.length; i += 1) { 
				                        if( list[i].nameNew == null){
				                        	 ngToast.danger("Please, upload " + list[i].name);
				                              return 0;
				                          }  
				                     }
										
										
										for ( var index in $scope.queue) {
											paths += "'"
													+ $scope.queue[index].nameNew
													+ "',";

										}
										paths = paths.substring(0,
												paths.length -2)
												+ ".txt',";

										
										
 
									$http.post(urlBase + "/products/files/names/"
											+ paths + "")			
												.success(
														function(data) {
															ngToast
																	.success("Consulta realizada iniciado");

															$scope
																	.clear($scope.queue);

														})
												.error(
														function(data) {
															ngToast
																	.danger("Error proceso");
															$scope
																	.clear($scope.queue);
															console
																	.log("Error; "
																			+ data);
														});

									} else {
										ngToast.warning("0 files");
									}

								}
								
								
							
								
								$scope.cancelAll = function cancelAll() {
									$scope
									.clear($scope.queue);
									
									$scope.cancel();
									$scope
									.clear($scope.queue);

									
								}// end $scope.executeProccesFile = function executeProccesFile() {
								
								
								$scope.options = {
									url : url
								};
								
								
							

							} ])

			.controller('FileDestroyController',
					[ '$scope', '$http', function($scope, $http) {
						var file = $scope.file, state;
						console.log("url <<<" + url);
						if (file.url) {
							file.$state = function() {
								return state;
							};
							file.$destroy = function() {
								state = 'pending';
								return $http({
									url : file.deleteUrl,
									method : file.deleteType
								}).then(function() {
									state = 'resolved';
									$scope.clear(file);
								}, function() {
									state = 'rejected';
								});
							};
						} else if (!file.$cancel && !file._index) {
							file.$cancel = function() {
								$scope.clear(file);
							};
						}
					} ]);

}());
