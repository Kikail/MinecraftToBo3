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
        if(fileName.contains(".litematic")) {
            map.SaveMapInFile(fileName.replace(".litematic", ".map"),controller);
        }
        else if(fileName.contains(".schem")) {
            map.SaveMapInFile(fileName.replace(".schem", ".map"),controller);
        }
        else if(fileName.contains(".schematic")) {
            map.SaveMapInFile(fileName.replace(".schematic", ".map"),controller);
        }
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

    public static void readBlockData( String blockName,int x, int y, int z, Map map, File fileIds) {
        if(blockName.contains("minecraft:water")){
            return;
        }

        String blockID = "stone";
        try{
            Scanner scan = new Scanner(fileIds);
            while(scan.hasNext()){
                String lineIds = scan.nextLine().toString();

                String withoutPrefix = blockName.startsWith("minecraft") ? blockName.substring("minecraft".length()) : blockName;
                int bracketIndex = withoutPrefix.indexOf('[');
                if (bracketIndex != -1) {
                    withoutPrefix = withoutPrefix.substring(0, bracketIndex);
                }

                if(lineIds.contains(withoutPrefix)){
                    String[] lineIdsSplit = lineIds.split(",");
                    blockID = lineIdsSplit[1];
                    break;
                }
            }
        }
        catch (Exception e){

        }

        // Verifie qu il ne s agisse pas d un bloc d air
        if (!blockName.contains("minecraft:barrier") && !blockName.contains("button") && !blockName.contains("minecraft:wall_torch") && !blockName.contains("minecraft:lava")) {
            if(blockID.contains("prefab")){
                System.out.println(blockName+" "+blockID);
                map.AddPrefab(x,z,y,MatchingBlock.Get(blockID),(blockName.contains("north=true") || blockName.contains("south=true")));
                return;
            }
            // Verifier si c'est une dalle
            if(blockName.contains("slab")){
                map.AddSlab(x, z, y, MatchingBlock.Get(blockID), blockName.contains("type=bottom"));
            }
            else if(blockName.contains("stairs")){
                if (blockName.contains("facing=north")) {
                    map.AddStairs(x, z, y, MatchingBlock.Get(blockID).replace("stairs", "slab"), blockName.contains("half=bottom"),Direction.NORTH);
                }
                else if (blockName.contains("facing=south")) {
                    map.AddStairs(x, z, y, MatchingBlock.Get(blockID).replace("stairs", "slab"), blockName.contains("half=bottom"),Direction.SOUTH);
                }
                else if (blockName.contains("facing=east")) {
                    map.AddStairs(x, z, y, MatchingBlock.Get(blockID).replace("stairs", "slab"), blockName.contains("half=bottom"),Direction.EAST);
                }
                else{
                    map.AddStairs(x, z, y, MatchingBlock.Get(blockID).replace("stairs", "slab"), blockName.contains("half=bottom"),Direction.WEST);
                }
            }
            else{
                map.AddBlock(x, z, y, MatchingBlock.Get(blockID));
            }
            
            //System.out.println(position + " " + blockName);
        }

    }
}
