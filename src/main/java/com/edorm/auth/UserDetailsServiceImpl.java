package com.edorm.auth;

import com.edorm.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByEmail(username)
                .orElseGet(
                        () -> {
                            long id;
                            try {
                                id = Long.parseLong(username);
                            } catch (NumberFormatException e) {
                                throw new UsernameNotFoundException("User with username: " + username + " not found.");
                            }
                            return userRepository.findById(id)
                                    .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found."));

                        }
                );

    }

}