package plugin.interaction.object.wildyditch;

import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.node.entity.impl.ForceMovement;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.audio.Audio;
import org.arios.game.node.object.GameObject;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;

/**
 * Represents the wilderness interface plugin.
 * @author Emperor
 * @version 1.0
 */
public final class WildernessInterfacePlugin extends ComponentPlugin {

	/**
	 * The animation.
	 */
	private static final Animation ANIMATION = Animation.create(6132);

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ComponentDefinition.put(382, this);
		return this;
	}

	@Override
	public boolean handle(Player player, Component component, int opcode, int button, int slot, int itemId) {
		player.getInterfaceManager().close();
		if (button != 18) {
			return true;
		}
		handleDitch(player);
		return true;
	}

	/**
	 * Method used to handle the ditch interface.
	 * @param player the player.
	 */
	public static void handleDitch(final Player player) {
		GameObject ditch = player.getAttribute("wildy_ditch");
		if (ditch == null) {
			return;
		}
		player.removeAttribute("wildy_ditch");
		Location l = ditch.getLocation();
		int x = player.getLocation().getX();
		int y = player.getLocation().getY();
		if (ditch.getRotation() % 2 == 0) {
			if (y <= l.getY()) {
				ForceMovement.run(player, Location.create(x, l.getY() - 1, 0), Location.create(x, l.getY() + 2, 0), ANIMATION, 20).setEndAnimation(null);
			} else {
				ForceMovement.run(player, Location.create(x, l.getY() + 2, 0), Location.create(x, l.getY() - 1, 0), ANIMATION, 20).setEndAnimation(null);
			}
		} else {
			if (x > l.getX()) {
				ForceMovement.run(player, Location.create(l.getX() + 2, y, 0), Location.create(l.getX() - 1, y, 0), ANIMATION, 20).setEndAnimation(null);
			} else {
				ForceMovement.run(player, Location.create(l.getX() - 1, y, 0), Location.create(l.getX() + 2, y, 0), ANIMATION, 20).setEndAnimation(null);
			}
		}
		player.getAudioManager().send(new Audio(2462, 10, 30));
	}
}
