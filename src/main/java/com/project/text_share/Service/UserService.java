package com.project.text_share.Service;

import com.project.text_share.Entity.User;

public interface UserService {
    User registerUser(User user);
    User authenticate(String username, String password);

}
