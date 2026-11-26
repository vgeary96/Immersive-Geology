package com.igteam.immersivegeology.core.material.data.stone.compat.adastra;

import com.igteam.immersivegeology.core.material.helper.flags.BlockCategoryFlags;
import com.igteam.immersivegeology.core.material.helper.flags.IFlagType;

import java.util.Set;
import java.util.function.BiFunction;

public class MaterialMoonStone extends MaterialAdAstraStone
{

    public MaterialMoonStone() {
        super();
        this.name = "moon_stone"; // Special Case as we need to override the deafult name assignment method
        this.DIMENSIONS = Set.of(MOON);
        this.additionalOres = Set.of("pyrite");
    }

    @Override
    protected BiFunction<IFlagType<?>, Integer, Integer> materialColorFunction() {
        return ((p, i) -> (p == BlockCategoryFlags.ORE_BLOCK ? 0xffffff : 0x888c8d));
    }
}
