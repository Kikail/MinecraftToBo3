package toRadiant;

import java.util.*;
import java.util.Map;

public class MergedBlock {
    Position min;
    Position max;
    String texture;

    int id;

    public MergedBlock(Position min, Position max, String texture, int id) {
        this.min = min;
        this.max = max;
        this.texture = texture;
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("// brush " + id + "\n");
        sb.append("{\n");

        String formattedId = String.format("%8s", id).replace(' ', '0');
        sb.append(" guid \"{").append(formattedId).append("-0101-0101-0101-010101010101}\"\n");

        int minX = min.x * 40 - 20;
        int minY = min.y * 40 - 20;
        int minZ = min.z * 40 - 20;

        int maxX = max.x * 40 + 20;
        int maxY = max.y * 40 + 20;
        int maxZ = max.z * 40 + 20;

        // Bottom face (z = minZ)
        sb.append(String.format(" ( %d %d %d ) ( %d %d %d ) ( %d %d %d ) %s -40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n",
                maxX, maxY, minZ,
                minX, maxY, minZ,
                minX, minY, minZ,
                texture));

        // Top face (z = maxZ)
        sb.append(String.format(" ( %d %d %d ) ( %d %d %d ) ( %d %d %d ) %s -40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n",
                minX, minY, maxZ,
                minX, maxY, maxZ,
                maxX, maxY, maxZ,
                texture));

        // Front face (y = maxY)
        sb.append(String.format(" ( %d %d %d ) ( %d %d %d ) ( %d %d %d ) %s -40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n",
                minX, maxY, maxZ,
                minX, maxY, minZ,
                maxX, maxY, minZ,
                texture));

        // Back face (y = minY)
        sb.append(String.format(" ( %d %d %d ) ( %d %d %d ) ( %d %d %d ) %s -40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n",
                maxX, minY, minZ,
                minX, minY, minZ,
                minX, minY, maxZ,
                texture));

        // Left face (x = minX)
        sb.append(String.format(" ( %d %d %d ) ( %d %d %d ) ( %d %d %d ) %s -40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n",
                minX, maxY, minZ,
                minX, maxY, maxZ,
                minX, minY, maxZ,
                texture));

        // Right face (x = maxX)
        sb.append(String.format(" ( %d %d %d ) ( %d %d %d ) ( %d %d %d ) %s -40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n",
                maxX, minY, maxZ,
                maxX, maxY, maxZ,
                maxX, maxY, minZ,
                texture));

        sb.append("}\n");
        return sb.toString();
    }

public static List<MergedBlock> greedyMerge(Map<Position, Block> blockMap) {
    Set<Position> visited = new HashSet<>();
    List<MergedBlock> MergedBlocks = new ArrayList<>();

    List<Position> positions = new ArrayList<>(blockMap.keySet());

    for (Position pos : positions) {
        if (visited.contains(pos)) continue;

        Block start = blockMap.get(pos);
        int identifiant = start.identifiant;
        String texture = start.texture;

        int maxX = pos.x;
        while (true) {
            Position next = new Position(maxX + 1, pos.y, pos.z);
            Block b = blockMap.get(next);
            if (b != null && !visited.contains(next) && b.texture.equals(texture)) {
                maxX++;
            } else {
                break;
            }
        }

        int maxY = pos.y;
        outerY:
        while (true) {
            for (int x = pos.x; x <= maxX; x++) {
                Position next = new Position(x, maxY + 1, pos.z);
                Block b = blockMap.get(next);
                if (b == null || visited.contains(next) || !b.texture.equals(texture)) {
                    break outerY;
                }
            }
            maxY++;
        }

        int maxZ = pos.z;
        outerZ:
        while (true) {
            for (int x = pos.x; x <= maxX; x++) {
                for (int y = pos.y; y <= maxY; y++) {
                    Position next = new Position(x, y, maxZ + 1);
                    Block b = blockMap.get(next);
                    if (b == null || visited.contains(next) || !b.texture.equals(texture)) {
                        break outerZ;
                    }
                }
            }
            maxZ++;
        }

        for (int x = pos.x; x <= maxX; x++) {
            for (int y = pos.y; y <= maxY; y++) {
                for (int z = pos.z; z <= maxZ; z++) {
                    visited.add(new Position(x, y, z));
                }
            }
        }

        Position min = new Position(pos.x, pos.y, pos.z);
        Position max = new Position(maxX, maxY, maxZ);
        MergedBlocks.add(new MergedBlock(min, max, texture, identifiant));
    }

    return MergedBlocks;
}

}

    