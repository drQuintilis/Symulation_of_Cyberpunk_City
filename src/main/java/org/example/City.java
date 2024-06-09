package org.example;

import java.util.LinkedList;
import java.util.List;

public class City {
    public List<CitySquare> citySquareList;
    public CitySquare square;

    public City(){
        this.citySquareList = new LinkedList<>();
        this.square = new CitySquare(1,this);
    }

    public List getPath(){
        return citySquareList;
    }

}
