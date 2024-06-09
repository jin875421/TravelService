package com.example.android.service;

import com.example.android.entity.PictureEdit;
import com.example.android.repository.pictureEditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<PictureEdit> getPictureList(String userId) {
        if(userId!=null){
            List<PictureEdit> pictureEditList=pictureEditRepository.findPictureEditsByUserId(userId);
            return pictureEditList;
        }else{
            return null;
        }
    }
    public void deletePicture(String pictureUrl){
        pictureEditRepository.deleteByPictureUrl(pictureUrl);
    }
}
