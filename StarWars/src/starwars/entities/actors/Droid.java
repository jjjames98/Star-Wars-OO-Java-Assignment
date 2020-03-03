package starwars.entities.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Move;

public class Droid extends SWActor {

	private String name;
	private SWActor owner;
	private Direction randomDirection = null;
	// DamageSymbols are the symbols for the areas on the map where a droid takes damage. In this version it is only the badlands.
	private char damageSymbol = 'b';
	private int areaDamageAmt = 10;
	/**
	 * Create a Droid.  Droids will not move if they don't have an owner.
	 * If they can't find their owner in a the current or neighbouring
	 * location they will move in a random direction and continue moving
	 * in that direction until they can't anymore. If they have found
	 * their owner in the current or neighbouring location they will
	 * move to that location. Droids lose health when they move in the 
	 * badlands.
	 * 
	 * @param hitpoints
	 *            the number of hit points of this droid. If this
	 *            decreases to below zero, the will become immobile.
	 * @param name
	 *            this droids name. Used in displaying descriptions.
	 * @param owner
	 *			  the droids owner. Can be set to null for no owner
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>TuskenRaider</code> belongs to
	 * 
	 */
	public Droid(int hitpoints, String name, MessageRenderer m, SWWorld world, SWActor owner) {
		// Note the team is set to the same team as the owner
		super(owner.getTeam(), hitpoints, m, world);
		// TODO Auto-generated constructor stub
		this.name = name;
		this.owner = owner;
	}

	@Override
	public void act() {
		say(describeLocation());
		// Check if droid is out of health
		if (isDead()) {
			return;
		}
		// Get current location
		SWLocation location = world.getEntityManager().whereIs(this);
		
		// Check the symbol of current location to check if in badlands, if so take damages
		if(location.getSymbol() == damageSymbol) {
			this.takeDamage(areaDamageAmt);
		}
		
		if(owner != null) {
			// check if owner is near
			// get all possible directions that the droid can move in
			ArrayList<Direction> possibleDirections = getPossibleDirections();
			SWLocation ownerLoc = null;
			Direction heading = null;
			
			// Get world for use in checks
			EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
			// Loop through all possible directions directions to check for owner in neighbouring tiles
			for(int i = 0; i < possibleDirections.size(); i++) {
				SWLocation neighbouringLoc = (SWLocation) location.getNeighbour(possibleDirections.get(i));
				List<SWEntityInterface> entities = em.contents(neighbouringLoc);
				if(entities != null) {
					if(entities.contains(owner)) {
						ownerLoc = neighbouringLoc;
						// Set the heading to the owner heading
						heading = possibleDirections.get(i);
						say(this.name + " is following " + this.owner.getShortDescription());
					}
				}
			}
			
			// If owner is not in a neighbouring location and not in current location, then move in random direction
			if(ownerLoc == null && em.whereIs(this) != em.whereIs(owner)) {
				say(this.name + " can't find " + this.owner.getShortDescription());
				// If there is no current direction set, or the current direction is no longer able to be used (at edge of map)
				if(randomDirection == null || possibleDirections.contains(randomDirection) == false) {
					Random r = new Random();
					int low = 0;
					int high = possibleDirections.size() - 1;
					int result = r.nextInt(high - low) + low;
					// Assign the heading to a random direction
					randomDirection = possibleDirections.get(result);
					heading = randomDirection;
					
				} else {
					// move in stored direction
					heading = randomDirection;
				}
			

			}
			// Move the droid
			Move myMove = new Move(heading, messageRenderer, world);
			scheduler.schedule(myMove, this, 1);
		}
	}

	@Override
	public String getShortDescription() {
		return name;
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
	
	// Get a list of possible directions for this actor
	private ArrayList<Direction> getPossibleDirections() {
		ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
		
		// build a list of available directions
		for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
			if (SWWorld.getEntitymanager().seesExit(this, d)) {
				possibleDirections.add(d);
			}
		}
		
		return possibleDirections;
	}
	
}
