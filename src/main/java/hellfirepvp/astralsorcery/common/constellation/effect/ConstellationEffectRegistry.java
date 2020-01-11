/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2020
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.constellation.effect;

import hellfirepvp.astralsorcery.common.constellation.IWeakConstellation;
import hellfirepvp.astralsorcery.common.constellation.effect.aoe.*;
import hellfirepvp.astralsorcery.common.data.config.ServerConfig;
import hellfirepvp.astralsorcery.common.lib.RegistriesAS;
import hellfirepvp.astralsorcery.common.util.block.ILocatable;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ConstellationEffectRegistry
 * Created by HellFirePvP
 * Date: 11.06.2019 / 19:32
 */
public class ConstellationEffectRegistry {

    public static final String LUCERNA_SKIP_ENTITY = "skip.spawn.deny";

    public static void addConfigEntries(ServerConfig config) {
        config.addConfigEntry(CEffectAevitas.CONFIG);
        config.addConfigEntry(CEffectArmara.CONFIG);
        config.addConfigEntry(CEffectBootes.CONFIG);
        config.addConfigEntry(CEffectDiscidia.CONFIG);
        config.addConfigEntry(CEffectEvorsio.CONFIG);
        config.addConfigEntry(CEffectFornax.CONFIG);
        config.addConfigEntry(CEffectVicio.CONFIG);
    }

    @Nullable
    public static ConstellationEffect createInstance(ILocatable origin, IWeakConstellation constellation) {
        ConstellationEffectProvider effect = RegistriesAS.REGISTRY_CONSTELLATION_EFFECT.getValue(constellation.getRegistryName());
        if (effect != null) {
            return effect.createEffect(origin);
        }
        return null;
    }
}
