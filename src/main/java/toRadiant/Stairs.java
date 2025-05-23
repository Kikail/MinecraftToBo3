package toRadiant;

public class Stairs extends Brush {
    public Face bottom, top, front, back, left, right;
    public String texture;
    public int identifiant;
    public Slab slab;
    public Stairs(String t,int id, Slab s){
        identifiant = id;
        texture = t;
        bottom = new Face(new Vector3D(20,20,-20), new Vector3D(-20,20,-20), new Vector3D(-20,-20,-20));
        top = new Face(new Vector3D(-20,-20,0), new Vector3D(-20,20,0), new Vector3D(20,20,0));
        front = new Face(new Vector3D(-20,-20,12), new Vector3D(20,-20,12), new Vector3D(20,-20,-20));
        back = new Face(new Vector3D(20,-20,12), new Vector3D(20,20,12), new Vector3D(20,20,-20));
        left = new Face(new Vector3D(20,20,12), new Vector3D(-20,20,12), new Vector3D(-20,20,-20));
        right = new Face(new Vector3D(-20,20,12), new Vector3D(-20,-20,12), new Vector3D(-20,-20,-20));
        slab = s;
    }
    public Stairs(int x, int y, int z, String t,int id, boolean isBottom, Slab s, Direction d){
        identifiant = id;
        texture = t;
        switch (d) {
            case Direction.EAST:
                bottom = new Face(new Vector3D(32+(x*40),20+(y*40),0+(z*40)), new Vector3D(0+(x*40),20+(y*40),0+(z*40)), new Vector3D(0+(x*40),-12+(y*40),0+(z*40)));
                top = new Face(new Vector3D(-20+(x*40),-12+(y*40),20+(z*40)), new Vector3D(-20+(x*40),20+(y*40),20+(z*40)), new Vector3D(12+(x*40),20+(y*40),20+(z*40)));
                front = new Face(new Vector3D(-12+(x*40),-20+(y*40),-12+(z*40)), new Vector3D(20+(x*40),-20+(y*40),-12+(z*40)), new Vector3D(20+(x*40),-20+(y*40),-20+(z*40)));
                back = new Face(new Vector3D(0+(x*40),-24+(y*40),-32+(z*40)), new Vector3D(0+(x*40),8+(y*40),-32+(z*40)), new Vector3D(0+(x*40),8+(y*40),-40+(z*40)));
                left = new Face(new Vector3D(12+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),20+(y*40),-20+(z*40)));
                right = new Face(new Vector3D(-20+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),-12+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),-12+(y*40),-20+(z*40)));
                break;
            case Direction.WEST:
                bottom = new Face(new Vector3D(52+(x*40),20+(y*40),0+(z*40)), new Vector3D(20+(x*40),20+(y*40),0+(z*40)), new Vector3D(20+(x*40),-12+(y*40),0+(z*40)));
                top = new Face(new Vector3D(-20+(x*40),-12+(y*40),20+(z*40)), new Vector3D(-20+(x*40),20+(y*40),20+(z*40)), new Vector3D(12+(x*40),20+(y*40),20+(z*40)));
                front = new Face(new Vector3D(-12+(x*40),-20+(y*40),-12+(z*40)), new Vector3D(20+(x*40),-20+(y*40),-12+(z*40)), new Vector3D(20+(x*40),-20+(y*40),-20+(z*40)));
                back = new Face(new Vector3D(20+(x*40),-20+(y*40),-32+(z*40)), new Vector3D(20+(x*40),12+(y*40),-32+(z*40)), new Vector3D(20+(x*40),12+(y*40),-40+(z*40)));
                left = new Face(new Vector3D(12+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),20+(y*40),-20+(z*40)));
                right = new Face(new Vector3D(0+(x*40),20+(y*40),8+(z*40)), new Vector3D(0+(x*40),-12+(y*40),8+(z*40)), new Vector3D(0+(x*40),-12+(y*40),0+(z*40)));
                break;
            case Direction.SOUTH:
                bottom = new Face(new Vector3D(32+(x*40),40+(y*40),0+(z*40)), new Vector3D(0+(x*40),40+(y*40),0+(z*40)), new Vector3D(0+(x*40),8+(y*40),0+(z*40)));
                top = new Face(new Vector3D(-20+(x*40),-12+(y*40),20+(z*40)), new Vector3D(-20+(x*40),20+(y*40),20+(z*40)), new Vector3D(12+(x*40),20+(y*40),20+(z*40)));
                front = new Face(new Vector3D(-12+(x*40),-0+(y*40),-8+(z*40)), new Vector3D(20+(x*40),0+(y*40),8+(z*40)), new Vector3D(20+(x*40),0+(y*40),0+(z*40)));
                back = new Face(new Vector3D(20+(x*40),-20+(y*40),-32+(z*40)), new Vector3D(20+(x*40),12+(y*40),-32+(z*40)), new Vector3D(20+(x*40),12+(y*40),-40+(z*40)));
                left = new Face(new Vector3D(12+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),20+(y*40),-20+(z*40)));
                right = new Face(new Vector3D(-20+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),-12+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),-12+(y*40),-20+(z*40)));
                break;
            case Direction.NORTH:
                bottom = new Face(new Vector3D(32+(x*40),0+(y*40),0+(z*40)), new Vector3D(0+(x*40),0+(y*40),0+(z*40)), new Vector3D(0+(x*40),-32+(y*40),0+(z*40)));
                top = new Face(new Vector3D(-20+(x*40),-12+(y*40),20+(z*40)), new Vector3D(-20+(x*40),20+(y*40),20+(z*40)), new Vector3D(12+(x*40),20+(y*40),20+(z*40)));
                front = new Face(new Vector3D(-12+(x*40),-20+(y*40),-12+(z*40)), new Vector3D(20+(x*40),-20+(y*40),-12+(z*40)), new Vector3D(20+(x*40),-20+(y*40),-20+(z*40)));
                back = new Face(new Vector3D(20+(x*40),-20+(y*40),-32+(z*40)), new Vector3D(20+(x*40),12+(y*40),-32+(z*40)), new Vector3D(20+(x*40),12+(y*40),-40+(z*40)));
                left = new Face(new Vector3D(12+(x*40),0+(y*40),8+(z*40)), new Vector3D(-20+(x*40),0+(y*40),8+(z*40)), new Vector3D(-20+(x*40),0+(y*40),0+(z*40)));
                right = new Face(new Vector3D(-20+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),-12+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),-12+(y*40),-20+(z*40)));
                break;
            default:
                bottom = new Face(new Vector3D(32+(x*40),20+(y*40),0+(z*40)), new Vector3D(0+(x*40),20+(y*40),0+(z*40)), new Vector3D(0+(x*40),-12+(y*40),0+(z*40)));
                top = new Face(new Vector3D(-20+(x*40),-12+(y*40),20+(z*40)), new Vector3D(-20+(x*40),20+(y*40),20+(z*40)), new Vector3D(12+(x*40),20+(y*40),20+(z*40)));
                front = new Face(new Vector3D(-12+(x*40),-20+(y*40),-12+(z*40)), new Vector3D(20+(x*40),-20+(y*40),-12+(z*40)), new Vector3D(20+(x*40),-20+(y*40),-20+(z*40)));
                back = new Face(new Vector3D(0+(x*40),-24+(y*40),-32+(z*40)), new Vector3D(0+(x*40),8+(y*40),-32+(z*40)), new Vector3D(0+(x*40),8+(y*40),-40+(z*40)));
                left = new Face(new Vector3D(12+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),20+(y*40),-20+(z*40)));
                right = new Face(new Vector3D(-20+(x*40),20+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),-12+(y*40),-12+(z*40)), new Vector3D(-20+(x*40),-12+(y*40),-20+(z*40)));
                break;
        }

        if (!isBottom) {
            bottom.first.z -= 20;
            top.first.z -= 20;
            front.first.z -= 20;
            back.first.z -= 20;
            left.first.z -= 20;
            right.first.z -= 20;
            bottom.second.z -= 20;
            top.second.z -= 20;
            front.second.z -= 20;
            back.second.z -= 20;
            left.second.z -= 20;
            right.second.z -= 20;
            bottom.third.z -= 20;
            top.third.z -= 20;
            front.third.z -= 20;
            back.third.z -= 20;
            left.third.z -= 20;
            right.third.z -= 20;
        }

        slab = s;
    }
    public String getName(){
        return texture;
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
        s += " "+bottom.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 -20 -20 0 0\n";
        s += " "+top.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 -20 -20 0 0\n";
        s += " "+front.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 -20 20 0 0\n";
        s += " "+back.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 20 20 0 0\n";
        s += " "+left.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 -20 20 0 0\n";
        s += " "+right.toString() + " " + texture + " " + "-40 40 -20 20 0 0 lightmap_gray 16384 16384 20 20 0 0\n";
        s += "}\n";
        s += slab.toString();
        return s;
    }
}
