package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the plugin used for peksa.
 * @author 'Vexia
 * @version 1.0
 */
public final class PeksaDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code PeksaDialogue} {@code Object}.
	 */
	public PeksaDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code PeksaDialogue} {@code Object}.
	 * @param player the player.
	 */
	public PeksaDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new PeksaDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Are you interested in buying or selling a helmet?");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendOptions("Select an Option", "I could be, yes.", "No, I'll pass on that.");
			stage = 1;
			break;
		case 1:
			switch (buttonId) {
			case 1:
				end();
				npc.openShop(player);
				break;
			case 2:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "No, I'll pass on that.");
				stage = 20;
				break;
			}
			break;
		case 20:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Well, come back if you change your mind.");
			stage = 21;
			break;
		case 21:
			end();
			break;
		}

		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 538 };
	}
}