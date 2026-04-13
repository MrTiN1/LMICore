package ru.lni.core.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import ru.lni.core.managers.CooldownManager;
import java.util.List;

public class HeadCommand implements CommandExecutor {
    private final CooldownManager cm;
    private final MiniMessage mm = MiniMessage.miniMessage();

    public HeadCommand(CooldownManager cm) { this.cm = cm; }

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (a.length == 2 && a[0].equalsIgnoreCase("reset") && s.hasPermission("lni.admin")) {
            Player target = Bukkit.getPlayer(a[1]);
            if (target != null) {
                cm.reset(target.getUniqueId());
                s.sendMessage("§a[LNI] Кулдаун сброшен для " + target.getName());
                return true;
            }
        }
        
        if (!(s instanceof Player p)) return true;
        
        if (!p.hasPermission("lni.head")) {
            p.sendMessage(mm.deserialize("<red>Команда доступна только для <yellow>LNI</yellow>!"));
            return true;
        }
        
        if (!cm.canUse(p.getUniqueId())) {
            p.sendMessage(mm.deserialize("<red>Лимит: 3 раза в 30 мин. Ждите: <yellow>" + cm.getTimeToReset(p.getUniqueId()) + "м."));
            return true;
        }
        
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta m = (SkullMeta) head.getItemMeta();
        m.setOwningPlayer(p);
        m.displayName(mm.deserialize("<yellow>Игрока " + p.getName() + "</yellow>"));
        m.lore(List.of(mm.deserialize("<gray>Выдано по подписке <gold>LNI</gold>")));
        head.setItemMeta(m);
        p.getInventory().addItem(head);
        p.sendMessage(mm.deserialize("<green>Вы получили свою голову!"));
        return true;
    }
}
