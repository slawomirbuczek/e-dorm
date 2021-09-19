package com.edorm.models.validations;

import com.edorm.models.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationExceptionMessages {

    private List<ResponseMessage> exceptionsMessage;

}