/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.GUIlogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author noemi
 */
public class Login implements ActionListener {
    
    private final GUIlogin log;
    
    public Login(GUIlogin log){
        this.log = log;
    }
    
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == log.getCancel()) {
            log.dispose();
        } else if (event.getSource() == log.getConfirm() && log.getId().getText().length() != 0 && log.getPassword().getPassword().length != 0) {
            try {
                if (log.getMysql().checkLogin(log.getId().getText(), new String(log.getPassword().getPassword()), log.getIsCustomer())) {
                    log.setUser( log.getMysql().getCustomer());
                    System.out.println("Successful authentication !");
                    log.dispose();
                } else {
                    log.getErrorAuthentification().setText("Wrong ID or Password ! Please try again");
                }
            } catch (SQLException | NoSuchAlgorithmException ex) {
                Logger.getLogger(GUIlogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            log.getErrorAuthentification().setText("Please complete all fields ! Try again ...");
        }
    }    
}
