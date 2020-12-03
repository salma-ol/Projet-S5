/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import User.User;
import java.sql.Date;

/**
 *
 * @author ahaz1
 */
public class Employee extends User {
    
    public Employee(String id, String password, String email, String firstname, String lastname, Date birthday) {
        super(id, password, email, firstname, lastname, birthday);        
    }
}
