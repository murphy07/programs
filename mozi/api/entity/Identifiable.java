
package mozi.api.entity;

/**
 *
 * @param <T> Az azonosító típusa amit felülről a Number korlátoz
 */
public interface Identifiable<T extends Number> {
    /**
     * A metódus segítségével elkérhetjük az azonosítandó objektum azonosítóját
     * @return azonosító
     */
    T getID();
    /**
     * A metódus segítségével beállíthatjuk az aktuális objektom azonosítóját
     * @param param azonosító
     * 
     */
    void setID(T param);
    
}
