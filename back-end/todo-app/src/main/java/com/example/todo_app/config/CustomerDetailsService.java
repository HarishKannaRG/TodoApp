package com.example.todo_app.config;

import com.example.todo_app.constants.Constants;
import com.example.todo_app.dto.UserDTO;
import com.example.todo_app.entity.Users;
import com.example.todo_app.exception.UserNotFoundException;
import com.example.todo_app.repository.UserRepository;
import com.example.todo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user;
        Optional<Users> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        user = optionalUser.get();
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
