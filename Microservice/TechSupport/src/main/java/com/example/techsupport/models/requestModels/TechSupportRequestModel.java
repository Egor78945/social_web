package com.example.techsupport.models.requestModels;

import lombok.Data;

import java.io.Serializable;

@Data
public class TechSupportRequestModel {
    private Long senderId;
    private String message;
    private String date;
}
