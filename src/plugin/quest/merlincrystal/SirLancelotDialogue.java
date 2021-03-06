package plugin.quest.merlincrystal;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.content.global.quest.impl.member.MerlinCrystal;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the dialogue plugin used for king arthur.
 * @author 'Vexia
 * @author Splinter
 * @version 2.0
 */
public final class SirLancelotDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code KingArthurDialogue} {@code Object}.
	 */
	public SirLancelotDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code KingArthurDialogue} {@code Object}.
	 * @param player the player.
	 */
	public SirLancelotDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new SirLancelotDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Greetings! I am Sir Lancelot, the greatest Knight in the", "land! What do you want?");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			if (player.getQuestRepository().getQuest(MerlinCrystal.NAME).getStage() >= 20 && player.getQuestRepository().getQuest(MerlinCrystal.NAME).getStage() < 50) {
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "Any idea on how to get into Morgan Le Faye's", "stronghold?");
				stage = 1;
			} else {
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "You're a little full of yourself, aren't you?");
				stage = 16;
			}
			break;
		case 1:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "That stronghold is built in a strong defensive position.");
			stage = 2;
			break;
		case 2:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "There are two ways in that I know of, the large heavy", "front doors, and the sea entrance, only penetrable by", "boat.");
			stage = 3;
			break;
		case 3:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "They take all their deliveries by boat.");
			player.getQuestRepository().getQuest(MerlinCrystal.NAME).setStage(30);
			player.getQuestRepository().update(player);
			stage = 15;
			break;
		case 15:
			end();
			break;
		case 16:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "I have every right to be proud of myself.");
			stage = 17;
			break;
		case 17:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "My prowess in battle is world renowned!");
			stage = 15;
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 239 };
	}
}