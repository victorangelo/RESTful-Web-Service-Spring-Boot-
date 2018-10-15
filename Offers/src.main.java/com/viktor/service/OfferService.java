package com.viktor.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.viktor.offer.Offer;

public interface OfferService {

	public Optional<Offer> getOfferById(long id);
	public Iterable<Offer> getAllOffers();
	public ResponseEntity<Offer> add(Offer offer);
	public Offer deleteOffer(long id);
	public Offer cancelOffer(long id);
}
