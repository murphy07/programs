
package mozi.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import mozi.api.entity.PerformanceEntity;
import mozi.api.entity.PlacesEntity;
import mozi.api.entity.RoomEntity;
import mozi.backend.source.DataSource;

public class PlacesDao extends AbstractEntityDao<PlacesEntity>{
    
    public PlacesDao(){
        super("PLACES_TABLE");
    }

    @Override
    protected PlacesEntity newEntity() {
        return new PlacesEntity();
    }

    @Override
    protected void getEntityAttributes(ResultSet rs, PlacesEntity entity) throws SQLException {
       rs.updateLong("ROOM_ID", entity.getRoom().getID());
       rs.updateLong("PERFORMANCE_ID", entity.getPerformance().getID());
       rs.updateInt("PLACES_ROW", entity.getRow());
       rs.updateInt("PLACES_COLUMN", entity.getColumn());
       rs.updateInt("PLACES_STATUS", entity.getStatus());
    }

    @Override
    protected void setEntityAttributes(PlacesEntity entity, ResultSet rs) throws SQLException {
        try{
            PerformanceEntity performance= DataSource.getInstance().getPerformanceDao()
                    .getEntityById(rs.getLong("PERFORMANCE_ID"));
            entity.setPerformance(performance);
        }catch(SQLException ex){
           throw new SQLException("Nem talalhato a megadott ID-val PerformanceEntity!", ex);        
        }
        
        try{
            RoomEntity room= DataSource.getInstance().getPerformanceDao()
                    .getEntityById(rs.getLong("PERFORMANCE_ID")).getRoom();
            entity.setRoom(room);
        }catch(SQLException ex){
           throw new SQLException("Nem talalhato a megadott ID-val RoomEntity!", ex);        
        }
            
        entity.setColumn(rs.getInt("PLACES_COLUMN"));
        entity.setStatus(rs.getInt("PLACES_STATUS"));
    
}
