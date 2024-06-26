package com.example.android.service;

import com.example.android.entity.Personal;
import com.example.android.repository.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalService {
    @Autowired
    private PersonalRepository personalRepository;


    public Personal loadBackground(String userId){
        Personal personal=new Personal();
        personal=personalRepository.findByUserId(userId);
        return personal;
    }
    public boolean addPersonal(Personal personal){
        if(personal!=null){
            personalRepository.save(personal);
            return true;
        }else{
            return false;
        }

    }
}
