/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.GUIemployee;
import View.GUIhomePage;
import View.GUIlogin;
import View.GUIsignup;
import Modele.MemberCustomer;
import View.GUIMovieTheatre;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author noemi
 */
public class HomePage implements ActionListener{
    
    private final GUIhomePage home;
    
    public HomePage(GUIhomePage home){
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
                    GUIMovieTheatre movieTheatre = new GUIMovieTheatre((MemberCustomer) home.getUser());
                } else {
                    GUIemployee employeeInterface = new GUIemployee();
                }
            } catch (ClassNotFoundException | SQLException ex ) {
                Logger.getLogger(GUIhomePage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            home.dispose();
        }
    }
}
