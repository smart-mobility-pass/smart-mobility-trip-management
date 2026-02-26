package com.smartmobilitytripmanagement.service;

import com.smartmobilitytripmanagement.beans.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


public interface TripService {
    Trip startTrip(Long userId, String transportType, String startLocation);
    Trip completeTrip(Long tripId, String endLocation, Double price);
    Trip cancelTrip(Long tripId);

    Trip saveTrip(Trip trip);
    List<Trip> findAll();
    List<Trip> getUserHistory(Long userId);
}
