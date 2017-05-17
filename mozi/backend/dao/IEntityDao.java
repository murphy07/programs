
package mozi.backend.dao;

import java.sql.SQLException;
import java.util.List;
import mozi.api.entity.AbstractEntity;
/**
 * Az entitásokkal végezhető műveletekhez kapcsolódó közös felület.
 * @param <E> entitás típusa, melyet felülről az AbstractEntity típus korlátoz.
 */
public interface IEntityDao<E extends AbstractEntity> {
    /**
     * Metódus melynek a segítségével lekérdezhetjük, hogy hány sor található az adatbázisban, az adott entitásnak megfelelő táblában.
     * @return darabszám
     * @throws java.sql.SQLException
     */
    int getEntityCount() throws SQLException;
    /**
     * Metódus, amely visszaadja az adott táblához tartozó összes sort entitásként.
     * @return entitásokat tartalmazó lista
     * @throws java.sql.SQLException
     */
    List<E> getEntities() throws SQLException;
    /**
     * Metódus, amely segítségével visszaadhatjuk entitásként az azonosítóhoz tartozó sor az adatbázisból, amennyiben létezik.
     * @param id azonosító
     * @return adott azonosítóhoz tartozó entitást adja vissza
     * @throws java.sql.SQLException
     */
    E getEntityById(long id) throws SQLException;
    /**
     * Metódus, amely segítségével visszaadhatjuk entitásként az adott indexhez tartozó sor az adatbázisból, amennyiben létezik.
     * @param rowIndex sor index.
     * @return az adott sorszámhoz tartozó entittást adja vissza
     * @throws java.sql.SQLException
     */
    E getEntityByRowIndex(int rowIndex) throws SQLException;
    /**
     * Metódus, melynek segítségével új sort tudunk szúrni az entitásnak megfelelő táblába.
     * @param entity Entitás, amely tartalmazza az adatbázisba menteni kívánt értékeket.
     * @throws java.sql.SQLException
     */
    void addEntity(E entity) throws SQLException;
    /**
     * Metódus, melynek segítségével törölni tudunk egy sort az entitásnak megfelelő táblából.
     * @param index sor index
     * @throws java.sql.SQLException
     */
    void deleteEntity(int index) throws SQLException;
    /**
     * Metódus, melynek segítségével módosítani tudunk egy létező sort az adatbázisban.
     * @param entity Módosítani kívánt entitás, amely tartalmazza a módosított adatokat.
     * @param index Sor index, ahol a módosítani kívánt entitás elhelyezkedik.
     * @throws java.sql.SQLException
     */
     void updateEntity(E entity, int index) throws SQLException;
}
