/* Class that represents the action to train player and increase their force ability */
package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class Train extends SWAffordance implements SWActionInterface {

	/**
	 * Constructor for the <code>Train</code>.
	 * 
	 * @param master
	 *            specifies which actor is training the player.
	 * @param m
	 *            message renderer for this <code>Train</code> to display
	 *            message. messages
	 */
	public Train(SWEntityInterface master, MessageRenderer m) {
		super(master, m);
		priority = 1;
	}

	/**
	 * Returns the time it takes to perform this <code>Train</code>.
	 * 
	 * @return The duration of the Train action.
	 */
	@Override
	public int getDuration() {
		return 1;
	}

	/**
	 * A string describing what this <code>Train</code> will do.
	 * 
	 * @return The description of the Train action.
	 */
	@Override
	public String getDescription() {
		return "Train with " + this.getTarget().getShortDescription();
	}

	/**
	 * Determines whether a particular <code>SWActor a</code> can be trained.
	 * 
	 * @return true, any actor can be trained but may be rejected by the master.
	 */
	@Override
	public boolean canDo(SWActor a) {
		return true;
	}

	/**
	 * Perform the <code>Train<code> command on <code>SWActor a</code>. This
	 * method can only be performed if <code>SWActor a</code>'s force ability is
	 * equal to 2. It can not be performed the actor's force ability is less
	 * than or greater than 2 due to being either too weak or too strong in the
	 * Force. If the player can be trained, their force ability is increased to
	 * 3.
	 */
	@Override
	public void act(SWActor a) {
		SWEntityInterface master = this.getTarget();
		if (a.getForce().getForceAbility() == 2) {
			a.getForce().setForceAbility(3);
			a.say(a.getShortDescription() + "'s force ability has increased to 3");
		} else if (a.getForce().getForceAbility() > 2) {
			master.say(master.getShortDescription() + " says: I have nothing more to teach you");
		} else if (a.getForce().getForceAbility() < 2) {
			master.say(
					master.getShortDescription() + " says: You are not strong enough in the force for me to train you");
		}
	}

}
