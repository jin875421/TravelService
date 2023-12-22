package com.example.android.service;

import com.example.android.entity.RecoAttractionImg;
import com.example.android.repository.RecoAttractionImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecoAttractionImgService {
    @Autowired
    private RecoAttractionImgRepository recoAttractionImgRepository;
    public List<RecoAttractionImg> getRecoAttractionImgList(){
        return recoAttractionImgRepository.findAll();
    }
}
