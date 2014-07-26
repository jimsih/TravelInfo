package model;

import java.util.List;

/**
 * Interface for all travel companies offer parsers
 * 
 * @author Jimmy Sihlberg
 *
 */

public interface OfferParser {
	
	/**
	 * 
	 * @return	A list with offers
	 */
	public List<Offer> getOffers();

}
