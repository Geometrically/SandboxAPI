package org.sandboxpowered.api;

import org.sandboxpowered.api.addon.AddonSpec;
import org.sandboxpowered.api.util.Log;
import org.sandboxpowered.api.util.Side;

public interface SandboxAPI {
    AddonSpec getSourceAddon();

    Side getSide();

    default void execute(Side side, Runnable runnable) {
        if (getSide() == side) {
            runnable.run();
        }
    }

    Log getLog();
}