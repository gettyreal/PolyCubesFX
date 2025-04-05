package math;

import java.util.HashSet;
import java.util.Set;

// Polycube class
// represent the Polycube as a set of cubes
public class Polycube {
    public Set<Cube> cubes = new HashSet<Cube>(); // al cubes contained in the polycube

    // default constructor
    public Polycube() {
        cubes.add(new Cube(0, 0, 0)); // add a default cube, to be remembered
    }

    // constructor to copy a polycube
    // take as parameter a polycube and copy it in the current obj
    public Polycube(Polycube polycube) {
        this.cubes = new HashSet<>(polycube.cubes); // copy the cubes from the input polycube
    }

    // function to add a cube to the polycube
    // take as parameter a cube and add it to the polycube
    // does not check adjacency because it automatically is adjacent cause program
    // structure
    public void addCube(Cube cube) {
        cubes.add(cube);
    }

    // function to calculate the minumum coordinates of the polycube
    // return the stored coordinate in a cube object called "BoundingMinimumCube"
    private Cube calculateBoundingMinimumCube() {
        // sets max value possible (in case of 2147483647 cubes polycube (max found is
        // 22 so not likely))
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;

        // for each cube check if it has min coordinates
        for (Cube cube : cubes) {
            minX = Math.min(minX, cube.x);
            minY = Math.min(minY, cube.y);
            minZ = Math.min(minZ, cube.z);
        }

        // return the BoundingMinimumCube containing the min coordinates
        return new Cube(minX, minY, minZ);
    }

    // function to translate the polycube to positive coordinates
    
    // functioning:
    // 1) get the minimum coordinates of the polycube by the
    // calculateBoundingMinimumCube function
    // 2) for each cube in the polycube translate it to positive coordinates by
    // subtracting the min(<= 0) coordinates
    // 3) replace the cubes in the polycube with the translated cubes
    public void translatePolycube() {
        Cube translationPivotCube = calculateBoundingMinimumCube(); // get the minimum cube to translate the polycube

        int pivotX = translationPivotCube.x; // gets min x 
        int pivotY = translationPivotCube.y; // gets min y
        int pivotZ = translationPivotCube.z; // gets min z

        Set<Cube> translatedCubes = new HashSet<>(); // creates new hashset to not mess up memory allocation
        // for each cube creates a copy translated and adds it to the new hashset
        for (Cube cube : this.cubes) {
            Cube translatedCube = new Cube(
                    cube.x - pivotX,
                    cube.y - pivotY,
                    cube.z - pivotZ);
            translatedCube.setID(translatedCube.x, translatedCube.y, translatedCube.z);
            translatedCubes.add(translatedCube);
        }
        // update this.cubes with the new translated hashset of cubes
        this.cubes = translatedCubes;
    }

    // ROTATIONS

    // funtion to rotate the polycube around the x axis
    public void rotateX() {
        for (Cube cube : cubes) {
            // rotate the cube around the x-axis
            int tempY = cube.y;
            cube.y = -cube.z;
            cube.z = tempY;
            cube.setID(cube.x, cube.y, cube.z);
        }
    }

    // function to rotate the polycube around the y axis
    public void rotateY() {
        for (Cube cube : cubes) {
            // rotate the cube around the y-axis
            int tempX = cube.x;
            cube.x = -cube.z;
            cube.z = tempX;
            cube.setID(cube.x, cube.y, cube.z);
        }
    }

    // function to rotate the polycube around the z axis
    public void rotateZ() {
        for (Cube cube : cubes) {
            // rotate the cube around the z-axis
            int tempX = cube.x;
            cube.x = -cube.y;
            cube.y = tempX;
            cube.setID(cube.x, cube.y, cube.z);
        }
    }

    // TRANSLATIONS

    // function to translate the polycube along the x axis
    public void translateX() {
        for (Cube cube : cubes) {
            cube.x++;
            cube.setID(cube.x, cube.y, cube.z);
        }
    }

    // function to translate the polycube along the y axis
    public void translateY() {
        for (Cube cube : cubes) {
            cube.y++;
            cube.setID(cube.x, cube.y, cube.z);
        }
    }

    // function to translate the polycube along the z axis
    public void translateZ() {
        for (Cube cube : cubes) {
            cube.z++;
            cube.setID(cube.x, cube.y, cube.z);

        }
    }


    // function to check if two polycubes are equal
    // gets as parameter the polycube to check
    public boolean isEqual(Polycube polycube) {
        if (this.cubes.size() != polycube.cubes.size()) { // 1) if polycubes have different number of cubes they are not equal
            return false;
        }

        // checks for each cube in the 2 poltycube if they are equal
        for (Cube cube : this.cubes) {
            boolean found = false;
            for (Cube otherCube : polycube.cubes) {
                if (cube.id.equals(otherCube.id)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    // function to print the polycube in the terminal
    public void printPolycube() {
        System.out.println("POLYCUBE");
        for (Cube cube : cubes) {
            System.out.println("Cube at: " + cube.x + ", " + cube.y + ", " + cube.z + ", id = " + cube.id);
        }
        System.out.println();
    }
}
