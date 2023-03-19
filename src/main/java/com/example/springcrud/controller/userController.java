package com.example.springcrud.controller;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springcrud.model.User;
import com.example.springcrud.repository.userRepository;

@RequestMapping(value = "/")
@RestController

public class userController {

    @Autowired
    private userRepository userRepository;

    


    //
    //
    //
    //error Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // private String[] students = {"Jonathan","John","David"};

    // @GetMapping("/test")
    // public List<String> getstudent (){
    //    return List.of(students);
    // }


   //
   //
   //
   //
   //
    //Get All user
    @GetMapping("/getuser")
   public ResponseEntity<List<User>> getuser(){
        try{
       
            List<User> users= userRepository.findAll();

            if(users.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users,HttpStatus.OK);

        }catch(Exception e){
            throw new RuntimeException("Error retrieving all users", e);
        }
    }
   //
   //
   //
   //
    //Get Specific user 
    @GetMapping("/getuser/{id}")
   public ResponseEntity<Optional<User>> getUserWithId(@PathVariable("id") Long id ){
    try {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
        return new ResponseEntity<>(user,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    } catch (Exception e) {
             throw new RuntimeException("Error retrieving user with id " + id, e);
    }
   }

   //
   //
   //
   //
   //
    //Create user
    @PostMapping("/createuser")
    public ResponseEntity<User> createUser(@RequestBody User newUser ){
    try {
     return new ResponseEntity<>(userRepository.save(newUser),HttpStatus.CREATED);
    } catch (Exception e) {
     
     throw new RuntimeException("Error creating new user ", e);
    }
    }
   //
   //
   //
   //
   //
    //Delete user
    @DeleteMapping("/deleteuser/{id}")
    ResponseEntity<Optional<User>> deleteUser(@PathVariable("id") Long id ){
     
    try {
       
        Optional<User> deleted = userRepository.findById(id);
        userRepository.deleteById(id);
        return new ResponseEntity<>(deleted,HttpStatus.NO_CONTENT);
        
    } catch (Exception e) {
        throw new RuntimeException("Error deleting user with id " + id, e);
    }
    }
   //
   //
   //
   //
   //
    //Update user
    @PutMapping("/edituser/{id}")
    ResponseEntity<User> editUser(@RequestBody User user,@PathVariable("id") Long id){
        try {
        Optional<User> existingUser = userRepository.findById(id);
        User updateUser = existingUser.get();
         updateUser.setName(user.getName());
         updateUser.setEmail(user.getEmail());
        return new ResponseEntity<>(userRepository.save(existingUser.get()), HttpStatus.OK);
        } catch (Exception e) {
         throw new RuntimeException("Error Updating user with id " + id, e);
        }
    }

    
}
