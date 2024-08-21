package com.project2.angry_parrot.config.auth;

import com.project2.angry_parrot.model.User;
import com.project2.angry_parrot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService 의 loadUserByUsername");
        User userEntity = userRepository.findByUsername(username);
        return new PrincipalDetails(userEntity);
    }

}
