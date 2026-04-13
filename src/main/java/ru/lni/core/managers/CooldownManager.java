package ru.lni.core.managers;

import java.util.*;

public class CooldownManager {
    private final Map<UUID, List<Long>> headUsage = new HashMap<>();
    private final long WINDOW_MS = 30 * 60 * 1000;
    private final int MAX_USES = 3;

    public boolean canUse(UUID uuid) {
        long now = System.currentTimeMillis();
        List<Long> times = headUsage.computeIfAbsent(uuid, k -> new ArrayList<>());
        times.removeIf(time -> (now - time) > WINDOW_MS);
        if (times.size() < MAX_USES) {
            times.add(now);
            return true;
        }
        return false;
    }

    public void reset(UUID uuid) {
        headUsage.remove(uuid);
    }

    public long getTimeToReset(UUID uuid) {
        List<Long> times = headUsage.get(uuid);
        if (times == null || times.isEmpty()) return 0;
        return (WINDOW_MS - (System.currentTimeMillis() - times.get(0))) / 1000 / 60;
    }
}
