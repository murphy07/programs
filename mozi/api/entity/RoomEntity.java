
package mozi.api.entity;

import java.util.Objects;
/**
 * egy termet reprezentál, megfelel a ROOM_TABLE egy sorának
 */
public class RoomEntity extends AbstractEntity{
    /**
     * a terem neve, megfelel a ROOM_NAME attribútumnak
     */
    private String name;
    /**
     * a teremben található sorok száma, megfelel a ROOM_ROW attribútumnak
     */
    private int row;
    /**
     * a teremben található oszlopok száma, megfelel a ROOM_COLUMN attribútumnak
     */
    private int column;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public int hashCode() {
        return Objects.hash(name,row,column);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoomEntity other = (RoomEntity) obj;
        if (this.row != other.row) {
            return false;
        }
        if (this.column != other.column) {
            return false;
        }
        if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
    /**
     * A terem egy szöveges reprezentációja
     * @return a terem nevét adja vissza 
     */
    @Override
    public String toString(){
        return this.name;
    }
    /**
     * oszlopok elnevezése a megjelenítéshez
     */
    public static final String[] FIELD_NAMES = {"ROOM_NAME", "ROOM_ROW", "ROOM_COLUMN"};
}
