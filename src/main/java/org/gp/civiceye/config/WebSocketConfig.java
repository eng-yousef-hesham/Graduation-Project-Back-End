package org.gp.civiceye.config;

import org.gp.civiceye.security.JwtTokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

    public WebSocketConfig(JwtHandshakeInterceptor jwtHandshakeInterceptor) {
        this.jwtHandshakeInterceptor = jwtHandshakeInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")                    // Your websocket endpoint URL
                .addInterceptors(jwtHandshakeInterceptor)  // <-- Register your JWT interceptor here
                .setAllowedOriginPatterns("*")         // Allow CORS as needed
                .withSockJS();                         // Enable SockJS fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");  // Example broker prefixes
        config.setApplicationDestinationPrefixes("/app");
    }

    // Optional: configure transport settings
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        // Customize if needed
    }
}
