package dev.mrsterner.nyctoplus.client.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.util.SpriteIdentifier;

public class NPSpriteIdentifierRegistry {
    public static final NPSpriteIdentifierRegistry INSTANCE = new NPSpriteIdentifierRegistry();
    private final List<SpriteIdentifier> identifiers;

    private NPSpriteIdentifierRegistry() {
        identifiers = new ArrayList<>();
    }

    public void addIdentifier(SpriteIdentifier sprite) {
        this.identifiers.add(sprite);
    }

    public Collection<SpriteIdentifier> getIdentifiers() {
        return Collections.unmodifiableList(identifiers);
    }
}