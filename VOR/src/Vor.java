import java.util.Random;

/**
 * Simulates an avionic VOR system.
 * Main class for the VOR
 * Calculations primarily done in Calculations class
 * @author Duane Leong
 */
public class Vor {
	
	//1:OVER_RADIO+1 chance of being over the radio tower
	public static final int OVER_RADIO = 24;
	private Radio radio;
	private int obsInput; //value of obs input
	private boolean overStation;
	
	/**
	 * Constructor for VOR
	 */
	public Vor() {
		this.radio = new Radio();
		this.obsInput = 0;
		//1:OVER_RADIO+1 chance of being over the radio tower
		Random random = new Random();
		this.overStation = (random.nextInt(OVER_RADIO) == 0);
	}
	
	/**
	 * Setter for obInput
	 * @param radial value to be set to obsInput
	 */
	public void setObs(int radial) {
		//makes sure the obs always remains a positive between 0-359
		this.obsInput = Calculations.normalizeAngle(radial);
	}
	
	/**
	 * Rotate the obs
	 * @param change made to the obs  + change is clockwise, - change is counterclockwise
	 */
	public void rotateObs(int change) {
		//makes sure the obs always remains positive between 0-359
		this.obsInput = Calculations.normalizeAngle(obsInput + change);
	}
	
	/**
	 * Checks if signal is good
	 * The assignment defines a bad signal as being 1 degree within "abeam" or 90 degrees
	 * of either side of the desired radial and if the plane is over the station.
	 * 
	 * Thus, the signal is considered good if the degree is greater than 1
	 * degree of "abeam" and if the plane isn't over the station.  The random-ness of the
	 * radio being over the station is dictated by the randomOverStation() method in the
	 * radio class.
	 * 
	 * A good signal would be not over the station or not abeam.
	 * 
	 * @return True if the signal is good, False if bad
	 */
	public boolean getSignal() {
		int interceptedRadial = radio.getRadial();
		return !(overStation) &&
				!(Calculations.checkAbeam(interceptedRadial, this.obsInput));
	}
	
	/**
	 * An angle coming FROM would be a difference less than or equal an
	 * + or - 90 degrees.  Otherwise it would be a going TO.  More info
	 * in Calculations class.
	 * 
	 * @return True if going to the intercepted radial.
	 */
	public boolean goingTo() {
		return Calculations.goingTo(radio.getRadial(), this.obsInput);
	}

	/**
	 * Gets the current OBS value as an integer.  Should awlways remain a positive
	 * integer between 0-359.
	 * @return int of the current OBS value
	 */
	public int getObs() {
		return this.obsInput;
	}
	
	/**
	 * Gets the station ID from the radio
	 * @return String representation of a morse code station ID
	 */
	public String getStationID() {
		return radio.getStationID();
	}
	
	/**
	 * Used to return the needle angle needed in the GUI
	 * @return
	 */
	public int getNeedleAngle() {
		return Calculations.needleAngle(radio.getRadial(), this.obsInput);
	}
}
