package com.example.techsupport.models.entities;

import com.example.techsupport.models.requestModels.TechSupportRequestModel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "requests")
@Data
public class TechSupportRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "sender_id")
    private Long senderId;
    @Column(name = "message")
    private String message;
    @Column(name = "date")
    private String date;
    public TechSupportRequest(TechSupportRequestModel requestModel){
        this.senderId = requestModel.getSenderId();
        this.message = requestModel.getMessage();
        this.date = requestModel.getDate();
    }
    public TechSupportRequest(){

    }

}
