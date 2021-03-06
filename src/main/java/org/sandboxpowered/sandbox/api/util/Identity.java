package org.sandboxpowered.sandbox.api.util;

public interface Identity {

    static Identity of(String namespace, String path) {
        return Functions.getInstance().createIdentityFromString(namespace, path);
    }

    static Identity of(String identity) {
        return Functions.getInstance().createIdentityFromString(identity);
    }

    String getNamespace();

    String getPath();
}