/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2020
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.block;

import hellfirepvp.astralsorcery.common.util.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: BlockPredicates
 * Created by HellFirePvP
 * Date: 29.11.2019 / 21:09
 */
public class BlockPredicates {

    public static BlockPredicate isInTag(Tag<Block> blockTag) {
        return (world, pos, state) -> state.isIn(blockTag);
    }

    public static BlockPredicate isBlock(Block... blocks) {
        Set<Block> applicable = new HashSet<>(Arrays.asList(blocks));
        return (world, pos, state) -> applicable.contains(state.getBlock());
    }

    public static BlockPredicate isState(BlockState... states) {
        Set<BlockState> applicable = new HashSet<>(Arrays.asList(states));
        return (world, pos, state) -> applicable.contains(state);
    }

    public static BlockPredicate doesTileExist(TileEntity tile, boolean loadTileWorldAndChunk) {
        DimensionType dimType = tile.getWorld().getDimension().getType();
        Class<? extends TileEntity> tileClazz = tile.getClass();
        MinecraftServer srv = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);

        return (world, pos, state) -> {
            if (loadTileWorldAndChunk || srv.forgeGetWorldMap().containsKey(dimType)) {
                World foundWorld = srv.getWorld(dimType);
                if (!loadTileWorldAndChunk && !foundWorld.getChunkProvider().isChunkLoaded(new ChunkPos(pos))) {
                    return true;
                }
                return MiscUtils.getTileAt(foundWorld, pos, tileClazz, true) != null;
            }
            return true;
        };
    }
}