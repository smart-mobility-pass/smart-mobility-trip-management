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
            @RequestParam String userId,
            @RequestParam String transportType,
            @RequestParam String startLocation,
            @RequestParam Long transportLineId) {

        Trip newTrip = tripService.startTrip(userId, transportType, startLocation, transportLineId);
        return ResponseEntity.ok(newTrip);
    }

    // 2. Terminer un trajet (met à jour la fin)
    @PutMapping("/complete/{id}")
    public ResponseEntity<Trip> completeTrip(
            @PathVariable Long id,
            @RequestParam String endLocation,
            @RequestParam Long transportLineId) {

        Trip completedTrip = tripService.completeTrip(id, endLocation, transportLineId);
        return ResponseEntity.ok(completedTrip);
    }

    // 3. Lire l'historique d'un utilisateur
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Trip>> getHistory(@PathVariable String userId) {
        List<Trip> history = tripService.getUserHistory(userId);
        return ResponseEntity.ok(history);
    }

    // 4. Récupérer le trajet actif (si existant)
    @GetMapping("/active/{userId}")
    public ResponseEntity<Trip> getActive(@PathVariable String userId) {
        return ResponseEntity.ok(tripService.getActiveTrip(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        return trip != null ? ResponseEntity.ok(trip) : ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<Trip> save(@RequestBody Trip trip) {
        return ResponseEntity.ok(tripService.saveTrip(trip));
    }
}
