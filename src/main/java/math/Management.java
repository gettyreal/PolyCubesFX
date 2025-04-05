package math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//class to manage polycumbes and confront them
public class Management {
    public ArrayList<Polycube> polycubes = new ArrayList<Polycube>(); // list of all possible polycubes
    public int cubeNum; // number of cubes in the polycube

    /*
     * the faces of a cube are represented as follows:
     * 1 = R face (right)
     * 2 = L face (left)
     * 3 = U face (up)
     * 4 = D face (down)
     * 5 = F face (front)
     * 6 = B face (back)
     * 
     * this is the common term for rubix cube solving algorithms
     */

    // function to generate every possible polycube with a given number of cubes
    // basically main function of the programm
    // gets as parameter the number of cubes in the polycube and the first cube to
    // start from (always 0,0,0 to be remembered)

    // functioning:
    // 0.5) creates filterCubes
    // 1) for each cube in the polycubes attemps to generate a cube for each of the
    // faces
    // 2) if the cube is not already in the polycube it creates a new polycube with
    // the new cube attached
    // 3) if the cubeNum == 1 it means wthere are no more cube to be generated so we
    // check if the polycube is unique
    // 3.5) if it is unique it is added to the list of final polycubes
    public void generatePolycubes(int cubeNum, Polycube inputPolyCube) {
        Set<Cube> filterCubes = new HashSet<>(inputPolyCube.cubes); // gets filter cubes to check if new cube generated
                                                                    // is already in the polycube

        // for each of the cube already present in the polycube
        // generates 6 new cubes (one for each face of the cube) and checks if they are
        // already in the polycube
        for (Cube cube : filterCubes) {
            for (int i = 1; i <= 6; i++) { // 6 repetitions for each face of the cube
                Cube newCube = new Cube(); // generates the new cube

                // swithc to set the coordinates of the new cube (always adjacent to a face)
                switch (i) {
                    case 1:
                        newCube.setCoordinates(cube.x + 1, cube.y, cube.z); // R face
                        break;
                    case 2:
                        newCube.setCoordinates(cube.x - 1, cube.y, cube.z); // L face
                        break;
                    case 3:
                        newCube.setCoordinates(cube.x, cube.y + 1, cube.z); // U face
                        break;
                    case 4:
                        newCube.setCoordinates(cube.x, cube.y - 1, cube.z); // D face
                        break;
                    case 5:
                        newCube.setCoordinates(cube.x, cube.y, cube.z + 1); // F face
                        break;
                    case 6:
                        newCube.setCoordinates(cube.x, cube.y, cube.z - 1); // B face
                        break;
                }
                // sets the new cube ID (to be used to check if the cube is already in the
                // polycube)
                newCube.setID(newCube.x, newCube.y, newCube.z);

                if (!isCubeInList(newCube, filterCubes)) { // check if the cube isnt already in the polycube
                    Polycube newPolyCube = new Polycube(inputPolyCube); // creates a new polycube from the input
                                                                        // polycube
                    newPolyCube.addCube(newCube); // and adds it the new cube

                    newPolyCube.translatePolycube(); // translate the polycube to positive cooridinates

                    if (cubeNum == 1) { // if == 1 it means there are no more cubes to add so we can check if the
                                        // polycube is unique
                        if (isPolycubeUnique(newPolyCube)) { // check if polycube is unique
                            this.polycubes.add(newPolyCube); // if so adds it into the final array
                        }
                    } else {
                        generatePolycubes(cubeNum - 1, newPolyCube); // if cubeNum > 1 it means we have to add more
                                                                     // cubes so we do recursive call
                                                                     // with cubeNum - 1
                    }
                }
            }
        }
    }

    // function to check if a polycube is unique
    // gets as parameter the polycube to check

    // functioning:
    // 1) cretes a copy to now mess memory allocation (pivot)
    // 2) rotates pivot and checks if it is equal to any other polycube in the list
    // 2.5) if it is equal it is not unique and returns false
    // 3) if ievery rotation arent in the list it is unique and returns true
    public boolean isPolycubeUnique(Polycube polycube) {
        Polycube pivot = new Polycube(polycube); // copy of the polycube to check

        // iterates through all the possible rotations of the polycube
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 4; z++) {
                    // for each rotation translates the cube to standardise checking procedure
                    // (also for easy 3d representation)
                    pivot.translatePolycube();

                    // check if the polycube is equal to other polycube
                    for (Polycube testPolycube : this.polycubes) {
                        if (testPolycube.isEqual(pivot)) {
                            return false;
                        }
                    }
                    pivot.rotateZ(); // rotate around Z-axis
                }
                pivot.rotateY(); // rotate around Y-axis
            }
            pivot.rotateX(); // rotate around X-axis
        }
        return true; // return true if all the work didn't find any equal polycube

    }

    // function to check if a single cube is already in the polycube
    // gets as parameters the cube to check and the filter (the polycube)
    public boolean isCubeInList(Cube testCube, Set<Cube> filter) {
        boolean isInList = false;
        for (Cube cube : filter) {
            if (cube.isEqual(testCube)) { // if a cube in the filter has equal coordinates to the testCube
                isInList = true;
                break;
            }
        }
        return isInList;
    }

    // function to print the polycubes
    public void printPolycubes() {
        // prints all the polycubes in the list
        for (Polycube polycube : this.polycubes) {
            polycube.printPolycube();
        }
    }
}
