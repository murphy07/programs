
package mozi.frontend.model.combobox;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import mozi.api.entity.AbstractEntity;
import mozi.backend.dao.IEntityDao;

/**
 * Ez az osztály a comboboxok modelljét valósítja meg, DefaultComboBoxModel osztályt terjeszti ki
 * @param <E> felülről az abstractEntity osztály korlátozza
 */
public abstract class AbstractEntityComboBoxModel<E extends AbstractEntity> extends DefaultComboBoxModel<E> {
    /**
     * entitásokat tartalmazó lista
     */
    private List<E> entities;
    /**
     * Időintervallum, amilyen időközönként lefut a comboboxok frissítése
     */
    private final int refreshInterval;
    /**
     * timer objektum mely számolja az időt
     */
    private final Timer timer;
    /**
     * entitásokhoz tartozó dao objektum, minden entitáshoz az ő osztáylának
     * dao objektuma lesz
     */
    private final IEntityDao<E> dao;
    public AbstractEntityComboBoxModel(IEntityDao<E> dao) {
        this.dao = dao;
        entities = new ArrayList<>();
        this.refreshInterval = 10 * 1000;
        this.timer = new Timer(refreshInterval, (ActionEvent e) -> {
            refreshEntities();
        });
        timer.start();
        refreshEntities();
    }
    @Override
    public int getSize() {
        return entities.size();
    }
    /**
     * visszaadja az entities listából az indexedik elemet
     * @param index hányadik indexű objektumot szeretnénk visszaadni
     * @return entitásokat tartalmazó lista indexedik eleme
     */
    @Override
    public E getElementAt(int index) {
        return entities.get(index);
    }
    
    private void refreshEntities() {
        new SwingWorker<List<E>, Void>() {

            @Override
            protected List<E> doInBackground() throws Exception {
                List<E> entities = new ArrayList<>();
                int entityCount = dao.getEntityCount();
                for (int rowIndex = 0; rowIndex < entityCount; rowIndex++) {
                    entities.add(dao.getEntityByRowIndex(rowIndex));
                }
                return entities;
            }

            @Override
            protected void done() {
                try {
                    entities = get();
                    fireContentsChanged(this, 0, getSize());
                } catch (InterruptedException | ExecutionException ex) {
                    displayError((SQLException) ex.getCause());
                }
            }
        }.execute();
    }
    protected abstract void displayError(SQLException sqlException);
}
