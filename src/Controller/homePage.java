/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.*;

/**
 *
 * @author noemi
 */
public class homePage implements ActionListener{
    
    private final GUIhomePage home;
    public homePage(GUIhomePage home){
        this.home = home;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if ((home.getMember().isSelected() || home.getEmployee().isSelected()) && home.getUser() == null) {
            GUIlogin login = new GUIlogin(home, true, home.getMember().isSelected());
            home.setUser(login.getUser());
            if (home.getUser() != null) {
                home.getConfirm().doClick();
            }
        } else if (home.getSignup().isSelected()) {
            GUIsignup inscription = new GUIsignup(home, true);
            if (inscription.getRegistered()) {
                home.getMember().setSelected(true);
                home.getConfirm().doClick();
            }
        } else if (home.getGuest().isSelected() || (home.getUser() != null && home.getMember().isSelected() || home.getEmployee().isSelected())) {
            try {
                if (home.getMember().isSelected() || home.getGuest().isSelected()) {
                    MovieTheatre movieTheatre = new MovieTheatre((MemberCustomer) home.getUser());
                } else {
                    GUIemployee employeeInterface = new GUIemployee();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GUIhomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            home.dispose();
        }
    }
}
