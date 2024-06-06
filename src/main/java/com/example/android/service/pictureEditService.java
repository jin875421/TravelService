package com.example.android.service;

import com.example.android.entity.PictureEdit;
import com.example.android.repository.pictureEditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class pictureEditService {
    @Autowired
    private pictureEditRepository pictureEditRepository;
    public PictureEdit loadPicture(String userId){
        PictureEdit pictureEdit=new PictureEdit();
        pictureEdit=pictureEditRepository.findByUserId(userId);
        return pictureEdit;
    }
    public boolean addPicture(PictureEdit pictureEdit){
        if(pictureEdit!=null){
            pictureEditRepository.save(pictureEdit);
            return true;
        }else{
            return false;
        }

    }
}
