package at.er.sfmb.plugin;

import at.er.sfmb.plugin.command.BattleCommandManager;
import at.er.sfmb.plugin.command.manager.AbstractCommandManager;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.team.Team;
import at.er.sfmb.plugin.team.TeamColor;
import at.er.sfmb.plugin.timer.timeables.GraceTimer;
import at.er.sfmb.plugin.timer.timeables.RemindTimer;
import at.er.sfmb.util.BattleUtils;
import at.er.sfmb.util.ConfigurationHelper;
import at.er.sfmb.util.PlayerArmor;
import at.er.sfmb.util.XStreamUtil;
import com.thoughtworks.xstream.XStream;
import feature.FeatureLoader;
import feature.FeatureManager;
import feature.listener.FeatureListener;
import gnu.trove.set.hash.TIntHashSet;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.HashMap;

public class BattlePlugin extends JavaPlugin {

    private static BattlePlugin instance;
    private static ConfigurationHelper configurationHelper;
    private static Game game;

    private boolean dontSave;

    public HashMap<Player, PlayerArmor> playerArmor;
    public TIntHashSet deadPlayersItems;
    private AbstractCommandManager commandManager;
    private FeatureManager featureManager;

    public void onEnable() {
        instance = this;
        configurationHelper = new ConfigurationHelper();
        game = loadGame(new File(getDataFolder(), BattleUtils.SAVE_FILE_NAME));

        this.featureManager = new FeatureManager(this);

        this.dontSave = false;

        this.playerArmor = new HashMap<>();
        this.deadPlayersItems = new TIntHashSet();

        this.addCraftings();
        this.registerCommands();
        this.registerEvents();

        FeatureLoader.loadFromPackage(this.getFeatureManager(), "at.er.sfmb.plugin.feature.impl");
        this.featureManager.getFeature("TestFeature").enable();

        BattleUtils.setTags();
        BattleUtils.updateScoreboard();

        for (World w : Bukkit.getServer().getWorlds()) {
            w.setPVP(false);
        }
    }

    @Override
    public void onDisable() {
        BattlePlugin.game().getTimerManager().pauseAllTimers();
        BattlePlugin.game().setPaused(true);
        saveGame();
    }

    public void registerEvents() {
        new FeatureListener();

        /*new BlockBreakListener();
        new BlockPlaceListener();
        new EntityDeathListener();
        new EntityExplodeListener();
        new PlayerChatListener();
        new PlayerDeathListener();
        new PlayerInteractListener();
        new PlayerJoinListener();
        new PlayerMoveListener();
        new PlayerRespawnListener();
        new PlayerShearListener();
        new PrepareItemCraftListener();
        new InvincibilityListener();

        new AwesomeTestListener();*/
    }

    public void registerCommands() {
        this.commandManager = new BattleCommandManager();
        getCommand("battle").setExecutor(this.commandManager);
        getCommand("b").setExecutor(this.commandManager);
    }

    public void addCraftings() {

        ItemStack tear = new ItemStack(Material.GHAST_TEAR, 1);
        ItemMeta tearMeta = tear.getItemMeta();
        tearMeta.setDisplayName(ChatColor.GOLD + "Live Exchanger");
        tearMeta.setLore(Collections.singletonList("Right Click Me"));
        tear.setItemMeta(tearMeta);

        for (TeamColor tc1 : TeamColor.values()) {
            for (TeamColor tc2 : TeamColor.values()) {
                Wool w1 = new Wool(tc1.getDyeColor());
                Wool w2 = new Wool(tc2.getDyeColor());
                ShapelessRecipe lifes = new ShapelessRecipe(tear);
                lifes.addIngredient(1, w1);
                lifes.addIngredient(1, w2);
                Bukkit.getServer().addRecipe(lifes);
            }
        }

        ItemStack steak = new ItemStack(Material.COOKED_BEEF);
        ItemMeta steakMeta = steak.getItemMeta();
        steakMeta.setDisplayName(ChatColor.DARK_PURPLE + "Nom Nom!");

        ShapelessRecipe food = new ShapelessRecipe(steak);
        food.addIngredient(Material.ROTTEN_FLESH);
        food.addIngredient(Material.GOLD_NUGGET);
        Bukkit.getServer().addRecipe(food);
    }

    public void dontSave(boolean b) {
        this.dontSave = b;
    }

    private static Game loadGame(File saveFile) {
        Game game;
        if (saveFile.exists()) {
            XStream xstream = XStreamUtil.createXStream();
            try {
                game = (Game) xstream.fromXML(saveFile);
                System.out.println("Loaded " + BattleUtils.SAVE_FILE_NAME + "!");
            } catch (Exception e) {
                System.err.println("Error while trying to load " + BattleUtils.SAVE_FILE_NAME + "!");
                e.printStackTrace(System.err);
                game = new Game();
            }
        } else {
            game = new Game();
        }
        return game;
    }

    public void saveGame() {
        if (!dontSave) {
            if (game.isStarted()) {
                XStream xstream = XStreamUtil.createXStream();
                try {
                    xstream.toXML(BattlePlugin.game(), new FileOutputStream(new File(getDataFolder(), BattleUtils.SAVE_FILE_NAME)));
                } catch (Exception e) {
                    System.err.println("Error while trying to write " + BattleUtils.SAVE_FILE_NAME + "!");
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    public void startGame(int graceTime) {
        if (!BattlePlugin.game().isStarted()) {
            BattlePlugin.game().setStarted(true);

            int startlifes = BattlePlugin.configurationHelper().getConfigFile().getInt(ConfigurationHelper.GAME_STARTERLIFES_PATH);
            boolean baseItem = BattlePlugin.configurationHelper().getConfigFile().getBoolean(ConfigurationHelper.GAME_BASEBLOCK_ENABLED_PATH);

            if (BattlePlugin.configurationHelper().getConfigFile().getBoolean(ConfigurationHelper.TIMER_REMINDER_ENABLED_PATH)) {
                new RemindTimer().startReminder();
            }

            if (graceTime > 0) {
                for (World w : Bukkit.getServer().getWorlds()) {
                    w.setPVP(false);
                }
                new GraceTimer(graceTime * 60);
            } else {
                for (World w : Bukkit.getServer().getWorlds()) {
                    w.setPVP(true);
                }
            }

            BattlePlugin.game().getSpawn().getLocation().getWorld().setTime(200);

            ItemStack base = new ItemStack(Material.QUARTZ_ORE);
            ItemMeta baseMeta = base.getItemMeta();
            baseMeta.setDisplayName(ChatColor.GRAY + "Base Block");
            baseMeta.setLore(Collections.singletonList("Place me to create a base"));
            base.setItemMeta(baseMeta);

            for (Team t : BattlePlugin.game().getTeamManager().getTeams()) {
                if (t.getTeamSize() > 0) {
                    t.setLifes(startlifes);
                    t.setupInitialWool();

                    for (BattlePlayer p : t.getPlayers()) {
                        p.closeInventory();
                        p.teleport(BattlePlugin.game().getSpawn().getLocation());
                        p.setGameMode(GameMode.SURVIVAL);
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1));
                        p.getInventory().clear();
                        p.getInventory().setHelmet(new ItemStack(Material.AIR));
                        p.getInventory().setChestplate(new ItemStack(Material.AIR));
                        p.getInventory().setLeggings(new ItemStack(Material.AIR));
                        p.getInventory().setBoots(new ItemStack(Material.AIR));
                        p.getInventory().addItem(new Wool(t.getTeamColor().getDyeColor()).toItemStack(2));
                        if (baseItem) {
                            p.getInventory().addItem(base);
                        }
                        p.setHealth(20);
                        p.setFoodLevel(20);
                        p.setSaturation(20);
                    }
                }
            }

            BattlePlugin.game().setStarted(true);
            BattleUtils.setTags();
            BattleUtils.updateScoreboard();

            Location soundLocation = BattlePlugin.game().getSpawn().getLocation();
            soundLocation.getWorld().playSound(soundLocation, Sound.AMBIENCE_THUNDER, 10, 1);
            soundLocation.getWorld().playSound(soundLocation, Sound.EXPLODE, 10F, 0.5F);
        }
    }

    public void pauseGame() {
        if (!BattlePlugin.game().isPaused()) {
            BattlePlugin.game().setPaused(true);
            BattlePlugin.game().getTimerManager().pauseAllTimers();
            BattlePlugin.instance().saveGame();
            BattlePlugin.instance().dontSave(true);
        }
    }

    public void resumeGame() {
        for (World w : Bukkit.getServer().getWorlds()) {
            w.setPVP(true);
        }
        if (BattlePlugin.game().isPaused()) {
            BattlePlugin.game().setPaused(false);
            BattlePlugin.game().getTimerManager().resumeAllTimers();
            BattlePlugin.instance().dontSave(false);
        }
    }

    public void resetGame() {
        BattlePlugin.game().getTimerManager().removeAllTimers();
        BattlePlugin.instance().dontSave(true);
        BattleUtils.unsetTags();
        BattleUtils.updateScoreboard();

        File saveFile = BattleUtils.getSaveFile();
        if (saveFile.exists())
            if (!saveFile.delete())
                saveFile.deleteOnExit();

        Bukkit.reload();
    }

    public AbstractCommandManager getCommandManager() {
        return commandManager;
    }

    public FeatureManager getFeatureManager() {
        return featureManager;
    }

    public static String prefix() {
        return ChatColor.GOLD + "[Battle] " + ChatColor.WHITE;
    }

    public static BattlePlugin instance() {
        return instance;
    }

    public static ConfigurationHelper configurationHelper() {
        return configurationHelper;
    }

    public static Game game() {
        return game;
    }

}
