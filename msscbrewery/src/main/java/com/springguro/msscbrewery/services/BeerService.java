package com.springguro.msscbrewery.services;

import java.util.UUID;

import com.springguro.msscbrewery.web.model.BeerDto;

public interface BeerService {
	BeerDto getBeerById(UUID beerId);
	BeerDto saveNewBeer(BeerDto beerDto);
}
