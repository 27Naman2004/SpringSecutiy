package leanrning.springsecurity.service;

import leanrning.springsecurity.Repository.UserRepo;
import leanrning.springsecurity.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepo userRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;


    public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

//        var result = userRepo.findByUsername(user.getUsername());
//        var ans = userRepo.findByPassword(user.getPassword());
        if (authentication.isAuthenticated())
            return jwtService.generateToken(user);
        else return "Fail";
    }
}
