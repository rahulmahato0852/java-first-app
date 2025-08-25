package com.example.server.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.server.model.Res;
import com.example.server.model.User;
import com.example.server.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register-user")
    public Res registerUser(
            @RequestPart("user") User user,
            @RequestPart(value = "profileImage", required = false) MultipartFile image) {
        try {
            System.out.println("--------------------user");
            System.out.println(user);
            if (image != null && !image.isEmpty()) {
                // Save file locally
                String uploadDir = "uploads/users/";
                Files.createDirectories(Paths.get(uploadDir));
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.write(filePath, image.getBytes());
                // Store file path in user object (so you can save in DB)
                user.setHero(filePath.toString());
            }

            // Call your service
            return userService.registerUser(user);

        } catch (IOException e) {
            return new Res(false, Optional.of("File upload failed: " + e.getMessage()), null);
        }
    }

    @PostMapping("/login")
    public Res loginUser(@RequestBody User uuser, HttpServletResponse response) {
        String email = uuser.getEmail();
        String password = uuser.getPassword();
        System.out.println(email);
        System.out.println(password);
        System.out.println("LOGIN USER-----------------");
        Res res = userService.loginUser(email, password);
        if (res.getStatus()) {
            User user = (User) res.getData();
            System.out.println(user.getEmail() + "---email at login");
            // String token = JwtUtils.generateToken(user.getEmail());
            // Example: set JWT or session ID as cookie
            Cookie cookie = new Cookie("authToken", "as");
            cookie.setHttpOnly(true);// protect from JS access
            cookie.setSecure(true); // send only over HTTPS
            cookie.setPath("/"); // available for whole app
            cookie.setMaxAge(24 * 60 * 60); // 1 day expiry
            response.addCookie(cookie);
        }
        return res;
    }

    @GetMapping("/get-user")
    public ResponseEntity<User> getUser(@RequestParam String email) {
        User user = userService.getUser(email);
        return ResponseEntity.ok(user);
    }

}
