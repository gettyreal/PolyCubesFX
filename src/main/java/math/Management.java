package math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//class to manage polycumbes and confront them
public class Management {
    public ArrayList<Polycube> polycubes = new ArrayList<Polycube>(); // list of all polycubes

    public void generatePolycubes(int cubeNum, Polycube inputPolyCube) {
        Set<Cube> filterCubes = new HashSet<>(inputPolyCube.cubes);
        for (Cube cube : filterCubes) {
            for (int i = 1; i <= 6; i++) {
                Cube newCube = new Cube();
                switch (i) {
                    case 1:
                        newCube.setCoordinates(cube.x + 1, cube.y, cube.z);
                        break;
                    case 2:
                        newCube.setCoordinates(cube.x - 1, cube.y, cube.z);
                        break;
                    case 3:
                        newCube.setCoordinates(cube.x, cube.y + 1, cube.z);
                        break;
                    case 4:
                        newCube.setCoordinates(cube.x, cube.y - 1, cube.z);
                        break;
                    case 5:
                        newCube.setCoordinates(cube.x, cube.y, cube.z + 1);
                        break;
                    case 6:
                        newCube.setCoordinates(cube.x, cube.y, cube.z - 1);
                        break;
                }
                newCube.setID(newCube.x, newCube.y, newCube.z);

                if (!isCubeInList(newCube, filterCubes)) {
                    Polycube newPolyCube = new Polycube(inputPolyCube);
                    newPolyCube.addCube(newCube);

                    Cube newMinCube = newPolyCube.calculateBoundingMinimumCube();
                    newPolyCube.translatePolycube(newMinCube); // translate the polycube to the minimum cube

                    if (cubeNum == 1) { // no more cube to add
                        if (!checkPolycube(newPolyCube)) {
                            this.polycubes.add(newPolyCube);
                        }
                    } else {
                        generatePolycubes(cubeNum - 1, newPolyCube);
                    }
                }
            }
        }
    }

    public boolean checkPolycube(Polycube polycube) {
        Polycube pivot = new Polycube(polycube); // copy the polycube to be compared
        for (int xRotations = 0; xRotations < 4; xRotations++) {
            for (int yRotations = 0; yRotations < 4; yRotations++) {
                for (int zRotations = 0; zRotations < 4; zRotations++) {
                    Cube minPivotCube = pivot.calculateBoundingMinimumCube();
                    pivot.translatePolycube(minPivotCube);

                    for (Polycube testPolycube : this.polycubes) {
                        if (testPolycube.isEqual(pivot)) { // if a polycube in the list has equal coordinates to the
                                                           // testPolyCube
                            return true;
                        }
                    }
                    pivot.rotateZ(); // rotate around Z-axis
                }
                pivot.rotateY(); // rotate around Y-axis
            }
            pivot.rotateX(); // rotate around X-axis
        }
        return false;

    }

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

    public static Polycube generateBasic2Polycube() {
        // generates the first 2 cubes in a polycube
        Polycube polycube = new Polycube();
        polycube.addCube(1, 0, 0); // add the first cube
        return polycube;
    }

    public void printPolycubes() {
        // prints all the polycubes in the list
        for (Polycube polycube : this.polycubes) {
            polycube.printPolycube();
        }
    }
}
