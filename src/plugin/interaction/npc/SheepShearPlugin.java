package plugin.interaction.npc;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;
import org.arios.tools.RandomFunction;

/**
 * Represents the plugin used to shear a sheep.
 * @author 'Vexia
 * @version 1.0
 */
public final class SheepShearPlugin extends OptionHandler {

	/**
	 * Represents the animation to use.
	 */
	private static final Animation ANIMATION = new Animation(893);

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		NPCDefinition.setOptionHandler("shear", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		final NPC sheep = (NPC) node;
		if (!player.getInventory().contains(1735, 1)) {
			player.getPacketDispatch().sendMessage("You need shears to shear a sheep.");
			return true;
		}
		if (sheep.getDefinition().hasAction("attack")) {
			player.getPacketDispatch().sendMessage("That one looks a little too violent to shear...");
			return true;
		}
		if (player.getInventory().freeSlots() == 0) {
			player.getPacketDispatch().sendMessage("You don't have enough space in your inventory to carry any wool you would shear.");
			return true;
		}
		sheep.lock(3);
		sheep.getWalkingQueue().reset();
		player.animate(ANIMATION);
		int random = RandomFunction.random(1, 5);
		if (random != 4) {
			sheep.getLocks().lockMovement(2);
			sheep.transform(5153);
			player.getPacketDispatch().sendMessage("You get some wool.");
			player.getInventory().add(new Item(1737, 1));// 5160
			GameWorld.submit(new Pulse(80, sheep) {
				@Override
				public boolean pulse() {
					sheep.reTransform();
					return true;
				}

			});
		} else {
			player.getPacketDispatch().sendMessage("The sheep manages to get away from you!");
			sheep.moveStep();
		}
		return true;
	}

}
