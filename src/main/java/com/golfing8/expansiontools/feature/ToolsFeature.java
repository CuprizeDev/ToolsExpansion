package com.golfing8.expansiontools.feature;

import com.golfing8.expansiontools.util.ChatUtil;
import com.golfing8.expansiontools.util.FactionsUtil;
import com.golfing8.expansiontools.util.WorldGuardUtil;
import com.golfing8.kore.config.annotation.ConfigValue;
import com.golfing8.kore.feature.Feature;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;

public class ToolsFeature extends Feature {


    // Configuration Settings / Messages

    // Not High Enough
    @ConfigValue(name = "not-high-enough", path = "feature")
    private String notHighEnough;

    // Incorrect Block
    @ConfigValue(name = "incorrect-block", path = "feature")
    private String incorrectBlock;

    // Give Trench
    @ConfigValue(name = "give-trench", path = "feature")
    private String giveTrench;
    public String giveTrench() {
        return this.giveTrench;
    }

    // Gave Trench
    @ConfigValue(name = "given-trench", path = "feature")
    private String givenTrench;
    public String givenTrench() {
        return this.givenTrench;
    }

    // Invalid Type
    @ConfigValue(name = "invalid-type", path = "feature")
    private String invalidType;
    public String getInvalidType() {
        return this.invalidType;
    }

    // Invalid Args
    @ConfigValue(name = "invalid-args", path = "feature")
    private String invalidArgs;
    public String getInvalidArgs() {
        return this.invalidArgs;
    }

    // No Permission
    @ConfigValue(name = "no-permission", path = "feature")
    private String noPerm;
    public String getNoPerm() {
        return this.noPerm;
    }

    // Unbreakable
    @ConfigValue(name = "unbreakable", path = "feature")
    private List<String> unbreakable;
    public List<String> getUnbreakable() {
        return unbreakable;
    }

    // Invalid Player
    @ConfigValue(name = "offline", path = "feature")
    private String invalidPlayer;
    public String getInvalidPlayer() {return this.invalidPlayer; }


    public ToolsFeature(Plugin plugin, String name, String description, String basePermDesc, String adminPermDesc) {
        super(plugin, name, description, basePermDesc, adminPermDesc);
        setDataFolder();
    }

    public void onDisable() {
    }

    public void onEnable() {
    }

    // Listeners

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        ItemStack itemInHand = p.getInventory().getItemInMainHand();
        Block block = e.getBlock();

        if (itemInHand.getType() == Material.AIR) {
            return;
        }

        if (!itemInHand.hasItemMeta()) {
            return;
        }

        if (!hasNBTTag(itemInHand, "koretools")) {
            return;
        }

        if (!FactionsUtil.canBuild(p, block.getLocation())) {
            return;
        }

        if (!WorldGuardUtil.canBreak(p, block.getLocation())) {
            return;
        }

        if (getStringItemTag(itemInHand, "type").equals("trench")) {
            destroyTrenchBlocks(block.getLocation(), getIntItemTag(itemInHand, "radius"), p);
        }

        if (getStringItemTag(itemInHand, "type").equals("tray")) {

            if (!(block.getType() == Material.NETHERRACK)) {
                p.sendMessage(ChatUtil.color(ChatUtil.color(incorrectBlock)));
                e.setCancelled(true);
                return;
            }

            if (block.getLocation().getBlockY() < 200) {
                p.sendMessage(ChatUtil.color(ChatUtil.color(notHighEnough)));
                e.setCancelled(true);
                return;
            }

            destroyTrayBlocks(e.getBlock().getLocation(), getIntItemTag(itemInHand, "radius"), p);
        }
    }


    // Methods

    public boolean hasNBTTag(ItemStack item, String key) {
        if (item == null || key == null) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return false;
        }
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        if (dataContainer.has(getKey(key), PersistentDataType.STRING)) {
            return true;
        }
        if (dataContainer.has(getKey(key), PersistentDataType.INTEGER)) {
            return true;
        }
        if (dataContainer.has(getKey(key), PersistentDataType.BYTE)) {
            return true;
        }
        return false;
    }

    private NamespacedKey getKey(String key) {
        return new NamespacedKey(this.getPlugin(), key);
    }

    // The following methods are unchanged from the previous implementation

    private NamespacedKey getKey(JavaPlugin plugin, String key) {
        return new NamespacedKey(plugin, key);
    }

    public void setCustomNBTStringTag(ItemStack item, String key, String value) {
        if (item == null || key == null) {
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(getKey(key), PersistentDataType.STRING, value);
        item.setItemMeta(itemMeta);
    }

    public void setCustomNBTIntTag(ItemStack item, String key, int value) {
        if (item == null || key == null) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(getKey(key), PersistentDataType.INTEGER, value);
        item.setItemMeta(itemMeta);
    }

    public void setCustomNBTBooleanTag(ItemStack item, String key, boolean value) {
        if (item == null || key == null) {
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(getKey(key), PersistentDataType.BYTE, (byte) (value ? 1 : 0));
        item.setItemMeta(itemMeta);
    }

    public String getStringItemTag(ItemStack item, String key) {
        if (item == null || key == null) {
            return null;
        }

        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(getKey(key), PersistentDataType.STRING);
    }

    public boolean getBooleanItemTag(ItemStack item, String key) {
        if (item == null || key == null) {
            return false;
        }

        ItemMeta itemMeta = item.getItemMeta();
        Byte value = itemMeta.getPersistentDataContainer().get(getKey(key), PersistentDataType.BYTE);

        // Convert the stored byte back to a boolean (0 for false, 1 for true)
        return value != null && value == 1;
    }

    public int getIntItemTag(ItemStack item, String key) {
        if (item == null || key == null) {
            return 0;
        }

        ItemMeta itemMeta = item.getItemMeta();
        Integer value = itemMeta.getPersistentDataContainer().get(getKey(key), PersistentDataType.INTEGER);
        return value != null ? value : 0;
    }

    public ItemStack buildTrenchTool(String name) {
        String path = "feature.tools." + name;
        ItemStack tool = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta toolMeta = tool.getItemMeta();
        toolMeta.setDisplayName(ChatUtil.color(getConfig().getString(path + ".name")));
        List<String> lore = new LinkedList<>();
        int radius = getConfig().getInt(path + ".radius");
        for (String s: getConfig().getStringList("feature.tools." + name + ".lore")) {
            lore.add(ChatUtil.color(s).replace("%radius%", radius + "x" + radius));
        }
        toolMeta.setLore(lore);
        toolMeta.addEnchant(Enchantment.DIG_SPEED, 100, true);
        toolMeta.addEnchant(Enchantment.DURABILITY, 100, true);
        toolMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        toolMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        toolMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        toolMeta.setUnbreakable(true);
        tool.setItemMeta(toolMeta);

        setCustomNBTStringTag(tool, "type", getConfig().getString(path + ".type"));
        setCustomNBTBooleanTag(tool, "koretools", true);
        setCustomNBTIntTag(tool, "radius", getConfig().getInt(path + ".radius"));

        return tool;
    }

    private boolean isUnbreakable(Material blockType) {
        // Retrieve the list of unbreakable blocks from the configuration
        List<String> unbreakableBlocks = getUnbreakable();

        // Check if the block type is present in the list
        return unbreakableBlocks.contains(blockType.toString());
    }

    public void destroyTrenchBlocks(Location center, int radius, Player p) {
        radius = radius/2;

        World world = center.getWorld();
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();

        int minX = centerX - radius;
        int minY = centerY - radius;
        int minZ = centerZ - radius;
        int maxX = centerX + radius;
        int maxY = centerY + radius;
        int maxZ = centerZ + radius;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location blockLocation = new Location(world, x, y, z);
                    Material blockType = blockLocation.getBlock().getType();

                    if (!FactionsUtil.canBuild(p, blockLocation)) {
                        continue;
                    }

                    if (!WorldGuardUtil.canBreak(p, blockLocation)) {
                        continue;
                    }

                    if (blockType == Material.AIR) {
                        continue;
                    }

                    if (isUnbreakable(blockType)) {
                        continue;
                    }

                    blockLocation.getWorld().setBlockData(blockLocation, Material.AIR.createBlockData());
                }
            }
        }
    }

    public void destroyTrayBlocks(Location center, int radius, Player p) {
        radius = radius-2;
        World world = center.getWorld();
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();
        int minX = centerX - radius;
        int minZ = centerZ - radius;
        int maxX = centerX + radius;
        int maxZ = centerZ + radius;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                Location blockLocation = new Location(world, x, centerY, z);

                if (!FactionsUtil.canBuild(p, blockLocation)) {
                    continue;
                }

                if (!WorldGuardUtil.canBreak(p, blockLocation)) {
                    continue;
                }

                if (blockLocation.getBlock().getType() == Material.NETHERRACK) {
                    blockLocation.getWorld().setBlockData(blockLocation, Material.AIR.createBlockData());
                }
            }
        }
    }
}
