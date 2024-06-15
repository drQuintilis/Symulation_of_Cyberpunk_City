package org.example;

import org.example.implants.Implant;

import java.util.*;

public class City {

    private Random random;

    private List<CitySquare> citySquareList;

    private int[][][] optimalPaths;


    /**
     * konstruktor 1 klasy City
     * <p>
     * @param linkageList list zawierający wierzchołki grafu
     * @param defaultThroughput przepustowość agentów do innych dzielnic
     */
    public City(int[][] linkageList, int defaultThroughput){ // konstruktor #1
        this(linkageList, new Random(), defaultThroughput);
    }

    /**
     * konstruktor 2 klasy City
     * <p>
     * @param linkageList list zawierający wierzchołki grafu
     * @param random dla wyboru randomowych agentów dla przenoszenia się do innej dzielnicy
     * @param defaultThroughput przepustowość agentów do innych dzielnic
     */
    public City(int[][] linkageList, Random random, int defaultThroughput){ // konstruktor #2
        this.random = random;
        this.citySquareList = new LinkedList<>();
        for (int i = 0; i < linkageList.length; i++) { // przekształcenie indeksów w linkageList na indeksację od 0
            for (int j = 0; j < linkageList[i].length; j++) {
                linkageList[i][j]--;
            }
        }
        this.generateCity(linkageList, defaultThroughput);
        optimalPaths = new int[linkageList.length][linkageList.length][];  // inicjalizacja tablicy do przechowywania najkrótszych ścieżek
        for (int i = 0; i < linkageList.length; i++) {  // obliczanie najkrótszych ścieżek dla wszystkich par wierzchołków
            for (int j = 0; j < linkageList.length; j++) {
                optimalPaths[i][j] = findShortestPath(linkageList, i, j); // wywołanie metody do znalezienia najkrótszej ścieżki między wierzchołkami i i j
            }
        }
    }

    /**
     * Funkcja findShortestPath zawiera tablicę do śledzenia odwiedzonych wierzchołków
     * i tablicę do przechowywania poprzednich wierzchołków w ścieżce.
     * Korzysta z metody poszukiwania wszerz, czyli dodanie punktu początkowego do kolejki i oznaczenie go jako odwiedzonego,
     * przejście przez wszystkich sąsiadów bieżącego wierzchołka oraz zaznaczenie wierzchołków jako odwiedzonych
     * i zapisywanie bieżącego wierzchołka jako poprzednika sąsiada. W razie gdy sąsiad jest wierzchołkiem końcowym,
     * to funkcja zwraca najkrótszą ścieżkę z danymi parametrami.
     * <p>
     * @param linkageList list zawierający wierzchołki grafu
     * @param start punkt początkowy
     * @param end punkt końcowy
     * @return zwracamy funkcje, która buduje i zwraca najkrótszą ścieżkę z danymi parametrami
     */
    private static int[] findShortestPath(int[][] linkageList, int start, int end) {
        if (start == end) { // jeśli punkt początkowy i końcowy są takie same, to zwraca pustą tablicę
            return new int[0];
        }

        boolean[] visited = new boolean[linkageList.length]; // tablica do śledzenia odwiedzonych wierzchołków
        int[] previous = new int[linkageList.length];   // tablica do przechowywania poprzednich wierzchołków w ścieżce
        for (int i = 0; i < linkageList.length; i++) {  // inicjalizowanie tablicy previous wartościami -1, bo odległość nie może być równa - 1, a to znaczy, że ta ścieżka jeszcze nie była znaleziona
            previous[i] = -1;
        };

        Queue<Integer> queue = new LinkedList<>(); // kolejka do BFS (przeszukiwanie wszerz)
        queue.add(start); // dodanie punktu początkowego do kolejki i oznaczenie go jako odwiedzonego
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll(); // pobranie i usunięcie pierwszego elementu z kolejki, bo już na nim "stoimy" i znamy go

            for (int neighbor : linkageList[current]) { // przejście przez wszystkich sąsiadów bieżącego wierzchołka
                if (!visited[neighbor]) { // jeśli sąsiad nie został odwiedzony
                    visited[neighbor] = true; // zaznaczamy go jako odwiedzony
                    previous[neighbor] = current; // zapisujemy bieżący wierzchołek jako poprzednik sąsiada
                    queue.add(neighbor); // dodajemy sąsiada do kolejki

                    if (neighbor == end) { // jeśli sąsiad jest wierzchołkiem końcowym, budujemy i zwracamy ścieżkę
                        return buildPath(previous, start, end);
                    }
                }
            }
        }

        return new int[0]; // ścieżka nie była znaleziona
    }

    /**
     * Funkcja buildPath buduje ścieżkę od końca do początku, idąc wstecz od wierzchołka końcowego
     * do wierzchołka początkowego oraz tworzy tablicę wynikową o jeden mniejszą od rozmiaru listy ścieżki
     * (pomijając punkt początkowy).
     *
     * @param previous tablica do przechowywania poprzednich wierzchołków w ścieżce
     * @param start punkt początkowy
     * @param end punkt końcowy
     * @return tablica reprezentująca ścieżkę od punktu początkowego do końcowego
     */
    private static int[] buildPath(int[] previous, int start, int end) {
        List<Integer> path = new ArrayList<>(); // tworzymy listę do przechowywania ścieżki
        for (int at = end; at != -1; at = previous[at]) { // budujemy ścieżkę od końca do początku, idąc wstecz od wierzchołka końcowego do wierzchołka początkowego
            path.add(at);
        }
        Collections.reverse(path); // odwracamy ścieżkę, aby była od początku do końca
        int[] retVal = new int[path.size()-1]; // tworzymy tablicę wynikową o jeden mniejszą od rozmiaru listy ścieżki (pomijamy punkt początkowy)
        for (int i = 1; i < path.size(); i++) { // przepisujemy elementy z listy do tablicy, znowu pomijając pierwszy element (punkt początkowy)
            retVal[i-1] = path.get(i);
        }
        return retVal; // tablica reprezentująca ścieżkę od punktu początkowego do końcowego
    }

    /**
     * Funkcja generateCity tworzy nowy CitySquare dla każdego wierzchołka i dodaje go do listy citySquareList
     * oraz łączy bieżący wierzchołek z sąsiadującymi wierzchołkami.
     *
     * @param linkageList list zawierający wierzchołki grafu
     * @param defaultThroughput przepustowość agentów do innych dzielnic
     */
    private void generateCity(int[][] linkageList, int defaultThroughput) {
        for (int i = 0; i < linkageList.length; i++) {  // iteracja przez wszystkie wierzchołki (dzielnicy) w linkageList
            citySquareList.add(new CitySquare(i, linkageList.length, defaultThroughput));  // tworzenie nowego CitySquare dla każdego wierzchołka i dodawanie go do listy citySquareList
        }
        for (int i = 0; i < linkageList.length; i++) {
            CitySquare currentSqaure = citySquareList.get(i);  // pobieranie bieżącego wierzchołka z listy citySquareList
            for (int j = 0; j < linkageList[i].length; j++){ // iteracja przez wszystkie połączenia bieżącego wierzchołka
                currentSqaure.connectSquare(citySquareList.get(linkageList[i][j])); // łączenie bieżącego wierzchołka z sąsiadującymi wierzchołkami
            }
        }
    }

    /**
     * Getter zwracający tablice najkrótszej ścieżki
     *
     * @param start punkt początkowy
     * @param end punkt końcowy
     * @return tablica najkrótszej ścieżki
     */
    // gettery
    public int[] getShortestPath(int start, int end) {
        return this.optimalPaths[start][end];
    } // dostajemy tablice najkrótszej ścieżki

    /**
     * Funkcja doTick wykonywa krok symulacji
     *
     * @param step bieżący krok wypełnienia działań w ciągu jednego ticku
     */
    public void doTick(TickSteps step){
        for (CitySquare citySquare:
             this.citySquareList) {
            citySquare.doTick(step);
        }
    }

    /**
     * @return getter dla zwracania listy dzielnic miasta
     */
    public CitySquare[] getCitySquareList() {
        return citySquareList.toArray(new CitySquare[0]);
    }

    /**
     * @return getter dla zwracania randomowej dzielnicy, żeby tam utworzyć agenta
     */
    public CitySquare getRandomCitySqaureForAgent() { // otrzymujemy randomową dzielnice, żeby tam utworzyć agenta
        return citySquareList.get(random.nextInt(citySquareList.size()));
    }

    /**
     * @return wyświetlanie informacji tekstowych
     */
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
