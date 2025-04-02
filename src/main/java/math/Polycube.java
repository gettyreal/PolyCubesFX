package math;

import java.util.HashSet;
import java.util.Set;

public class Polycube {
    public Set<Cube> cubes = new HashSet<Cube>();

    // constructor
    public Polycube() {
        cubes.add(new Cube(0, 0, 0)); // add a default cube, to be remembered
    }

    public Polycube(Polycube polycube) {
        this.cubes = new HashSet<>(polycube.cubes); // copy the cubes from the input polycube
    }

    // add cubes to the polyCube
    public void addCube(int x, int y, int z) {
        boolean isAdjacent = false;
        // check if the cube is adjacent to any of the existing cubes in the
        // polyCube
        for (Cube cube : cubes) {
            if (Math.abs(cube.x - x) + Math.abs(cube.y - y) + Math.abs(cube.z - z) == 1) {
                isAdjacent = true;
                break;
            }
        }
        if (isAdjacent) {
            cubes.add(new Cube(x, y, z));
            calculateCubeAdjacency(); 
        } else {
            System.out.println("not adjacent");
        }
    }

    public void addCube(Cube cube) {
        cubes.add(cube);
        calculateCubeAdjacency(); // recalculate adjacency after adding a new cube
    }

    public void calculateCubeAdjacency() {
        int sharedSides = 0;
        for (Cube cube : cubes) {
            // check for each cube how many sides are shared with other cubes
            for (Cube otherCube : cubes) {
                if (cube != otherCube) {
                    if (Math.abs(cube.x - otherCube.x) + Math.abs(cube.y - otherCube.y) + Math.abs(cube.z - otherCube.z) == 1) {
                        sharedSides++;
                    }
                }
            }
            cube.freeSides = 6 - sharedSides; // update the number of free sides of the cube
            sharedSides = 0; // reset the counter for the next cube
        }
    }

    public void rotateX() {
        for (Cube cube : cubes) {
            // rotate the cube around the x-axis
            int tempY = cube.y;
            cube.y = -cube.z;
            cube.z = tempY;
        }
    }

    public void rotateY() {
        for (Cube cube : cubes) {
            // rotate the cube around the y-axis
            int tempX = cube.x;
            cube.x = -cube.z;
            cube.z = tempX;
        }
    }

    public void rotateZ() {
        for (Cube cube : cubes) {
            // rotate the cube around the z-axis
            int tempX = cube.x;
            cube.x = -cube.y;
            cube.y = tempX;
        }
    }

    public boolean isEqual(Polycube polycube) {
        if (this.cubes.size() != polycube.cubes.size()) {
            return false;
        }
        boolean isEqual = true;
        for (Cube cube : this.cubes) {
            boolean found = false;
            for (Cube otherCube : polycube.cubes) {
                if (cube.isEqual(otherCube)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                isEqual = false;
                break;
            }
        }
        return isEqual;
    }

    public void printPolycube() {
        System.out.println("POLYCUBE");
        for (Cube cube : cubes) {
            System.out.println("Cube at: " + cube.x + ", " + cube.y + ", " + cube.z + " with " + cube.freeSides + " free sides.");
        }
        System.out.println();
    }
}
