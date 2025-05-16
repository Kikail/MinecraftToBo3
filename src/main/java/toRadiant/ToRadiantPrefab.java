package toRadiant;

import com.example.minecraftbo3.HelloController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToRadiantPrefab {
    public static void createPrefab(String fileName, HelloController controller) {
        Map map = readBlockData(fileName);
        map.SaveMapInFile(fileName.replace(".txt", ".map"),controller);
        File myObj = new File(fileName);
        myObj.delete();
    }

    public static int[] extractCoordinates(String line) {
        // Utilisation d'une expression régulière pour extraire les nombres
        Pattern pattern = Pattern.compile("\\((-?\\d+),(-?\\d+),(-?\\d+)\\)");
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            try {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                int z = Integer.parseInt(matcher.group(3));
                return new int[]{x, y, z};
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public static Map readBlockData(String filePath) {
        Map map = new Map();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\"\"");
                if (parts.length >= 2) {
                    String position = parts[0].replace("\"", "").trim();
                    String blockType = parts[1].replace("\"", "").trim();

                    // Verifie qu il ne s agisse pas d un bloc d air
                    if (!blockType.contains("minecraft:air") && !blockType.contains("minecraft:water")&& !blockType.contains("minecraft:barrier") && !blockType.contains("button") && !blockType.contains("minecraft:wall_torch") && !blockType.contains("minecraft:lava")) {
                        int[] coordinates = extractCoordinates(position);
                        if (coordinates != null) {
                            // Verifier si c'est une dalle
                            if(blockType.contains("slab")){
                                map.AddSlab(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockType), blockType.contains("type=bottom"));
                            }
                            else if(blockType.contains("stairs")){
                                if (blockType.contains("facing=north")) {
                                    map.AddStairs(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockType).replace("stairs", "slab"), blockType.contains("half=bottom"),Direction.NORTH);
                                }
                                else if (blockType.contains("facing=south")) {
                                    map.AddStairs(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockType).replace("stairs", "slab"), blockType.contains("half=bottom"),Direction.SOUTH);
                                }
                                else if (blockType.contains("facing=east")) {
                                    map.AddStairs(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockType).replace("stairs", "slab"), blockType.contains("half=bottom"),Direction.EAST);
                                }
                                else if (blockType.contains("facing=west")) {
                                    map.AddStairs(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockType).replace("stairs", "slab"), blockType.contains("half=bottom"),Direction.WEST);
                                }
                            }
                            else{
                                map.AddBlock(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockType));
                            }
                        }
                        //System.out.println(position + " " + blockType);
                    }

                }

            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier : " + e.getMessage());
        }

        return map;
    }
}
