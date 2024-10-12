package com.airbnb.repository;

import com.airbnb.entity.Favroite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavroiteRepository extends JpaRepository<Favroite, Long> {
}