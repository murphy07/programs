/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mozi.logic;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import mozi.api.entity.MovieEntity;
import mozi.api.entity.PerformanceEntity;
import mozi.api.entity.PlacesEntity;
import mozi.backend.dao.PlacesDao;
import mozi.backend.source.DataSource;
import mozi.frontend.MainFrame;


public class PlacesLogic {
    PlacesDao dao=new PlacesDao();
    /**
     * A metódus ellenőrzi, hogy a foglalás helyes-e
     * @param entity az adatbázisba felvenni kívánt entitás
     */
    public void newPlaces(PlacesEntity entity){
        
        if(check(entity)){
        decrementFreePlaces(entity.getPerformance());
        new SwingWorker<Void,Void>() {
            @Override
            protected Void doInBackground() throws Exception{
                dao.addEntity(entity);
                return null;
            }
            @Override
            protected void done(){
                try{
                    get();
                    
                }catch(InterruptedException | ExecutionException ex) {
                  MainFrame.showError(ex.getMessage());
            }
            }
        }.execute();
    }
        else{
            MainFrame.showError("A hely már foglalt!");
        }
    }
    /**
     * a metódus ellenőrzi, hogy a paraméterben megadott entitás létezik-e már
     * (a megadott hely foglalt-e)
     * @param entity a foglalni kívánt hely
     * @return foglalt-e vagy sem
     */
    public boolean check(PlacesEntity entity){
        if(entity.getRow()<=0||entity.getRow()>entity.getRoom().getRow()||entity.getColumn()<=0||entity.getColumn()>entity.getRoom().getColumn()){
            MainFrame.showError("Megadott sor és/vagy oszlop nem megfelelő!");
            return false;
        }
        boolean l=true;
        try{
        List<PlacesEntity> Entities=dao.getEntities();
        for(int i=0;i<Entities.size()&&l;++i){
            l=!(entity.equals(Entities.get(i)));
        }
        }catch(SQLException ex){
            MainFrame.showError(ex.getMessage());
        }
        return l;
    }/**
     * a metódus ellenőrzi h az adott foglalás kiadható-e
     * @param rowIndex az adott entitás sorindexe
     * @return kiadható-e a jegy vagy nem
     */
    public boolean checkPlaceForFinalize(final int rowIndex){
        boolean l=false;
        try{
        PlacesEntity entity=dao.getEntityByRowIndex(rowIndex);
        MovieEntity movie=entity.getPerformance().getMovie();
        if(entity.getStatus()==0){
            l=true;
            incrementSoldTicketCount(movie);
            return l;
        }
        }catch(SQLException ex){
            MainFrame.showError(ex.getMessage());
        }
        return l;
    }
    private void incrementSoldTicketCount(MovieEntity movie){
        try{
            List<MovieEntity> entities=DataSource.getInstance().getMovieDao().getEntities();
            int i=0;
            boolean l=false;
            for(i=0;i<entities.size()&&!l;++i){
                l=entities.get(i).equals(movie);
            }
            int ticketSold=movie.getCountTickets();
            movie.setCountTickets(ticketSold+1);
            DataSource.getInstance().getMovieDao().updateEntity(movie, i-1);
        }catch(SQLException ex){
            MainFrame.showError(ex.getMessage());
        }
    }

    private void decrementFreePlaces(PerformanceEntity performance) {
        try{
            List<PerformanceEntity> entities=DataSource.getInstance().getPerformanceDao().getEntities();
            int i=0;
            boolean l=false;
            for(i=0;i<entities.size()&&!l;++i){
                l=entities.get(i).equals(performance);
            }
            int freePlaces=performance.getFreePlaces();
            performance.setFreePlaces(freePlaces-1);
            DataSource.getInstance().getPerformanceDao().updateEntity(performance, i-1);
        }catch(SQLException ex){
            MainFrame.showError(ex.getMessage());
        }
    }
    /**
     * a metódus ellenőrzi hogy a paraméterben kapott sorindexű elem törölhető-e az adatbázisből
     * @param rowIndex elemnek a sorindexe melyet törölni szeretnénk
     */
    public void deletePlaces(final int rowIndex){
        try{
            PlacesEntity entity=dao.getEntityByRowIndex(rowIndex);
            PerformanceEntity performance=entity.getPerformance();
            
            if(entity.getStatus()==0){
            incrementFreePlaces(performance);    
            new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() throws Exception{
                dao.deleteEntity(rowIndex);
                get();
                return null;
            }
            @Override
            protected void done(){
                try{
                    get();
                }catch(InterruptedException | ExecutionException ex) {
                    MainFrame.showError(ex.getMessage());
                }
            }
            }.execute();
            }
            else{
                MainFrame.showError("Kiadott jegy nem törölhető!");
            }
            }catch(SQLException ex){
            MainFrame.showError(ex.getMessage());
        }
        
        
}
    private void incrementFreePlaces(PerformanceEntity performance) {
        try{
            List<PerformanceEntity> entites=DataSource.getInstance().getPerformanceDao().getEntities();
            int i=0;
            boolean l=false;
            for(i=0;i<entites.size()&&!l;++i){
                l=entites.get(i).equals(performance);
            }
            int freePlaces=performance.getFreePlaces();
            performance.setFreePlaces(freePlaces+1);
            DataSource.getInstance().getPerformanceDao().updateEntity(performance, i-1);
        }catch(SQLException ex){
            MainFrame.showError(ex.getMessage());
        }
    }
    }

