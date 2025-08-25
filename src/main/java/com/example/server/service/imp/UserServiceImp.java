package com.example.server.service.imp;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.server.model.Res;
import com.example.server.model.User;
import com.example.server.repository.UserRepository;
import com.example.server.service.UserService;

@Service
public class UserServiceImp implements UserService {

    PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public Res loginUser(String email, String password) {
        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(email);
        System.out.println(email);
        System.out.println(userOptional);
        System.out.println(userOptional.isEmpty() + "------------is Empty");
        Res res = new Res(false, null, null);
        if (userOptional.isEmpty()) {
            res.setErrors(Optional.of("User not register with use"));
            res.setStatus(false);
            return res;
        }
        User user = userOptional.get();
        // Check password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            res.setErrors(Optional.of("Invalid credential"));
            return res;
        }
        res.setData(user);
        res.setStatus(true);
        res.setMessage(Optional.of("Login successfully"));
        return res; // login success
    }

    @Override
    public Res registerUser(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (!userOptional.isEmpty()) {
            return new Res(false, Optional.of("User Already exists with us"), Optional.empty());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new Res(true, Optional.empty(), Optional.of("Register succesfully"));
    }

}
