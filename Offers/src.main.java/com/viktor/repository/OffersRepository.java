package com.viktor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.viktor.offer.Offer;

@Repository("offersRepository")
public interface OffersRepository extends CrudRepository<Offer, Long> {

}
