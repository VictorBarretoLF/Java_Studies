package com.springguro.msscbrewery.services.v2;

import java.util.UUID;

import com.springguro.msscbrewery.web.model.v2.BeerDtoV2;

public interface BeerServiceV2 {
    BeerDtoV2 getBeerById(UUID beerId);

    BeerDtoV2 saveNewBeer(BeerDtoV2 beerDto);

    void updateBeer(UUID beerId, BeerDtoV2 beerDto);

    void deleteById(UUID beerId);
}
