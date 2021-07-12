package de.tuberlin.tkn.lit.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import de.tuberlin.tkn.lit.model.Requests.LoginRequest;
import de.tuberlin.tkn.lit.model.Requests.SignupRequest;
import de.tuberlin.tkn.lit.model.Responses.JwtResponse;
import de.tuberlin.tkn.lit.model.Responses.MessageResponse;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.security.jwt.JwtUtils;
import de.tuberlin.tkn.lit.security.services.UserDetailsImpl;
import de.tuberlin.tkn.lit.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthController {
    @Autowired
    Storage storage;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

   /* @PostConstruct
    public void init(){
        SignupRequest testUser1 = new SignupRequest();
        testUser1.setUsername("testuser1");
        testUser1.setPassword("password");
        registerUser(testUser1);

        SignupRequest testUser2 = new SignupRequest();
        testUser2.setUsername("testuser2");
        testUser2.setPassword("password");
        registerUser(testUser2);
    } */

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (storage.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Create new user's account
        Person user = new Person(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        storage.createActor(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername()));
    }
}
