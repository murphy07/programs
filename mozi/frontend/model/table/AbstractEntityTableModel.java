package mozi.frontend.model.table;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import mozi.api.entity.AbstractEntity;
import mozi.backend.dao.IEntityDao;

/**
 * az osztály abstract leírást ad az egyes entitások táblanézetéhez,
 * és tartalmazza az alapvető műveleteket hozzá
 * @param <E> az osztályt felülről az AbstractEntity osztály korlátozza
 */
public abstract class AbstractEntityTableModel<E extends AbstractEntity> extends AbstractTableModel {
    protected final String[] columnNames;
    protected final int refreshInterval;
    protected final Timer timer;
    protected List<E> entities;
    protected IEntityDao<E> dao;
    
    public AbstractEntityTableModel(String[] columnNames, IEntityDao<E> dao){
        this.dao=dao;
        this.columnNames=columnNames;
        this.refreshInterval=15*1000;
        entities=new ArrayList<>();
        timer = new Timer(refreshInterval, (ActionEvent e) -> reloadEntities());
        reloadEntities();
        timer.start();
        
    }
    
    protected E getEntityAtRow(int rowIndex){
        return entities.get(rowIndex);
    }
    
    @Override
    public int getRowCount(){
        return entities.size();
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    /**
     * metódus a tábla adott sorához tartozó entitás, adott oszlopában
     * szereplő értéket adja vissza
     * @param rowIndex a tábla sorindexe
     * @param columnIndex a tábla oszlopindexe
     * @return a tábla adott sorában és adott oszlopában szereplő érték
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        E entity = getEntityAtRow(rowIndex);
        return getAttributeOfEntity(entity, columnIndex);
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    /**
     * frissíti az entitásokat a táblában, hogy mindig az aktuális állapotot lássuk
     */
    public void reloadEntities() {
        new SwingWorker<List<E>,Void>(){
           @Override
           protected List<E> doInBackground() throws Exception{
               return dao.getEntities();
           }
           @Override
           protected void done(){
               try{
                   entities=get();
                   fireTableDataChanged();
               }catch (InterruptedException | ExecutionException ex) {
                    displayError((SQLException) ex.getCause());
                }
           }
        }.execute();
    }
    /**
     * törli a paraméterben kapott sorindexű entitást
     * @param rowIndex a tábla sorindexe 
     */
    public void deleteEntity(final int rowIndex){
        new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() throws Exception{
                dao.deleteEntity(rowIndex);
                return null;
            }
            @Override
            protected void done(){
                try{
                    get();
                    reloadEntities();
                }catch(InterruptedException | ExecutionException ex) {
                    displayError((SQLException) ex.getCause());
            }
        }
    }.execute();
}
    protected void addNewEntity(final E entity){
        new SwingWorker<Void,Void>() {
            @Override
            protected Void doInBackground() throws Exception{
                dao.addEntity(entity);
                return null;
            }
            @Override
            protected void done(){
                try{
                    get();
                    reloadEntities();
                }catch(InterruptedException | ExecutionException ex) {
                    displayError((SQLException) ex.getCause());
            }
            }
        }.execute();
    }
    /**
     * a metódus segítségével adott sor és oszopindexű elemet beállíthatunk egy új értékre
     * @param value beállítani kívánt értek
     * @param rowIndex sorindex, ahol az értéket be szeretnénk állítani
     * @param columnIndex oszlopindex, ahol az értéket be szeretnénk állítani
     */
    @Override
    public void setValueAt(Object value, final int rowIndex, int columnIndex){
        final E entity=getEntityAtRow(rowIndex);
        setEntityAttributes(columnIndex,entity,value);
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dao.updateEntity(entity, rowIndex);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    reloadEntities();
                } catch (InterruptedException | ExecutionException ex) {
                    displayError((SQLException) ex.getCause());
                }
            }

        }.execute();
    }
    
    protected abstract void displayError(SQLException sqlException);
    protected abstract Object getAttributeOfEntity(E entity, int columnIndex);
    public abstract void setEntityAttributes(int columnIndex, final E entity, Object aValue);
    public abstract void addNewEntity();

}




