package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the lumbridge jail guard.
 * @author 'Vexia
 * @version 1.0
 */
public final class LumbridgeJailGuard extends DialoguePlugin {

	/**
	 * Constructs a new {@code LumbridgeJailGuard} {@code Object}.
	 */
	public LumbridgeJailGuard() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code LumbridgeJailGuard} {@code Object}.
	 * @param player the player.
	 */
	public LumbridgeJailGuard(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new LumbridgeJailGuard(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(player, FacialExpression.NORMAL, "Hello.");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Why are you here ? You must leave at once.");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Err.. Okay.");
			stage = 2;
			break;
		case 2:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 917, 447, 448, 449 };
	}
}