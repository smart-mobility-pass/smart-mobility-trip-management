package com.smartmobilitytripmanagement.service;

import com.smartmobilitytripmanagement.beans.Trip;

import java.util.List;

public interface TripService {
    Trip startTrip(Long userId, String transportType, String startLocation);

    Trip completeTrip(Long tripId, String endLocation);

    Trip cancelTrip(Long tripId);

    Trip saveTrip(Trip trip);

    List<Trip> findAll();

    List<Trip> getUserHistory(Long userId);
}
