package math;


//class to represent a cube in 3D space
public class Cube {
    //coordinates represent a cube with corrisponding x,y,z coordinates
    // into a 3d matrix (example 0,0,0 is a cube in the relative position 0,0,0)
    public int x,y,z; // grid coordinates
    public String id; // cube ID
    
    // constructor
    // creates default cube at 0,0,0
    public Cube() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.id = setID(0, 0, 0);
    }

    // constructor to create a cube at specific coordinates
    public Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = setID(x, y, z);
    }

    // function to check if two cubes are equal
    // gets as paramenter cube to compare with
    // checks if IDs are equal (if so also cooridinates are equal and cube are in the same position in the grid)
    public boolean isEqual(Cube cube) {
        return this.x == cube.x && this.y == cube.y && this.z == cube.z && this.id.equals(cube.id);
    }

    // SETTERS

    // function to set the coordinates of the cube
    public void setCoordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // function to set / update the ID of the cube
    // ID format is "box_x_y_z"
    public String setID(int x, int y, int z) {
        this.id = "box_" + x + "_" + y + "_" + z;
        return this.id;
    }
}
