package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the falador man house dialogue.
 * @author 'Vexia
 * @version 1.0
 */
public final class FaladorManHouseDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code FaladorManHouseDialogue} {@code Object}.
	 */
	public FaladorManHouseDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code FaladorManHouseDialogue} {@code Object}.
	 * @param player the player.
	 */
	public FaladorManHouseDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new FaladorManHouseDialogue(player);
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
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "What are you doing in my house?");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "I was just exploring.");
			stage = 2;
			break;
		case 2:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "You're exploring my house?");
			stage = 3;
			break;
		case 3:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "You don't mind, do you?");
			stage = 4;
			break;
		case 4:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "But... why are you exploring in my house?");
			stage = 5;
			break;
		case 5:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Oh, I don't know. I just wandered in, saw you and thought", "it'd be fun to speak to you.");
			stage = 6;
			break;
		case 6:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "... you are very strange...");
			stage = 7;
			break;
		case 7:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Perhaps I should go now.");
			stage = 8;
			break;
		case 8:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Yes, please go away now.");
			stage = 9;
			break;
		case 9:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 3223 };
	}
}