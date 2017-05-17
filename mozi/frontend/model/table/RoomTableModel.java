/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mozi.frontend.model.table;

import mozi.api.entity.RoomEntity;
import mozi.backend.source.DataSource;
import mozi.frontend.MainFrame;

/**
 * a termek osztály megjelenítéséhez tartozó model
 */
public class RoomTableModel extends EntityTableModel<RoomEntity>{
    public RoomTableModel(){
        super(RoomEntity.FIELD_NAMES,DataSource.getInstance().getRoomDao());
    }
    /**
     * a sorindexű és oszlopindexű elemet tudjuk-e módosítani a JTable adattagon keresztül
     * @param rowIndex a paraméterben kapott soridex
     * @param columnIndex a paraméterben kapott oszlopindex
     * @return minden oszlop módosítható
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
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
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return Integer.class;
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
    public void setEntityAttributes(int columnIndex, RoomEntity entity, Object aValue) {
        switch(columnIndex){
            case 0:
                entity.setName((String) aValue);
                break;
            case 1:
                if((Integer) aValue <= 0){
                    MainFrame.showError("Nem lehet negatív vagy nulla sora a teremnek!");
                    return;
                }
                entity.setRow((Integer) aValue);
                break;
            case 2:
                if((Integer) aValue <= 0){
                    MainFrame.showError("Nem lehet negatív vagy nulla oszlopa a teremnek!");
                    return;
                }
                entity.setColumn((Integer) aValue);
                break;
        }
    }
    @Override
    protected Object getAttributeOfEntity(RoomEntity entity, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return entity.getName();
            case 1:
                return entity.getRow();
            case 2:
                return entity.getColumn();
            default:
                return null;
        }
    }
    /**
     * ha a táblához új értéket adunk ez a metódus fut le,
     * itt beállítjuk az új entitás default értékeit
     */
    @Override
    public void addNewEntity(){
        RoomEntity entity=new RoomEntity();
        entity.setName("default name");
        entity.setRow(1);
        entity.setColumn(1);
        addNewEntity(entity);
    }
}
