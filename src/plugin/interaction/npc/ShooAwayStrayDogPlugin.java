package plugin.interaction.npc;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;

/**
 * Represents the option plugin used to shoo away a dog.
 * @author 'Vexia
 * @version 1.0
 */
public final class ShooAwayStrayDogPlugin extends OptionHandler {

	/**
	 * Represents the animatio to use.
	 */
	private static final Animation ANIMATION = new Animation(2110);

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		NPCDefinition.forId(5917).getConfigurations().put("option:shoo-away", this);
		NPCDefinition.setOptionHandler("shoo-away", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		player.sendChat("Thbbbbt!");
		player.animate(ANIMATION);
		NPC dog = (NPC) node;
		dog.sendChat("Whine!");
		dog.moveStep();
		dog.getPulseManager().clear();
		return true;
	}

}
