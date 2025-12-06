package com.ohgiraffers.secondbackend.user.service;

import com.ohgiraffers.secondbackend.user.entity.User;
import com.ohgiraffers.secondbackend.user.entity.UserRole;
import com.ohgiraffers.secondbackend.user.repository.UserRepository;
import com.ohgiraffers.secondbackend.user.util.JWTUtil;
import io.netty.util.internal.StringUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService  implements UserDetailsService{

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;


    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JWTUtil jwtUtil,
                       RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));
    }

   //회원가입
    @Transactional
    public User signup(String username, String password, String nickname){
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("이미 존재함");
        }

        User user= User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(UserRole.USER)
                .build();

        return userRepository.save(user);
    }

    //로그인
    public String[] login(String username,String rawPassword){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("잘못된 아이디"));


        if(!passwordEncoder.matches(rawPassword,user.getPassword())){
            throw new IllegalArgumentException("잘못된 비번");
        }

        String role=user.getRole().name();
        String accessToken=jwtUtil.createAccessToken(username,role);
        String refreshToken=jwtUtil.createRefreshToken(username,role);

        String refreshKey="refresh:"+username;
        //28일간 저장
        redisTemplate.opsForValue().set(refreshKey,refreshToken,28, TimeUnit.DAYS);

        return new String[]{accessToken,refreshToken};
    }



}
