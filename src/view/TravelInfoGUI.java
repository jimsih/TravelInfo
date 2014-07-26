package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.OverlayLayout;
import javax.swing.RowFilter;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import model.FritidsresorParser;
import model.Offer;
import model.OfferModel;
import model.UpdateListener;
import model.VingParser;

import controller.UpdateController;

/**
 * GUI for TravelInfo
 * 
 * @author Jimmy Sihlberg
 *
 */

public class TravelInfoGUI {
	private JFrame frame;
	
	private JPanel homePanel;
	private JPanel offersPanel;
	
	private JMenuBar menu;
	private JMenu travelInfo;
	private JMenuItem update;
	private JMenuItem setUpdateInterval;
	private JMenuItem home;
	
	private JTable jTable;
	
	private JButton buttonFritidsresor;
	private JButton buttonVing;
	
	private JTextField searchField;
	
	private OfferModel offerModel;

	private TableRowSorter<OfferModel> sorter;
	
	private UpdateController controller;

	/**
	 * Constructor
	 */
 	public TravelInfoGUI() {
 		frame = new JFrame();
 		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		frame.setSize(new Dimension(900, 700));
 		frame.setBackground(new Color(102, 204, 255));
  		frame.setLocationRelativeTo(null);
 		
 		createHomePanel();
 		frame.setJMenuBar(menu);
 		
 		frame.add(homePanel);
 		
 		frame.pack();
	}
 	/**
 	 * Set frame visible
 	 * @param v Boolean value
 	 */
	public void show(boolean v) {
 		frame.setVisible(v);
 	}
	
	/**
	 * Creates menu bar in frame
	 */
	private void createMenu() {
 		menu = new JMenuBar();
		travelInfo = new JMenu("TravelInfo");
		menu.add(travelInfo);
		
		update = new JMenuItem("Update");
		update.addActionListener(menuItemActionListener);
		setUpdateInterval = new JMenuItem("Set update interval");
		setUpdateInterval.addActionListener(menuItemActionListener);
		home = new JMenuItem("Home");
		home.addActionListener(menuItemActionListener);
		
		travelInfo.add(home);
		travelInfo.add(update);
		travelInfo.add(setUpdateInterval);
		menu.add(travelInfo);
		
		frame.add(menu, BorderLayout.NORTH);
	}
 	
	/**
	 * Creates a home page
	 */
 	private void createHomePanel() {
 		homePanel = new JPanel();
  		LayoutManager overlay = new OverlayLayout(homePanel);
 		homePanel.setLayout(overlay);
 		
 		buttonFritidsresor = new JButton("Fritidsresor");
 		buttonFritidsresor.setMaximumSize(new Dimension(200, 50));
 		buttonFritidsresor.setAlignmentX(0.5f);
 		buttonFritidsresor.setAlignmentY(1f);
 		buttonFritidsresor.addActionListener(buttonActionListener);
 		
 		buttonVing = new JButton("Ving");
 		buttonVing.setMaximumSize(new Dimension(200, 50));
 		buttonVing.setAlignmentX(0.5f);
 		buttonVing.setAlignmentY(0.0005f);
 		buttonVing.addActionListener(buttonActionListener);
 		
 		homePanel.add(buttonFritidsresor);
 		homePanel.add(buttonVing);
 		
		JLabel l = new JLabel(new ImageIcon("TravelImage.png"));
		l.setAlignmentY(0.6f);
		l.setAlignmentX(0.5f);
		homePanel.add(l);
 	}
 	
 	/**
 	 * Creates a panel with a JTable and a JTextField search field
 	 */
 	private void createOffersPanel() {
 		frame.setBackground(Color.WHITE);
 		offersPanel = new JPanel(new BorderLayout());
  		offerModel = new OfferModel();
  		sorter = new TableRowSorter<OfferModel>(offerModel);
  		
 		jTable = new JTable(offerModel);
 		jTable.setRowSorter(sorter);
 		/* Show price in Price column on left side of column*/
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
 		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
 		jTable.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);
 		
 		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 		jTable.addMouseListener(new MouseAdapter() {
 			public void mousePressed(MouseEvent e) {
 				if(e.getClickCount() == 2) {
 					ArrayList<Offer> al = offerModel.getOfferList();
 					try {
						new OfferWindow(al.get(jTable.convertRowIndexToModel(
								jTable.getSelectedRow())));
					} catch (IOException e1) {
						/* Shows error message if connection is lost*/
						JOptionPane.showMessageDialog(null, 
								"Connection problem", 
								"Error", JOptionPane.ERROR_MESSAGE);
						goToHomePanel();
					}
 				}
 			}
 		});
 		JScrollPane s = new JScrollPane(jTable);
 		searchField = new JTextField("Destination");
 		searchField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				newFilter();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				newFilter();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				newFilter();
			}
 		});
 		offersPanel.add(searchField, BorderLayout.NORTH);
 		
 		offersPanel.add(s);
 		frame.add(offersPanel, BorderLayout.CENTER);
 	}
 	
 	/**
 	 * Filters content in jTable
 	 */
 	private void newFilter() {
		RowFilter<OfferModel, Object> rf = null;
		rf = RowFilter.regexFilter(searchField.getText(), 0);
		sorter.setRowFilter(rf);
	}
 	
 	/**
 	 * Updates jTable with new content
 	 * 
 	 * @param list
 	 * 		A list with offers
 	 */
 	private void updatejTable(List<Offer> list) {
 		if(offerModel.getOfferList().size() != 0) {
 			offerModel.removeOffers();
 		}
 		for(int i = 0; i < list.size(); i++) {
 			offerModel.addOffer(list.get(i));
 		}
 	}
 	
 	/**
 	 * Builds up home page
 	 */
 	private void goToHomePanel() {
 		frame.remove(offersPanel);
		frame.remove(menu);
		frame.setBackground(new Color(102, 204, 255));
		frame.add(homePanel);
		frame.pack();
 	}
 	
 	/**
 	 * A listener that listens to new updates
 	 */
 	private UpdateListener updateListener = new UpdateListener() {
		@Override
		public void update() {
			SwingUtilities.invokeLater(new Runnable() {
				
				public void run() {
					if(controller.getOffers().isEmpty()) {
						/* Shows error message if no offers were found*/
						JOptionPane.showMessageDialog(null, 
								"Connection problem", 
								"Error", JOptionPane.ERROR_MESSAGE);
						controller.saveAndExit();
						goToHomePanel();
					}else {
						updatejTable(controller.getOffers());
					}
				}
			});
			
		}
	};
 	
	/**
	 * Listens to home page buttons
	 */
 	private ActionListener buttonActionListener = new ActionListener() {
 		
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source == buttonFritidsresor) {
				frame.remove(homePanel);
				createOffersPanel();
				createMenu();
		 		frame.pack();
				controller = new UpdateController(FritidsresorParser.class, 
												updateListener);
			}else if(source == buttonVing) {
				frame.remove(homePanel);
				createOffersPanel();
				createMenu();
		 		frame.pack();
				controller = new UpdateController(VingParser.class, 
												updateListener);
			}
		}
 	};
 	
 	/**
 	 * Actionlisteners for menuItems
 	 */
 	private ActionListener menuItemActionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source == home) {
				controller.saveAndExit();
				goToHomePanel();
			} else if(source == update) {
				controller.update();
			} else if(source == setUpdateInterval) {
				SpinnerModel model = new SpinnerNumberModel(
						controller.getUpdateInterval(), 30, 120, 30);
				final JSpinner spinner = new JSpinner(model);
				spinner.setEditor(new JSpinner.DefaultEditor(spinner));
				spinner.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent arg0) {
						controller.setUpdateInterval((Integer)spinner.getValue());
					}
				});
				
				JFrame sf = new JFrame();
				sf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				sf.setMinimumSize(new Dimension(200, 70));
				sf.setLocationRelativeTo(null);
				sf.setResizable(false);
				sf.add(spinner, BorderLayout.CENTER);
				sf.add(new JLabel("Update interval (minutes)"), BorderLayout.NORTH);
				sf.setVisible(true);
			}
		}
 	};
}
