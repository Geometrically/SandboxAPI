package org.sandboxpowered.api.util;

import org.sandboxpowered.api.block.Block;
import org.sandboxpowered.api.block.Material;
import org.sandboxpowered.api.block.entity.BlockEntity;
import org.sandboxpowered.api.client.Client;
import org.sandboxpowered.api.component.Component;
import org.sandboxpowered.api.content.Content;
import org.sandboxpowered.api.entity.Entity;
import org.sandboxpowered.api.fluid.Fluid;
import org.sandboxpowered.api.fluid.FluidStack;
import org.sandboxpowered.api.item.Item;
import org.sandboxpowered.api.item.ItemStack;
import org.sandboxpowered.api.registry.Registry;
import org.sandboxpowered.api.server.Server;
import org.sandboxpowered.api.state.Property;
import org.sandboxpowered.api.util.annotation.Internal;
import org.sandboxpowered.api.util.math.Position;
import org.sandboxpowered.api.util.nbt.CompoundTag;
import org.sandboxpowered.api.util.nbt.ReadableCompoundTag;
import org.sandboxpowered.api.util.text.Text;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

@Internal
public interface Functions {

    @Nonnull
    static Functions getInstance() {
        throw new RuntimeException("No functions defined, this is a bug.");
    }

    Identity createIdentityFromString(String identity);

    Identity createIdentityFromString(String name, String path);

    Text createLiteralText(String text);

    Text createTranslatedText(String translation);

    Material getMaterial(String material);

    <T extends BlockEntity> BlockEntity.Type<T> blockEntityTypeFunction(Supplier<T> supplier, Block[] blocks);

    ItemStack createItemStack(Item item, int amount);

    ItemStack createItemStackFromTag(ReadableCompoundTag tag);

    <T extends Content> Registry<T> registryFunction(Class<T> c);

    CompoundTag createCompoundTag();

    Property getProperty(String property);

    Server serverInstance();

    Position createPosition(int x, int y, int z);

    Position.Mutable createMutablePosition(int x, int y, int z);

    <T> Component<T> componentFunction(Class<T> c);

    Entity.Type entityTypeEntityFunction(Entity e);

    FluidStack fluidStackFunction(Fluid fluid, int amount);

    FluidStack fluidStackFromTagFunction(ReadableCompoundTag tag);

    Client clientInstance();

    Identity.Variant createVariantIdentity(Identity identity, String variant);
}