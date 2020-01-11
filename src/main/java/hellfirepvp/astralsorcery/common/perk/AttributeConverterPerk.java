/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2020
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.perk;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;

import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: AttributeConverterPerk
 * Created by HellFirePvP
 * Date: 08.08.2019 / 18:09
 */
public class AttributeConverterPerk extends ProgressGatedPerk implements ConverterPerk {

    private List<PerkConverter> converters = Lists.newArrayList();

    public AttributeConverterPerk(ResourceLocation name, int x, int y) {
        super(name, x, y);
    }

    public <T> T addConverter(PerkConverter converter) {
        this.converters.add(converter);
        return (T) this;
    }

    public <T> T addRangedConverter(double radius, PerkConverter converter) {
        this.converters.add(converter.asRangedConverter(new Point.Double(this.getOffset().getX(), this.getOffset().getY()), radius));
        return (T) this;
    }

    @Override
    public List<PerkConverter> provideConverters(PlayerEntity player, LogicalSide side) {
        if (modifiersDisabled(player, side)) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(converters);
    }

    @Override
    public void applyPerkLogic(PlayerEntity player, LogicalSide side) {}

    @Override
    public void removePerkLogic(PlayerEntity player, LogicalSide side) {}
}
