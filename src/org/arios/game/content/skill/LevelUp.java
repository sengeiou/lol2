package org.arios.game.content.skill;

import org.arios.game.component.Component;
import org.arios.game.content.global.Skillcape;
import org.arios.game.content.global.tutorial.TutorialSession;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.emote.Emotes;
import org.arios.game.world.repository.Repository;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.MusicContext;
import org.arios.net.packet.out.MusicPacket;

/**
 * Represents a leveling up action.
 * @author Emperor
 */
public final class LevelUp {
	
	/**
	 * Level up sound ids.
	 */
	private static final int[] SOUND_EFFECTS = { 29, 37, 65, 48, 57, 55, 52, 33, 69, 44, 41, 39, 35, 43, 53, 45, 28, 34, 62, 11, 60, 49, 31, 300 };
	
	/**
	 * The skillcapes data.
	 */
	private static final int[] SKILLCAPES = { 9747, // Attack
			9753, // Defence
			9750, // Strength
			9768, // Hitpoints
			9756, // Range
			9759, // Prayer
			9762, // Magic
			9801, // Cooking
			9807, // Woodcutting
			9783, // Fletching
			9798, // Fishing
			9804, // Firemaking
			9780, // Crafting
			9795, // Smithing
			9792, // Mining
			9774, // Herblore
			9771, // Agility
			9777, // Thieving
			9786, // Slayer
			9810, // Farming
			9765, // Runecrafting
			9948, // Hunter
			9789, // Construction
			12169 // Summoning
	};

	/**
	 * The skill icon config values send on the level up chat box interface.
	 */
	public static final int[] SKILL_ICON = { 67108864, 335544320, 134217728,
			402653184, 201326592, 469762048, 268435456, 1073741824, 1207959552,
			1275068416, 1006632960, 1140850688, 738197504, 939524096,
			872415232, 603979776, 536870912, 671088640, 1342177280, 1409286144,
			805306368, 1543503872, 1476395008, 1610612736, 1677721600 };

	/**
	 * The flashing icon config values on level up.
	 */
	public static final int[] FLASH_ICONS = { 1, 4, 2, 64, 8, 16, 32, 32768,
			131072, 2048, 16384, 65536, 1024, 8192, 4096, 256, 128, 512,
			524288, 1048576, 262144, 4194304, 2097152, 8388608, 16777216 };
	
	/**
	 * Sends the level up interface etc.
	 * @param player The player.
	 * @param slot The skill slot.
	 */
	public static void levelup(Player player, int slot) {
		if (player.getAttribute("tut-island") != null) {
			return;
		}
		if (TutorialSession.getExtension(player).getStage() < TutorialSession.MAX_STAGE) {
			return;
		}
		int soundId = SOUND_EFFECTS[slot];
		if (soundId > -1) {
			PacketRepository.send(MusicPacket.class, new MusicContext(player, soundId, true));
		}
		player.getPacketDispatch().sendGraphic(199);
		player.getPacketDispatch().sendString("<col=00008B>Congratulations, you've just advanced a " + Skills.SKILL_NAME[slot] + " level!", 740, 0);
		player.getPacketDispatch().sendString("Your " + Skills.SKILL_NAME[slot] + " level is now " + player.getSkills().getStaticLevel(slot) + ".", 740, 1);
		player.getPacketDispatch().sendMessage("You've just advanced a " + Skills.SKILL_NAME[slot] + " level! You have reached level " + player.getSkills().getStaticLevel(slot) + ".");
		if (slot == Skills.PRAYER) {
			player.getSkills().incrementPrayerPoints(1);
		}
		if (player.getSkills().getStaticLevel(slot) == 99) {
			Repository.sendNews(player.getUsername() + " has just achieved 99 " + Skills.SKILL_NAME[slot]);
			Skillcape.trim(player);
			player.getEmoteManager().unlock(Emotes.SKILLCAPE);
		}
		player.setAttribute("/save:levelup:" + slot, true);
		sendFlashingIcons(player, slot);
	}
	
	/**
	 * Sends the flashing icons and level up interface (if slot is larger than -1).
	 * @param player The player.
	 * @param slot The currently level skill id (-1 for just sending flashing icons).
	 */
	public static void sendFlashingIcons(Player player, int slot) {
		int value = 0;
		for (int i = 0; i < Skills.SKILL_NAME.length; i++) {
			if (player.getAttribute("levelup:" + i, false)) {
				value |= FLASH_ICONS[i];
			}
		}
		if (slot > -1) {
			value |= SKILL_ICON[slot];
			player.getInterfaceManager().openChatbox(new Component(740));
		}
		player.getConfigManager().set(1179, value);
	}

	/**
	 * @return the skillcapes
	 */
	public static int[] getSkillcapes() {
		return SKILLCAPES;
	}
}