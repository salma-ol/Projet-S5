/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.GUIaddRemoveSessions;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 *
 * @author noemi
 */
public class AddRemoveSession implements ActionListener {
    
    private final GUIaddRemoveSessions change;
    
    public AddRemoveSession(GUIaddRemoveSessions change){
        this.change = change;
    }
    
    @Override
    public void actionPerformed(ActionEvent event){
        boolean update = false;
        if (event.getSource() == change.getCancel()) {
            change.dispose();
        } else if (event.getSource() == change.getRemoveSessions()) {
            for (int i = 0; i < change.getAllSessionsList().size(); i++) {
                try {
                    if (change.getSessionsCheckBox().get(i).isSelected() && change.getMysql().removeSession(change.getAllSessionsList().get(i).getID(), change.getAllSessionsList().get(i).getIDMovie())) {
                        JOptionPane.showMessageDialog(change, "You have succesfully deleted this session : " + change.getAllSessionsList().get(i).getDate(), "Remove a Session", JOptionPane.INFORMATION_MESSAGE);
                        update = true;
                    }
                    if (!update) {
                        change.getErrorModification().setText("You can't remove this Session, because it already has reservations");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GUIaddRemoveSessions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (event.getSource() == change.getAddSessions()) {
            try {
                JPanel optionPane = new JPanel();
                JComboBox<Object> movieList = new JComboBox<>(change.getMysql().loadMovies().toArray());

                JTextField price = new JTextField("13.50");
                JFormattedTextField time = new JFormattedTextField();

                optionPane.add(new JLabel("Name"));
                optionPane.add(movieList);
                optionPane.add(new JLabel("Room"));

                JComboBox roomList = new JComboBox<>(change.getMysql().getRoom().toArray());
                optionPane.add(roomList);

                optionPane.add(new JLabel("Time"));

                time.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))));
                time.setValue(Calendar.getInstance().getTime());
                optionPane.add(time);

                optionPane.add(new JLabel("Price"));
                optionPane.add(price);

                JOptionPane.showMessageDialog(change, optionPane, "Add a Session", JOptionPane.INFORMATION_MESSAGE);

                try {
                    Integer.parseInt(price.getText());
                } catch (NumberFormatException error) {
                    price.setText("13.50");
                }

                if (change.getMysql().addSession(movieList.getSelectedItem().toString(), roomList.getSelectedIndex() + 1, Timestamp.valueOf(time.getText()), Float.parseFloat(price.getText()), change.getMoviesList().size())) {
                    JOptionPane.showMessageDialog(change, movieList.getSelectedItem() + ": " + time.getText() + " succesfully recorded !", "Add a Session", JOptionPane.INFORMATION_MESSAGE);
                    change.dispose();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GUIaddRemoveSessions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(update) {
            change.dispose();
        }
    }
    
}
