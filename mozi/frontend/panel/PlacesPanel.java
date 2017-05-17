package mozi.frontend.panel;

import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mozi.api.entity.PerformanceEntity;
import mozi.api.entity.PlacesEntity;
import mozi.api.entity.RoomEntity;
import mozi.backend.dao.PlacesDao;
import mozi.backend.source.DataSource;
import mozi.frontend.model.combobox.EntityComboBoxModel;
import mozi.logic.PlacesLogic;

/**
 * OSztály segítségével jelenítjük meg a helyeket
 */
public class PlacesPanel extends JPanel{
    protected PlacesDao dao=new PlacesDao();
    PlacesLogic logic=new PlacesLogic();
    /**
     * textfieldek a sor és oszlop megadásahoz, ahova a helyet foglalni szeretnénk
     */
    JTextField t1;
    JTextField t2;
    /**
     * combobox az előadás kiválasztásához
     */
    JComboBox combo2;
    public PlacesPanel(){
        t1=new JTextField("column number");
        add(t1, BorderLayout.NORTH);
        t2=new JTextField("row number");
        add(t2, BorderLayout.NORTH);
        combo2=new JComboBox<>(new EntityComboBoxModel<>(DataSource.getInstance().getPerformanceDao()));
        add(combo2);
    }
    /**
         * metódus segítségével tudunk új entitást felvenni
         * ez a metódus ellenőrzi, hogy minden feltételnek megfelel-e a létrehozni
         * kívánt foglalás
         */
    public void newEntity(){
        String columnString=t1.getText();
        String rowString=t2.getText();
        int column=Integer.parseInt(columnString);
        int row=Integer.parseInt(rowString);
        PerformanceEntity performance=(PerformanceEntity) combo2.getSelectedItem();
        RoomEntity room=performance.getRoom();
        PlacesEntity entity=new PlacesEntity();
        entity.setPerformance(performance);
        entity.setRoom(room);
        entity.setColumn(column);
        entity.setRow(row);
        logic.newPlaces(entity);
    }
}
