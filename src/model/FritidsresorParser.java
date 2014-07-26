package model;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parses a xml file from Fritidsresor and saves offers
 * in a list. FritidsresorParser does not handle connection 
 * problems. FritidsresorParser implements OfferParser. 
 * 
 * @author Jimmy Sihlberg
 *
 */

public class FritidsresorParser implements OfferParser {
	
	private List<Offer> offers;
	
	/**
	 * FritidsresorParser constructor
	 */
	public FritidsresorParser() {
		try {
			offers = new ArrayList<Offer>();
			Document doc = parseXML();
			parseOffers(doc);
		} catch (IOException e) {
			/* Error message will be shown in TravelInfoGUI*/
		}
	}
	
	/**
	 * Parses xml file through url to document
	 * 
	 * @return Document parsed from url
	 * @throws IOException
	 * 		Throws if no connection
	 */
	private Document parseXML() throws IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			URL url = new URL("http://www.fritidsresor.se/Blandade-Sidor/feeds/tradera/");
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			doc = builder.parse(url.openStream());
			doc.getDocumentElement().normalize();
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Parses document data to Offer objects
	 * 
	 * @param doc 
	 * 		Document with offer information
	 */
	private void parseOffers(Document doc) {
		NodeList nl = doc.getElementsByTagName("Offer");
		for(int i = 0; i < nl.getLength(); i++) {
			Element o =	(Element)nl.item(i);
			try {
				String p = o.getElementsByTagName("CurrentPrice").item(0).getTextContent();
				String[] price = p.split(" ");
				
				Offer offer = new Offer(
					o.getElementsByTagName("CampaignName").item(0).getTextContent(), 
					o.getElementsByTagName("DepartureName").item(0).getTextContent(), 
					o.getElementsByTagName("OutDate").item(0).getTextContent(), 
					o.getElementsByTagName("DestinationName").item(0).getTextContent(), 
					o.getElementsByTagName("CityName").item(0).getTextContent(), 
					o.getElementsByTagName("RoomDescription").item(0).getTextContent(), 
					Integer.parseInt(price[0]), 
					o.getElementsByTagName("HotelName").item(0).getTextContent(), 
					o.getElementsByTagName("HotelImage").item(0).getTextContent(),
					o.getElementsByTagName("NoOfSeatsRemaining").item(0).getTextContent(), 
					o.getElementsByTagName("ContentLink").item(0).getTextContent(), 
					o.getElementsByTagName("BookLink").item(0).getTextContent(), 
					Integer.parseInt(o.getElementsByTagName("JourneyLengthWeeks").
							item(0).getTextContent()));
				
				offers.add(offer);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (DOMException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns list with Offer objects
	 */
	@Override
	public List<Offer> getOffers() {
		return offers;
	}

}
