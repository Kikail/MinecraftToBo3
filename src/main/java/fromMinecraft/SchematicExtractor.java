
package fromMinecraft;

import net.querz.nbt.io.NBTDeserializer;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.*;
import toRadiant.Map;
import toRadiant.ToRadiantPrefab;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class SchematicExtractor {

    public static String Extract(String path, Map map, File fileIDs) {
        try {
            parseLitematicaFile(new File(path), map,fileIDs);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void parseLitematicaFile(File file, Map map, File fileIDs) throws IOException {
        NBTDeserializer deserializer = new NBTDeserializer(false);
        NamedTag namedTag = deserializer.fromStream(new GZIPInputStream(new FileInputStream(file)));
        CompoundTag root = (CompoundTag) namedTag.getTag();

        // Get real dimensions from Metadata
        CompoundTag metadata = root.getCompoundTag("Metadata");
        CompoundTag enclosingSize = metadata.getCompoundTag("EnclosingSize");
        int sizeX = enclosingSize.getInt("x");
        int sizeY = enclosingSize.getInt("y");
        int sizeZ = enclosingSize.getInt("z");

        System.out.printf("Actual schematic size: %dx%dx%d%n", sizeX, sizeY, sizeZ);

        CompoundTag regions = root.getCompoundTag("Regions");
        for (String regionName : regions.keySet()) {
            CompoundTag region = regions.getCompoundTag(regionName);

            // Use position from region but our corrected size
            int posX = region.getInt("PositionX");
            int posY = region.getInt("PositionY");
            int posZ = region.getInt("PositionZ");

            ListTag<CompoundTag> palette = region.getListTag("BlockStatePalette").asCompoundTagList();
            long[] blockStates = region.getLongArray("BlockStates");

            int bitsPerBlock = Math.max(4, 32 - Integer.numberOfLeadingZeros(palette.size() - 1));
            int blocksPerLong = 64 / bitsPerBlock;
            int mask = (1 << bitsPerBlock) - 1;

            System.out.printf("Processing %,d blocks with %,d palette entries%n",
                    sizeX * sizeY * sizeZ, palette.size());

            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {
                    for (int x = 0; x < sizeX; x++) {
                        int index = y * sizeZ * sizeX + z * sizeX + x;
                        int longIndex = index / blocksPerLong;
                        int offset = (index % blocksPerLong) * bitsPerBlock;

                        if (longIndex < blockStates.length) {
                            long value = (blockStates[longIndex] >>> offset) & mask;
                            if (value < palette.size()) {
                                CompoundTag blockState = palette.get((int) value);
                                String blockName = blockState.getString("Name");
                                if (!blockName.equals("minecraft:air")) { // Skip air blocks
                                    String blockStateStr = "";
                                    if (blockState.containsKey("Properties")) {
                                        CompoundTag properties = blockState.getCompoundTag("Properties");
                                        blockStateStr = propertiesToString(properties);
                                    }
                                    String worldPos = String.format("(%d,%d,%d)", posX + x, posY + y, posZ + z);
                                    ToRadiantPrefab.readBlockData(worldPos,blockName+blockStateStr,map,fileIDs);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private static String propertiesToString(CompoundTag properties) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;

        for (String key : properties.keySet()) {
            if (!first) {
                sb.append(",");
            }
            sb.append(key).append("=").append(properties.getString(key));
            first = false;
        }

        sb.append("]");
        return sb.toString();
    }
}