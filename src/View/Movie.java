/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.sql.Time;

/**
 *
 * @author ahaz1
 */
public class Movie {

    private final int m_id;
    private final String m_name;
    private final String m_director;
    private final String m_genre;
    private final Time m_time;

    public Movie(int id, String name, String director, String genre, Time time) {
        m_id = id;
        m_name = name;
        m_director = director;
        m_genre = genre;
        m_time = time;
    }

    public int getID() {
        return m_id;
    }

    public String getName() {
        return m_name;
    }

    public String getDirector() {
        return m_director;
    }

    public String getGenre() {
        return m_genre;
    }

    public Time getTime() {
        return m_time;
    }

    public void display() {
        System.out.println(m_id + "\n" + m_name + "\n" + m_director + "\n" + m_genre + "\n" + m_time.toString());
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
