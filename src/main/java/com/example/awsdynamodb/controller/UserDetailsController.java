package com.example.awsdynamodb.controller;

import com.example.awsdynamodb.dao.UserDetailsRepositoryV2;
import com.example.awsdynamodb.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tapas
 *
 */
@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsRepositoryV2 dao;

    @PostMapping("/UserDetails")
    public String addUserDetails(@RequestBody UserDetails userDetail) {
        return dao.addUserDetails(userDetail);
    }

    @GetMapping("/UserDetails/{key}")
    public UserDetails getUserDetails(@PathVariable("key") String key) {
        return dao.getUserDetails(key);
    }

}