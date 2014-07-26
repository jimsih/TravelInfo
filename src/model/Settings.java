package model;
import java.io.Serializable;

/**
 * Settings is a class containing settings information.
 * The class is loaded from a binary file.
 * 
 * @author jimsih
 *
 */
public class Settings implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/* Counted in minutes*/
	private int updateInterval;
	private int lastUpdated;
	
	/**
	 * Settings constructor, sets updateInterval to default 30
	 */
	public Settings() {
		setUpdateInterval(30);
		setLastUpdated(0);
	}
	
	/**
	 * 
	 * @return Update interval
	 */
	public synchronized int getUpdateInterval() {
		return updateInterval;
	}
	
	/**
	 * 
	 * @param updateInterval
	 * 		New update interval
	 */
	public synchronized void setUpdateInterval(int updateInterval) {
		this.updateInterval = updateInterval;
	}
	
	/**
	 * 
	 * @return last update
	 */
	public synchronized int getLastUpdated() {
		return lastUpdated;
	}
	
	/**
	 * 
	 * @param lastUpdated
	 * 		New last update
	 */
	public synchronized void setLastUpdated(int lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
