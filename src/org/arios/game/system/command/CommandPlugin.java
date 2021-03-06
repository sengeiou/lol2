package org.arios.game.system.command;

import org.arios.game.node.entity.player.Player;
import org.arios.game.world.repository.Repository;
import org.arios.plugin.Plugin;

/**
 * Represents a command plugin that can be linked to a command set.
 * @author Vexia
 */
public abstract class CommandPlugin implements Plugin<Object> {

	/**
	 * Method used to wrap around a command sets pase method.
	 * @param player the player.
	 * @param name the name.
	 * @param args the arguments.
	 * @return <code>True</code> if so.
	 */
	public abstract boolean parse(final Player player, final String name, final String[] args);

	/**
	 * Used to override for specific plugins.
	 * @param player the player.
	 * @return {@code True} if so.
	 */
	public boolean validate(Player player) {
		return true;
	}

	@Override
	public Object fireEvent(String identifier, Object... args) {
		return null;
	}

	/**
	 * Method used to link this command plugin to a command set.
	 * @param sets the sets to link to.
	 */
	public final void link(final CommandSet... sets) {
		for (CommandSet set : sets) {
			set.getPlugins().add(this);
		}
	}

	/**
	 * Gets the target player.
	 * @param player the player.
	 * @param name the name.
	 * @return the player.
	 */
	public static Player getTarget(String name) {
		Player target = Repository.getPlayerByDisplay(name);
		if (target == null) {
			target = Repository.getPlayerFile(name);
		}
		return target;
	}

	/**
	 * Method used to parse the string as an integer.
	 * @param string the string.
	 * @return the integer.
	 */
	public static int toInteger(final String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException exception) {
			return 1;
		}
	}

	/**
	 * Gets the argument line (starting at index 1).
	 * @param args The arguments.
	 * @return The argument line.
	 */
	public static String getArgumentLine(String[] args) {
		return getArgumentLine(args, 1, args.length);
	}

	/**
	 * Gets the argument line from the given arguments.
	 * @param args The arguments.
	 * @param offset The start index.
	 * @param length The end index.
	 * @return the line.
	 */
	public static String getArgumentLine(String[] args, int offset, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = offset; i < length; i++) {
			if (i != offset) {
				sb.append(" ");
			}
			sb.append(args[i]);
		}
		return sb.toString();
	}
}
