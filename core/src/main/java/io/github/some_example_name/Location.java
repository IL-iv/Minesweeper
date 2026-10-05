package io.github.some_example_name;

public class Location {
    private int row;
    private int column;

    public Location(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    @Override
    public String toString() {
        return "Location{" +
            "row=" + row +
            ", column=" + column +
            '}';
    }

}
