package com.smartmobilitytripmanagement.controller;

import com.smartmobilitytripmanagement.beans.Trip;
import com.smartmobilitytripmanagement.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    // 1. Enregistrer/Démarrer un trajet
    @PostMapping("/start")
    public ResponseEntity<Trip> startTrip(
            @RequestParam Long userId,
            @RequestParam String transportType,
            @RequestParam String startLocation) {

        Trip newTrip = tripService.startTrip(userId, transportType, startLocation);
        return ResponseEntity.ok(newTrip);
    }

    // 2. Terminer un trajet (met à jour la fin)
    @PutMapping("/complete/{id}")
    public ResponseEntity<Trip> completeTrip(
            @PathVariable Long id,
            @RequestParam String endLocation) {

        Trip completedTrip = tripService.completeTrip(id, endLocation);
        return ResponseEntity.ok(completedTrip);
    }

    // 3. Lire l'historique d'un utilisateur
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Trip>> getHistory(@PathVariable Long userId) {
        List<Trip> history = tripService.getUserHistory(userId);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/save")
    public ResponseEntity<Trip> save(@RequestBody Trip trip) {
        return ResponseEntity.ok(tripService.saveTrip(trip));
    }
}
