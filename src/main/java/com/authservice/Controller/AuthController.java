package com.authservice.Controller;

import com.authservice.Entity.User;
import com.authservice.Payload.APIResponse;
import com.authservice.Payload.LoginDto;
import com.authservice.Payload.UserDto;
import com.authservice.Service.JWTService;
import com.authservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;
     @Autowired
    private AuthenticationManager authenticationManager;
      @Autowired
     private JWTService jwtService;

    @PostMapping("/post")
    public ResponseEntity<APIResponse<String>> registeruser( @RequestBody UserDto userDto) {

        APIResponse<String> response = userService.registerUser(userDto);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

     @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> login( @RequestBody LoginDto loginDto){
        APIResponse<String> response = new APIResponse<>();
         UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
         try{
            Authentication authenticate = authenticationManager.authenticate(token);
            if(authenticate.isAuthenticated()){
                String jwtToken = jwtService.generateToken(loginDto.getUsername(), authenticate.getAuthorities().iterator().next().getAuthority());
                response.setMessage("login sucessful");
                response.setStatusCode(200);
                response.setData(jwtToken);
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
            }
        }catch (Exception e){
e.printStackTrace();
        }
         response.setMessage("Access denied");
         response.setStatusCode(401);
         response.setData("Login denied");
         return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }



}
