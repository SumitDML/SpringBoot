package com.photoapp.api.users.ui.controller;

import com.photoapp.api.users.service.UserService;
import com.photoapp.api.users.shared.UserDto;
import com.photoapp.api.users.ui.model.CreateUserRequest;
import com.photoapp.api.users.ui.model.UserResponse;
import com.photoapp.api.users.ui.model.UserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_ATOM_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_ATOM_XML_VALUE} )
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest userRequest){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userRequest,UserDto.class);
        UserDto createUser = userService.createUser(userDto);

        UserResponse returnValue = modelMapper.map(createUser,UserResponse.class);


        return new ResponseEntity(returnValue,HttpStatus.CREATED);


    }

    @GetMapping(value = "/{userId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId){
        UserDto userDto = userService.getUserDetailsByUserId(userId);
        UserResponseModel returnValue = new ModelMapper().map(userDto,UserResponseModel.class);



        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
