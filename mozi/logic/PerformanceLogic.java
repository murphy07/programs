/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mozi.logic;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import mozi.api.entity.MovieEntity;
import mozi.api.entity.PerformanceEntity;
import mozi.api.entity.PlacesEntity;
import mozi.backend.dao.PerformanceDao;
import mozi.backend.source.DataSource;
import mozi.frontend.MainFrame;


public class PerformanceLogic {
    protected PerformanceDao dao=new PerformanceDao();
    /**
     * a metódus segítségével tudunk új előadást hírdetni
     * @param entity a meghírdetni kívánt entitás
     */
    public void newPerformance(PerformanceEntity entity) {
        MovieEntity movie=entity.getMovie();
        if(checkAgeLimit(entity.getMovie(),entity.getStartTime())&&checkMaxPlay(entity.getMovie())
                        &&checkRoomIsFree(entity)&&checkLinearScreening(entity)){
                 incrementPlayNum(movie);
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
                        System.out.println(ex.getMessage());
            }
            }
        }.execute();
                }
                else{
                    MainFrame.showError("Az előadás nem hozható létre");
                }
        }
    /**
     * a metódus ellenőrzi a paraméterben megadott entitás korhatár-besorolását
     * @param entity a felvenni kívánt előadás film entitása
     * @param startTime az előadás kezdőidőpontja
     * @return vetíthető-e abban az időpontban vagy nem
     */
    public boolean checkAgeLimit(MovieEntity entity,Timestamp startTime){
            int hour=startTime.getHours();
            if(entity.getAgeLimit()<=3){
                return true;
            }
            if(entity.getAgeLimit()==4){
                return hour>=17;
            }
            if(entity.getAgeLimit()==5){
                return hour>=21;
            }
            return false;
        }
    /**
     * a metódus ellenőrzi, hogy a film vetíthető-e még
     * @param entity az ellenőrizni kívánt entitás
     * @return vetíthető-e
     */
        public boolean checkMaxPlay(MovieEntity entity){
            return entity.getMaxPlay()>entity.getPlaynum();
        }
        public void incrementPlayNum(MovieEntity entity){
            try{
            List<MovieEntity> entities=DataSource.getInstance().getMovieDao().getEntities();
            int i=0;
            boolean l=false;
            for(i=0;i<entities.size()&&!l;i++){
                l=entities.get(i).equals(entity);
            }
            int playNum=entity.getPlaynum();
            entity.setPlaynum(playNum+1);
            DataSource.getInstance().getMovieDao().updateEntity(entity, i-1);
        }catch(SQLException ex){
                MainFrame.showError(ex.getMessage());
        }
        }
        /**
         * a metódus ellenőrzi, hogy abban az időpontban mikor az előadást
         * meg szeretnénk hírdetni, a terem szebad-e
         * @param entity a meghírdetni kívánt előadás entitás
         * @return szabad-e a terem
         */
        public boolean checkRoomIsFree(PerformanceEntity entity){
            try{
            List<PerformanceEntity> sameRoomAndSameDay=new ArrayList<>();
            int year=entity.getStartTime().getYear();
            int month=entity.getStartTime().getMonth();
            int day=entity.getStartTime().getDay();
            List<PerformanceEntity> entities=DataSource.getInstance().getPerformanceDao().getEntities();
                for (PerformanceEntity entitie : entities) {
                    if(entitie.getRoom().equals(entity.getRoom())&&entitie.getStartTime().getYear()==year
                            &&entitie.getStartTime().getMonth()==month&&entitie.getStartTime()
                            .getDay()==day){
                        sameRoomAndSameDay.add(entitie);
                    }
                }
            if(sameRoomAndSameDay.isEmpty()){
                return true;
            }
            else{
                int startMinute=entity.getStartTime().getMinutes();
                int startHour=entity.getStartTime().getHours();
                int minuteSum=startMinute+entity.getMovie().getLength()+30;
                int endMinute;
                int endHour;
                int c=0;
                if(minuteSum>59){
                    c=minuteSum/60;
                    endMinute=minuteSum%60;
                    endHour=startHour+c;
                }
                else{
                    endMinute=minuteSum;
                    endHour=startHour;
                }
                boolean ll=true;
                for(PerformanceEntity entitie:sameRoomAndSameDay){
                    int startMinute2=entitie.getStartTime().getMinutes();
                    int startHour2=entitie.getStartTime().getHours();
                    int minuteSum2=startMinute2+entitie.getMovie().getLength()+30;
                    int endMinute2;
                    int endHour2;
                    int c2=0;
                    if(minuteSum>59){
                        c2=minuteSum2/60;
                        endMinute2=minuteSum2%60;
                        endHour2=startHour2+c2;
                    }
                    else{
                    endMinute2=minuteSum2;
                    endHour2=startHour2;
                    }
                    if((endHour2>startHour&&startHour2<endHour)||(endHour2==startHour&&endMinute2>startMinute)){
                        return false;
                    }    
                    if((startHour2<endHour&&endHour2>startHour)||(startHour2==endHour&&startMinute2<endMinute))
                        return false;
                    if(startHour==startHour2)
                        return false;
                    if((startHour2<startHour&&endHour<endHour2)||((startHour2==startHour&&startMinute2<
                        startMinute)&&(endHour<endHour2))||((startHour2==startHour&&startMinute2<startMinute2
                        )&&(endHour==endHour2&&endMinute<endMinute2))||(startHour2<endHour)&&(endHour==endHour2&&endMinute<endMinute2))
                            return false;
                    if((startHour<startHour2&&endHour2<endHour)||((startHour==startHour2&&startMinute2
                        >startMinute)&&(endHour2<endHour))||((startHour<startHour2)&&(endHour==endHour2)&&
                        (endMinute2<endMinute))||((startHour==startHour2&&startMinute2
                        >startMinute)&&(endHour==endHour2)&&
                        (endMinute2<endMinute)))
                            return false;
                }
                return true;
            }
            }catch(SQLException ex){
                MainFrame.showError(ex.getMessage());
            }
            return false;
        }
        /**
         * a metódus ellenőrzi, hogy a film hányszor fut párhuzamosan
         * @param entity az ellenőrizni kívánt film entitás
         * @return 3-nál kevesebbszer vetítik-e párhuzamosan
         */
        public boolean checkLinearScreening(PerformanceEntity entity){
            try{
            MovieEntity movie=entity.getMovie();
            List<PerformanceEntity> entities=DataSource.getInstance().getPerformanceDao().getEntities();
            int count=0;
            for(PerformanceEntity entiti:entities){
                if(entiti.getMovie().equals(movie)&&entiti.getStartTime().equals(entity.getStartTime()))
                    count=count+1;
            }
            if(count<=2){
                return true;
            }
            }catch(SQLException ex){
                MainFrame.showError(ex.getMessage());
            }
            return false;
            }
        /**
         * a metódus segítségével tudunk előadást törölni
         * @param rowIndex a törölni kívánt entitás sorindexe
         */
        public void deletePerformance(final int rowIndex){
            try{
        List<PlacesEntity> placesEntities=DataSource.getInstance().getPlacesDao().getEntities();
        PerformanceEntity entity=dao.getEntityByRowIndex(rowIndex);
        boolean l=false;
        for(int i=0;i<placesEntities.size()&&!l;++i){
            l=placesEntities.get(i).getPerformance().equals(entity);
        }
        if(!l){
            decrementMoviePlayNum(entity);
            new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() throws Exception{
                dao.deleteEntity(rowIndex);
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
            MainFrame.showError("Az előadás nem törölhető, van rá foglalás");
        }
            }catch(SQLException ex){
            MainFrame.showError(ex.getMessage());
        }
}
        /**
         * a mozi lejátszási számát tudjuk vele csökkenteni
         * @param entity az előadás filmje
         */
    public void decrementMoviePlayNum(PerformanceEntity entity) {
        try{
            List<MovieEntity> entitis=DataSource.getInstance().getMovieDao().getEntities();
            MovieEntity movie=entity.getMovie();
            boolean l=false;
            int i=0;
            for(i=0;i<entitis.size()&&!l;++i){
                l=entitis.get(i).equals(movie);
            }
            int playNum=movie.getPlaynum();
            movie.setPlaynum(playNum-1);
            DataSource.getInstance().getMovieDao().updateEntity(movie, i-1);
        }catch(SQLException ex){
            MainFrame.showError(ex.getMessage());
        }
    }
} 

    
    

