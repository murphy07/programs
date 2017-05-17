
package mozi.api.entity;

import java.sql.Timestamp;
import java.util.Objects;
/**
 * a PERFORMANCE_TABLE egy sorát reprezentálja
 */
public class PerformanceEntity extends AbstractEntity{
    /**
     * az előadás mozifilmje, mely a MOVIE_ID feloldásának felel meg
     */
    private MovieEntity movie;
    /**
     * az előadás terme, mely a ROOM_ID feloldásának felel meg
     */
    private RoomEntity room;
    /**
     * Az előadás kezdőidőpontja, mely a PEFOMANCE_STARTTIME attribútumnak felel meg
     */
    private Timestamp startTime;
    /**
     * az előadás kezdő időpontja, mely a PERFORMANCE_FREEPLACES attribútumnak felel meg
     */
    private int freePlaces;

    public int getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }
    
    public Timestamp getStartTime(){
        return this.startTime;
    }
    public void setStartTime(Timestamp startTime){
        this.startTime=startTime;
    }
    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }
    /**
     * az előadás szöveges reprezentációja
     * @return a film címe + a terem neve
     */
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append(this.movie.toString());
        sb.append(" ");
        sb.append(this.room.toString());
        return sb.toString();
    }

    

    @Override
    public int hashCode() {
        return Objects.hash(movie,room,startTime);
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
        final PerformanceEntity other = (PerformanceEntity) obj;
        if (!this.movie.equals(other.movie)) {
            return false;
        }
        if (!this.room.equals(other.room)) {
            return false;
        }
        if (!this.startTime.equals(other.startTime)) {
            return false;
        }
        return true;
    }
    /**
     * oszlopok elnevezése a megjelenítéshez
     */
    public static final String[] FIELD_NAMES={
        "Movie", "Room", "Start time","FREE_PLACES"
    };
}
