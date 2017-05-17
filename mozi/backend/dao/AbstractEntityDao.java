
package mozi.backend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mozi.api.entity.AbstractEntity;
import mozi.backend.connection.DBConnectionSource;
import mozi.frontend.MainFrame;

public abstract class AbstractEntityDao<E extends AbstractEntity> implements IEntityDao<E>{
    private final String fullSelectSqlString;
    private final String selectCountSqlString;
    private final String selectByIdSqlString;
    
    AbstractEntityDao(String tableName) {
        fullSelectSqlString = "SELECT * FROM " + tableName;
        selectCountSqlString = "SELECT COUNT(*) AS CNT FROM " + tableName;
        selectByIdSqlString = fullSelectSqlString + " WHERE ID = ";
    }
    private void doOnResultSet(String sql, int resultSetType, int resultSetConcurrency, RunnableOnResultSet todo) throws SQLException {
        try (Connection connection = DBConnectionSource.getInstance().getConnection();
             Statement statement = connection.createStatement(resultSetType, resultSetConcurrency);
             ResultSet rs = statement.executeQuery(sql)) {
            todo.run(rs);
        }
    }
    protected interface RunnableOnResultSet{
    void run(ResultSet rs);
}
    @Override
    public int getEntityCount() throws SQLException {
        final IntegerHolder count = new IntegerHolder();
        doOnResultSet(selectCountSqlString, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
            try {
                rs.next();
                count.setIntValue(rs.getInt("CNT"));
            } catch (SQLException ex) {
                MainFrame.showError(ex.getMessage());
            }
        });
        return count.getIntValue();
    }
    @Override
    public List<E> getEntities() throws SQLException{
        final List<E> entities = new ArrayList<>();
        doOnResultSet(fullSelectSqlString,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
            try {
                while (rs.next()) {
                    E entity = newEntity();
                    entity.setID(rs.getLong("ID"));
                    setEntityAttributes(entity, rs);
                    entities.add(entity);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AbstractEntityDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return entities;
    }
    
    @Override
    public E getEntityById(long id) throws SQLException {
        final E entity = newEntity();
        doOnResultSet(selectByIdSqlString + id, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
            try {
                rs.next();
                entity.setID(rs.getLong("ID"));
                setEntityAttributes(entity, rs);
            } catch (SQLException ex) {
                MainFrame.showError(ex.getMessage());
            }
        });
        return entity;
    }
    
    @Override
    public E getEntityByRowIndex(final int rowIndex) throws SQLException {
        final E entity = newEntity();
        doOnResultSet(fullSelectSqlString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
            try {
                rs.absolute(rowIndex + 1);
                entity.setID(rs.getLong("ID"));
                setEntityAttributes(entity, rs);
            } catch (SQLException ex) {
                MainFrame.showError(ex.getMessage());
            }
        });
        return entity;
    }

    @Override
    public void addEntity(final E entity) throws SQLException {
        doOnResultSet(fullSelectSqlString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE, (ResultSet rs) -> {
            try {
                rs.moveToInsertRow();
                rs.updateLong("ID", DBConnectionSource.getInstance().obtainNewId());
                
                getEntityAttributes(rs, entity);
                rs.insertRow();
            } catch (SQLException ex) {
                MainFrame.showError(ex.getMessage());
            }
        });
    }

    @Override
    public void deleteEntity(final int index) throws SQLException {
        doOnResultSet(fullSelectSqlString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, (ResultSet rs) -> {
            try {
                rs.absolute(index + 1);
                rs.deleteRow();
            } catch (SQLException ex) {
                MainFrame.showError(ex.getMessage());
            }
        });
    }

    @Override
    public void updateEntity(final E entity, final int index) throws SQLException {
        doOnResultSet(fullSelectSqlString, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, (ResultSet rs) -> {
            try {
                rs.absolute(index + 1);
                rs.updateLong("ID", entity.getID().intValue());
                getEntityAttributes(rs, entity);
                rs.updateRow();
            } catch (SQLException ex) {
                MainFrame.showError(ex.getMessage());
            }
        });
    }
    
    
    private class IntegerHolder{
        private int intValue;

        int getIntValue() {
            return intValue;
        }

        void setIntValue(int intValue) {
            this.intValue = intValue;
        }
    }
    protected abstract E newEntity();
    protected abstract void getEntityAttributes(ResultSet rs, E entity) throws SQLException;
    protected abstract void setEntityAttributes( E entity, ResultSet rs) throws SQLException;
}
