package com.example.demo.controller;


import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try{
            UserEntity user = UserEntity.builder().email(userDTO.getEmail()).username(userDTO.getUsername()).password(userDTO.getPassword()).build();

            UserEntity registeredUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder().email(registeredUser.getEmail()).id(registeredUser.getId()).username(registeredUser.getUsername()).build();

            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e){
            //사용자 정보는 항상 하나이므로 리스트로 만들어야하는 ResponseDTO를 사용하지 않고 그냥 UserDTO 리턴
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword());

        //계정이 존재할때
        if(user != null){
            final String token = tokenProvider.create(user);
            final UserDTO reponseUserDTO = UserDTO.builder().email(user.getEmail()).id(user.getId()).token(token).build();
            return ResponseEntity.ok().body(reponseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder().error("Login failed").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


}
