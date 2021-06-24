package dreamys.studio.staffessentials;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public class StaffEssentials extends JavaPlugin {
    ArrayList<String> names = new ArrayList<>(Arrays.asList("heal", "feed", "vanish", "report"));
    @Override
    public void onEnable() {
        Utils.log("Initializing...");
        Commands commands = new Commands();
        names.forEach(name -> getCommand(name).setExecutor(commands));
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        Utils.log("Initialized");
    }

    @Override
    public void onDisable() {
        Utils.log("Terminating...");
    }
}
