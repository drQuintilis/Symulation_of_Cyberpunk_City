package org.example;

import org.example.implants.Implant;

import java.util.*;

public class City {

    private Random random;

    private List<CitySquare> citySquareList;

    private int[][][] optimalPaths;

    public City(int[][] linkageList){
        this(linkageList, new Random());
    }

    public City(int[][] linkageList, Random random){
        this.random = random;
        this.citySquareList = new LinkedList<>();
        for (int i = 0; i < linkageList.length; i++) {
            for (int j = 0; j < linkageList[i].length; j++) {
                linkageList[i][j]--;
            }
        }
        this.generateCity(linkageList);
        optimalPaths = new int[linkageList.length][linkageList.length][];
        for (int i = 0; i < linkageList.length; i++) {
            for (int j = 0; j < linkageList.length; j++) {
                optimalPaths[i][j] = findShortestPath(linkageList, i, j);
            }
        }
    }

    private static int[] findShortestPath(int[][] linkageList, int start, int end) {
        if (start == end) {
            return new int[0];
        }

        boolean[] visited = new boolean[linkageList.length];
        int[] previous = new int[linkageList.length];
        for (int i = 0; i < linkageList.length; i++) {
            previous[i] = -1;
        };

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbor : linkageList[current]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    previous[neighbor] = current;
                    queue.add(neighbor);

                    if (neighbor == end) {
                        return buildPath(previous, start, end);
                    }
                }
            }
        }

        return new int[0]; // Путь не найден
    }

    private static int[] buildPath(int[] previous, int start, int end) {
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = previous[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        int[] retVal = new int[path.size()-1];
        for (int i = 1; i < path.size(); i++) {
            retVal[i-1] = path.get(i);
        }
        return retVal;
    }

    private void generateCity(int[][] linkageList) {
        for (int i = 0; i < linkageList.length; i++) {
            citySquareList.add(new CitySquare(i, this, linkageList.length));
        }
        for (int i = 0; i < linkageList.length; i++) {
            CitySquare currentSqaure = citySquareList.get(i);
            for (int j = 0; j < linkageList[i].length; j++){
                currentSqaure.connectSquare(citySquareList.get(linkageList[i][j]));
            }
        }
    }

    public int[] getShortestPath(int start, int end) {
        return this.optimalPaths[start][end];
    }

    public void doTick(TickSteps step){
        for (CitySquare citySquare:
             this.citySquareList) {
            citySquare.doTick(step);
        }
    }

    public CitySquare[] getCitySquareList() {
        return citySquareList.toArray(new CitySquare[0]);
    }

    public CitySquare getRandomCitySqaureForAgent() {
        return citySquareList.get(random.nextInt(citySquareList.size()));
    }

    @Override
    public String toString() {
        StringBuilder arrayOfSquares = new StringBuilder();
        for(CitySquare square: this.citySquareList){
            if(square != null){
                arrayOfSquares.append(square);
            }
        }
        return "City{ \n"+arrayOfSquares+"}";
    }
}
