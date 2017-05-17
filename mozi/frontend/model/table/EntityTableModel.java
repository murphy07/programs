
package mozi.frontend.model.table;

import java.sql.SQLException;
import mozi.api.entity.AbstractEntity;
import mozi.backend.dao.IEntityDao;
import mozi.frontend.MainFrame;


public abstract class EntityTableModel<E extends AbstractEntity> extends AbstractEntityTableModel<E>{
    public EntityTableModel(String[] columnNames, IEntityDao<E> dao){
        super(columnNames,dao);
    }
    @Override
    protected void displayError(SQLException sqlException) {
        MainFrame.showError(sqlException.getMessage());
    }
}
