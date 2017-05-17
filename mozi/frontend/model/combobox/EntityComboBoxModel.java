package mozi.frontend.model.combobox;

import java.sql.SQLException;
import mozi.api.entity.AbstractEntity;
import mozi.backend.dao.IEntityDao;
import mozi.frontend.MainFrame;

public class EntityComboBoxModel<E extends AbstractEntity> extends AbstractEntityComboBoxModel<E>{
    public EntityComboBoxModel(IEntityDao<E> dao) {
        super(dao);
    }

    @Override
    protected void displayError(SQLException sqlException) {
        MainFrame.showError(sqlException.getMessage());
    }
}