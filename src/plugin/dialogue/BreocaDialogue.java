package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the breoca dialogue plugin.
 * @author 'Vexia
 * @version 1.0
 */
public final class BreocaDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code BreocaDialogue} {@code Object}.
	 */
	public BreocaDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code BreocaDialogue} {@code Object}.
	 * @param player the player.
	 */
	public BreocaDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new BreocaDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(player, FacialExpression.NORMAL, "Hi!");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Hello stranger.");
			stage = 1;
			break;
		case 1:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 1084 };
	}
}