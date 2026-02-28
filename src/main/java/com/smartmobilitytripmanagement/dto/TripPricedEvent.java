package com.smartmobilitytripmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripPricedEvent {
        private Long tripId;
        private Long userId;
        private BigDecimal basePrice;
        private List<AppliedDiscountDto> appliedDiscounts;
        private BigDecimal finalAmount;
}
