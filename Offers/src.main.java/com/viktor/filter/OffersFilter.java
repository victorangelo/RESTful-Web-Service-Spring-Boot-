package com.viktor.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.viktor.offer.Offer;

public class OffersFilter {

	public static List<Offer> filterOffers(Iterable<Offer> offers){
		Iterator<Offer> iOffers = offers.iterator();
		List<Offer> result = new ArrayList<Offer>();
		while(iOffers.hasNext()){
			Offer offer = iOffers.next();
			if(filterOffer(offer) == null){
				iOffers.remove();
			}else{
				result.add(offer);
			}
		}
		
		return result;
	}
	
	public static Offer filterOffer(Offer offer){
		if(offer.getExpires().before(new Date())){
			return null;
		}else {
			return offer;
		}
	}
	
}
