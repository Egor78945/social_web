package com.example.socialweb.models.requestModels;

import lombok.Data;

@Data
public class BanModel {
    private Long userId;
    private String reason;
}
