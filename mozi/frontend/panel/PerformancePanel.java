
package mozi.frontend.panel;

import java.awt.BorderLayout;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mozi.api.entity.MovieEntity;
import mozi.api.entity.PerformanceEntity;
import mozi.api.entity.RoomEntity;
import mozi.backend.dao.PerformanceDao;
import mozi.backend.source.DataSource;
import mozi.frontend.MainFrame;
import mozi.frontend.model.combobox.EntityComboBoxModel;
import mozi.logic.PerformanceLogic;

/**
 * Ezen osztály segítségével jelenítjük meg az előadásokat
 * 
 */
public class PerformancePanel extends JPanel{
    protected PerformanceDao dao=new PerformanceDao();
    private PerformanceLogic logic=new PerformanceLogic();
    /**
     * textField amelynek segtségével beírhatjuk az előadás kezdő időpontját
     */
    JTextField t1;
    /**
     * comboboxok a terem és a film kiválasztásához
     */
    JComboBox combo1;
    JComboBox combo2;
        
        public PerformancePanel(){
            t1=new JTextField("START TIME FORM: XXXX-XX-XX HH:MM:SS.S");
            add(t1, BorderLayout.NORTH);
            combo1=new JComboBox<>(new EntityComboBoxModel<>(DataSource.getInstance().getRoomDao()));
            combo2=new JComboBox<>(new EntityComboBoxModel<>(DataSource.getInstance().getMovieDao()));
            add(combo1);
            add(combo2);
        }
        /**
         * metódus segítségével tudunk új entitást felvenni
         * ez a metódus ellenőrzi, hogy minden feltételnek megfelel-e a létrehozni
         * kívánt előadás
         */
        public void newEntity(){
            String time=t1.getText();
            try{
                DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.s");
                Date st=format.parse(time);
                Timestamp ts=new Timestamp(st.getTime());
                System.out.println(combo1.getItemAt(0));
                RoomEntity room=(RoomEntity) combo1.getSelectedItem();
                MovieEntity movie=(MovieEntity) combo2.getSelectedItem();
                PerformanceEntity entity=new PerformanceEntity();
                entity.setMovie(movie);
                entity.setRoom(room);
                entity.setStartTime(ts);
                entity.setFreePlaces(room.getColumn()*room.getRow());
                logic.newPerformance(entity);
            }catch(ParseException ex){
                MainFrame.showError(ex.getMessage());
            }
                
    }
}