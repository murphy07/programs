
package mozi.backend.source;

import mozi.backend.dao.MovieDao;
import mozi.backend.dao.PerformanceDao;
import mozi.backend.dao.PlacesDao;
import mozi.backend.dao.RoomDao;


public class DataSource {
    /**
     * filmekhez tartozó adatbázis műveletek osztálya
     */
    private final MovieDao movieDao;
    /**
     * előadásokhoz tertozó adatbázis műveletek osztálya
     */
    private final PerformanceDao performanceDao;
    /**
     * helyekhez tartozó adatbázis műveletek osztálya
     */
    private final PlacesDao placesDao;
    /**
     * termekhez tartozó adatbázis műveletek osztálya
     */
    private final RoomDao roomDao;

    private DataSource() {
        this.movieDao = new MovieDao();
        this.performanceDao = new PerformanceDao();
        this.placesDao = new PlacesDao();
        this.roomDao = new RoomDao();
    }
    /**
     * 
     * @return visszaadja a nekünk szükséges dao objektumot
     */
    public static DataSource getInstance(){
        return DataSourceHolder.INSTANCE;
    }
    private static class DataSourceHolder{
        private static final DataSource INSTANCE = new DataSource();
    }

    public MovieDao getMovieDao() {
        return movieDao;
    }

    public PerformanceDao getPerformanceDao() {
        return performanceDao;
    }

    public PlacesDao getPlacesDao() {
        return placesDao;
    }

    public RoomDao getRoomDao() {
        return roomDao;
    }
    
   
}
