package plugin.interaction.item;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.Region;
import org.arios.game.world.map.RegionManager;
import org.arios.plugin.Plugin;
import org.arios.tools.RandomFunction;

/**
 * Handles the Silver Sickle (b) to collect Mort Myre Fungus.
 * @author Splinter
 */
public final class SilverSicklePlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ItemDefinition.forId(2963).getConfigurations().put("option:operate", this);
		ItemDefinition.forId(2963).getConfigurations().put("option:cast bloom", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		Region region = RegionManager.getRegionCache().get(player.getLocation().getRegionId());
		switch (option) {
		case "operate":
		case "cast bloom":
			if (player.getSkills().getPrayerPoints() < 1) {
				player.getPacketDispatch().sendMessage("You don't have enough prayer points to do this.");
			}
			for (GameObject[] o : region.getPlanes()[0].getObjects()) {
				for (GameObject obj : o) {
					if (obj != null) {
						if (obj.getName().equalsIgnoreCase("Rotting log") && player.getSkills().getPrayerPoints() >= 1) {
							if (player.getLocation().withinDistance(obj.getLocation(), 2)) {
								handleVisuals(player, node);
								ObjectBuilder.add(new GameObject(3509, obj.getLocation(), obj.getRotation()));
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Handles the draining of prayer points and physical graphics and
	 * animation.
	 */
	public void handleVisuals(Player player, Node node) {
		player.getSkills().decrementPrayerPoints(RandomFunction.random(1, 3));
		player.getPacketDispatch().sendAnimation(9021);
		final Location[] AROUND_YOU = new Location[] { Location.create(player.getLocation().getX() - 1, player.getLocation().getY(), 0), Location.create(player.getLocation().getX() + 1, player.getLocation().getY(), 0), Location.create(player.getLocation().getX(), player.getLocation().getY() - 1, 0), Location.create(player.getLocation().getX(), player.getLocation().getY() + 1, 0), Location.create(player.getLocation().getX() + 1, player.getLocation().getY() + 1, 0), Location.create(player.getLocation().getX() - 1, player.getLocation().getY() + 1, 0), Location.create(player.getLocation().getX() + 1, player.getLocation().getY() - 1, 0), Location.create(player.getLocation().getX() - 1, player.getLocation().getY() - 1, 0), Location.create(player.getLocation().getX() + 1, player.getLocation().getY() + 1, 0), };
		for (Location location : AROUND_YOU) {
			// The graphic is meant to play on a 3x3 radius around you, but not
			// including the tile you are on.
			player.getPacketDispatch().sendGlobalPositionGrapicc(263, location);
		}

	}

}