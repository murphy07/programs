
package mozi.api.entity;

import java.util.Objects;

/**
 *
 * Adott előadáson egy helyet reprezentál, megfelel a PLACES_TABLE egy sorának
 */
public class PlacesEntity extends AbstractEntity{
    /**
     * a helyhez tartozó terem, megfelel a ROOM_ID feloldásának
     */
    private RoomEntity room;
    /**
     * a helyhez tartozó előadás, megfelel a PERFORMANCE_ID feloldásának
     */
    private PerformanceEntity performance;
    /**
     * a helyet egyértelműen egy sor és egy oszlop megadásával adhatunk meg,
     * megfelelnek a PLACES_ROW és PLACES_COLUMN attribútumoknak
     */
    private int row;
    private int column;
    /**
     * azt adja meg, hogy egy hely foglalt-e vagy kiadott-e
     * megfelel a PLACES_STATUS attribútumnak
     */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public PerformanceEntity getPerformance() {
        return performance;
    }

    public void setPerformance(PerformanceEntity performance) {
        this.performance = performance;
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

    @Override
    public int hashCode() {
        return Objects.hash(room, performance, row, column);
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
        final PlacesEntity other = (PlacesEntity) obj;
        if (this.row != other.row) {
            return false;
        }
        if (this.column != other.column) {
            return false;
        }
        if (!this.room.equals(other.room)) {
            return false;
        }
        if (!this.performance.equals(other.performance)) {
            return false;
        }
        return true;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    /**
     * oszlopok elnevezése a megjelenítéshez
     */
    public static final String[] FIELD_NAMES = {"Room", "Performance", "Row", "Column", "Status"};
}
