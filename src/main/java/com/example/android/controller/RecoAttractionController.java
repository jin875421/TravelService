package com.example.android.controller;

import com.example.android.entity.RecoAttraction;
import com.example.android.entity.RecoAttractionImg;
import com.example.android.service.RecoAttractionImgService;
import com.example.android.service.RecoAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recoattraction")
public class RecoAttractionController {
    @Autowired
    private RecoAttractionService recoAttractionService;
    @Autowired
    private RecoAttractionImgService recoAttractionImgService;

    @GetMapping("/getRecoAttractionList")
    public List<RecoAttraction> getRecoAttractionList(){
        List<RecoAttraction> recoAttractionList = recoAttractionService.getRecoAttractionList();
        List<RecoAttractionImg> recoAttractionImgList = recoAttractionImgService.getRecoAttractionImgList();
        for (RecoAttraction recoAttraction : recoAttractionList) {
            for (RecoAttractionImg recoAttractionImg : recoAttractionImgList) {
                if (recoAttraction.getAttractionId().equals(recoAttractionImg.getAttractionId())){
                    recoAttraction.getTraRecoAttractionImgList().add(recoAttractionImg);
                }
            }
        }
        return recoAttractionList;
    }
}
