package toRadiant;

import com.example.minecraftbo3.HelloController;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToRadiantPrefab {
    public static void createPrefab(String fileName, HelloController controller, Map map) {
        map.SaveMapInFile(fileName.replace(".litematic", ".map"),controller);
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

    public static void readBlockData(String worldPos, String blockName, Map map, File fileIds) {
        String blockID = "stone";
        if(blockName.contains("cobblestone")){
            if(blockName.contains("mossy")){
                blockID = "_mc_block_mossy_cobblestone";
            }
            else{
                blockID = "_mc_block_cobblestone";
            }
        }
        else{
            try{
                Scanner scan = new Scanner(fileIds);
                while(scan.hasNext()){
                    String lineIds = scan.nextLine().toString();

                    String withoutPrefix = blockName.startsWith("minecraft:") ? blockName.substring("minecraft:".length()) : blockName;
                    int bracketIndex = withoutPrefix.indexOf('[');
                    if (bracketIndex != -1) {
                        withoutPrefix = withoutPrefix.substring(0, bracketIndex);
                    }

                    if(lineIds.contains(withoutPrefix)){
                        String[] lineIdsSplit = lineIds.split(",");
                        blockID = lineIdsSplit[1];
                    }
                }
            }
            catch (Exception e){

            }
        }

        // Verifie qu il ne s agisse pas d un bloc d air
        if (!blockName.contains("minecraft:air") && !blockName.contains("minecraft:water")&& !blockName.contains("minecraft:barrier") && !blockName.contains("button") && !blockName.contains("minecraft:wall_torch") && !blockName.contains("minecraft:lava")) {
            int[] coordinates = extractCoordinates(worldPos);
            if (coordinates != null) {
                System.out.println(blockName);
                // Verifier si c'est une dalle
                if(blockName.contains("slab")){
                    map.AddSlab(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockID), blockName.contains("type=bottom"));
                }
                else if(blockName.contains("stairs")){
                    if (blockName.contains("facing=north")) {
                        map.AddStairs(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockID).replace("stairs", "slab"), blockName.contains("half=bottom"),Direction.NORTH);
                    }
                    else if (blockName.contains("facing=south")) {
                        map.AddStairs(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockID).replace("stairs", "slab"), blockName.contains("half=bottom"),Direction.SOUTH);
                    }
                    else if (blockName.contains("facing=east")) {
                        map.AddStairs(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockID).replace("stairs", "slab"), blockName.contains("half=bottom"),Direction.EAST);
                    }
                    else{
                        map.AddStairs(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockID).replace("stairs", "slab"), blockName.contains("half=bottom"),Direction.WEST);
                    }
                }
                else{
                    map.AddBlock(coordinates[0], coordinates[2], coordinates[1], MatchingBlock.Get(blockID));
                }
            }
            //System.out.println(position + " " + blockName);
        }

    }
}
