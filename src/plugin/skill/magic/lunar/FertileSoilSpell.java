package plugin.skill.magic.lunar;

import org.arios.game.content.skill.free.magic.MagicSpell;
import org.arios.game.content.skill.free.magic.Runes;
import org.arios.game.content.skill.member.farming.FarmingPatch;
import org.arios.game.content.skill.member.farming.wrapper.PatchWrapper;
import org.arios.game.node.Node;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.equipment.SpellType;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.SpellBookManager.SpellBook;
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.plugin.Plugin;

/**
 * Represents the fertile soil spell plugin.
 * @author 'Vexia
 * @version 1.0
 */
public final class FertileSoilSpell extends MagicSpell {

	/**
	 * Represents the graphic to use.
	 */
	private static final Graphics GRAPHIC = new Graphics(141, 96);

	/**
	 * Represents the animaton to use.
	 */
	private static final Animation ANIMATION = new Animation(722);

	/**
	 * Constructs a new {@code FertileSoilSpell} {@code Object}.
	 */
	public FertileSoilSpell() {
		super(SpellBook.LUNAR, 83, 87, null, null, null, new Item[] { new Item(Runes.NATURE_RUNE.getId(), 2), new Item(Runes.ASTRAL_RUNE.getId(), 3), new Item(Runes.EARTH_RUNE.getId(), 15) });
	}

	@Override
	public Plugin<SpellType> newInstance(SpellType arg) throws Throwable {
		SpellBook.LUNAR.register(2, this);
		return this;
	}

	@Override
	public boolean cast(Entity entity, Node target) {
		final Player player = ((Player) entity);
		final GameObject object = (GameObject) target;
		if (FarmingPatch.forObject(object.getWrapper().getId()) == null) {
			return false;
		}
		final PatchWrapper wrapper = player.getFarmingManager().getPatchWrapper(object.getWrapper().getId());
		if (!wrapper.isEmpty() && !wrapper.getCycle().getGrowthHandler().isGrowing()) {
			player.getPacketDispatch().sendMessage("You can't fertilize the patch.");
			return false;
		}
		if (!super.meetsRequirements(player, true, true)) {
			return false;
		}
		player.graphics(GRAPHIC);
		player.animate(ANIMATION);
		wrapper.getCycle().setCompostThreshold(2);
		return true;
	}
}
