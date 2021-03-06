package org.arios.game.world.update.flag.player;

import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.impl.ForceMovement;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.UpdateFlag;
import org.arios.net.packet.IoBuffer;

/**
 * Handles the force movement player update flag.
 * @author Emperor
 * 
 */
public final class ForceMovementFlag extends UpdateFlag<ForceMovement> {

	/** 
	 * Constructs a new {@code ForceMovementFlag} {@code Object}. 
	 * @param forceMovement The force movement data. 
	 */
	public ForceMovementFlag(ForceMovement forceMovement) {
		super(forceMovement);
	}

	@Override
	public void write(IoBuffer buffer) {
	}

	@Override
	public void writeDynamic(IoBuffer buffer, Entity e) {
		Location l = ((Player) e).getPlayerFlags().getLastSceneGraph();
		buffer.putC(context.getStart().getSceneX(l)) // Start location
				.put(context.getStart().getSceneY(l)).putA(context.getDestination().getSceneX(l)) // Destination
																									// location
				.put(context.getDestination().getSceneY(l)).putLEShort(context.getCommenceSpeed() * 30)// Commencing
																										// speed
				.putLEShort((context.getCommenceSpeed() * 30) + (context.getPathSpeed() * 30 + 1)) // Path
																									// speed
				.putC(context.getDirection().toInteger());
	}

	@Override
	public int data() {
		return maskData();
	}

	@Override
	public int ordinal() {
		return 5;
	}

	/**
	 * Gets the mask data.
	 * @return The mask data.
	 */
	public static int maskData() {
		return 0x400;
	}

}