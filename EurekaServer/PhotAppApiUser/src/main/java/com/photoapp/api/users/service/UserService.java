package com.photoapp.api.users.service;

import com.photoapp.api.users.shared.UserDto;

public interface UserService {
        UserDto createUser(UserDto userDetails);
}
