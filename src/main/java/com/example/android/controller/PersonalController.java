package com.example.android.controller;

import com.example.android.entity.Personal;
import com.example.android.entity.UserInfo;
import com.example.android.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personal")
public class PersonalController {
    @Autowired
    private PersonalService personalService;
    @GetMapping("/getBackground")
    public Personal getBackground(@RequestParam("userId") String userId) {
        Personal personal=new Personal();
        if (personal != null) {
            return personal;
        } else {
            // Handle the case when userInfo is null
            return null;
        }
    }
}
