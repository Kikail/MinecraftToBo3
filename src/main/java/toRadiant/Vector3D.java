package toRadiant;

public class Vector3D {
    public int x,y,z;
    public Vector3D(){
        x = 0;
        y = 0;
        z = 0;
    }
    public Vector3D(int _x, int _y, int _z){
        x = _x;
        y = _y;
        z = _z;
    }
    public String toString(){
        return "( "+x+" "+y+" "+z+" )";
    }
}
