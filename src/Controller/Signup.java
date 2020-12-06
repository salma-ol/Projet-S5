/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.GUIsignup;
import com.itextpdf.text.DocumentException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author noemi
 */
public class Signup implements ActionListener {
    
    private final GUIsignup sign;
    
    public Signup(GUIsignup sign){
        this.sign = sign;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        int code;
        if (event.getSource() == sign.getCancel()) {
            sign.dispose();
        } else if (event.getSource() == sign.getConfirm() && sign.getId().getText().length() != 0 && sign.getPassword().getPassword().length != 0 && sign.getVerifyPassword().getPassword().length != 0 && sign.getEmail().getText().length() != 0 && sign.getFirstname().getText().length() != 0 && sign.getLastname().getText().length() != 0 && sign.getBirthday().getModel().getValue() != null) {
            if (sign.getBirthday().getJFormattedTextField().getText().compareTo(new SimpleDateFormat("yyyy-MM-dd").format(sign.getDeadLine().getTime())) > 0) {
                sign.getRegistrationFailed().setText("You are too young to be registered !");
            } else if (!new String(sign.getPassword().getPassword()).equals(new String(sign.getPassword().getPassword()))) {
                sign.getRegistrationFailed().setText("Wrong Password ! Please try again...");
            } else if (sign.getEmail().getText().length() - sign.getEmail().getText().replace("@", "").length() != 1 || sign.getEmail().getText().length() - sign.getEmail().getText().replace(".", "").length() < 1 || sign.getEmail().getText().length() < 5) {
                sign.getRegistrationFailed().setText("Invalid Email ! Please try again...");
            } else {
                try {
                    if (sign.getMysql().checkUniqueField(sign.getId().getText(), sign.getEmail().getText())) {
                        MailIo test = new MailIo();
                        int confirmation = test.envoyer_confirmation(sign.getEmail().getText(), sign.getFirstname().getText(), sign.getLastname().getText());
                        do {
                            code = 0;
                            try {
                                code = Integer.parseInt(JOptionPane.showInputDialog(sign, "Enter the code", "Verify authentification", JOptionPane.INFORMATION_MESSAGE));
                            } catch (NumberFormatException error) {
                            }
                            if (confirmation != code) {
                                JOptionPane.showMessageDialog(sign, "Your code is not valable", "Try again", JOptionPane.ERROR_MESSAGE);
                                sign.setRegistered(true);
                            }
                        } while (code != confirmation);
                        sign.getMysql().addMemberCustomer(sign.getId().getText(), new String(sign.getPassword().getPassword()), sign.getEmail().getText(), sign.getFirstname().getText(), sign.getLastname().getText(), Date.valueOf(sign.getBirthday().getJFormattedTextField().getText()));
                        System.out.println("Member registered !");
                        sign.setRegistered(true);
                        sign.dispose();
                    } else {
                        sign.getRegistrationFailed().setText("ID or Email is already use ! Please try again...");
                    }
                } catch (SQLException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(GUIsignup.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            sign.getRegistrationFailed().setText("Please complete all fields !");
        }
    }
}
