package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the dialogue plugin used for the crafting tutor.
 * @author 'Vexia
 * @version 1.0
 */
public final class CraftingTutorDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code CraftingTutorDialogue} {@code Object}.
	 */
	public CraftingTutorDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code CraftingTutorDialogue} {@code Object}.
	 * @param player the player.
	 */
	public CraftingTutorDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new CraftingTutorDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(player, FacialExpression.NORMAL, "Can you teach me the basics of crafting please?");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Firstly, you should know that not all places associated", "with crafting will be marked on your mini map. Some", "take quite a bit of hunting down to find, don't lose", "heart!");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "I see... so where should I start?");
			stage = 2;
			break;
		case 2:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "When you have a full inventory, take it to the bank,", "you can find it on the roof of this very castle.");
			stage = 3;
			break;
		case 3:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 4900 };
	}
}