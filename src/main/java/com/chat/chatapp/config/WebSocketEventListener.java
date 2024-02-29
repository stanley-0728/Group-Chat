package com.chat.chatapp.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.chat.chatapp.ChatController.ChatMessage;
import com.chat.chatapp.ChatController.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
/*
 * This  Event Listener main purpose is to listen to the events like when user disconnects 
 * the chat ,all the online users should be notified or for that session others users should 
 * be notified.
 */

    private final SimpMessageSendingOperations messageTemplate;
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){

        // as we set users into SimpMessageHeaderAccesor whenever the disconnect 
        // sessionDisconnectEvent throws StompHeaderAccesor which extends SimpHeaderAccessor
        System.out.println(event.getMessage());
        StompHeaderAccessor headerAccessor =StompHeaderAccessor.wrap(event.getMessage());
        String username=(String)headerAccessor.getSessionAttributes().get("username");
        if(username!=null){
            log.info("User disconnected :{}"+username);
            var chatMessage= ChatMessage.builder()
                                        .type(MessageType.LEAVE)
                                        .sender(username)
                                        .build();
            // We need to inform other users that A Specific User has left the chat
            // SimpMessageSendingOperations converts the message and sends to the queue with destination path set to /topic/public with the help of this url other subscribers are listening to events
            messageTemplate.convertAndSend("/topic/public", chatMessage);


        }
    
    }
}
