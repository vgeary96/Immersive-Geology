package com.igteam.immersivegeology.core.material.data.stone.compat.adastra;

import com.igteam.immersivegeology.core.material.data.types.MaterialStone;
import com.igteam.immersivegeology.core.material.helper.flags.BlockCategoryFlags;
import com.igteam.immersivegeology.core.material.helper.flags.IFlagType;
import com.igteam.immersivegeology.core.material.helper.flags.MaterialFlags;
import com.igteam.immersivegeology.core.material.helper.flags.ModFlags;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;
import java.util.Set;

public class MaterialAdAstraStone extends MaterialStone {

    public static final ResourceLocation MOON = new ResourceLocation("ad_astra", "moon");
    public static final ResourceLocation MARS = new ResourceLocation("ad_astra", "mars");

    protected Set<String> additionalOres = Set.of();

    public MaterialAdAstraStone() {
        super();
        addFlags(MaterialFlags.EXISTING_IMPLEMENTATION);
        addFlags(ModFlags.AD_ASTRA);
    }

    @Override
    protected BiFunction<IFlagType<?>, Integer, Integer> materialColorFunction()
    {
        return ((p, i) -> (p == BlockCategoryFlags.ORE_BLOCK ? 0xffffff : 0x888c8d));
    }

    @Override
    public ResourceLocation getTextureLocation(IFlagType<?> flag)
    {
        return new ResourceLocation("ad_astra", "block/"+getName());
    }

    public ResourceLocation getHostBlockId()
    {
        return new ResourceLocation("ad_astra", getName());
    }

    public boolean acceptsAdditionalOre(String ore)
    {
        return additionalOres.contains(ore);
    }
}
