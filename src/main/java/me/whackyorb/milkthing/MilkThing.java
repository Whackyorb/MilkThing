package me.whackyorb.milkthing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class MilkThing extends JavaPlugin {
	
	@Override
	public void onEnable(){
		// Nothihng :P
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,String label, String[] args){
		
		// Check if it is a player issuing the command (not a command block or the console)
		if (!(sender instanceof Player)){
			return true; // do nothing
		}
		
		// get a player object from the sender object so we can get what world it is in
		Player player = (Player)sender;
		
		// get the worlds time
		long time = player.getWorld().getTime();
		
		// check if is day
		if (time < 12300 || time > 23850){
			return true; // do nothing
		}
		
		// check if there is a bed in 3 blocks of the players location
		if (!bedIsNear(player.getLocation(), 3)){
			return true; // do nothing
		}
		
		PlayerInventory inventory = player.getInventory();
		
		// give feathers (NOTE THIS WILL KILL ANYTHING IN THEIR HANDS)
		inventory.setItemInMainHand(new ItemStack(Material.FEATHER));
		inventory.setItemInOffHand(new ItemStack(Material.FEATHER));
		return true;
	}
	
	// could have just tweaked the "getNearbyBlocks" method but I wanted to keep my code separate for this demo
	public boolean bedIsNear(Location location, int radius){
		List<Block> blocks = getNearbyBlocks(location, radius);
		
		for(Block block : blocks){
			if (block.getType() == Material.BED_BLOCK){
				return true;
			}
		}
		return false;
	}
	
	// As the name implies gets nearby blocks, ripped from a https://www.spigotmc.org thread after a quick google
    public List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                   blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

}
