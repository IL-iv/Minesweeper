package io.github.some_example_name;

public class Location {
    private int row;
    private int column;

    public Location(int row, int column) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return row == location.row && column == location.column;
    }


    @Override
    public String toString() {
        return "Location{" +
            "row=" + row +
            ", column=" + column +
            '}';
    }

}
