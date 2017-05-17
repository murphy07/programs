
package mozi.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import mozi.api.entity.RoomEntity;


public class RoomDao extends AbstractEntityDao<RoomEntity>{
    
    public RoomDao(){
        super("ROOM_TABLE");
    }

    @Override
    protected RoomEntity newEntity() {
        return new RoomEntity();
    }

    @Override
    protected void getEntityAttributes(ResultSet rs, RoomEntity entity) throws SQLException {
        rs.updateString("ROOM_NAME", entity.getName());
        rs.updateInt("ROOM_ROW", entity.getRow());
        rs.updateInt("ROOM_COLUMN", entity.getColumn());
    }

    @Override
    protected void setEntityAttributes(RoomEntity entity, ResultSet rs) throws SQLException {
        entity.setName(rs.getString("ROOM_NAME"));
        entity.setRow(rs.getInt("ROOM_ROW"));
        entity.setColumn(rs.getInt("ROOM_COLUMN"));
    }
    
}
