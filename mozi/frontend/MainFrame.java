
package mozi.frontend;


import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import mozi.backend.connection.DBConnectionSource;
import mozi.frontend.model.table.AbstractEntityTableModel;
import mozi.frontend.model.table.MovieTableModel;
import mozi.frontend.model.table.PerformanceTableModel;
import mozi.frontend.model.table.PlacesTableModel;
import mozi.frontend.model.table.RoomTableModel;
import mozi.frontend.panel.PerformancePanel;
import mozi.frontend.panel.PlacesPanel;
import mozi.logic.PlacesLogic;

/**
 * a program indulásakor a MainFrame osztály indul el, ez adja felhasználói felületet
 * JFrame osztályt terjeszti ki
 */
public class MainFrame extends JFrame{
    private PlacesLogic logic;
    /**
     * JTable attribútom mely a filmek fülön jeleníti meg a filmeket
     */
    private final JTable movieTable;
    /**
     * JTable attribútom mely a termek fülön jeleníti meg a termeket
     */
    private final JTable roomTable;
    /**
     * JTable attribútom mely az előadások fülön jeleníti meg az előadásokat
     */
    private final JTable performanceTable;
    /**
     * JTable attribútom mely a helek fülön jeleníti meg a helyeket
     */
    private final JTable placesTable;
    /**
     * PlacesPanel osztály egy példánya mely a helyek megjelenítését végzi,
     * a helyekkel kapcsolatos műveleteket is ez az osztály végzi
     */
    private PlacesPanel placesPanel;
    /**
     * PerformancePanel osztály egy példánya mely az előadások megjelenítését végzi,
     * az előadásokkal kapcsolatos műveleteket is ez az osztály végzi
     */
    private PerformancePanel performancePanel;
    /**
     * az adattag segtségével tudjuk szűrni az előadásokat
     */
    private TableRowSorter<PerformanceTableModel> sorter;
    /**
     * ezek segítségével tudjuk beírni a szűrési feltételeket,
     * a filterTextel a film szerinti, a filterText2-vel a terem
     * szerinti szűrést tudjuk megvalósítani
     */
    private JTextField filterText;
    private JTextField filterText2;
    /**
     * a filmek tábla modelljét valósitja meg
     */
    private final MovieTableModel movieTableModel;
    /**
     * a termek tábla modelljét valósitja meg
     */
    private final RoomTableModel roomTableModel;
    /**
     * az előadások tábla modelljét valósitja meg
     */
    private final PerformanceTableModel performanceTableModel;
    /**
     * a helyek tábla modelljét valósitja meg
     */
    private final PlacesTableModel placesTableModel;
    /**
     * ezen adattag segítségével tudjuk megvalósítani, hogy tudjunk váltani
     * a tábla/panelek között
     */
    private final JTabbedPane tabbedPane;
    /**
     * az alkalmazásunk menüje
     */
    private final MoviesMenu moviesMenu;
    
    public MainFrame(){
        logic=new PlacesLogic();
        try{
            DBConnectionSource.getInstance().getConnection().close();
        }catch(SQLException ex){
            showError(ex.getMessage());
            System.exit(1);
        }
        applyNimbusLookAndFeelTheme();
        setFrameProperties();
        
        this.moviesMenu=new MoviesMenu();
        tabbedPane=new JTabbedPane();
        
        movieTableModel=new MovieTableModel();
        movieTable=new JTable(movieTableModel);
        movieTable.setAutoCreateRowSorter(true);
        movieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabbedPane.addTab("Movies", new JScrollPane(movieTable));
        
        roomTableModel=new RoomTableModel();
        roomTable=new JTable(roomTableModel);
        roomTable.setAutoCreateRowSorter(true);
        roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabbedPane.addTab("Rooms", new JScrollPane(roomTable));
        
        performancePanel=new PerformancePanel();
        performanceTableModel=new PerformanceTableModel();
        performanceTable=new JTable(performanceTableModel);
        sorter=new TableRowSorter<>(performanceTableModel);
        performanceTable.setRowSorter(sorter);
        filterText = new JTextField("filterText for movie");
        filterText2 = new JTextField("filerText for room");
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
        filterText2.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        newFilter2();
                    }
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        newFilter2();
                    }
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        newFilter2();
                    }
                });
        for(int i=0;i<3;i++){
            performanceTable.getColumnModel().getColumn(i).setPreferredWidth(200);
        }
        performancePanel.add(filterText, BorderLayout.PAGE_END);
        performancePanel.add(filterText2, BorderLayout.PAGE_END);
        performanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        performancePanel.add(performanceTable,BorderLayout.SOUTH);
        tabbedPane.addTab("Performances", performancePanel);
        
        placesPanel=new PlacesPanel();
        placesTableModel=new PlacesTableModel();
        placesTable=new JTable(placesTableModel);
        placesTable.setAutoCreateRowSorter(true);
        placesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        placesPanel.add(placesTable,BorderLayout.SOUTH);
        tabbedPane.addTab("Places", placesPanel);
        
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.addChangeListener(moviesMenu);

        setJMenuBar(moviesMenu);
        pack();
    }
    private void setFrameProperties(){
        setTitle("Multiplex mozi");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }
    private void applyNimbusLookAndFeelTheme(){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }
    }
    private void deleteRowsFromTable(JTable table, AbstractEntityTableModel tableModel) {
        int[] selectedRows = table.getSelectedRows();
        ArrayList<Integer> rowIndicesList = new ArrayList<>(selectedRows.length);
        for (int selectedRowIdx : selectedRows) {
            rowIndicesList.add(table.convertRowIndexToModel(selectedRowIdx));
        }
        Collections.sort(rowIndicesList);
        Collections.reverse(rowIndicesList);
        for (Integer rowIndex : rowIndicesList) {
            tableModel.deleteEntity(rowIndex);
        }
    }
    
    private final Action addMovieAction = new AbstractAction("Add new movie") {
        @Override
        public void actionPerformed(ActionEvent e) {
           movieTableModel.addNewEntity();
           movieTableModel.reloadEntities();
        }
    };
    
    private final Action deleteMovieAction = new AbstractAction("Delete selected Movie") {

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteRowsFromTable(movieTable, movieTableModel);
            movieTableModel.reloadEntities();
        }
    };
    
    private final Action addRoomAction = new AbstractAction("Add new room") {
        @Override
        public void actionPerformed(ActionEvent e) {
           roomTableModel.addNewEntity();
           roomTableModel.reloadEntities();
        }
    };
    
    private final Action deletePlacesAction = new AbstractAction("Delete selected places") {

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteRowsFromTable(placesTable, placesTableModel);
            placesTableModel.reloadEntities();
        }
    };
    
    private final Action addPlacesAction = new AbstractAction("Add new places") {
        @Override
        public void actionPerformed(ActionEvent e) {
            placesPanel.newEntity();
            placesTableModel.reloadEntities();
        }
    };
    
    private final Action finalizePlacesAction=new AbstractAction("Finalize places"){
        @Override
        public void actionPerformed(ActionEvent e){
            int rowIndex=placesTable.getSelectedRow();
            if(logic.checkPlaceForFinalize(rowIndex)){
                placesTable.setValueAt(1, rowIndex, 4);
                placesTableModel.reloadEntities();
            }
            else{
                MainFrame.showError("A jegy nem adható ki, már ki van adva!");
            }
        }
    };
    
    private final Action addPerformanceAction = new AbstractAction("Add new performance") {
        @Override
        public void actionPerformed(ActionEvent e) {
           performancePanel.newEntity();
           performanceTableModel.reloadEntities();
        }
    };
    
    private final Action deletePerformanceAction = new AbstractAction("Delete selected performance") {

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteRowsFromTable(performanceTable, performanceTableModel);
            performanceTableModel.reloadEntities();
        }
    };
    
    private final Action deleteRoomAction = new AbstractAction("Delete selected Room") {

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteRowsFromTable(roomTable, roomTableModel);
            roomTableModel.reloadEntities();
        }
    };
    /**
     * a metódus segítségével tudjuk megjeleníteni, ha a gelhasználó rossz adatokat vitt be
     * a program futása során
     * @param message a megjeleníteni kívánt üzenet
     */
    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private class MoviesMenu extends JMenuBar implements ChangeListener{
        
        private final JMenu movieMenu, roomMenu, performanceMenu,placesMenu;

        public MoviesMenu() {
            movieMenu=new JMenu("Movie");
            roomMenu=new JMenu("Room");
            performanceMenu=new JMenu("Performance");
            placesMenu=new JMenu("Places");
            
            add(movieMenu);
            
            movieMenu.add(addMovieAction);
            movieMenu.add(deleteMovieAction);
            roomMenu.add(addRoomAction);
            roomMenu.add(deleteRoomAction);
            performanceMenu.add(addPerformanceAction);
            performanceMenu.add(deletePerformanceAction);
            placesMenu.add(addPlacesAction);
            placesMenu.add(deletePlacesAction);
            placesMenu.add(finalizePlacesAction);
        }
        /**
         * a metódus feladata, hogy amelyik fülön vagyunk a tabbedPane-el
         * a hozzátartozó menü legyen megjelenítve
         * @param e 
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            removeAll();
            repaint();
            switch (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())) {
                case "Movies":
                    add(movieMenu);
                    break;
                case "Rooms":
                    add(roomMenu);
                    break;
                case "Performances":
                    add(performanceMenu);
                    break;
                case "Places":
                    add(placesMenu);
                    break;
            }
        }
    }
    /**
     * metódusok végzik a tényleges szűrését a filmeknek/termeknek az előadás fülön
     * a filtertext/filtertext2 megváltozásakor futnak le.
     */
    private void newFilter(){
        RowFilter<PerformanceTableModel,Object> rf=null;
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }
    private void newFilter2(){
        RowFilter<PerformanceTableModel,Object> rf=null;
        try {
            rf = RowFilter.regexFilter(filterText2.getText(), 1);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }
}
