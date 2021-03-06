package org.sandboxpowered.sandbox.api.block;

import org.sandboxpowered.sandbox.api.fluid.BaseFluid;

public class FluidBlock extends BaseBlock {
    private BaseFluid fluid;

    public FluidBlock(Settings settings, BaseFluid fluid) {
        super(settings);
        this.fluid = fluid;
    }

    public BaseFluid getFluid() {
        return fluid;
    }
}