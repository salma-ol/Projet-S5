/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.GUIhomePage;
import View.GUIpayment;
import Modele.Movie;
import View.GUIMovieTheatre;
import com.itextpdf.text.DocumentException;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;

/**
 *
 * @author noemi
 */
public class MovieTheatre implements ActionListener, ItemListener {

    private final GUIMovieTheatre theatre;

    public MovieTheatre(GUIMovieTheatre theatre) {
        this.theatre = theatre;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == theatre.getNext() || event.getSource() == theatre.getPrevious()) {
            if (event.getSource() == theatre.getNext()) {
                theatre.setMovieNumber(theatre.getMovieNumber() + 1);
                if (theatre.getMovieNumber() > theatre.getMoviesList().size() - 1) {
                    theatre.setMovieNumber(0);
                }
            } else {
                theatre.setMovieNumber(theatre.getMovieNumber() - 1);
                if (theatre.getMovieNumber() < 0) {
                    theatre.setMovieNumber(theatre.getMoviesList().size() - 1);
                }
            }
            try {
                theatre.nextMovie();
            } catch (SQLException ex) {
                Logger.getLogger(GUIMovieTheatre.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getSource() == theatre.getBuyTickets() && theatre.getNumberOfTickets().getText().length() != 0) {
            boolean validNumberOfTickets = true;
            for (Character digit : theatre.getNumberOfTickets().getText().toCharArray()) {
                if (((int) digit < 48 || (int) digit > 57) || (theatre.getNumberOfTickets().getText().length() == 1 && (int) digit == 48)) {
                    validNumberOfTickets = false;
                    theatre.getTicketError().setText("Please enter a VALID number of Tickets !");
                    break;
                }
            }
            if (validNumberOfTickets && Integer.parseInt(theatre.getRemainingTickets().getText().split(" ")[0]) < Integer.parseInt(theatre.getNumberOfTickets().getText())) {
                validNumberOfTickets = false;
                theatre.getTicketError().setText("There are not enough Tickets left !");
            }

            if (validNumberOfTickets) {

                GUIpayment payment = new GUIpayment(theatre, true, theatre.getCustomer(), theatre.getSessionsList().get(theatre.getSessionsDate().getSelectedIndex()), Integer.parseInt(theatre.getNumberOfTickets().getText()), theatre.getSessionsList().get(theatre.getSessionsDate().getSelectedIndex()).getPrice());

                if (payment.getPaymentComplete()) {
                    if (payment.getEmail() != null && payment.getId() != null) {
                        JOptionPane.showMessageDialog(theatre, "The purchase of your Ticket(s) has been completed successfully.\n\nAn Email has been sent to " + payment.getEmail() + " with your Ticket(s)", "Purchase complete !", JOptionPane.INFORMATION_MESSAGE);
                        MailIo pdf = new MailIo();
                        for (Movie movies : theatre.getMoviesList()) {
                            if (movies.getID() == theatre.getSessionsList().get(theatre.getSessionsDate().getSelectedIndex()).getIDMovie()) {
                                try {
                                    pdf.envoyer_reservation(payment.getEmail(), payment.getId(), movies.getName(), theatre.getSessionsList().get(theatre.getSessionsDate().getSelectedIndex()).getDate(), Integer.parseInt(theatre.getNumberOfTickets().getText()), theatre.getSessionsList().get(theatre.getSessionsDate().getSelectedIndex()).getRoom(), theatre.getSessionsList().get(theatre.getSessionsDate().getSelectedIndex()).getPrice());
                                } catch (MessagingException | DocumentException ex) {
                                    Logger.getLogger(MovieTheatre.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            }
        } else if (event.getSource() == theatre.getBack()) {
            for (Component component : theatre.getPanel().getComponents()) {
                component.setVisible(true);
            }
            theatre.getPurchase().setVisible(false);
            theatre.getShopping().setVisible(false);
            theatre.getBack().setVisible(false);
            theatre.getCP().setVisible(false) ; 
        } else {
            theatre.getTicketError().setText("Please enter a number of tickets !");
        }
        if (event.getSource() == theatre.getHome()) {
            GUIhomePage home = new GUIhomePage();
            theatre.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        if (theatre.getSessionsDate().getSelectedIndex() != -1) {
            try {
                theatre.getRemainingTickets().setText(theatre.getMysql().getNumberTickets(theatre.getSessionsList().get(theatre.getSessionsDate().getSelectedIndex()).getRoom(), theatre.getSessionsList().get(theatre.getSessionsDate().getSelectedIndex()).getID()) + " Tickets remaining !");
            } catch (SQLException ex) {
                Logger.getLogger(GUIMovieTheatre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
