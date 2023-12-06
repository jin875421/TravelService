package com.example.android.repository;

import com.example.android.entity.VariableInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VariableRespository extends JpaRepository<VariableInfo, String>, JpaSpecificationExecutor<VariableInfo> {

    int findByNameNum(String NameNum);
}
