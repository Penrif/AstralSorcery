/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2020
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.loot.global;

import com.google.gson.JsonObject;
import hellfirepvp.astralsorcery.common.data.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.data.research.ResearchHelper;
import hellfirepvp.astralsorcery.common.perk.node.key.KeyMagnetDrops;
import hellfirepvp.astralsorcery.common.util.item.ItemUtils;
import hellfirepvp.astralsorcery.common.util.loot.LootUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.fml.LogicalSide;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: LootModifierMagnetDrops
 * Created by HellFirePvP
 * Date: 18.07.2020 / 10:19
 */
public class LootModifierMagnetDrops extends LootModifier {

    private LootModifierMagnetDrops(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (!LootUtil.doesContextFulfillSet(context, LootParameterSets.BLOCK)) {
            return generatedLoot;
        }
        Entity e = context.get(LootParameters.THIS_ENTITY);
        if (!(e instanceof PlayerEntity)) {
            return generatedLoot;
        }
        PlayerEntity player = (PlayerEntity) e;
        PlayerProgress prog = ResearchHelper.getProgress(player, LogicalSide.SERVER);
        if (!prog.isValid() || !prog.getPerkData().hasPerkEffect(perk -> perk instanceof KeyMagnetDrops)) {
            return generatedLoot;
        }

        return generatedLoot.stream()
                .filter(stack -> !stack.isEmpty())
                .map(result -> ItemUtils.dropItemToPlayer(player, result))
                .filter(stack -> !stack.isEmpty())
                .collect(Collectors.toList());
    }

    public static class Serializer extends GlobalLootModifierSerializer<LootModifierMagnetDrops> {

        @Override
        public LootModifierMagnetDrops read(ResourceLocation location, JsonObject object, ILootCondition[] lootConditions) {
            return new LootModifierMagnetDrops(lootConditions);
        }

        @Override
        public JsonObject write(LootModifierMagnetDrops instance) {
            return this.makeConditions(instance.conditions);
        }
    }
}
