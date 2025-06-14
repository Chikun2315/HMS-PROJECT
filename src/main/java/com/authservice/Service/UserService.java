package com.authservice.Service;


import com.authservice.Entity.User;
import com.authservice.Payload.APIResponse;
import com.authservice.Payload.UserDto;
import com.authservice.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
@Autowired
private UserRepository userRepository;
@Autowired
private PasswordEncoder passwordEncoder;


public APIResponse<String> registerUser(UserDto userDto){
    APIResponse<String> response = new APIResponse<>();
    // cheack if the username exist or not
    if(userRepository.existsByUsername(userDto.getUsername())){

        response.setMessage("Registration failed");
        response.setStatusCode(409);
        response.setData("Username alredy exists");
    return response;
    }
    // cheack by email
    if(userRepository.existsByEmail(userDto.getEmail())){
       response.setMessage("Registratrion failed");
       response.setStatusCode(409);
       response.setData("Email alredy exists");
        return response;
    }


    // encrypt the password and save
    String encryptPassword = passwordEncoder.encode(userDto.getPassword());
    User user = new User();
    BeanUtils.copyProperties(userDto, user);
    user.setPassword(encryptPassword);
    user.setRole("ROLE_ADMIN");
    userRepository.save(user);


    //return response with API response
    response.setMessage("OK");
    response.setStatusCode(200);
    response.setData("Registration sucessful");
   return response;
}













    User ConvertToEntity(UserDto userDto){
        User ur = new User();
        ur.setId(userDto.getId());
        ur.setName(userDto.getName());
        ur.setEmail(userDto.getEmail());
        ur.setMobile(userDto.getMobile());
        return ur;
    }

    UserDto convertToDto(User user){
        UserDto urd = new UserDto();
        urd.setId(user.getId());
        urd.setName(user.getName());
        urd.setEmail(user.getEmail());
        urd.setMobile(user.getMobile());
        return urd;

    }

}
