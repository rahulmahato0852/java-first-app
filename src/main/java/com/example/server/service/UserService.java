package com.example.server.service;

import com.example.server.model.Res;
import com.example.server.model.User;

public interface UserService {

    Res registerUser(User user);

    User getUser(String email);

    Res loginUser(String email, String password);

}
