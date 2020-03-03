package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> drop up an object.
 * 
 * @author ram
 */

public class Leave extends SWAffordance {

	/**
	 * Constructor for the <code>Leave</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being taken
	 * @param m the message renderer to display messages
	 */
	public Leave(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}


	/**
	 * Returns if or not this <code>Leave</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if an actor is carrying an item
	 *  
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a.getItemCarried()!=null;
	}

	/**
	 * Perform the leave action by setting the item carried to null, and placing the object that was carried back onto the 
	 * grid. Add a take affordance to the item so it can be picked up again.
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof SWEntityInterface) {
			SWEntityInterface theItem = (SWEntityInterface) target;
			a.setItemCarried(null);
			// Get location of actor dropping object
			SWLocation loc = SWAction.getEntitymanager().whereIs(a);
			System.out.print("got to here");
			SWAction.getEntitymanager().setLocation(theItem, loc);
			//remove the leave affordance and add the take affordance
			target.removeAffordance(this);
			theItem.addAffordance(new Take(theItem, messageRenderer));
		}
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 */
	@Override
	public String getDescription() {
		return "leave " + target.getShortDescription();
	}

}
