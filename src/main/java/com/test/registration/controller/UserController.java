package com.test.registration.controller;

import com.test.registration.data.ResponseMessage;
import com.test.registration.data.User;
import com.test.registration.exception.UserAlreadyDeletedException;
import com.test.registration.exception.UserNotFoundException;
import com.test.registration.exception.UsernameAlreadyExistsException;
import com.test.registration.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Operation(summary = "Register User", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered user"),
            @ApiResponse(responseCode = "400", description = "Invalid Request")
    })
    @PostMapping()
    public ResponseEntity<ResponseMessage> registerUser(@Valid @RequestBody User user) throws UsernameAlreadyExistsException {
        logger.info("Start to register new user {}", user.getUsername());
        userService.registerUser(user);
        logger.info("User {} registered successfully", user.getUsername());
        return new ResponseEntity<>(new ResponseMessage("User " + user.getUsername() + " is now registered"),HttpStatus.CREATED);
    }

    @Operation(summary = "Update User", description = "Update a registered user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user"),
            @ApiResponse(responseCode = "400", description = "Invalid Request")
    })
    @PutMapping()
    public ResponseEntity<ResponseMessage> updateUser(@Valid @RequestBody User user) throws UserNotFoundException, UserAlreadyDeletedException {
        logger.info("Start to update user {}", user.getUsername());
        userService.updateUser(user);
        logger.info("User {} updated successfully", user.getUsername());
        return new ResponseEntity<>(new ResponseMessage("User " + user.getUsername() + " is updated"),HttpStatus.OK);
    }

    @Operation(summary = "Delete User", description = "Delete a registered user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted user"),
            @ApiResponse(responseCode = "400", description = "Invalid Request")
    })
    @DeleteMapping("/{username}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable String username) throws UserNotFoundException, UserAlreadyDeletedException {
        logger.info("Start to delete user {}", username);
        userService.softDeleteUser(username);
        logger.info("User {} deleted successfully", username);
        return new ResponseEntity<>(new ResponseMessage("User " + username + " is now deleted"),HttpStatus.OK);
    }

    @Operation(summary = "Get All Users", description = "Retrieve the list of all registered user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved registered users"),
    })
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Start to retrieve all registered users");
        var users = userService.listAllRegisteredUsers();
        logger.info("All registered users retrieved successfully");
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}