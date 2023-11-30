package com.springguro.msscbrewery.web.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springguro.msscbrewery.services.BeerService;
import com.springguro.msscbrewery.web.model.BeerDto;

@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

	@Autowired
    private BeerService beerService;

    @GetMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.OK)
    public BeerDto getBeer(@PathVariable("beerId") UUID beerId){
        return beerService.getBeerById(beerId);
    }

}
