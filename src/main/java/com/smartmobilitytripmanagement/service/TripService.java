package com.smartmobilitytripmanagement.service;

import com.smartmobilitytripmanagement.beans.Trip;

import java.util.List;

public interface TripService {
    Trip startTrip(String userId, String transportType, String startLocation, Long transportLineId);

    Trip completeTrip(Long tripId, String endLocation, Long transportLineId);

    Trip cancelTrip(Long tripId);

    Trip saveTrip(Trip trip);

    List<Trip> findAll();

    List<Trip> getUserHistory(String userId);

    Trip getActiveTrip(String userId);

    Trip getTripById(Long id);
}
