package leanrning.springsecurity.service;

import leanrning.springsecurity.CoustomerUserDetail;
import leanrning.springsecurity.Repository.UserRepo;
import leanrning.springsecurity.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class CustomerUserDetailService implements UserDetailsService {

    public CustomerUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(Objects.isNull(user)){
            System.out.println(username + " not found");
            throw new UsernameNotFoundException(username + " not found");
        }
        return new CoustomerUserDetail(user);
    }
}
