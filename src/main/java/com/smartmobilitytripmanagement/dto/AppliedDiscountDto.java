package com.smartmobilitytripmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppliedDiscountDto {
    private String ruleType;
    private BigDecimal percentage;
    private BigDecimal amountDeducted;
}