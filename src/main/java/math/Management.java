package math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//class to manage polycumbes and confront them
public class Management {
    public ArrayList<Polycube> polycubes = new ArrayList<Polycube>(); // list of all polycubes
    public ArrayList<Polycube> finalPolycubes = new ArrayList<Polycube>();
    Polycube templateCube;

    public void generatePolycubes(int cubeNum, Polycube inputPolyCube) {
        Set<Cube> filterCubes = new HashSet<>(inputPolyCube.cubes);
        for (Cube cube : filterCubes) {
            for (int i = 1; i <= 6; i++) {
                Cube newCube = new Cube();
                switch (i) {
                    case 1: // x + 1
                        newCube.setCoordinates(cube.x + 1, cube.y, cube.z);
                        break;
                    case 2: // x - 1
                        newCube.setCoordinates(cube.x - 1, cube.y, cube.z);
                        break;
                    case 3: // y + 1
                        newCube.setCoordinates(cube.x, cube.y + 1, cube.z);
                        break;
                    case 4: // y - 1
                        newCube.setCoordinates(cube.x, cube.y - 1, cube.z);
                        break;
                    case 5: // z + 1
                        newCube.setCoordinates(cube.x, cube.y, cube.z + 1);
                        break;
                    case 6: // z - 1
                        newCube.setCoordinates(cube.x, cube.y, cube.z - 1);
                        break;
                }

                // to add filter restriction.
                if (!isCubeInList(newCube, filterCubes)) {
                    // check if the new cube isnt already in the polycube
                    Polycube newPolyCube = new Polycube(inputPolyCube); // create a new polycube from the input polycube
                    newPolyCube.addCube(newCube); // add the new cube to the polycube
                    if (cubeNum == 1) { // last iteration
                        this.polycubes.add(newPolyCube); // add the polycube to the list of polycubes
                    } else {
                        generatePolycubes(cubeNum - 1, newPolyCube); // recursive call to add more cubes
                    }
                }
            }
        }
    }

    public void calculateTotalPossiblePolyCubes() {
        for(Polycube pivotPolycube : this.polycubes) {
            boolean isUnique = true;
            // check if test polycube or any of its rotations are in final list
            ArrayList<Polycube> RotativeCubes = new ArrayList<Polycube>(this.polycubes);
            for(Polycube testPolycube : RotativeCubes) {
                if(!pivotPolycube.isEqual(testPolycube)) { // if the two polycubes are not equal
                    if (checkPolycube(pivotPolycube, testPolycube)) { // check if the two polycubes are equal after rotation
                        isUnique = false; 
                        break;
                    }
                }
            }

            if (isUnique) {
                this.finalPolycubes.add(pivotPolycube);
            }
        }
    }

    private boolean checkPolycube(Polycube pivot, Polycube testPolycube) {
        boolean isEqual = false;
        //sequence of rotations
        String[] rotationSequence = {
            "Y","Y","Y","Y","X","Y","Y","Y","Y","X", 
            "Y","Y","Y","Y","X","Y","Y","Y","Y","X",
            "Y","Y","Y","Y","Z","Y","Y","Y","Y","Z",
            "Y","Y","Y","Y","Z","Y","Y","Y","Y","Z","Z",
            "Y","Y","Y","Y"
        };
        // rotations based on the sequence
        for (String rotation : rotationSequence) {
            switch (rotation) {
                case "Y":
                    testPolycube.rotateY();
                    break;
                case "X":
                    testPolycube.rotateX();
                    break;
                case "Z":
                    testPolycube.rotateZ();
                    break;
            }
            if (pivot.isEqual(testPolycube)) {
                isEqual = true;
                break;
            }
        }
        return isEqual;
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

    public Polycube generateBasic2Polycube() {
        // generates the first 2 cubes in a polycube
        Polycube polycube = new Polycube();
        polycube.addCube(1, 0, 0); // add the first cube
        return polycube;
    }

    public void printPolycubes() {
        // prints all the polycubes in the list
        for (Polycube polycube : this.finalPolycubes) {
            polycube.printPolycube();
        }
    }
}
