package toRadiant;

import com.example.minecraftbo3.HelloController;
import com.example.minecraftbo3.Options;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
    public ArrayList<Brush> brushes;
    public java.util.Map<Position, Block> blocksMap = new HashMap<>();
    public java.util.Map<Position, Slab> slabsMap = new HashMap<>();
    public java.util.Map<Position, Stairs> stairsMap = new HashMap<>();

    public int id;
    public int idEntity;
    public Options options;

    public Map(Options o){
        id = 0;
        idEntity = 1;
        brushes = new ArrayList<>();
        options = o;
    }

    public void AddBlock(int x, int y, int z, String t){
        Block b = new Block(x,y,z,t,id);
        brushes.add(b);
        blocksMap.put(b.position,b);
        id++;
    }
    public void AddSlab(int x, int y, int z, String t, boolean isBottom){
        Slab s = new Slab(x,y,z,t,id,isBottom);
        brushes.add(s);
        slabsMap.put(s.position,s);
        id++;
    }
    public void AddStairs(int x, int y, int z, String t, boolean isBottom, Direction d){
        Slab s = new Slab(x,y,z,t,id+1,isBottom);
        Stairs stair = new Stairs(x,y,z,t,id,d,isBottom);
        brushes.add(stair);
        brushes.add(s);
        slabsMap.put(s.position,s);
        stairsMap.put(stair.position,stair);
        id += 2;
    }
    public void AddPrefab(int x, int y, int z, String p, Direction direction){
        brushes.add(new Prefab(x,y,z,p,idEntity,direction));
        idEntity ++;
    }
    public void AddModel(int x, int y, int z, String p, Direction direction){
        brushes.add(new Model(x,y,z,p,idEntity,direction));
        idEntity ++;
    }

    public String header(){
        String s = "";

        s+="iwmap 4\n";
        s+="\"script_startingnumber\" 0\n";
        s+="\"000_Global\" flags  active\n";
        s+="\"The Map\" flags expanded\n";
        s+="// entity 0\n";
        s+="{\n";
        s+="guid \"{C76FCEF8-FE9C-11EE-BAE4-047C16075CBE}\"\n";
        s+="\"classname\" \"worldspawn\"\n";
        s+="\"lightingquality\" \"1024\"\n";
        s+="\"samplescale\" \"1\"\n";
        s+="\"skyboxmodel\" \"skybox_default_day\"\n";
        s+="\"ssi\" \"default_day\"\n";
        s+="\"wsi\" \"default_day\"\n";
        s+="\"fsi\" \"default\"\n";
        s+="\"gravity\" \"800\"\n";
        s+="\"lodbias\" \"default\"\n";
        s+="\"lutmaterial\" \"luts_t7_default\"\n";
        s+="\"numOmniShadowSlices\" \"24\"\n";
        s+="\"numSpotShadowSlices\" \"64\"\n";
        s+="\"sky_intensity_factor0\" \"1\"\n";
        s+="\"sky_intensity_factor1\" \"1\"\n";
        s+="\"state_alias_1\" \"State 1\"\n";
        s+="\"state_alias_2\" \"State 2\"\n";
        s+="\"state_alias_3\" \"State 3\"\n";
        s+="\"state_alias_4\" \"State 4\"\n";

        return s;
    }
    public String footer(){
        String s = "";
        s +=  "}\n";
        return s;
    }

    public void deleteUselessBlocks(){
        java.util.Map<Position, Block> finalBlocks = new HashMap<>();

        List<Position> positions = new ArrayList<>(blocksMap.keySet());
        int i = 0;

        for (Position pos : positions){
            // We have to test if all blocks are good
            Block neighTop = blocksMap.get(new Position(pos.x,pos.y,pos.z + 1));
            Block neighBot = blocksMap.get(new Position(pos.x,pos.y,pos.z - 1));
            Block neighLeft = blocksMap.get(new Position(pos.x,pos.y + 1,pos.z));
            Block neighRight = blocksMap.get(new Position(pos.x,pos.y - 1,pos.z));
            Block neighFront = blocksMap.get(new Position(pos.x + 1,pos.y,pos.z));
            Block neighBack = blocksMap.get(new Position(pos.x - 1,pos.y,pos.z));
            if(
                    Block.isBlockInvisible(neighTop) ||
                    Block.isBlockInvisible(neighBot) ||
                    Block.isBlockInvisible(neighLeft) ||
                    Block.isBlockInvisible(neighRight) ||
                    Block.isBlockInvisible(neighFront) ||
                    Block.isBlockInvisible(neighBack)
            ){
                finalBlocks.put(pos, blocksMap.get(pos));
            }
            else{
                i++;
                continue;
            }
        }

        System.out.println("Removed " + i + " useless blocks");

        blocksMap.clear();
        blocksMap.putAll(finalBlocks);
    }

    public void SaveMapInFile(String filename, HelloController controller){
        try {
            if(options.deleteUselessBlocks){
                // Deleting all useless blocks
                deleteUselessBlocks();
            }

            // create a FileWriter object with the file name
            FileWriter writer = new FileWriter(filename);

            writer.write(header());

            if(options.includeBlocks){
                if(options.autoMerge){
                    //Getting class groups
                    List<MergedSlab> mergedSlabs = MergedSlab.greedyMerge(slabsMap);
                    List<MergedStair> mergedStairs = MergedStair.greedyMerge(stairsMap);
                    List<MergedBlock> mergedBlocks = MergedBlock.greedyMerge(blocksMap);
                    for (MergedBlock mBlock : mergedBlocks) {
                        writer.write(mBlock.toString());
                    }
                    for (MergedSlab mSlab : mergedSlabs) {
                        writer.write(mSlab.toString());
                    }
                    for (MergedStair mStair : mergedStairs) {
                        writer.write(mStair.toString());
                    }
                }
                else{
                    for (Brush b : brushes) {
                        if (!(b instanceof Prefab) && !(b instanceof Model)) {
                            writer.write(b.toString());
                        }
                    }
                }
            }

            writer.write(footer());

            if(options.includeModels || options.includePrefabs){
                for (Brush b : brushes) {
                    if (options.includePrefabs && b instanceof Prefab) {
                        writer.write(b.toString());
                    }
                    if (options.includeModels && b instanceof Model) {
                        writer.write(b.toString());
                    }
                }
            }

            // close the writer
            writer.close();

            controller.addLabel(filename);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
