package plugin.interaction.item.withobject;

import org.arios.game.content.skill.Skills;
import org.arios.game.content.skill.free.smithing.SmithingBuilder;
import org.arios.game.content.skill.free.smithing.smelting.Bar;
import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.UseWithHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.plugin.Plugin;

/**
 * Represents the option handler used for smithing.
 * @author 'Vexia
 * @version 1.0
 */
public final class SmithingPlugin extends UseWithHandler {

	/**
	 * Constructs a new {@code SmithingPlugin} {@code Object}.
	 */
	public SmithingPlugin() {
		super(2349, 2351, 2353, 2359, 2361, 2363, 2366, 2368, 9467, 11286, 1540, 11710, 11712, 11714, 11686, 11688, 11692);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		addHandler(2782, OBJECT_TYPE, this);
		addHandler(2783, OBJECT_TYPE, this);
		addHandler(4306, OBJECT_TYPE, this);
		addHandler(6150, OBJECT_TYPE, this);
		addHandler(22725, OBJECT_TYPE, this);
		addHandler(26817, OBJECT_TYPE, this);
		addHandler(37622, OBJECT_TYPE, this);
		return this;
	}

	@Override
	public boolean handle(NodeUsageEvent event) {
		final Player player = event.getPlayer();
		if (((GameObject) event.getUsedWith()).getId() == 2782 && !player.getQuestRepository().isComplete("Doric's Quest")) {
			player.getDialogueInterpreter().sendDialogue("Property of Doric the Dwarf.");
			return true;
		}
		if (!player.getInventory().contains(2347, 1)) {
			player.getDialogueInterpreter().sendDialogue("You need a hammer to work the metal with.");
			return true;
		} else {
			if (event.getUsedItem().getId() == 2366 || event.getUsedItem().getId() == 2368) {
				if (player.getSkills().getLevel(Skills.SMITHING) < 60) {
					player.getDialogueInterpreter().sendDialogue("You need a smithing level of 60 to smith this.");
					return true;
				}
				if (!player.getInventory().contains(2366, 1) && !player.getInventory().contains(2368, 1)) {
					player.getDialogueInterpreter().sendDialogue("You need a shield left half and a shield right half.");
					return true;
				}
				player.getDialogueInterpreter().open(82127843, 1, event.getUsedItem().getId());
				return true;
			}
			if (event.getUsedItem().getId() == 11286 || event.getUsedItem().getId() == 1540) {
				if (player.getSkills().getLevel(Skills.SMITHING) < 90) {
					player.getDialogueInterpreter().sendDialogue("You need a smithing level of 90 to smith this.");
					return true;
				}
				if (!player.getInventory().contains(1540, 1) && !player.getInventory().contains(11286, 1)) {
					player.getDialogueInterpreter().sendDialogue("You need a draconic visage and a anti-dragon shield.");
					return true;
				}
				player.getDialogueInterpreter().open(82127843, 2, event.getUsedItem().getId());
				return true;
			}
			if (event.getUsedItem().getId() == 11710 || event.getUsedItem().getId() == 11712 || event.getUsedItem().getId() == 11714 || event.getUsedItem().getId() == 11686 || event.getUsedItem().getId() == 11688 || event.getUsedItem().getId() == 11692) {
				if (player.getSkills().getLevel(Skills.SMITHING) < 80) {
					player.getDialogueInterpreter().sendDialogue("You need a smithing level of 80 to smith this.");
					return true;
				}
				player.getDialogueInterpreter().open(62362, event.getUsedItem().getId());
				return true;
			}
			Bar bar = Bar.forId(event.getUsedItem().getId());
			if (bar == null) {
				return true;
			}
			if (player.getSkills().getLevel(Skills.SMITHING) < bar.getLevel()) {
				player.getDialogueInterpreter().sendDialogue("You need a smithing level of at leat " + bar.getLevel() + " to work " + bar.getProduct().getName().toLowerCase() + "s.");
				return true;
			}
			final SmithingBuilder builder = new SmithingBuilder(event.getUsedItem());
			builder.build(player);
		}
		return true;
	}

}
