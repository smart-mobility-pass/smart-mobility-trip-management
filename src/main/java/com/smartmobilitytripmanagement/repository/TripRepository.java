package com.smartmobilitytripmanagement.repository;

import com.smartmobilitytripmanagement.beans.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUserId(String userId);

    Optional<Trip> findFirstByUserIdAndStatus(String userId, String status);
}
