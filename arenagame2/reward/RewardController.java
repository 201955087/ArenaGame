package me.air_bottle.arenagame2.reward;

import me.air_bottle.arenagame2.reward.items.SpecialItems;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Objects;

public class RewardController implements RewardBuilder {
    private final SpecialItems specialItems;

    public RewardController(SpecialItems specialItems) {
        this.specialItems = specialItems;
    }

    @Override
    public void defaultItems(Player player) {
        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
    }

    @Override
    public void startingItems(Player player) {
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD, 1));
    }

    @Override
    public void upgradeArmor(Player player) {
        boolean maxUpgraded = false;
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggins = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        if(helmet == null && chestplate == null && leggins == null && boots == null) {
            player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
            player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
            player.sendMessage("철 세트로 업그레이드");
        } else if (Objects.requireNonNull(helmet).getType() == Material.IRON_HELMET
                && Objects.requireNonNull(chestplate).getType() == Material.IRON_CHESTPLATE
                && Objects.requireNonNull(leggins).getType() == Material.IRON_LEGGINGS
                && Objects.requireNonNull(boots).getType() == Material.IRON_BOOTS) {
            player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            player.sendMessage("다이아 세트로 업그레이드");
        } else if(Objects.requireNonNull(helmet).getType() == Material.DIAMOND_HELMET
                && Objects.requireNonNull(chestplate).getType() == Material.DIAMOND_CHESTPLATE
                && Objects.requireNonNull(leggins).getType() == Material.DIAMOND_LEGGINGS
                && Objects.requireNonNull(boots).getType() == Material.DIAMOND_BOOTS) {
            player.getInventory().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
            player.getInventory().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
            player.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
            player.sendMessage("네더 세트로 업그레이드");
        } else if(Objects.requireNonNull(helmet).getType() == Material.NETHERITE_HELMET
                && Objects.requireNonNull(chestplate).getType() == Material.NETHERITE_CHESTPLATE
                && Objects.requireNonNull(leggins).getType() == Material.NETHERITE_LEGGINGS
                && Objects.requireNonNull(boots).getType() == Material.NETHERITE_BOOTS) {
            player.sendMessage("장비 최대 업글, 특수 아이템 지급");
            player.getInventory().addItem(specialItems.armorSpecial());
            maxUpgraded = true;
        }
        if (maxUpgraded) {
            player.sendMessage("아머 최대 업그레이드립니다");
        }
    }
    @Override
    public void upgradeWeapon(Player player) {
        boolean maxUpgraded = false;
        boolean maxWeapon = false;
        ItemStack[] inventory = player.getInventory().getContents();

        for (int i = 0; i < inventory.length; i++) {
            ItemStack item = inventory[i];
            if (item != null) {
                if (item.getType() == Material.IRON_SWORD) {
                    inventory[i] = new ItemStack(Material.DIAMOND_SWORD);
                    player.sendMessage("다이아몬드 검으로 업그레이드했습니다.");
                    player.getInventory().setContents(inventory);
                    break;
                } else if (item.getType() == Material.DIAMOND_SWORD) {
                    inventory[i] = new ItemStack(Material.NETHERITE_SWORD);
                    player.sendMessage("네더라이트 검으로 업그레이드했습니다.");
                    player.getInventory().setContents(inventory);
                    break;
                } else if (item.getType() == Material.NETHERITE_SWORD) {
                    item.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                    player.sendMessage("네더라이트 검에 날카로움 5 인챈트를 추가했습니다.");
                    player.getInventory().setContents(inventory);
                    maxWeapon = true;
                    break;
                }
            }
        }
        if(maxWeapon) {
            player.sendMessage("무기 최대 업글, 특수 아이템 지급");
            player.getInventory().addItem(specialItems.weaponSpecial());
            maxUpgraded = true;
        }
        if(maxUpgraded) {
            player.sendMessage("최대 업그레이드입니다");
        }
    }
    @Override
    public void buffUpgrade(Player player) {
        PotionEffectType[] effects = {PotionEffectType.SPEED, PotionEffectType.REGENERATION};
        boolean maxUpgraded = false;

        for(PotionEffectType effectType : effects) {
            PotionEffect currentEffect = player.getPotionEffect(effectType);
            int currentLevel = currentEffect == null ? 0 : currentEffect.getAmplifier();

            if(currentLevel < 3) {
                player.addPotionEffect(new PotionEffect(effectType, Integer.MAX_VALUE, currentLevel + 1));
            } else if(currentLevel == 3 && !maxUpgraded) {
                player.getInventory().addItem(specialItems.buffSpecial());
                player.sendMessage("버프 최대 업글, 특수 아이템 지급");
                maxUpgraded = true;
            }
        }
        if(maxUpgraded) {
            player.sendMessage("버프 최대 업그레이드입니다");
        } else {
            player.sendMessage("버프 업그레이드");
        }
    }
    @Override
    public void resetReward(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

        ItemStack[] inventory = player.getInventory().getContents();
        Arrays.fill(inventory, null);
        player.getInventory().setContents(inventory);

        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        player.sendMessage("보상들 모두 초기화");
    }
}
