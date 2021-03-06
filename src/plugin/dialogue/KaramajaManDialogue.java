package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the karamja man dialogue plugin.
 * @author 'Vexia
 * @version 1.0
 */
public final class KaramajaManDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code KaramajaManDialogue} {@code Object}.
	 */
	public KaramajaManDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code KaramajaManDialogue} {@code Object}.
	 * @param player the player.
	 */
	public KaramajaManDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new KaramajaManDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(player, FacialExpression.NORMAL, "Hello, how's it going?");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Not too bad, but I'm a little worried about the increase", "of goblins these days.");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Don't worry, I'll kill them.");
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
		return new int[] { 3915 };
	}
}