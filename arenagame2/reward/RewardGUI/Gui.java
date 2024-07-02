package me.air_bottle.arenagame2.reward.RewardGUI;

import me.air_bottle.arenagame2.reward.RewardController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Gui implements GuiBuilder, Listener {
    private final RewardController rewardcontroller;
    public Gui(RewardController rewardcontroller) {
        this.rewardcontroller = rewardcontroller;
    }
    @Override
    public void openRewardGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, "라운드 클리어 보상");

        ItemStack armorUpgrade = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta armorMeta = armorUpgrade.getItemMeta();
        armorMeta.setDisplayName("갑옷 업그레이드");
        armorMeta.setLore(null);
        armorUpgrade.setItemMeta(armorMeta);
        gui.setItem(1, armorUpgrade);

        ItemStack weaponUpgrade = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta weaponMeta = weaponUpgrade.getItemMeta();
        weaponMeta.setDisplayName("무기 업그레이드");
        weaponMeta.setLore(null);
        weaponUpgrade.setItemMeta(weaponMeta);
        gui.setItem(4, weaponUpgrade);

        ItemStack buffUpgrade = new ItemStack(Material.POTION);
        ItemMeta buffMeta = buffUpgrade.getItemMeta();
        buffMeta.setDisplayName("버프 업그레이드");
        buffMeta.setLore(null);
        buffUpgrade.setItemMeta(buffMeta);
        gui.setItem(7, buffUpgrade);

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getView().getTitle().equals("라운드 클리어 보상")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if(clickedItem == null || !clickedItem.hasItemMeta() ||
                    clickedItem.getItemMeta().getDisplayName() == null) {
                Bukkit.getLogger().warning("잘못된 요청");
                return;
            }
            Player player = (Player) event.getWhoClicked();
            String displayName = clickedItem.getItemMeta().getDisplayName();
            switch (displayName) {
                case "갑옷 업그레이드":
                    rewardcontroller.upgradeArmor(player);
                    break;
                case "무기 업그레이드":
                    rewardcontroller.upgradeWeapon(player);
                    break;
                case "버프 업그레이드":
                    rewardcontroller.buffUpgrade(player);
                    break;
            }
            player.closeInventory();
        }
    }
}
