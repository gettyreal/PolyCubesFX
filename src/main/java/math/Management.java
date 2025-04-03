package math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//class to manage polycumbes and confront them
public class Management {
    public ArrayList<Polycube> polycubes = new ArrayList<Polycube>(); // list of all polycubes
    public ArrayList<Polycube> finalPolycubes = new ArrayList<Polycube>();

    public ArrayList<Polycube> rotationPolycubes = new ArrayList<Polycube>();

    public void generatePolycubes(int cubeNum, Polycube inputPolyCube) {
        Set<Cube> filterCubes = new HashSet<>(inputPolyCube.cubes);
        for (Cube cube : filterCubes) {
            for (int i = 1; i <= 6; i++) {
                Cube newCube = new Cube();
                switch (i) {
                    case 1: newCube.setCoordinates(cube.x + 1, cube.y, cube.z); break;
                    case 2: newCube.setCoordinates(cube.x - 1, cube.y, cube.z); break;
                    case 3: newCube.setCoordinates(cube.x, cube.y + 1, cube.z); break;
                    case 4: newCube.setCoordinates(cube.x, cube.y - 1, cube.z); break;
                    case 5: newCube.setCoordinates(cube.x, cube.y, cube.z + 1); break;
                    case 6: newCube.setCoordinates(cube.x, cube.y, cube.z - 1); break;
                }
                newCube.setID(newCube.x, newCube.y, newCube.z);

                if (!isCubeInList(newCube, filterCubes)) {
                    Polycube newPolyCube = new Polycube(inputPolyCube);
                    newPolyCube.addCube(newCube);
                    if (cubeNum == 1) {
                        this.polycubes.add(newPolyCube);
                    } else {
                        generatePolycubes(cubeNum - 1, newPolyCube);
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

    public boolean checkPolycube(Polycube pivot, Polycube testPolycube) {
        
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
            // if (pivot.isEqual(testPolycube)) {
            //     isEqual = true;
            //     break;
            // }
            Polycube pc = new Polycube(testPolycube);
            Cube cube = pc.calculateBoundingMinimumCube();
            pc.translatePolycube(cube);
            rotationPolycubes.add(pc);
        }
        return isEqual;
    }

    public void translatePolycubes() {
        for (Polycube polycube : this.polycubes) {
            Cube pivotCube = polycube.calculateBoundingMinimumCube();
            polycube.translatePolycube(pivotCube);
        }
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
