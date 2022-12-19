package com.webcheckers.model;

import java.util.*;

/**
 * The model for the rows in a checker game board.
 * @author Eric Chen
*/
public class Row {

    private int index;
    private ArrayList<Space> spaceList; //The list of all the spaces in the row

    /**
     * The constructor for the row of spaces.
     * Creates the row using the index of the row
     * and the spaces in that row
     * @param index the index or number of this row within the board
     * @param spaces an array of all the spaces in this row
     */
    public Row(int index, Space[] spaces){
        this.index = index;
        spaceList = new ArrayList<>();
        spaceList.addAll(Arrays.asList(spaces));
    }

    /**
     * Gets the index of this row
     * @return the index of the row
     */
    public int getIndex(){
        return index;
    }

    /**
     * Gets the space iterator
     * @return the space iterator
     */
    public ListIterator<Space> iterator(){
        return spaceList.listIterator();
    }
}
