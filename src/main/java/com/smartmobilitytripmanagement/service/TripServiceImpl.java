package com.smartmobilitytripmanagement.service;

import com.smartmobilitytripmanagement.beans.Trip;
import com.smartmobilitytripmanagement.dto.TripCompletedEvent;
import com.smartmobilitytripmanagement.error.ResourceNotFoundException;
import com.smartmobilitytripmanagement.proxy.UserProxy;
import com.smartmobilitytripmanagement.publisher.TripPublisher;
import com.smartmobilitytripmanagement.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private TripPublisher tripPublisher;

    // 1. Démarrer un trajet
    @Override
    public Trip startTrip(Long userId, String transportType, String startLocation) {
        userProxy.getUserById(userId);

        Trip trip = new Trip(userId, transportType, startLocation);
        return tripRepository.save(trip);
    }

    // 2. Terminer un trajet
    @Override
    public Trip completeTrip(Long tripId, String endLocation) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet introuvable avec l'ID: " + tripId));

        if (!"STARTED".equals(trip.getStatus())) {
            throw new IllegalStateException("Impossible de terminer : le trajet est déjà " + trip.getStatus());
        }

        trip.setEndLocation(endLocation);
        trip.setEndTime(LocalDateTime.now());
        trip.setStatus("COMPLETED");

        Trip savedTrip = tripRepository.save(trip);

        // Envoyer l'événement à RabbitMQ
        TripCompletedEvent event = TripCompletedEvent.builder()
                .tripId(savedTrip.getId())
                .userId(savedTrip.getUserId())
                .transportType(savedTrip.getTransportType())
                .startTime(savedTrip.getStartTime())
                .startLocation(savedTrip.getStartLocation())
                .endTime(savedTrip.getEndTime())
                .endLocation(savedTrip.getEndLocation())
                .status(savedTrip.getStatus())
                .build();

        tripPublisher.publishTripCompleted(event);

        return savedTrip;
    }

    // 3. Annuler un trajet
    @Override
    public Trip cancelTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Impossible d'annuler : Trajet introuvable"));

        if ("COMPLETED".equals(trip.getStatus())) {
            throw new IllegalStateException("Impossible d'annuler un trajet déjà terminé.");
        }

        trip.setStatus("CANCELLED");
        return tripRepository.save(trip);
    }

    @Override
    public Trip saveTrip(Trip trip) {
        userProxy.getUserById(trip.getUserId());
        return tripRepository.save(trip);
    }

    @Override
    public List<Trip> getUserHistory(Long userId) {
        userProxy.getUserById(userId);
        return tripRepository.findByUserId(userId);
    }

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }
}
