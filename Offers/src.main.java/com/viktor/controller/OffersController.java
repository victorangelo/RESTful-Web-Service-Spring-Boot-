package com.viktor.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.viktor.filter.OffersFilter;
import com.viktor.offer.Offer;
import com.viktor.service.OfferService;

@RestController
public class OffersController {

	private static final Logger logger = Logger.getLogger(OffersController.class);

	@Autowired
	private OfferService offerService;

	@RequestMapping(value = "/ad", method = RequestMethod.GET)
	public @ResponseBody List<Offer> getOffers() {
		List<Offer> response = OffersFilter.filterOffers(offerService.getAllOffers());

		if (logger.isDebugEnabled()) {
			logger.debug("getOffers() result: '" + response + "'");
		}

		return response;
	}

	@RequestMapping(value = "/ad/{id}", method = RequestMethod.GET)
	public @ResponseBody Offer getOffer(@PathVariable("id") long id) {
		Offer response = OffersFilter.filterOffer(offerService.getOfferById(id).get());

		if (logger.isDebugEnabled()) {
			logger.debug("getOffer(long id) result: '" + response + "'");
		}

		return response;
	}

	@RequestMapping(method = POST)
	public @ResponseBody ResponseEntity<Offer> add(@Valid @RequestBody Offer offer) {
		ResponseEntity<Offer> response = offerService.add(offer);

		if (logger.isDebugEnabled()) {
			logger.debug("add(Offer) result: '" + response.getBody() + "'");
		}

		return response;
	}

	@RequestMapping(method = DELETE, value = "/{id}")
	public @ResponseBody Offer deleteOffer(@PathVariable("id") long id) {
		Offer response = offerService.deleteOffer(id);

		if (logger.isDebugEnabled()) {
			logger.debug("deleteOffer(long id) result: '" + response + "'");
		}

		return response;
	}

	@RequestMapping(value = "/cancel/{id}", method = POST)
	public @ResponseBody Offer cancelOffer(@PathVariable("id") long id) {
		Offer response = offerService.cancelOffer(id);

		if (logger.isDebugEnabled()) {
			logger.debug("cancelOffer(long id) result: '" + response + "'");
		}

		return response;
	}

}
