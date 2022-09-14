package com.photoapp.api.users.ui.controller;

import com.photoapp.api.users.service.UserService;
import com.photoapp.api.users.shared.UserDto;
import com.photoapp.api.users.ui.model.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
public class usersController {

    @Autowired
    private Environment env;

    @Autowired
    UserService userService;


    @GetMapping("/status/check")
    public String status(){

        return "Working on port "+ env.getProperty("local.server.port");
    }


    @PostMapping
    public String createUser(@Valid @RequestBody CreateUserRequest userRequest){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userRequest,UserDto.class);
        userService.createUser(userDto);

        return "Create User Method is called!";
    }
}
