package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Handles the SeerDialoguePlugin dialogue.
 * @author 'Vexia
 */
public class SeerDialoguePlugin extends DialoguePlugin {

	public SeerDialoguePlugin() {

	}

	public SeerDialoguePlugin(Player player) {
		super(player);
	}

	@Override
	public int[] getIds() {
		return new int[] { 388 };
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Anyway, sorry about that.");
			stage = 1;
			break;
		case 1:
			interpreter.sendOptions("Choose an option:", "Many greetings.", "I seek knowledge and power.");
			stage = 2;
			break;
		case 2:
			switch (buttonId) {
			case 1:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "Many greetings.");
				stage = 10;
				break;
			case 2:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "I seek knowledge and power.");
				stage = 20;
				break;

			}
			break;
		case 10:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Greetings!");
			stage = 11;
			break;
		case 11:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Remember, whenever you set out to do something,", "something else must be done first.");
			stage = 12;
			break;
		case 12:
			end();
			break;
		case 20:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Knowledge comes from experience, power", "comes from battleaxes.");
			stage = 21;
			break;
		case 21:
			end();
			break;
		}
		return true;
	}

	@Override
	public DialoguePlugin newInstance(Player player) {

		return new SeerDialoguePlugin(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Uh, what was that dark force? I've never sensed anything", "like it...");
		stage = 0;
		return true;
	}
}