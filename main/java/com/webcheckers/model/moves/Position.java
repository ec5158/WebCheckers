package com.webcheckers.model.moves;

import java.util.Objects;

public class Position {
    private int row;
    private int cell;
    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public int getCell() {
        return cell;
    }

    public String toJson(){
        return "{\"row\":" + this.row + ",\"cell\":"+this.cell+"}";
    }

    public boolean isValid(){
        return this.row >= 0 && this.row < 8 && this.cell >= 0 && this.cell < 8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                cell == position.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, cell);
    }
}
