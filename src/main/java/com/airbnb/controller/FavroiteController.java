package com.airbnb.controller;

import com.airbnb.entity.Favroite;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.FavroiteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/favroites")
public class FavroiteController {

    //nested object approch
    private FavroiteRepository favroiteRepository;

    public FavroiteController(FavroiteRepository favroiteRepository) {
        this.favroiteRepository = favroiteRepository;
    }
    @PostMapping
    public ResponseEntity<Favroite>  addFavourite(
            @RequestBody Favroite favroite,
            @AuthenticationPrincipal PropertyUser user
            ){
        favroite.setPropertyUser(user);
        Favroite savedFav = favroiteRepository.save(favroite);
        return new ResponseEntity<>(savedFav, HttpStatus.CREATED);
    }
}
