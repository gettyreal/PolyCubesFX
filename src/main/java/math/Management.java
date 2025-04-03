package math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

//class to manage polycumbes and confront them
public class Management {
    public ArrayList<Polycube> polycubes = new ArrayList<Polycube>(); // list of all polycubes
    public int cubeNum;

    public Management() {
        Scanner sc = new Scanner(System.in);
        System.out.println("inserire numero di cubi: ");
        cubeNum = sc.nextInt();
        sc.close();
    }

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

                    if (cubeNum == 1) { // no more cubes to add
                        if (isPolycubeUnique(newPolyCube)) {
                            this.polycubes.add(newPolyCube);
                        }
                    } else {
                        generatePolycubes(cubeNum - 1, newPolyCube);
                    }
                }
            }
        }
    }

    public boolean isPolycubeUnique(Polycube polycube) {
        Polycube pivot = new Polycube(polycube);

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 4; z++) {

                    Cube minPivotCube = pivot.calculateBoundingMinimumCube();
                    pivot.translatePolycube(minPivotCube);

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
        return true;

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
