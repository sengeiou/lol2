package plugin.npc;

import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.world.map.Location;
import org.arios.tools.RandomFunction;

/**
 * Represents the a cow npc.
 * @author 'Vexia
 * @version 1.0
 */
public final class CowNPC extends AbstractNPC {

	/**
	 * Represents the force chat to use.
	 */
	private static final String FORCE_CHAT = "Moo";

	/**
	 * Constructs a new {@code CowNPC} {@code Object}.
	 */
	public CowNPC() {
		super(0, null, true);
	}

	/**
	 * Constructs a new {@code CowNPC} {@code Object}.
	 * @param id the id.
	 * @param location the location.
	 */
	public CowNPC(int id, Location location) {
		super(id, location, true);
	}

	@Override
	public void tick() {
		if (RandomFunction.random(45) == 5) {
			sendChat(FORCE_CHAT);
		}
		super.tick();
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new CowNPC(id, location);
	}

	@Override
	public int[] getIds() {
		return new int[] { 81, 397, 955, 1767, 3309 };
	}

}
