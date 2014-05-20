package com.hotmail.ownedwtf.UltimateShop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

import com.hotmail.ownedwtf.UltimateShop.cmds.*;

public class CommandManager implements CommandExecutor{

private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();

    public void setup() {
        commands.add(new checkVotePoints());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player)) {
            MessageManager.getInstance().severe(sender, "Only players can use UltimateShop");
            return true;
        }

        Player p = (Player) sender;

        SubCommand target = get(args[0]);

        if (target == null) {
            MessageManager.getInstance().severe(p, "/ultmateshop " + args[0] + " is not a valid subcommand!");
            return true;
        }

        ArrayList<String> a = new ArrayList<String>();
        a.addAll(Arrays.asList(args));
        a.remove(0);
        args = a.toArray(new String[a.size()]);

        try {
            target.onCommand(p, args);
        }

        catch (Exception e) {
            MessageManager.getInstance().severe(p, "An error has occured: " + e.getCause());
            e.printStackTrace();
        }
        return true;
    }

    private String aliases(SubCommand cmd) {
        String fin = "";

        for (String a : cmd.aliases()) {
            fin += a + " | ";
        }

        return fin.substring(0, fin.lastIndexOf(" | "));
    }

    private SubCommand get(String name) {
        for (SubCommand cmd : commands) {
            if (cmd.name().equalsIgnoreCase(name)) return cmd;
            for (String alias : cmd.aliases()) if (name.equalsIgnoreCase(alias)) return cmd;
        }
        return null;
    }

}

