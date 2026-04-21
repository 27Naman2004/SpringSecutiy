package leanrning.springsecurity.controller;


import leanrning.springsecurity.Repository.UserRepo;
import leanrning.springsecurity.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserController {

    private final UserRepo userRepo;
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/reg")
    public User register(@RequestBody User user){
        return userRepo.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        var result = userRepo.findByUsername(user.getUsername());
        var ans = userRepo.findByPassword(user.getPassword());
        if (!Objects.isNull(result) && !Objects.isNull(ans))
            return "Success";
        else return "Fail";
    }
}