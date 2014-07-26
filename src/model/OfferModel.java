package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Table model for offers. Contains list with offers.
 * 
 * @author Jimmy Sihlberg
 *
 */

public class OfferModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Offer> offerList;
	
	/**
	 * OfferModel constructor
	 */
	public OfferModel() {
		offerList = new ArrayList<Offer>();
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return offerList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Offer offer = offerList.get(rowIndex);
		switch(columnIndex) {
		case 0:
			return offer.getDestination();
		case 1:
			return offer.getDate();
		case 2:
			return offer.getHotelName() != "" ? "Yes" : "No";
		case 3:
			return offer.getPrice();
		}
		return null;
	}
	
	@Override
    public Class<?> getColumnClass(int columnIndex) {
       switch(columnIndex) {
       case 0: 
	       	return String.class;
	   case 1:
	      	return String.class;
	   case 2:
		   return String.class;
	   case 3:
	       	return Integer.class;
	   default:
	       	return null;
       }
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex) {
		case 0:
			return "Destination";
		case 1:
			return "Date";
		case 2:
			return "Hotel";
		case 3:
			return "Price SEK";
		}
		return null;
	}
	
	/**
	 * 
	 * @param o
	 * 		Offer to be added to list
	 */
	public void addOffer(Offer o) {
		offerList.add(o);
		this.fireTableRowsInserted(offerList.size()-1, offerList.size()-1);
	}
	
	/**
	 * 
	 * @return ArrayList with offers
	 */
	public ArrayList<Offer> getOfferList() {
		return offerList;
	}
	
	/**
	 * Remove all offers
	 */
	public void removeOffers() {
		int s = offerList.size()-1;
		offerList = new ArrayList<Offer>();
		this.fireTableRowsDeleted(0, s);
	}

}
