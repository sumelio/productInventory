package net.telintel.inventory.notify.config;

import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan(basePackages = "net.telintel.inventory.notify.controller")
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
	  
    registry.addEndpoint("/notify").withSockJS()
    //.setClientLibraryUrl("http://localhost:8080/myapp/js/sockjs-client.js")
    ; 
    
  }
}
