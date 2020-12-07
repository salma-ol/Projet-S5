/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Timestamp;
/**
 *
 * @author ahaz1
 */
public class Session {

    private final int m_id;
    private final int m_idMovie;
    private final int m_idRoom;
    private final Timestamp m_date;
    private final float m_price;

    public Session(int id, int idMovie, int idRoom, Timestamp date, float price) {
        m_id = id;
        m_idMovie = idMovie;
        m_idRoom = idRoom;
        m_date = date;
        m_price = price;
    }

    public int getID() {
        return m_id;
    }

    public int getIDMovie() {
        return m_idMovie;
    }

    public int getRoom() {
        return m_idRoom;
    }

    public Timestamp getDate() {
        return m_date;
    }

    public float getPrice() {
        return m_price;
    }
}
