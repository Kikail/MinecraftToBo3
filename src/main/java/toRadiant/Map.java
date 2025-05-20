package toRadiant;

import com.example.minecraftbo3.HelloController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    public ArrayList<Brush> blocks;
    public int id;

    public Map(){
        id = 0;
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

    public String header(){
        String s = "";
        s += "iwmap 4\n";
        s += "\"script_startingnumber\" 0\n";
        s += "\"000_Global\" flags  active\n";
        s += "\"The Map\" flags expanded \n";
        s += "// entity 0\n";
        s += "{\n";
        s += "guid \"{D5CCCB08-2FFB-11F0-BB8A-047C16075CBE}\"\n";
        s += "\"classname\" \"worldspawn\"\n";
        s += "\"lutmaterial\" \"luts_t7_default\"\n";
        s += "\"fsi\" \"default\"\n";
        s += "\"gravity\" \"800\"\n";
        s += "\"lightingquality\" \"1024\"\n";
        s += "\"lodbias\" \"default\"\n";
        s += "\"numOmniShadowSlices\" \"24\"\n";
        s += "\"numSpotShadowSlices\" \"64\"\n";
        s += "\"samplescale\" \"1\"\n";
        s += "\"shadowslices\" \"96\"\n";
        s += "\"sky_intensity_factor0\" \"1\"\n";
        s += "\"sky_intensity_factor1\" \"1\"\n";
        s += "\"skyboxmodel\" \"skybox_default_day\"\n";
        s += "\"ssi\" \"default_day\"\n";
        s += "\"state_alias_1\" \"State 1\"\n";
        s += "\"state_alias_2\" \"State 2\"\n";
        s += "\"state_alias_3\" \"State 3\"\n";
        s += "\"state_alias_4\" \"State 4\"\n";
        s += "\"umbraSmallestHole\" \"16\"\n";
        s += "\"umbraSmallestOccluder\" \"96\"\n";
        s += "\"umbraTileSize\" \"2\"\n";
        s += "\"umbra_prime_depth\" \"1\"\n";
        s += "\"vbloom\" \"none\"\n";
        s += "\"vcolor\" \"none\"\n";
        s += "\"wsi\" \"default_day\"\n";
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
            int i = 0;
            for(Brush b : blocks){
                writer.write(b.toString());
                i++;
            }
            writer.write(footer());

            // close the writer
            writer.close();

            controller.addLabel(filename);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
