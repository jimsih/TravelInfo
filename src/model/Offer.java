package model;

/**
 * Information about offers form travel companies
 * 
 * @author Jimmy Sihlberg
 *
 */

public class Offer {
	
	private String campaignName;
	private String departure;
	private String date;
	private String destination;
	private String cityName;
	private String roomDescription;
	private int price;
	private String hotelName;
	private String hotelImage;
	private String noOfSeatsRemaining;
	private String contentLink;
	private String bookLink;
	private int journeyLengthWeeks;
	
	/**
	 * Offer constructor
	 * 
	 * @param camp
	 * 		Campaign name
	 * @param dep
	 * 		Departure
	 * @param d
	 * 		Date
	 * @param dest
	 * 		Destination
	 * @param cityn
	 * 		City name
	 * @param room
	 * 		Room description
	 * @param p
	 * 		Price
	 * @param hotel
	 * 		Hotel name
	 * @param image
	 * 		Hotel image
	 * @param noOfS
	 * 		Number of seats remaining
	 * @param contLink
	 * 		Content link
	 * @param book
	 * 		Booking link
	 * @param weeks
	 * 		Number of weeks
	 */
	public Offer(String camp, String dep, String d, String dest, String cityn, 
			String room, int p, String hotel, String image, String noOfS, 
			String contLink, String book, int weeks) {
		campaignName = camp;
		departure = dep;
		date = d;
		destination = dest;
		cityName = cityn;
		roomDescription = room;
		price = p;
		hotelName = hotel;
		hotelImage = image;
		noOfSeatsRemaining = noOfS;
		contentLink = contLink;
		bookLink = book;
		journeyLengthWeeks = weeks;
	}
	
	/**
	 * 
	 * @return Destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * 
	 * @return Date
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * 
	 * @return Price
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * 
	 * @return Departure
	 */
	public String getDeparture() {
		return departure;
	}
	
	/**
	 * 
	 * @return Campaign name
	 */
	public String getCampaignName() {
		return campaignName;
	}
	
	/**
	 * 
	 * @return City name
	 */
	public String getCityName() {
		return cityName;
	}
	
	/**
	 * 
	 * @return Room description
	 */
	public String getRoomDescription() {
		return roomDescription;
	}
	
	/**
	 * 
	 * @return Hotel name
	 */
	public String getHotelName() {
		return hotelName;
	}
	
	/**
	 * 
	 * @return Content link
	 */
	public String getContentLink() {
		return contentLink;
	}
	
	/**
	 * 
	 * @return Booking link
	 */
	public String getBookLink() {
		return bookLink;
	}
	
	/**
	 * 
	 * @return Number of seats remaining
	 */
	public String getNoOfSeatsRemaining() {
		return noOfSeatsRemaining;
	}
	
	/**
	 * 
	 * @return Number of weeks
	 */
	public int getJourneyLengthWeeks() {
		return journeyLengthWeeks;
	}
	
	/**
	 * 
	 * @return Hotel image
	 */
	public String getHotelImage() {
		return hotelImage;
	}
}
