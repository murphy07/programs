
package mozi.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import mozi.api.entity.MovieEntity;
import mozi.api.entity.PerformanceEntity;
import mozi.api.entity.RoomEntity;
import mozi.backend.source.DataSource;

/**
 *
 * @author murph
 */
public class PerformanceDao extends AbstractEntityDao<PerformanceEntity>{
    
    public PerformanceDao(){
        super("PERFORMANCE_TABLE");
    }
            
    @Override
    protected PerformanceEntity newEntity() {
        return new PerformanceEntity();
    }

    @Override
    protected void getEntityAttributes(ResultSet rs, PerformanceEntity entity) throws SQLException {
        rs.updateLong("MOVIE_ID", entity.getMovie().getID());
        rs.updateLong("ROOM_ID", entity.getRoom().getID());
        rs.updateTimestamp("PEFOMANCE_STARTTIME", entity.getStartTime());
        rs.updateInt("PERFORMANCE_FREEPLACES", entity.getFreePlaces());
    }

    @Override
    protected void setEntityAttributes(PerformanceEntity entity, ResultSet rs) throws SQLException {
        try{
            MovieEntity movie= DataSource.getInstance().getMovieDao()
                    .getEntityById(rs.getLong("MOVIE_ID"));
            entity.setMovie(movie);
        }catch(SQLException ex){
           throw new SQLException("Nem talalhato a megadott ID-val ArtistEntity!", ex);        
        }
        
        try{
            RoomEntity room= DataSource.getInstance().getRoomDao()
                    .getEntityById(rs.getLong("ROOM_ID"));
            entity.setRoom(room);
        }catch(SQLException ex){
           throw new SQLException("Nem talalhato a megadott ID-val RoomEntity!", ex);        
        }
        
        entity.setStartTime(rs.getTimestamp("PEFOMANCE_STARTTIME"));
        entity.setFreePlaces(rs.getInt("PERFORMANCE_FREEPLACES"));
    }
    
}
