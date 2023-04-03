package com.abdiev.application.web;


import com.abdiev.application.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;



@Controller
@RequiredArgsConstructor
@CrossOrigin
public class ChatController  {


    @MessageMapping("/message")
    @SendTo("/pool/messages")
    public Message poolMessage(final Message message) throws InterruptedException {

        Thread.sleep(500);
        return new Message(HtmlUtils.htmlEscape(message.getMessage()),HtmlUtils.htmlEscape(message.getSender()));
    }

}
