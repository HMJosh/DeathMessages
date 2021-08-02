package net.joshb.deathmessages.listener;

import net.joshb.deathmessages.api.EntityManager;
import net.joshb.deathmessages.api.PlayerManager;
import net.joshb.deathmessages.config.EntityDeathMessages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

public class EntityDamageByBlock implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDeath(EntityDamageByBlockEvent e) {
        if (e.getEntity() instanceof Player && Bukkit.getOnlinePlayers().contains(e.getEntity())) {
            Player p = (Player) e.getEntity();
            PlayerManager pm = PlayerManager.getPlayer(p);
            pm.setLastDamageCause(e.getCause());
        } else {
            for (String listened : EntityDeathMessages.getInstance().getConfig().getConfigurationSection("Entities")
                    .getKeys(false)) {
                if(listened.contains(e.getEntity().getType().getEntityClass().getSimpleName().toLowerCase())){
                    EntityManager em;
                    if(EntityManager.getEntity(e.getEntity().getUniqueId()) == null){
                        em = new EntityManager(e.getEntity(), e.getEntity().getUniqueId());
                    } else {
                        em = EntityManager.getEntity(e.getEntity().getUniqueId());
                    }
                    em.setLastDamageCause(e.getCause());
                }
            }
        }
    }

}
