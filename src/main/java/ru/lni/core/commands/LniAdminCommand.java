package ru.lni.core.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import java.time.Duration;

public class LniAdminCommand implements CommandExecutor {
    private final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (!s.hasPermission("lni.admin")) return true;
        if (a.length == 0) return false;
        
        Player t = Bukkit.getPlayer(a[0]);
        if (t == null) {
            s.sendMessage("§cИгрок не найден.");
            return true;
        }
        
        var lp = LuckPermsProvider.get();
        lp.getUserManager().modifyUser(t.getUniqueId(), user -> {
            user.data().add(PermissionNode.builder("prefix.100.<yellow>『LNI』 </yellow>").expiry(Duration.ofDays(30)).build());
            user.data().add(PermissionNode.builder("lni.head").expiry(Duration.ofDays(30)).build());
        });
        
        t.sendMessage(mm.deserialize("<newline><yellow>Вам выдан статус <bold>LNI</bold> на 30 дней!<newline>"));
        s.sendMessage("§a[LNI] Статус успешно выдан игроку " + t.getName());
        return true;
    }
}
