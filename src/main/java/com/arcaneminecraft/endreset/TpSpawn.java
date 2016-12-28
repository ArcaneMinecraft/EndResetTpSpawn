package com.arcaneminecraft.endreset;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

public class TpSpawn extends JavaPlugin {
	private static final long END_RESET_TIME = 1482961133526L; // Subject to change when the actual reset happens.
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new CheckEndTime(), this);
	}
	
	public final class CheckEndTime implements Listener {
		// Low priority for this; normal for donor, high for mod
		@EventHandler (priority=EventPriority.LOW)
		public void checkEndTime(PlayerJoinEvent e) {
			Player p = e.getPlayer();
			// Player played before, is in the End, and last played time is smaller than the reset time
			if (p.hasPlayedBefore()
					&& p.getLocation().getWorld().getEnvironment().equals(Environment.THE_END)
					&& p.getLastPlayed() < END_RESET_TIME) {
				getLogger().info("Player " + p.getName() + " left off in the End before the End reset.");
				// Find the overworld (normal world)
				for (World w : getServer().getWorlds()) {
					if (w.getEnvironment().equals(Environment.NORMAL)) {
						// Spawn at the spawn point
						p.teleport(w.getSpawnLocation(), TeleportCause.END_PORTAL);
						return;
					}
				}
			}
		}
	}
}
