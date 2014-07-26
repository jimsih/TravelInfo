package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


import model.Offer;
import model.OfferParser;
import model.Settings;
import model.UpdateListener;

/**
 * Class to update offers in TravelInfoGUI. Uses two threads to 
 * handle updates. One thread is responsible to get offers 
 * from a OfferParser. The other thread is responsible to handle 
 * time and check when to update. UpdateController reads in a 
 * settings object to check last updated and update interval.
 * If settings file does not exist UpdateController writes a new 
 * settings file with default values.
 * 
 * If removing UpdateController saveAndExit() method must be called.
 * Else timer thread will be running in background and can cause 
 * problems.
 * 
 * @author Jimmy Sihlberg
 *
 */
public class UpdateController {
	
	private Settings settings;
	private Class<? extends OfferParser> parserClass;
	private List<Offer> list;
	private volatile UpdateTimer timer;
	private ArrayList<UpdateListener> updateListeners;

	/**
	 * UpdateController constructor
	 * 
	 * @param c
	 * 		Class implementing OfferParser
	 * @param listener
	 * 		Listener to call when new update has been done
	 */
	public UpdateController(Class<? extends OfferParser> c, 
								UpdateListener listener) {
		readSettings();
		parserClass = c;
		updateListeners = new ArrayList<UpdateListener>();
		setUpdateListener(listener);
		update();
		settings.setLastUpdated(0);
		
		timer = new UpdateTimer();
		timer.start();
	}
	
	/**
	 * Reads existing settings file or creates a new default 
	 * settings file to read from
	 */
	private void readSettings() {
		try {
			ObjectInputStream in;
			in = new ObjectInputStream(
									new FileInputStream("settings.dat"));
			settings = (Settings) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			/* Create new settings file if not found */
			ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(
						new FileOutputStream("settings.dat"));
				out.writeObject(new Settings());
				out.close();
				readSettings();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return List with offers
	 */
	public List<Offer> getOffers() {
		return list;
	}
	
	/**
	 * Update offers
	 */
	public void update() {
		ParserReader pr = new ParserReader();
		pr.start();
	}
	
	/**
	 * Calls listeners that a new update has been made
	 */
	private void newUpdate() {
		if (updateListeners != null) {
			for(UpdateListener ul : updateListeners) {
				ul.update();
			}
		}
	}
	
	/**
	 * Change update interval
	 * 
	 * @param value 
	 * 			Interval frequency 
	 */
	public void setUpdateInterval(int value) {
		settings.setUpdateInterval(value);
		saveSettings();
	}
	
	/**
	 * 
	 * @return Update interval
	 */
	public int getUpdateInterval() {
		return settings.getUpdateInterval();
	}
	
	/**
	 * 
	 * @param listener
	 * 		Adds a new UpdateListener
	 * 		
	 */
	public void setUpdateListener(UpdateListener listener) {
		updateListeners.add(listener);
	}
	
	/**
	 * Interrupts UpdateTimer thread and saves current settings.
	 * Should be called before removing UpdateController
	 */
	public void saveAndExit() {
		timer.interrupt();
		timer = null;
		saveSettings();
	}
	
	/**
	 * Saves current settings
	 */
	private void saveSettings() {
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(
									new FileOutputStream("settings.dat"));
			out.writeObject(settings);
			out.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Thread that gets offers from an OfferParser
	 * 
	 * @author Jimmy Sihlberg
	 *
	 */
	private class ParserReader extends Thread {
		public void run() {
			try {
				OfferParser p = parserClass.newInstance();
				list = p.getOffers();
				newUpdate();
				settings.setLastUpdated(0);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * A timer thread that calls update in UpdateController when 
	 * time equals update interval
	 * 
	 * @author Jimmy Sihlberg
	 *
	 */
	private class UpdateTimer extends Thread {
		
		public void run() {
			int time = settings.getLastUpdated();
			try {
				Thread.sleep(60*1000);
				while(true) {
					settings.setLastUpdated(time++);
					if(time == settings.getUpdateInterval()) {
						update();
						settings.setLastUpdated(0);
						time = 0;
					}
					Thread.sleep(60*1000);
				}
			} catch (InterruptedException e) {
			}
		}
	}
}
