package com.smartmobilitytripmanagement.service;

import com.smartmobilitytripmanagement.beans.Trip;
import com.smartmobilitytripmanagement.dto.UserDTO;
import com.smartmobilitytripmanagement.proxy.UserProxy;
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

    // 1. Démarrer un trajet
    @Override
    public Trip startTrip(Long userId, String transportType, String startLocation) {
        // Appel au microservice User via Feign
        UserDTO user = userProxy.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable. Trajet refusé.");
        }

        // Création du trajet via le constructeur personnalisé
        Trip trip = new Trip(userId, transportType, startLocation);
        return tripRepository.save(trip);
    }

    // 2. Terminer un trajet avec calcul de prix
    @Override
    public Trip completeTrip(Long tripId, String endLocation, Double price) {
        Trip trip = tripRepository.findById(tripId) // Utilisation directe du Long
                .orElseThrow(() -> new RuntimeException("Trajet introuvable ID: " + tripId));

        if (!"STARTED".equals(trip.getStatus())) {
            throw new RuntimeException("Impossible de terminer : le trajet est déjà " + trip.getStatus());
        }

        trip.setEndLocation(endLocation);
        trip.setEndTime(LocalDateTime.now());
        trip.setCalculatedPrice(price);
        trip.setStatus("COMPLETED");

        return tripRepository.save(trip);
    }

    // 3. Annuler un trajet
    @Override
    public Trip cancelTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trajet introuvable"));

        if ("COMPLETED".equals(trip.getStatus())) {
            throw new RuntimeException("Impossible d'annuler un trajet déjà terminé.");
        }

        trip.setStatus("CANCELLED");
        return tripRepository.save(trip);
    }

    // 4. Sauvegarde générique (utilisée par ton endpoint /save)
    @Override
    public Trip saveTrip(Trip trip) {
        // On s'assure que l'ID utilisateur est valide avant de persister
        UserDTO user = userProxy.getUserById(trip.getUserId());
        if (user == null) {
            throw new RuntimeException("Sauvegarde impossible : Utilisateur " + trip.getUserId() + " n'existe pas.");
        }
        return tripRepository.save(trip);
    }

    // 5. Historique (méthode appelée par le Controller)
    @Override
    public List<Trip> getUserHistory(Long userId) {
        return tripRepository.findByUserId(userId);
    }

    // 6. Historique (version alternative du Service)
    public List<Trip> getHistory(Long userId) {
        return tripRepository.findByUserId(userId);
    }

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }
}
