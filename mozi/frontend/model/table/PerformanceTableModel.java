
package mozi.frontend.model.table;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import mozi.api.entity.MovieEntity;
import mozi.api.entity.PerformanceEntity;
import mozi.api.entity.RoomEntity;
import mozi.backend.source.DataSource;
import mozi.frontend.MainFrame;
import mozi.logic.PerformanceLogic;

/**
 * az előadások osztályt megjelenítő táblamodell
 */
public class PerformanceTableModel extends EntityTableModel<PerformanceEntity>{
    PerformanceLogic logic=new PerformanceLogic();
    public PerformanceTableModel(){
        super(PerformanceEntity.FIELD_NAMES, DataSource.getInstance().getPerformanceDao());        
    }
    /**
     * a sorindexű és oszlopindexű elemet tudjuk-e módosítani a JTable adattagon keresztül
     * @param rowIndex a paraméterben kapott soridex
     * @param columnIndex a paraméterben kapott oszlopindex
     * @return egyik oszlop sem módosítható
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    protected Object getAttributeOfEntity(PerformanceEntity entity, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return entity.getMovie();
            case 1:
                return entity.getRoom();
            case 2:
                return entity.getStartTime();
            case 3:
                return entity.getFreePlaces();
            default:
                return null;
    }
    }
    /**
     * metódus segítségével beállíthatjuk a paraméterben kapott entitás egy adattagját
     * @param columnIndex melyik oszlopot szeretnénk módosítani
     * @param entity melyik entitásnak
     * @param aValue milyen értékre szeretnénk módosítani 
     */
    @Override
    public void setEntityAttributes(int columnIndex, PerformanceEntity entity, Object aValue) {
        switch(columnIndex){
            case 0:
                entity.setMovie((MovieEntity) aValue);
                break;
            case 1:
                entity.setRoom((RoomEntity) aValue);
                break;
            case 2:
                try{
                DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.s");
                Date st=format.parse((String) aValue);
                Timestamp ts=new Timestamp(st.getTime());
                entity.setStartTime(ts);    
                }catch(Exception ex){
                    MainFrame.showError(ex.getMessage());
                }
                break;
            case 3:
                entity.setFreePlaces((Integer) aValue);
        }
    }
    /**
     * ha a táblához új értéket adunk ez a metódus fut le,
     * itt beállítjuk az új entitás default értékeit
     */
    @Override
    public void addNewEntity() {
        try{
            if(DataSource.getInstance().getMovieDao().getEntityCount() == 0){
                throw new SQLException("Nincs még film!");
            }

            if(DataSource.getInstance().getRoomDao().getEntityCount() == 0){
                throw new SQLException("Nincs még terem");
            }
            PerformanceEntity entity=new PerformanceEntity();            
            entity.setMovie(DataSource.getInstance().getMovieDao().getEntityByRowIndex(0));
            entity.setRoom(DataSource.getInstance().getRoomDao().getEntityByRowIndex(0));
            Timestamp date = new Timestamp(new java.util.Date().getTime());
            entity.setStartTime(date);
            addNewEntity(entity);
        }catch(SQLException ex){
            displayError(ex);
        }
    }
    /**
     * adott sor indexű entitást törli, azért definiáltuk felül
     * az ősosztálytól kapott deleEntity-t mert ha van foglalás az adott
     * előadásra, az előadás nem törölhető
     * @param rowIndex sorindex, mely elemet szeretnénk törölni
     */
    @Override
    public void deleteEntity(final int rowIndex){
        logic.deletePerformance(rowIndex);
        
    }
}
    