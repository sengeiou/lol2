package org.arios.game.component;

import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.InterfaceManager;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.InterfaceContext;
import org.arios.net.packet.out.Interface;

/**
 * Represents a component.
 * @author Emperor
 *
 */
public class Component {

	/**
	 * The component id.
	 */
	protected int id;

	/**
	 * The component definitions.
	 */
	protected final ComponentDefinition definition;

	/**
	 * The close event.
	 */
	protected CloseEvent closeEvent;

	/**
	 * The component plugin.
	 */
	protected ComponentPlugin plugin;

	/**
	 * Constructs a new {@code Component} {@code Object}.
	 * @param id The component id.
	 */
	public Component(int id) {
		this.id = id;
		this.definition = ComponentDefinition.forId(id);
		this.plugin = definition.getPlugin();
	}

	/**
	 * Opens the component.
	 */
	public void open(Player player) {
		InterfaceManager manager = player.getInterfaceManager();
		if (definition == null) {
			PacketRepository.send(Interface.class, new InterfaceContext(player, manager.getWindowPaneId(), manager.getDefaultChildId(), getId(), false));
			return;
		}
		if (definition.getType() == InterfaceType.WINDOW_PANE) {
			return;
		}
		if (definition.getType() == InterfaceType.TAB) {
			PacketRepository.send(Interface.class, new InterfaceContext(player, definition.getWindowPaneId(manager.isResizable()), definition.getChildId(manager.isResizable()) + definition.getTabIndex(), getId(), definition.isWalkable()));
			return;
		}
		PacketRepository.send(Interface.class, new InterfaceContext(player, definition.getWindowPaneId(manager.isResizable()), definition.getChildId(manager.isResizable()), getId(), definition.isWalkable()));
	}

	/**
	 * Closes the component.
	 * @param player The player.
	 * @return {@code True} if the component can be closed.
	 */
	public boolean close(Player player) {
		if (closeEvent != null && !closeEvent.close(player, this)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the id.
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the definition.
	 * @return The definition.
	 */
	public ComponentDefinition getDefinition() {
		return definition;
	}

	/**
	 * Gets the closeEvent.
	 * @return The closeEvent.
	 */
	public CloseEvent getCloseEvent() {
		return closeEvent;
	}

	/**
	 * Sets the closeEvent.
	 * @param closeEvent The closeEvent to set.
	 */
	public Component setCloseEvent(CloseEvent closeEvent) {
		this.closeEvent = closeEvent;
		return this;
	}

	/**
	 * Sets the component unclosable.
	 * @param c The component.
	 */
	public static void setUnclosable(Player p, Component c) {
		p.setAttribute("close_c_", false);
		c.setCloseEvent(new CloseEvent() {
			@Override
			public boolean close(Player player, Component c) {
				if (!player.getAttribute("close_c_", false)) {
					return false;
				}
				return true;
			}
		});
	}

	/**
	 * Sets the plugin.
	 * @param plugin the plugin.
	 */
	public void setPlugin(ComponentPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Gets the component plugin.
	 * @return the plugin.
	 */
	public ComponentPlugin getPlugin() {
		if (plugin == null) {
			ComponentPlugin p = ComponentDefinition.forId(getId()).getPlugin();
			if ((plugin = p) != null) {
				return p;
			}
		}
		return plugin;
	}
}