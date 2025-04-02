package math;


//class to represent a cube in 3D space
public class Cube {
    //coordinates represent a cube with corrisponding x,y,z coordinates
    // into a 3d matrix (example 0,0,0 is a cube in the relative position 0,0,0)
    public int x,y,z;
    public int freeSides = 6;
    public String id;
    
    //constructor
    public Cube() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.id = createId(0, 0, 0);
    }

    public Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = createId(x, y, z);
    }

    public Cube(int x, int y, int z, int freeSides) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.freeSides = freeSides;
        this.id = createId(x, y, z);
    }

    public String createId(int x, int y, int z) {
        return "box_" + x + "_" + y + "_" + z;
    }

    //setter
    public void setCoordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setID(int x, int y, int z) {
        this.id = createId(x, y, z);
    }

    public boolean isEqual(Cube cube) {
        return this.x == cube.x && this.y == cube.y && this.z == cube.z && this.id.equals(cube.id);
    }
}
