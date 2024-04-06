package com.example.techsupport.services.converters;

import com.example.techsupport.models.entities.TechSupportRequest;
import com.example.techsupport.models.requestModels.TechSupportRequestModel;

public class TechSupportRequestConverter {
    public static TechSupportRequest convert(TechSupportRequestModel techSupportRequestModel){
        return new TechSupportRequest(techSupportRequestModel);
    }
}
