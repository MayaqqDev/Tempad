package earth.terrarium.tempad.common.compat.botarium;

import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.tempad.common.Tempad;

public class TempadFluidContainer extends SimpleFluidContainer {
    public TempadFluidContainer(long maxAmount) {
        super(integer -> maxAmount, 1, (integer, fluidHolder) -> fluidHolder.is(Tempad.TEMPAD_LIQUID_FUEL_TAG));
    }
}
