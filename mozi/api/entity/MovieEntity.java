
package mozi.api.entity;

import java.util.Objects;

/**
 *  A Movie_table egy sorát reprezentálja
 * 
 */
public class MovieEntity extends AbstractEntity {
    /**
     * a film címe ami a MOVIE_TITLE attribútumnak felel meg
     */
    private String title;
    /**
     * a film származási országa ami a MOVIE_FROM attribútumnak felel meg
     */
    private String from;
    /**
     * logikai érték mely azt adja meg, hogy a film szinkronizált-e, ez a MOVIE_ISDUBBED
     * attribútumnak felel meg
     */
    private boolean isDubbed;
    /**
     * a film rendezőjét adja meg, megfelel a MOVIE_DIRECTOR attribútumnak
     */
    private String director;
    /**
     * a film szinopszisa, megfelel a MOVIE_SYNOPSIS attribútumnak
     */
    private String synopsis;
    /**
     * a film percekben megadott hossza, megfelel a MOVIE_LENGTH attribútumnak
     */
    private int length;
    /**
     * a film maximális lejátszási számát adja meg, megfelel a MOVIE_MAXPLAY attribútumnak
     *
     */
    private int maxPlay;
    /**
     * a film korhatár-besorolását adja meg, megfelel a MOVIE_AGELIMIT attribútumnak
     */
    private int ageLimit;
    /**
     * a filmre összesen eladott jegyek számát adja meg, megfelel a MOVIE_COUNTTICKETS attribútumnak
     */
    private int countTickets;
    /**
     * a film eddigi lejátszásainak száma, megfelel a MOVIE_PLAYNUMBER attribútumnak
     */
    private int playnum;

    public int getPlaynum() {
        return playnum;
    }

    public void setPlaynum(int playnum) {
        this.playnum = playnum;
    }
    public int getCountTickets() {
        return countTickets;
    }

    public void setCountTickets(int countTickets) {
        this.countTickets = countTickets;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean getIsDubbed() {
        return isDubbed;
    }

    public void setIsDubbed(boolean isDubbed) {
        this.isDubbed = isDubbed;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMaxPlay() {
        return maxPlay;
    }

    public void setMaxPlay(int maxPlay) {
        this.maxPlay = maxPlay;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title,from,isDubbed,director,synopsis,length, maxPlay,ageLimit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MovieEntity other = (MovieEntity) obj;
        if (this.isDubbed != other.isDubbed) {
            return false;
        }
        if (this.length != other.length) {
            return false;
        }
        if (this.maxPlay != other.maxPlay) {
            return false;
        }
        if (this.ageLimit != other.ageLimit) {
            return false;
        }
        if (!this.title.equals(other.title)) {
            return false;
        }
        if (!this.from.equals(other.from)) {
            return false;
        }
        if (!this.director.equals(other.director)) {
            return false;
        }
        if (!this.synopsis.equals(other.synopsis)) {
            return false;
        }
        return true;
    }
    /**
     * a film szöveges reprezentációja
     * @return a film címét adja vissza
     */
    @Override
    public String toString(){
        return this.title;
    }
    /**
     * oszlopok elnevezése a megjelenítéshez
     */
    public static final String[] FIELD_NAMES = {"MOVIE_TITLE", "MOVIE_FROM", "MOVIE_ISDUBBED", 
    "MOVIE_DIRECTOR", "MOVIE_SYNOPSIS", "MOVIE_LENGTH", "MOVIE_MAXPLAY", "MOVIE_AGELIMIT", "TICKET_COUNT"};

}
