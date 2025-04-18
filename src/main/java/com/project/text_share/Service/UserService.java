package com.project.text_share.Service;

import com.project.text_share.Entity.MasterUser;

public interface UserService {
    MasterUser registerUser(MasterUser masterUser);
    MasterUser authenticate(String username, String password);

}
