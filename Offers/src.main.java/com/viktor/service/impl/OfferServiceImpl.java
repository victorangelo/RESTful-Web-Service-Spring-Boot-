package com.viktor.service.impl;

import java.net.URI;
import java.util.Optional;

import com.viktor.exception.OfferNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.viktor.offer.Offer;
import com.viktor.repository.OffersRepository;
import com.viktor.service.OfferService;

@Service("offerService")
public class OfferServiceImpl implements OfferService{

	@Autowired
	OffersRepository offersRepository; 
	

	@Override
	public Iterable<Offer> getAllOffers() {
		return offersRepository.findAll();
	}


	@Override
	public Optional<Offer> getOfferById(long id) {
		Optional<Offer> optionalOffer = offersRepository.findById(id);
        if (!optionalOffer.isPresent()) {
            throw new OfferNotFoundException(id);
        } else {
        	offersRepository.delete(optionalOffer.get());
            return optionalOffer;
        }
	}


	@Override
	public ResponseEntity<Offer> add(Offer offer) {
		Offer result = offersRepository.save(offer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/id")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();
	}


	@Override
	public Offer deleteOffer(long id) {
		Optional<Offer> optionalOffer = offersRepository.findById(id);
        if (!optionalOffer.isPresent()) {
            throw new OfferNotFoundException(id);
        } else {
        	offersRepository.delete(optionalOffer.get());
            return optionalOffer.get();
        }
	}


	@Override
	public Offer cancelOffer(long id) {
		Optional<Offer> optionalOffer = offersRepository.findById(id);
        if (!optionalOffer.isPresent()) {
            throw new OfferNotFoundException(id);
        } else {
        	optionalOffer.get().setValid(false);
        	offersRepository.save(optionalOffer.get());
            return optionalOffer.get();
        }
	}
}
