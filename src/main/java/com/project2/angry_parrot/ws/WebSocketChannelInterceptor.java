package com.project2.angry_parrot.ws;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project2.angry_parrot.config.auth.PrincipalDetails;
import com.project2.angry_parrot.config.jwt.JwtProperties;
import com.project2.angry_parrot.model.User;
import com.project2.angry_parrot.repository.UserRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class WebSocketChannelInterceptor implements ChannelInterceptor {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public WebSocketChannelInterceptor(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String jwtToken = accessor.getFirstNativeHeader("Authorization");

        if (jwtToken != null && jwtToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
            String token = jwtToken.replace(JwtProperties.TOKEN_PREFIX, "");
            String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token).getClaim("username").asString();

            if (username != null) {
                User user = userRepository.findByUsername(username);
                PrincipalDetails principal = new PrincipalDetails(user);

                Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        return message;
    }
}
