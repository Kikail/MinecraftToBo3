package toRadiant;

public class Block extends Brush{
    public Face bottom, top, front, back, left, right;
    public String texture;
    public int identifiant;

    public Position position;
    public Block(String t,int id){
        identifiant = id;
        texture = t;
        bottom = new Face(new Vector3D(20,20,-20), new Vector3D(-20,20,-20), new Vector3D(-20,-20,-20));
        top = new Face(new Vector3D(-20,-20,20), new Vector3D(-20,20,20), new Vector3D(20,20,20));
        front = new Face(new Vector3D(-20,-20,20), new Vector3D(20,-20,20), new Vector3D(20,-20,-20));
        back = new Face(new Vector3D(20,-20,20), new Vector3D(20,20,20), new Vector3D(20,20,-20));
        left = new Face(new Vector3D(20,20,20), new Vector3D(-20,20,20), new Vector3D(-20,20,-20));
        right = new Face(new Vector3D(-20,20,20), new Vector3D(-20,-20,20), new Vector3D(-20,-20,-20));
        position = new Position(0,0,0);
    }
    public String getName(){
        return texture;
    }
    public Block(int x, int y, int z, String t,int id){
        identifiant = id;
        texture = t;
        int baseX = x * 40;
        int baseY = y * 40;
        int baseZ = z * 40;

        bottom = new Face(
                new Vector3D(20 + baseX, 20 + baseY, -20 + baseZ),
                new Vector3D(-20 + baseX, 20 + baseY, -20 + baseZ),
                new Vector3D(-20 + baseX, -20 + baseY, -20 + baseZ)
        );
        top = new Face(
                new Vector3D(-20 + baseX, -20 + baseY, 20 + baseZ),
                new Vector3D(-20 + baseX, 20 + baseY, 20 + baseZ),
                new Vector3D(20 + baseX, 20 + baseY, 20 + baseZ)
        );
        front = new Face(
                new Vector3D(-20 + baseX, -20 + baseY, 20 + baseZ),
                new Vector3D(20 + baseX, -20 + baseY, 20 + baseZ),
                new Vector3D(20 + baseX, -20 + baseY, -20 + baseZ)
        );
        back = new Face(
                new Vector3D(20 + baseX, -20 + baseY, 20 + baseZ),
                new Vector3D(20 + baseX, 20 + baseY, 20 + baseZ),
                new Vector3D(20 + baseX, 20 + baseY, -20 + baseZ)
        );
        left = new Face(
                new Vector3D(20 + baseX, 20 + baseY, 20 + baseZ),
                new Vector3D(-20 + baseX, 20 + baseY, 20 + baseZ),
                new Vector3D(-20 + baseX, 20 + baseY, -20 + baseZ)
        );
        right = new Face(
                new Vector3D(-20 + baseX, 20 + baseY, 20 + baseZ),
                new Vector3D(-20 + baseX, -20 + baseY, 20 + baseZ),
                new Vector3D(-20 + baseX, -20 + baseY, -20 + baseZ)
        );
        position = new Position(x,y,z);
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
        s += "// brush " + identifiant + "\n";
        s += "{\n";
        s += " guid \"{"+getIdFormat()+"-0101-0101-0101-010101010101}\"\n";
        s += " "+bottom.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n";
        s += " "+top.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n";
        s += " "+front.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n";
        s += " "+back.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n";
        s += " "+left.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n";
        s += " "+right.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 0 0 0 0\n";
        s += "}\n";
        return s;
    }
}
