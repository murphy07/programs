
package mozi.frontend.model.table;

import mozi.api.entity.MovieEntity;
import mozi.backend.source.DataSource;

/**
 * a filmek osztály megjelenítéséhez tartozó táblamodel
 */
public class MovieTableModel extends EntityTableModel<MovieEntity>{

    public MovieTableModel() {
        super(MovieEntity.FIELD_NAMES, DataSource.getInstance().getMovieDao());
    }
    /**
     * a sorindexű és oszlopindexű elemet tudjuk-e módosítani a JTable adattagon keresztül
     * @param rowIndex a paraméterben kapott soridex
     * @param columnIndex a paraméterben kapott oszlopindex
     * @return minden oszlop módosítható kivéve a 8. oszlop
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 8;
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
                return String.class;
            case 2:
                return Boolean.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return Integer.class;
            case 6:
                return Integer.class;
            case 7:
                return Integer.class;
            case 8:
                return Integer.class;
            case 9:
                return Integer.class;
            default:
                return null;
        }
    }
    
    @Override
    protected Object getAttributeOfEntity(MovieEntity entity, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return entity.getTitle();
            case 1:
                return entity.getFrom();
            case 2:
                return entity.getIsDubbed();
            case 3:
                return entity.getDirector();
            case 4:
                return entity.getSynopsis();
            case 5:
                return entity.getLength();
            case 6:
                return entity.getMaxPlay();
            case 7:
                return entity.getAgeLimit();
            case 8:
                return entity.getCountTickets();
            case 9:
                return entity.getPlaynum();
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
    public void setEntityAttributes(int columnIndex, MovieEntity entity, Object aValue) {
        switch(columnIndex){
            case 0:
                entity.setTitle((String) aValue);
                break;
            case 1:
                entity.setFrom((String) aValue);
                break;
            case 2:
                entity.setIsDubbed((Boolean) aValue);
                break;
            case 3:
                entity.setDirector((String) aValue);
                break;
            case 4:
                entity.setSynopsis((String) aValue);
                break;
            case 5:
                entity.setLength((Integer) aValue);
                break;
            case 6:
                entity.setMaxPlay((Integer) aValue);
                break;
            case 7:
                entity.setAgeLimit((Integer) aValue);
                break;
            case 8:
                entity.setCountTickets((Integer) aValue);
                break;
            case 9:
                entity.setPlaynum((Integer) aValue);
                break;
        }
    }
    /**
     * ha a táblához új értéket adunk ez a metódus fut le,
     * itt beállítjuk az új entitás default értékeit
     */
    @Override
    public void addNewEntity() {
        MovieEntity entity=new MovieEntity();
        entity.setTitle("new movie");
        entity.setFrom("-");
        entity.setIsDubbed(false);
        entity.setDirector("-");
        entity.setSynopsis("-");
        entity.setLength(10);
        entity.setMaxPlay(999);
        entity.setAgeLimit(1);
        entity.setCountTickets(0);
        entity.setPlaynum(0);
        addNewEntity(entity);
    }
}
