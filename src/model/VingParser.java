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

public class VingParser implements OfferParser {
	
private List<Offer> offers;
	
	/**
	 * FritidsresorParser constructor
	 */
	public VingParser() {
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
			URL url = new URL("http://www.ving.se/Services/Lms/LmsRssService.svc/CreateFeed");
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
		NodeList nl = doc.getElementsByTagName("item");
		String title = doc.getElementsByTagName("title").item(0).getTextContent();
		for(int i = 0; i < nl.getLength(); i++) {
			Element o =	(Element)nl.item(i);
			try {
				String link = o.getElementsByTagName("link").item(0).getTextContent();
				String info = o.getElementsByTagName("title").item(0).getTextContent();
				String[] parser = info.split(" ");
				String date = parser[0];
				int j = 1;
				String destination = "";
				while(parser[j].matches("\\D+")) {
					destination = destination + " " + parser[j];
					j++;
				}
				int length = Integer.parseInt(parser[j++].replaceAll("\\D+",""));
				int price = Integer.parseInt(parser[j++].replaceAll("\\D+",""));
				String hotel = "";
				while(parser.length != j) {
					hotel = hotel + " " + parser[j];
					j++;
				}
				String room = "";
				if(hotel.equals(" Endast flyg")) {
					hotel = "";
					room = "Endast flyg";
				}
				String infoDep = o.getElementsByTagName("description").item(0).
															getTextContent();
				String[] d = infoDep.split(" ");
				String departure = d[d.length-1];
				
				Offer offer = new Offer(title, departure, date, destination, 
						"", room, price, hotel, "", "", "", link, length);
				
				offers.add(offer);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (DOMException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Offer> getOffers() {
		
		return offers;
	}

}
