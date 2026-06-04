package moe.prashast.security.service;

import moe.prashast.entity.User;
import moe.prashast.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] parts = username.split(":");
        if (parts.length != 3) {
            throw new UsernameNotFoundException("Invalid username format");
        }
        String role = parts[0];
        String udiseCode  = parts[1];
        String mobile=parts[2];
        User user;
        if (role.equals("1")) {
            user = userRepository.findByUdiseAndRole(udiseCode, Short.parseShort(role)).orElse(null);
        } else if(role.equals("2")) {
            user = userRepository.findByUdiseAndRoleAndMobile (udiseCode,Short.parseShort(role), mobile).orElse(null);
        }
        else {
            List<User> seUser = userRepository.findByPhoneMobileAndRole(mobile,Short.parseShort(role));
            user=seUser.get(0);
        }
        return  new CustomUserDetails(user,role);
    }

}
