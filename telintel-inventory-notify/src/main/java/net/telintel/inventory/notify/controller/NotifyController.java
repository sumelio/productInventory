package net.telintel.inventory.notify.controller;

import java.util.Date;

import org.slf4j.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
 
import net.telintel.test.model.domain.Message;
import net.telintel.test.model.domain.OutputMessage;

@Controller
@RequestMapping("/")
public class NotifyController {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @RequestMapping(method = RequestMethod.GET)
  public String viewApplication() {
    return "index";
  }
  
 
  @MessageMapping("/notify")
  @SendTo("/topic/message")
  public OutputMessage sendMessage(Message message) {
    logger.info("Message sent.... " + message);
    return new OutputMessage(message, new Date());
  }
}
