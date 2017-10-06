/**
 * Author: Freddy Lemus Date: 25/09/2017 General create angular modules
 * Description: Create three modules and contains utility functions
 * 
 */

// Angular
(function(angular) {
	angular.module("telintelApp", [ "telintelApp.controllers",
			"telintelApp.services" ]);

	angular.module("telintelApp.controllers", [ "ngToast" ]);
	angular.module("telintelApp.services", []);
})(angular);

// notify window
function notifyMe(notify, title) {

	try {
		if (!("Notification" in window)) {
			alert(notify);
		} else if (Notification.permission === "granted") {
			showNotify(title, notify);
		} else if (Notification.permission !== 'denied') {
			Notification.requestPermission(function(permission) {
				if (!('permission' in Notification)) {
					Notification.permission = permission;
				}

				if (permission === "granted") {
					showNotify(title, notify);
				}
			});
		}
	} catch (err) {
		console.log(err);
		alert(notify);

	}
}

// NOtification function
function showNotify(title, notify) {
	var options = {
		body : notify,
		icon : "img/notify.jpg",
		dir : "ltr"
	};
	var notification = new Notification(title, options);

}

// Menu Responsive
function myFunction() {
	var x = document.getElementById("myTopnav");
	if (x.className === "topnav") {
		x.className += " responsive";
	} else {
		x.className = "topnav";
	}
}

// Forma the notification message
function formatMessageNofity(label, notify, count) {
	msg = document.getElementById('msg.notify.' + label + '.msg').value;
	msg = msg.replace('PRODUCTO', notify);
	msg = msg.replace('[' + label + ']', '');
	msg = msg.replace('[' + label + ']', '');
	msg += 'Total stock ' + count;
	return msg;
}

// El proceso batch de archivo registro [NUM] producto(s). Nombre del
// archivo. Total stock [TOTAL_STOCK]
// The file batch process has registered [NUM] product(s). FIle name
// [FILE_NAME]. Total stock [TOTAL_STOCK]
// notify "12121,archivo.txt,Total stock 1212"
function formatMessageBatchNofity(label, notify, count) {
	var labels;
	labels = notify.split(",");

	var msg = document.getElementById('msg.notify.' + label + '.msg').value;

	msg = msg.replace('[NUM]', labels[0]);
	msg = msg.replace('[FILE_NAME]', labels[1]);
	msg = msg.replace('[TOTAL_STOCK]', labels[2]);
	msg = msg.replace('[batch]', '');

	return msg;
}











