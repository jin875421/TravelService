package com.example.android.service;

import com.example.android.entity.RecoAttraction;
import com.example.android.repository.RecoAttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecoAttractionService {

    @Autowired
    private RecoAttractionRepository recoAttractionRepository;

    public List<RecoAttraction> getRecoAttractionList() {
        return recoAttractionRepository.findAll();
    }
}
