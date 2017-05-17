
package mozi.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import mozi.api.entity.MovieEntity;


public class MovieDao extends AbstractEntityDao<MovieEntity> {

    public MovieDao(){
        super("MOVIE_TABLE");
    }
    
    @Override
    protected MovieEntity newEntity(){
        return new MovieEntity();
    }

    @Override
    protected void getEntityAttributes(ResultSet rs, MovieEntity entity) throws SQLException {
        rs.updateString("MOVIE_TITLE", entity.getTitle());
        rs.updateString("MOVIE_FROM", entity.getFrom());
        rs.updateBoolean("MOVIE_ISDUBBED", entity.getIsDubbed());
        rs.updateString("MOVIE_DIRECTOR", entity.getDirector());
        rs.updateString("MOVIE_SYNOPSIS", entity.getSynopsis());
        rs.updateInt("MOVIE_LENGTH", entity.getLength());
        rs.updateInt("MOVIE_MAXPLAY", entity.getMaxPlay());
        rs.updateInt("MOVIE_AGELIMIT", entity.getAgeLimit());
        rs.updateInt("MOVIE_COUNTTICKETS", entity.getCountTickets());
        rs.updateInt("MOVIE_PLAYNUMBER", entity.getPlaynum());
    }

    @Override
    protected void setEntityAttributes(MovieEntity entity, ResultSet rs) throws SQLException {
        entity.setTitle(rs.getString("MOVIE_TITLE"));
        entity.setFrom(rs.getString("MOVIE_FROM"));
        entity.setIsDubbed(rs.getBoolean("MOVIE_ISDUBBED"));
        entity.setDirector(rs.getString("MOVIE_DIRECTOR"));
        entity.setSynopsis(rs.getString("MOVIE_SYNOPSIS"));
        entity.setLength(rs.getInt("MOVIE_LENGTH"));
        entity.setMaxPlay(rs.getInt("MOVIE_MAXPLAY"));
        entity.setAgeLimit(rs.getInt("MOVIE_AGELIMIT"));
        entity.setCountTickets(rs.getInt("MOVIE_COUNTTICKETS"));
        entity.setPlaynum(rs.getInt("MOVIE_PLAYNUMBER"));
    }
}
