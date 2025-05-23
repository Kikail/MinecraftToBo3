package toRadiant;

public class Prefab extends Brush{
    Vector3D coordinate;
    Vector3D angles;
    String prefabName;
    int identifiant;

    public Prefab(int x, int y, int z, String p,int id, Direction direction){
        prefabName = p;
        identifiant = id;
        coordinate = new Vector3D(x*40,y*40,z*40);
        switch(direction){
            case NORTH:
                angles = new Vector3D(0,180,0);
                break;
            case SOUTH:
                angles = null;
                break;
            case WEST:
                angles = new Vector3D(0,270,0);
                break;
            case EAST:
                angles = new Vector3D(0,90,0);
                break;
        }
    }

    public String getIdFormat(){
        int i = 0;
        int diviser = 10000000;
        int n = identifiant;
        while((n/diviser)<1){
            if(diviser <= 1)
                break;
            diviser /= 10;
            i++;
        }
        String s = "";
        for(int k = 0; k<i ; k++){
            s += "0";
        }
        s += ""+identifiant;
        return s;
    }

    public String toString(){
        String s = "";
        s += "// entity " + identifiant + "\n";
        s += "{\n";
        s += " guid \"{"+getIdFormat()+"-0111-0111-0111-010101010101}\"\n";
        s += " \"classname\" \"misc_prefab\"\n";
        if(angles != null){
            s += " \"angles\" \""+angles.x+" "+angles.y+" "+angles.z+"\"\n";
        }
        s += " \"model\" \""+prefabName+"\"\n";
        s += " \"origin\" \""+coordinate.x+" "+coordinate.y+" "+coordinate.z+"\"\n";
        s += "}\n";
        return s;
    }

    @Override
    public String getName() {
        return "";
    }
}
