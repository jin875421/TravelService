package com.example.androdid.repository;

import com.example.androdid.entity.VariableInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VariableRespository extends JpaRepository<VariableInfo, String>, JpaSpecificationExecutor<VariableInfo> {

    int findByNameNum(String NameNum);
}
