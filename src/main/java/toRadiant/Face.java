package toRadiant;

public class Face {
    public Vector3D first, second, third;
    public Face(){
        first = new Vector3D();
        second = new Vector3D();
        third = new Vector3D();
    }
    public Face(Vector3D f, Vector3D s, Vector3D t){
        first = f;
        second = s;
        third = t;
    }
    public String toString(){
        String s = first.toString()+" "+second.toString()+" "+third.toString();
        return s;
    }
}
