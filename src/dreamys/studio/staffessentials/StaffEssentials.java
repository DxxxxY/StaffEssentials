package dreamys.studio.staffessentials;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public class StaffEssentials extends JavaPlugin {
    ArrayList<String> commandNames = new ArrayList<>(Arrays.asList("heal", "feed", "vanish", "staff", "report", "chat"));
    ArrayList<Listener> eventNames = new ArrayList<>(Arrays.asList(new Events()));
    @Override
    public void onEnable() {
        Utils.log("Initializing...");
        Commands commands = new Commands();
        commandNames.forEach(name -> getCommand(name).setExecutor(commands));
        eventNames.forEach(name -> getServer().getPluginManager().registerEvents(name, this));
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        Utils.log("Initialized");
    }

    @Override
    public void onDisable() {
        Utils.log("Terminating...");
    }
}
