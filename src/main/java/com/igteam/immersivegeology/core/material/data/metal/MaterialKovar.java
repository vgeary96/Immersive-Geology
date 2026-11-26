package com.igteam.immersivegeology.core.material.data.metal;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.igteam.immersivegeology.core.material.data.enums.MetalEnum;
import com.igteam.immersivegeology.core.material.data.types.MaterialMetalAlloy;
import com.igteam.immersivegeology.core.material.helper.flags.IFlagType;
import com.igteam.immersivegeology.core.material.helper.flags.ItemCategoryFlags;
import com.igteam.immersivegeology.core.material.helper.material.recipe.IGStageDesignation;
import com.igteam.immersivegeology.core.material.helper.material.recipe.helper.IGMethodBuilder;

import java.util.function.BiFunction;

public class MaterialKovar extends MaterialMetalAlloy
{
    @Override
    protected BiFunction<IFlagType<?>, Integer, Integer> materialColorFunction()
    {
        return (flag, index) -> 0xff98afc7;
    }

    @Override
    public void setupRecipeStages()
    {
        super.setupRecipeStages();
        IGMethodBuilder.arcSmelting(this, IGStageDesignation.SYNTHESIS).create(
                MetalEnum.Iron.instance(), ItemCategoryFlags.INGOT, 4, ItemCategoryFlags.INGOT, 7, 1,
                IngredientWithSize.of(MetalEnum.Nickel.getStack(ItemCategoryFlags.INGOT, 2)),
                IngredientWithSize.of(MetalEnum.Cobalt.getStack(ItemCategoryFlags.INGOT, 1)))
                .setTimeAndEnergy(800, 409600);
    }
}
