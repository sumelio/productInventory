(function(angular, SockJS, Stomp, _, undefined) {
  angular.module("telintelApp.services").service("TelintelService", function($http, $q, $timeout) {
    
    var service = {}, listener = $q.defer(), socket = {
      client: null,
      stomp: null
    }, messageIds = [];
    
    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/telintel-inventory-notify/notify";
    service.NOTIFY_TOPIC = "/topic/message";
    service.NOTIFY_BROKER = "/app/notify";
    
    service.receive = function() {
      return listener.promise;
    };
    
    service.send = function(message, count, product) { 
    	var id = Math.floor(Math.random() * 1000000);
      socket.stomp.send(service.NOTIFY_BROKER, {
        priority: 9
      }, JSON.stringify({
        message: message,
        id: id,
        count: count
        
      },  function( key, value ) {
    	    if( key === "$$hashKey" ) {
    	        return undefined;
    	    }

    	    return value;
    	}
      ));
      messageIds.push(id);
      console.log("id " + id);
    };
    
    var reconnect = function() {
      $timeout(function() {
        initialize();
      }, this.RECONNECT_TIMEOUT);
    };
    
   /* var getMessage = function(data) {
      var message = JSON.parse(data), out = {};
      out.message = message.message;
      out.time = new Date(message.time);
      if (_.contains(messageIds, message.id)) {
        out.self = true;
        messageIds = _.remove(messageIds, message.id);
      }
     
      if(out.message != null){  
        notifyMe(out.time, out.message); 
      }
      return out;
    };
    */
    var getMessage = function(data) {
    	console.log("data ->>" + data)
    	var dataJson = JSON.parse(data), out = {};
        
        out = dataJson;
     
        console.log("message ->>" + dataJson.message);
        console.log(".count ->>" + dataJson.count);
        console.log(".product ->>" + dataJson.product);
 
         
        return out;
      };
   
    var startListener = function() {
      socket.stomp.subscribe(service.NOTIFY_TOPIC, function(data) {
        listener.notify(getMessage(data.body)); 
      });
    };
    
    var initialize = function() {
      socket.client = new SockJS(service.SOCKET_URL);
      socket.stomp = Stomp.over(socket.client);
      socket.stomp.connect({}, startListener);
      socket.stomp.onclose = reconnect;
    };
    
    initialize();
    return service;
  });
})(angular, SockJS, Stomp, _);
