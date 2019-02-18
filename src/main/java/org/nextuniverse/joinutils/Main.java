package org.nextuniverse.joinutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by TheDiamondPicks on 3/10/2017.
 */
public class Main extends JavaPlugin implements Listener {

    private Location spawn;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        Bukkit.getPluginManager().registerEvents(this, this);
        spawn = new Location(Bukkit.getWorld(getConfig().getString("world")), getConfig().getDouble("x"), getConfig().getDouble("y"), getConfig().getDouble("z"), (float) getConfig().getDouble("yaw"), (float) getConfig().getDouble("pitch"));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.teleport(spawn);
        if (p.hasPermission("join.special"))
            e.setJoinMessage(ChatColor.DARK_AQUA + p.getName() + ChatColor.AQUA + " has joined!");
        else
            e.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + p.getName());

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

        BookMeta meta = (BookMeta) book.getItemMeta();

        meta.setTitle(ChatColor.AQUA + "Server Information");
        meta.setAuthor("NextUniverse Team");

        meta.setPages(ChatColor.AQUA + "Welcome to " + ChatColor.LIGHT_PURPLE + "NextUniverse\n\n" + ChatColor.RESET + "" + ChatColor.BOLD + "The Voting System\n"
        + ChatColor.RESET + "The core of NextUniverse is the voting system, which allows players to pick a game. There is only one 'server' on NextUniverse - but this server can have dozens of games (but not at the same time)" +
                ", meaning that players are spread across dozens of games, rather they are just in 1." +
                " The first part is the command §b/vote§r. This immediately votes for the minigame to switch, afterwards you will then be presented by a menu, allowing you to choose which minigame to switch too." +
                " The more of the server that votes for a game the more likely it will be.\n\n" +
                "§lTip! §rIf you are on the server alone, you can vote and then pick one of the games that are marked as Singleplayer. Because you are the only one online, the game will be guaranteed!\n" +
                "\nOnce you vote, a countdown will start from 5 minutes. At 1 minute remaining joining games will be disabled.\n When the timer is up the server will restart, transferring everyone to a lobby. During this time" +
                " the server will decide what minigame is going to be played next. Eventually you will be teleported to the new game!\n" +
                "\n§lHelp\n§rFor help with commands, use the command §b/help§r\nIf you want to see the server rules, use §b/rules" +
                "\n§rIf you see a rule-breaker use §b/report <player> <reason>§r. This notifies all online staff of them." +
                "\nIf you have another question, you can either ask someone online or use our forums: §bforums.nextuniverse.org");

        p.getInventory().setItemInMainHand(book);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("join.special"))
            e.setQuitMessage(ChatColor.DARK_AQUA + p.getName() + ChatColor.AQUA + " has quit!");
        else
            e.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + p.getName());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("spawn") && sender instanceof Player)
            ((Player) sender).teleport(spawn);
        else if (command.getName().equals("setspawn") && sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("spawn.set")) {
                getConfig().set("world", p.getLocation().getWorld().getName());
                getConfig().set("x", p.getLocation().getX());
                getConfig().set("y", p.getLocation().getY());
                getConfig().set("z", p.getLocation().getZ());
                getConfig().set("yaw", p.getLocation().getYaw());
                getConfig().set("pitch", p.getLocation().getPitch());

                saveConfig();
                reloadConfig();
                spawn = new Location(Bukkit.getWorld(getConfig().getString("world")), getConfig().getDouble("x"), getConfig().getDouble("y"), getConfig().getDouble("z"), (float) getConfig().getDouble("yaw"), (float) getConfig().getDouble("pitch"));

                p.sendMessage(ChatColor.AQUA + "Spawn set successfully!");
            }
        }
        else if (command.getName().equals("book") && sender instanceof Player) {
            Player p = (Player) sender;

            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

            BookMeta meta = (BookMeta) book.getItemMeta();

            meta.setTitle(ChatColor.AQUA + "Server Information");
            meta.setAuthor("NextUniverse Team");

            meta.setPages(ChatColor.AQUA + "Welcome to " + ChatColor.LIGHT_PURPLE + "NextUniverse\n\n" + ChatColor.RESET + "" + ChatColor.BOLD + "The Voting System\n"
                    + ChatColor.RESET + "The core of NextUniverse is the voting system, which allows players to pick a game. There is only one 'server' on NextUniverse - but this server can have dozens of games (but not at the same time)" +
                    ", meaning that players are spread across dozens of games, rather they are just in 1." +
                    " The first part is the command §b/vote§r. This immediately votes for the minigame to switch, afterwards you will then be presented by a menu, allowing you to choose which minigame to switch too." +
                    " The more of the server that votes for a game the more likely it will be.\n\n" +
                    "§lTip! §rIf you are on the server alone, you can vote and then pick one of the games that are marked as Singleplayer. Because you are the only one online, the game will be guaranteed!\n" +
                    "\nOnce you vote, a countdown will start from 5 minutes. At 1 minute remaining joining games will be disabled.\n When the timer is up the server will restart, transferring everyone to a lobby. During this time" +
                    " the server will decide what minigame is going to be played next. Eventually you will be teleported to the new game!\n" +
                    "\n§lHelp\n§rFor help with commands, use the command §b/help§r\nIf you want to see the server rules, use §b/rules" +
                    "\n§rIf you see a rule-breaker use §b/report <player> <reason>§r. This notifies all online staff of them." +
                    "\nIf you have another question, you can either ask someone online or use our forums: §bforums.nextuniverse.org");

            p.getInventory().setItemInMainHand(book);
        }
        else if (command.getName().equals("help")) {
            // TODO
        }
        return true;
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
        e.setCancelled(true);
        e.getSender().sendMessage(ChatColor.AQUA + "If you need help with the commands, use /help");
    }
}
