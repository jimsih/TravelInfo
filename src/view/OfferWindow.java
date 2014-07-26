package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;

import model.Offer;

/**
 * OfferWindow shows a specific offer to user in 
 * a separated window
 * 
 * @author Jimmy Sihlberg
 *
 */

public class OfferWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Offer offer;
	private JTextPane clLink;
	private JTextPane bookLink;
	
	/**
	 * OfferWindow constructor
	 * 
	 * @param o
	 * 		Offer to show for user
	 * 
	 * @throws IOException
	 * 		Is thrown when connection is lost
	 */
	public OfferWindow(Offer o) throws IOException {
		super(o.getCampaignName());
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(new Dimension(900, 700));
		this.setLocationRelativeTo(null);
		this.setBackground(new Color(102, 204, 255));
		offer = o;
		
		buildImagePanel();
		buildInformationPanel();
		this.setVisible(true);
	}
	
	/**
	 * Builds panel with information about offer
	 */
	private void buildInformationPanel() {
		JPanel information = new JPanel(new GridLayout(14, 1, 0, 0));
		this.add(information, BorderLayout.CENTER);
		JLabel dest = new JLabel(" Destination: "+offer.getDestination());
		JLabel city = new JLabel(" City: "+offer.getCityName());
		JLabel date = new JLabel(" Date: "+offer.getDate());
		JLabel price = new JLabel(" Price: "+offer.getPrice());
		JLabel dep = new JLabel(" Departure: "+offer.getDeparture());
		JLabel hotel = new JLabel(" Hotel: "+offer.getHotelName());
		JLabel room = new JLabel(" Room: "+offer.getRoomDescription());
		JLabel weeks = new JLabel(" Weeks: "+offer.getJourneyLengthWeeks());
		JLabel seats = new JLabel(" Seats available: "+offer.getNoOfSeatsRemaining());
		JLabel cl = new JLabel(" Content Link: ");
		clLink = new JTextPane();
		clLink.setContentType("text/html");
		clLink.setText("<html>"+offer.getContentLink()+"</html>");
		clLink.setEditable(false);
		clLink.setBackground(null);
		clLink.setBorder(null);
		clLink.addMouseListener(linkListener);
		JLabel book = new JLabel(" Booking Link: ");
		bookLink = new JTextPane();
		bookLink.setContentType("text/html");
		bookLink.setText("<html>"+offer.getBookLink()+"</html>");
		bookLink.setEditable(false);
		bookLink.setBackground(null);
		bookLink.setBorder(null);
		bookLink.addMouseListener(linkListener);
		
		information.add(dest);
		information.add(city);
		information.add(date);
		information.add(price);
		information.add(dep);
		information.add(hotel);
		information.add(room);
		information.add(weeks);
		information.add(seats);	
		information.add(cl);
		information.add(clLink);
		information.add(book);
		information.add(bookLink);
	}
	
	/**
	 * Builds panel with image
	 * 
	 * @throws IOException
	 * 		Throws exception if image can not be found
	 */
	private void buildImagePanel() throws IOException {
		JPanel image = new JPanel(new BorderLayout());
		Image i = null;
		JLabel l = null;
		if(offer.getHotelImage().equals("")) {
			l = new JLabel(new ImageIcon("logo2.png"));
		}else {
			URL url = new URL(offer.getHotelImage());
			i = ImageIO.read(url);
			l = new JLabel(new ImageIcon(i));
		}
		image.add(l);
		this.add(image, BorderLayout.NORTH);
	}
	
	private void createPopupMenu(final int link, MouseEvent e) {
		JPopupMenu copyMenu = new JPopupMenu();
  	   	JMenuItem copy = new JMenuItem("Copy link");
  	   	copy.addActionListener(new ActionListener() {

  	   		@Override
			public void actionPerformed(ActionEvent arg0) {
				StringSelection selection = null;
				if(link == 1) {
					selection = new StringSelection(
							offer.getContentLink());
				}else if(link == 2) {
					selection = new StringSelection(
							offer.getBookLink());
				}
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, selection);
			}
  		   
  	   });
  	   copyMenu.add(copy);
  	   if(link == 1) {
  		   copyMenu.show(clLink, e.getX(), e.getY());
  	   }else if(link == 2) {
  		   copyMenu.show(bookLink, e.getX(), e.getY());
  	   }
	}
	
	private MouseAdapter linkListener = new MouseAdapter() {
		
		public void mousePressed(MouseEvent e) {
			
			if(e.getButton() == MouseEvent.BUTTON1) {
		       if(e.getSource() == clLink) {
		    	   createPopupMenu(1, e);
		       }else if(e.getSource() == bookLink) {
		    	   createPopupMenu(2, e);
		       }
			}
		}
	};
}
