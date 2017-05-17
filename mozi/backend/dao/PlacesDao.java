
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
        
        try{
            RoomEntity room=DataSource.getInstance().getRoomDao()
                    .getEntityById(rs.getLong("ROOM_ID"));
            int n=rs.getInt("PLACES_ROW");
            if(n<=0 || n>room.getRow()){
                throw new SQLException("nincs ennyi sora a teremnek");
            }
            entity.setRow(rs.getInt("PLACES_ROW"));
        }catch(SQLException ex){
            throw new SQLException("nem talalhato a megadott ID-val RoomEntity");
        }
        entity.setColumn(rs.getInt("PLACES_COLUMN"));
        entity.setStatus(rs.getInt("PLACES_STATUS"));
    }
    
}
