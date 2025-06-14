package toRadiant;

import com.example.minecraftbo3.HelloController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    public ArrayList<Brush> blocks;
    public int id;
    public int idEntity;

    public Map(){
        id = 0;
        idEntity = 1;
        blocks = new ArrayList<Brush>();
    }

    public void AddBlock(int x, int y, int z, String t){
        blocks.add(new Block(x,y,z,t,id));
        id++;
    }
    public void AddSlab(int x, int y, int z, String t, boolean isBottom){
        blocks.add(new Slab(x,y,z,t,id,isBottom));
        id++;
    }
    public void AddStairs(int x, int y, int z, String t, boolean isBottom, Direction d){
        blocks.add(new Stairs(x,y,z,t,id,isBottom,new Slab(x,y,z,t,id+1,isBottom),d));
        id += 2;
    }
    public void AddPrefab(int x, int y, int z, String p, Direction direction){
        blocks.add(new Prefab(x,y,z,p,idEntity,direction));
        idEntity ++;
    }
    public void AddModel(int x, int y, int z, String p, Direction direction){
        blocks.add(new Model(x,y,z,p,idEntity,direction));
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
            // create a FileWriter object with the file name
            FileWriter writer = new FileWriter(filename);

            writer.write(header());
            for(Brush b : blocks){
                if(!(b instanceof Prefab)&&!(b instanceof Model)){
                    writer.write(b.toString());
                }
            }
            writer.write(footer());

            for(Brush b : blocks){
                if(b instanceof Prefab || b instanceof Model){
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
