package com.example.android.service;

import com.example.android.entity.RecoAttraction;
import com.example.android.entity.RecoAttractionImg;
import com.example.android.repository.RecoAttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecoAttractionService {

    @Autowired
    private RecoAttractionRepository recoAttractionRepository;
    @Autowired
    private RecoAttractionImgService recoAttractionImgService;

    public List<RecoAttraction> getRecoAttractionList() {
        List<RecoAttraction> recoAttractionList = recoAttractionRepository.findAll();
        for (RecoAttraction recoAttraction : recoAttractionList) {
            List<RecoAttractionImg> recoAttractionImgList = recoAttractionImgService.findByAttractionId(recoAttraction.getAttractionId());
            for (RecoAttractionImg recoAttractionImg : recoAttractionImgList) {
                recoAttraction.getImgUrls().add(recoAttractionImg.getImgUrl());
            }
        }
        return recoAttractionList;
    }
}
