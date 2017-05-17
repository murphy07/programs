/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mozi.frontend.model.table;

import java.sql.SQLException;
import mozi.api.entity.PerformanceEntity;
import mozi.api.entity.PlacesEntity;
import mozi.api.entity.RoomEntity;
import mozi.backend.source.DataSource;
import mozi.logic.PlacesLogic;

/**
 * a helyek osztály megjelenítéséhez tartozó táblamodell
 */
public class PlacesTableModel extends EntityTableModel<PlacesEntity>{
    PlacesLogic logic=new PlacesLogic();
    public PlacesTableModel(){
        super(PlacesEntity.FIELD_NAMES,DataSource.getInstance().getPlacesDao());
        
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
    protected Object getAttributeOfEntity(PlacesEntity entity, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return entity.getRoom();
            case 1:
                return entity.getPerformance();
            case 2:
                return entity.getRow();
            case 3:
                return entity.getColumn();
            case 4:
                return entity.getStatus();
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
    public void setEntityAttributes(int columnIndex, PlacesEntity entity, Object aValue) {
        switch(columnIndex){
            case 0:
                entity.setRoom((RoomEntity) aValue);
                break;
            case 1:
                entity.setPerformance((PerformanceEntity) aValue);
                break;
            case 2:
                entity.setRow((Integer) aValue);
                break;
            case 3:
                entity.setColumn((Integer) aValue);
                break;
            case 4:
                entity.setStatus((Integer) aValue);
                break;
        }
        
    }
    /**
     * ha a táblához új értéket adunk ez a metódus fut le,
     * itt beállítjuk az új entitás default értékeit
     */
    @Override
    public void addNewEntity(){
        try{
            if(DataSource.getInstance().getRoomDao().getEntityCount() == 0){
                throw new SQLException("There are no room yet!");
            }

            if(DataSource.getInstance().getPerformanceDao().getEntityCount() == 0){
                throw new SQLException("There are no performance yet!");
            }
            PlacesEntity entity=new PlacesEntity();
            
            entity.setPerformance(DataSource.getInstance().getPerformanceDao().getEntityByRowIndex(0));
            entity.setRoom(DataSource.getInstance().getPerformanceDao().getEntityByRowIndex(0).getRoom());
            entity.setRow(1);
            entity.setColumn(0);
            entity.setStatus(0);
            addNewEntity(entity);
        }catch(SQLException ex){
        displayError(ex);
        }
    }
    /**
     * a metódus segítségével megtdhatjuk,hogy adott oszlop elemei milyen osztályhoz tartoznak
     * @param columnIndex oszlop szám
     * @return milyen osztályhoz tartoznak az adott oszlopban szereplő elemek
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return RoomEntity.class;
            case 1:
                return PerformanceEntity.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
            case 4:
                return Integer.class;
            default:
                return null;
        }
    }
    /**
     * adott sor indexű entitást törli, azért definiáltuk felül
     * az ősosztálytól kapott deleEntity-t mert ha kiadták a jegyet(status=1)
     * a helyfoglalás nem törölhető
     * @param rowIndex sorindex, mely elemet szeretnénk törölni
     */
    @Override
    public void deleteEntity(final int rowIndex){
        logic.deletePlaces(rowIndex);
        
    }
    }
    

