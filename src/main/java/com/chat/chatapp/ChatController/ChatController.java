package com.chat.chatapp.ChatController;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
@Controller
public class ChatController {

    // Client side when user after login wants to send message to url /api/chat.sendMessage
    @MessageMapping("/chat.sendMessage")
    // once the user sent messsage it should be sent broker channel we defined broker /topic 
    // and /public is queue messaged pushed into this queue

    @SendTo("/topic/public") //Subscribers with the url and see latest msgs
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        return chatMessage;
    }
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
    SimpMessageHeaderAccessor headerAccessor){
        // Purpose Of this headAccessor is to whenever user enters the chat user must be stored into ws session
        
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        return chatMessage;
    }
    
}
/*
 * STOMP (Simple Text Oriented Messaging Protocol):
STOMP is a higher-level messaging protocol that can be layered on top of WebSocket or other transport protocols.
It adds a layer of abstraction on WebSocket by providing a simple text-based messaging format.
STOMP is designed to be easy to use and is suitable for building messaging and chat applications.
It provides features like message destinations (topics and queues), message acknowledgment, and subscription management.
STOMP simplifies the development of real-time applications by providing a standardized messaging format.

Use WebSocket when you need a direct, low-level, high-performance communication channel. WebSocket is ideal for scenarios where you have specific requirements, and you want to build custom real-time functionality from the ground up.

Use STOMP when you want a higher-level, more user-friendly messaging protocol built on top of WebSocket. STOMP abstracts the underlying WebSocket complexity and provides a standardized way to send and receive messages. It's suitable for chat applications, messaging systems, and scenarios where you don't need to reinvent the wheel.
 */