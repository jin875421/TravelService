package com.example.android.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class VariableInfo {
    @Id
    private String VariableId;
    private int nameNum;

    public String getVariableId() {
        return VariableId;
    }

    public void setVariableId(String variableId) {
        VariableId = variableId;
    }

    public int getNameNum() {
        return nameNum;
    }

    public void setNameNum(int NameNum) {
        this.nameNum = nameNum;
    }
}
