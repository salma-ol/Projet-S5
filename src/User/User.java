/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.sql.Date;

/**
 *
 * @author ahaz1
 */
abstract public class User {

    protected final String m_id;
    protected final String m_password;
    protected final String m_email;
    protected final String m_firstname;
    protected final String m_lastname;
    protected final Date m_birthday;

    public User(String id, String password, String email, String firstname, String lastname, Date birthday) {
        m_id = id;
        m_password = password;
        m_email = email;
        m_firstname = firstname;
        m_lastname = lastname;
        m_birthday = birthday;

    }

}
