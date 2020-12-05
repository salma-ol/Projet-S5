/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.GUIpayment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author noemi
 */
public class Payment implements ActionListener {
    private final GUIpayment payment;
    
    public Payment(GUIpayment payment){
        this.payment = payment;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if (payment.getCardsHolderName().getText().length() != 0 && payment.getCardNumber().getText().length() != 0 && payment.getCryptogram().getText().length() != 0) {
            if ((payment.getCryptogram().getText().length() != 3 && !payment.getAmex().isSelected()) || (payment.getCryptogram().getText().length() != 4 && payment.getAmex().isSelected())) {
                payment.getInvalidCard().setText("You enter an Invalid CVV ! Try again ...");
            } else if (payment.getCardNumber().getText().length() != 16) {
                payment.getInvalidCard().setText("Invalid Card Number, please try again");
            } else {
                try {
                    if (new Date().after(new SimpleDateFormat("MM/yy").parse(payment.getMonth().getSelectedItem().toString() + "/" + payment.getYear().getSelectedItem().toString()))) {
                        payment.getInvalidCard().setText("Your card has expired ! Change card !");
                    } else {
                        boolean validFields = true;
                        for (Character digit : payment.getCryptogram().getText().toCharArray()) {
                            if ((int) digit < 48 || (int) digit > 57) {
                                validFields = false;
                                break;
                            }
                        }
                        if (validFields) {
                            for (Character digit : payment.getCardNumber().getText().toCharArray()) {
                                if ((int) digit < 48 || (int) digit > 57) {
                                    validFields = false;
                                    break;
                                }
                            }
                        }
                        if (validFields) {
                            if (payment.getCustomer() == null) {
                                payment.setId("Guest");
                                payment.setEmail(JOptionPane.showInputDialog(payment, "Email:", "Enter your Email", JOptionPane.QUESTION_MESSAGE));
                            } else {
                                payment.setId(payment.getCustomer().getID());
                                payment.setEmail(payment.getCustomer().getEmail());
                            }
                            if (payment.getEmail() != null) {
                                payment.setPaymentComplete(true);
                                payment.getMysql().buyTickets(payment.getCustomer(), payment.getSession(), payment.getTickets(), payment.getPriceTicket(), Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())));
                            }
                            payment.dispose();
                        } else {
                            payment.getInvalidCard().setText("You enter an Invalid CVV ! Try again ...");
                        }
                    }
                } catch (ParseException | SQLException ex) {
                    Logger.getLogger(GUIpayment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            payment.getInvalidCard().setText("Complete all fields before purchase !");
        }
    }
    
}
