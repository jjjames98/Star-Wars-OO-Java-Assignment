package starwars;

public class Force {
	/** determines if owner of Force object can use the Force */
	private boolean canUse;

	/** the level of Force the owner of the <code>Force</code> object has */
	private int forceAbility;

	/**
	 * Constructor for the <code>Force</code>
	 * 
	 * @param newCanUse
	 *            the ability or inability to use the Force
	 * @param newForceAbility
	 *            the level of Force the owner of the <code>Force</code> has
	 */
	public Force(boolean newCanUse, int newForceAbility) {
		canUse = newCanUse;
		forceAbility = newForceAbility;
	}

	/**
	 * Return if the owner of the object can use the Force.
	 * 
	 * @return Whether a <code>SWActor</code> can use the force.
	 */
	public boolean getCanUse() {
		return canUse;
	}

	/**
	 * Return what level of Force the owner of the <code>Force</code> object
	 * possesses
	 * 
	 * @return The level of Force of a <code>SWActor</code>
	 */
	public int getForceAbility() {
		return forceAbility;
	}

	/**
	 * Sets whether the owner of the <code>Force</code> object can use the Force
	 * 
	 * @param newCanUse
	 *            Whether a <code>SWActor</code> can use the Force
	 */
	public void setCanUse(boolean newCanUse) {
		canUse = newCanUse;
	}

	/**
	 * Sets the level of Force the owner of the <code>Force</code> object
	 * possesses
	 * 
	 * @param newForceAbility
	 *            the level of Force of a <code>SWActor</code>
	 */
	public void setForceAbility(int newForceAbility) {
		forceAbility = newForceAbility;
	}

	/**
	 * Returns a copy of a given <code>Force</code> object
	 * 
	 * @return A copy of a <code>Force</code> object
	 */
	public Force clone() {
		return new Force(this.canUse, this.forceAbility);
	}
}
