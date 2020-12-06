/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author ahaz1
 */
public class MemberCustomer extends User {

    private float discount = 0;

    public MemberCustomer(String id, String password, String email, String firstname, String lastname, Date birthday) {
        super(id, password, email, firstname, lastname, birthday);

        if (Integer.parseInt(birthday.toString().split("-")[0]) > Integer.parseInt(new SimpleDateFormat("yyyy").format(new java.util.Date())) - 14) {
            discount = (float) 40 / 100;
        } else if (Integer.parseInt(birthday.toString().split("-")[0]) > Integer.parseInt(new SimpleDateFormat("yyyy").format(new java.util.Date())) - 65) {
            discount = (float) 20 / 100;
        } else {
            discount = (float) 30 / 100;
        }
    }

    public String getID() {
        return m_id;
    }

    public float getDiscount() {
        return discount;
    }
    
    public String getEmail() {
        return m_email;
    }

    public void display() {
        System.out.println(m_id + "\n" + m_email + "\n" + m_password + "\n" + m_firstname + "\n" + m_lastname + "\n" + m_birthday);
    }
}
