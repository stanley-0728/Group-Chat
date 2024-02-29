package com.chat.chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // we want add new Web socket end point 
        // If websocket is not established withSockJs will downgrade to htpp
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Whenever client wants to send message request url is assigned as /app/**
        registry.setApplicationDestinationPrefixes("/app");

          // TODO Auto-generated method stub
        // Using In-memory Simple Broker all subscribers will listening to /topic/** 
        registry.enableSimpleBroker("/topic");
    }

    
}
