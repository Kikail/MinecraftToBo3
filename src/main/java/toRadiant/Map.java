package toRadiant;

import com.example.minecraftbo3.HelloController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
    public ArrayList<Brush> brushes;
    public java.util.Map<Position, Block> blocksMap = new HashMap<>();

    public int id;
    public int idEntity;

    public Map(){
        id = 0;
        idEntity = 1;
        brushes = new ArrayList<>();
    }

    public void AddBlock(int x, int y, int z, String t){
        Block b = new Block(x,y,z,t,id);
        brushes.add(b);
        blocksMap.put(b.position,b);
        id++;
    }
    public void AddSlab(int x, int y, int z, String t, boolean isBottom){
        brushes.add(new Slab(x,y,z,t,id,isBottom));
        id++;
    }
    public void AddStairs(int x, int y, int z, String t, boolean isBottom, Direction d){
        brushes.add(new Stairs(x,y,z,t,id,isBottom,new Slab(x,y,z,t,id+1,isBottom),d));
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

    public void SaveMapInFile(String filename, HelloController controller){
        try {

            //Getting lass groups
            List<MergedBlock> groups = MergedBlock.greedyMerge(blocksMap);
            System.out.println(groups.size());

            // create a FileWriter object with the file name
            FileWriter writer = new FileWriter(filename);

            writer.write(header());

            for (MergedBlock group : groups) {
                writer.write(group.toString());
            }

            for (Brush b : brushes) {
                if (!(b instanceof Block || b instanceof Prefab || b instanceof Model)) {
                    writer.write(b.toString());
                }
            }

            writer.write(footer());

            for (Brush b : brushes) {
                if (b instanceof Prefab || b instanceof Model) {
                    writer.write(b.toString());
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
