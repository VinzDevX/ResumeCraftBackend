package com.resumecraft.ResumeCraft.controller;

import com.resumecraft.ResumeCraft.config.JwtProvider;
import com.resumecraft.ResumeCraft.dto.LoginRequest;
import com.resumecraft.ResumeCraft.dto.response.AuthResponse;
import com.resumecraft.ResumeCraft.exception.UserException;
import com.resumecraft.ResumeCraft.model.User;
import com.resumecraft.ResumeCraft.repository.UserRepository;
import com.resumecraft.ResumeCraft.services.CustomUserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserServices customUserServices;
//user
    //       for register User  Account
    @PostMapping("/registerUser")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)throws UserException {
        String email=user.getEmail();
        String password=user.getPassword();
        String firstName=user.getFirstName();
        String lastName=user.getLastName();

        User isEmailExist=userRepository.findByEmail(email);
        if(isEmailExist!=null){
            throw new UserException("Email is Already Used With Another Account ");
        }
        User createdUser=new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setRole("USER");

        User savedUser=userRepository.save(createdUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT after registration
        String jwt = jwtProvider.generateToken(authentication, savedUser.getId()); // Pass savedUser.getId()

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Signup Success");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    //for login
    @PostMapping("/loginUser")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Retrieve the user by email (or username)
        User loggedInUser = userRepository.findOptionalByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Generate JWT token with userId
        String jwt = jwtProvider.generateToken(authentication, loggedInUser.getId()); // Pass loggedInUser.getId()

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully");
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
//user
//admin
    //       for register Admin  Account
    @PostMapping("/registerAdmin")
    public ResponseEntity<AuthResponse> createAdminHandler(@RequestBody User user)throws UserException {
        String email=user.getEmail();
        String password=user.getPassword();
        String firstName=user.getFirstName();
        String lastName=user.getLastName();

        User isEmailExist=userRepository.findByEmail(email);
        if(isEmailExist!=null){
            throw new UserException("Email is Already Used With Another Account ");
        }
        User createdUser=new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setRole("ADMIN");

        User savedUser=userRepository.save(createdUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT after registration
        String jwt = jwtProvider.generateToken(authentication, savedUser.getId()); // Pass savedUser.getId()

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Signup Success");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    //for login
    @PostMapping("/loginAdmin")
    public ResponseEntity<AuthResponse> loginAdminHandler(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Retrieve the user by email (or username)
        User loggedInUser = userRepository.findOptionalByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Generate JWT token with userId
        String jwt = jwtProvider.generateToken(authentication, loggedInUser.getId()); // Pass loggedInUser.getId()

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully");
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

//admin
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserServices.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username...");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}
