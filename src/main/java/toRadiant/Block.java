package toRadiant;

public class Block extends Brush{
    public Face bottom, top, front, back, left, right;
    public String texture;
    public int identifiant;
    public Block(String t,int id){
        identifiant = id;
        texture = t;
        bottom = new Face(new Vector3D(20,20,-20), new Vector3D(-20,20,-20), new Vector3D(-20,-20,-20));
        top = new Face(new Vector3D(-20,-20,20), new Vector3D(-20,20,20), new Vector3D(20,20,20));
        front = new Face(new Vector3D(-20,-20,140), new Vector3D(20,-20,140), new Vector3D(20,-20,-20));
        back = new Face(new Vector3D(20,-20,140), new Vector3D(20,20,140), new Vector3D(20,20,-20));
        left = new Face(new Vector3D(20,20,140), new Vector3D(-20,20,140), new Vector3D(-20,20,-20));
        right = new Face(new Vector3D(-20,20,140), new Vector3D(-20,-20,140), new Vector3D(-20,-20,-20));
    }
    public String getName(){
        return texture;
    }
    public Block(int x, int y, int z, String t,int id){
        identifiant = id;
        texture = t;
        bottom = new Face(new Vector3D(20+(x*40),20+(y*40),-20+(z*40)), new Vector3D(-20+(x*40),20+(y*40),-20+(z*40)), new Vector3D(-20+(x*40),-20+(y*40),-20+(z*40)));
        top = new Face(new Vector3D(-20+(x*40),-20+(y*40),20+(z*40)), new Vector3D(-20+(x*40),20+(y*40),20+(z*40)), new Vector3D(20+(x*40),20+(y*40),20+(z*40)));
        front = new Face(new Vector3D(-20+(x*40),-20+(y*40),140+(z*40)), new Vector3D(20+(x*40),-20+(y*40),140+(z*40)), new Vector3D(20+(x*40),-20+(y*40),-20+(z*40)));
        back = new Face(new Vector3D(20+(x*40),-20+(y*40),140+(z*40)), new Vector3D(20+(x*40),20+(y*40),140+(z*40)), new Vector3D(20+(x*40),20+(y*40),-20+(z*40)));
        left = new Face(new Vector3D(20+(x*40),20+(y*40),140+(z*40)), new Vector3D(-20+(x*40),20+(y*40),140+(z*40)), new Vector3D(-20+(x*40),20+(y*40),-20+(z*40)));
        right = new Face(new Vector3D(-20+(x*40),20+(y*40),140+(z*40)), new Vector3D(-20+(x*40),-20+(y*40),140+(z*40)), new Vector3D(-20+(x*40),-20+(y*40),-20+(z*40)));
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
