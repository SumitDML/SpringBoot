package com.photoapp.api.users.service;

import com.photoapp.api.users.data.UserEntity;
import com.photoapp.api.users.data.UsersRepository;
import com.photoapp.api.users.shared.UserDto;
import com.photoapp.api.users.ui.model.AlbumResponseModel;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UsersRepository usersRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    RestTemplate restTemplate;
    Environment environment;


    public UserServiceImpl(UsersRepository usersRepository,BCryptPasswordEncoder bCryptPasswordEncoder,RestTemplate restTemplate,Environment environment){

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersRepository=usersRepository;
        this.restTemplate  = restTemplate;
       this.environment = environment;

    }
    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDetails,UserEntity.class);

        usersRepository.save(userEntity);

        UserDto returnValue =modelMapper.map(userEntity,UserDto.class);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity  = usersRepository.findByEmail(username);

        if(userEntity==null) throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList<>());

    }
    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity  = usersRepository.findByEmail(email);

        if(userEntity==null) throw new UsernameNotFoundException(email);


        return new ModelMapper().map(userEntity,UserDto.class);
    }

    @Override
    public UserDto getUserDetailsByUserId(String userId) {

        UserEntity userEntity = usersRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException("User Not Found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        String albumsUrl=String.format(environment.getProperty("albums.url"),userId);

        ResponseEntity<List<AlbumResponseModel>> albumListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
        });
        List<AlbumResponseModel> albumsList = albumListResponse.getBody();
        userDto.setAlbums(albumsList );
        return userDto;
    }
}
