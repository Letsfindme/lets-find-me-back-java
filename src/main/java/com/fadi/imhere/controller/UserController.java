package com.fadi.imhere.controller;

import com.fadi.imhere.dtos.UserDto;
import com.fadi.imhere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile/{username}")
    public UserDto getProfile(@PathVariable String username) {
        System.out.println(username);
        return userService.getProfile(username);
    }

    @PutMapping("/profile/")
    public UserDto updateProfile(@RequestBody UserDto userDto) {
        return userService.updateProfile(userDto);
    }

/*
    @GetMapping("/user/{ID}")
    ResponseEntity<User> getUserById(@PathVariable long ID) {
        User user = userRepository.findOne(ID);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/user/add")
    User addUser(@RequestBody User user){

        LocalDateTime today = LocalDateTime.now();
        // Mise à jour du lien avec la date
        user.setCreatedDate(today);


        return userRepository.save(user);
    }*/




}
