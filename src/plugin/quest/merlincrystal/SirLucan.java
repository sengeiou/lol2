package plugin.quest.merlincrystal;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.impl.member.MerlinCrystal;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Handles the dialogue for Sir Lucan
 * @author Splinter
 * @author Vexia
 */
public class SirLucan extends DialoguePlugin {

	/**
	 * Constructs a new {@code SirLucan} {@code Object}
	 */
	public SirLucan() {
		/*
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code SirLucan} {@code Object}
	 * @param player the player.
	 */
	public SirLucan(Player player) {
		super(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Hello there adventurer.");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			Quest quest = player.getQuestRepository().getQuest(MerlinCrystal.NAME);
			if (quest.getStage() == 100) {
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Congratulations on freeing Merlin!");
				stage = 20;
			} else if (quest.getStage() >= 10 && quest.getStage() < 50) {
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "Any suggestions on freeing Merlin?");
				stage = 1;
			} else {
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "I am afraid I have no time to chat.");
				stage = 20;
			}
			break;
		case 1:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "I've tried all the weapons I can find, yet none are", "powerful enough to break his crystal prison...");
			stage = 2;
			break;
		case 2:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Perhaps the mighty Excalibur would be strong enough...");
			stage = 3;
			break;
		case 3:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Excalibur eh? Where would I find that?");
			stage = 4;
			break;
		case 4:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "The Lady of the Lake is looking atfer it I believe... but", "I know not where she resides currently.");
			stage = 5;
			break;
		case 5:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Thanks. I'll try and find someone who does.");
			stage = 20;
			break;
		case 20:
			end();
			break;
		}
		return true;
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new SirLucan(player);
	}

	@Override
	public int[] getIds() {
		return new int[] { 245 };
	}
}