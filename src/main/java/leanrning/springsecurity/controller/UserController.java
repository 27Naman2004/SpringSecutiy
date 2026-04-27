package leanrning.springsecurity.controller;


import leanrning.springsecurity.Repository.UserRepo;
import leanrning.springsecurity.entity.User;
import leanrning.springsecurity.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserController {

    private final UserRepo userRepo;

    private final UserService userService;

    public UserController(UserRepo userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @PostMapping("/reg")
    public User register(@RequestBody User user){
//        return userRepo.save(user);
        return userService.save(user);
    }


    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.verify(user);
    }
}