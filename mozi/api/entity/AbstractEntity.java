package mozi.api.entity;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractEntity implements Identifiable<Long>,Serializable{
    protected Long id;
    
    @Override
    public void setID(Long ID){
        this.id=ID;
    }
    
    @Override
    public Long getID(){
        return this.id;
    }
    /**
     * Két absztrakt entitást azonosnak tekintünk amennyiben azonos a típusuk és az azonosítójuk
     * @param obj paraméterben kapott objektum amivel össze akarjuk hasonlítani az aktuális objektumunk
     * @return logikai érték, a megadott objektum azonos-e a paraméterben kapott objektummal
     */
    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(obj==null) return false;
        if(this.getClass()!=obj.getClass()) return false;
        Identifiable other=(Identifiable) obj;
        return this.getID().equals(other.getID());
    }
    /**
     * A hashCode metódusnak két megegyező objektum esetében egyforma hashCode-t kell visszaadnia
     * @return a generált hashCode 
     */
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
    
}
