/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.GUIaddRemoveMovies;
import View.GUIaddRemoveSessions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 *
 * @author noemi
 */
public class AddRemoveMovies implements ActionListener{
    
    private final GUIaddRemoveMovies change;
    
    public AddRemoveMovies(GUIaddRemoveMovies change){
        this.change = change;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        boolean update = false;
        boolean checked = false;
        if (event.getSource() == change.getCancel()) {
            change.dispose();
        } else if (event.getSource() == change.getRemoveMovies()) {
            int movieRemoved = 1;
            for (JCheckBox movieCheckBox : change.getMoviesCheckBox()) {
                try {
                    if (movieCheckBox.isSelected()) {
                        checked = true;
                        if (movieCheckBox.isSelected() && change.getMysql().checkSessions(change.getMoviesCheckBox().indexOf(movieCheckBox) + movieRemoved)) {
                            movieRemoved--;
                            update = true;
                            change.getMysql().removeMovie(movieCheckBox.getText());
                            JOptionPane.showMessageDialog(change, "You have succesfully deleted " + movieCheckBox.getText(), "Remove a Movie", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GUIaddRemoveSessions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (event.getSource() == change.getAddMovies()) {
            for (JCheckBox futurMovieCheckBox : change.getFuturMoviesCheckBox()) {
                if (futurMovieCheckBox.isSelected()) {
                    JPanel optionPane = new JPanel();
                    JTextField director = new JTextField(10);
                    JTextField genre = new JTextField(20);
                    JFormattedTextField time = new JFormattedTextField();

                    optionPane.add(new JLabel("Director"));
                    optionPane.add(director);
                    optionPane.add(new JLabel("Genre"));
                    optionPane.add(genre);
                    optionPane.add(new JLabel("Time"));

                    time.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("HH:mm:ss"))));
                    time.setValue(Calendar.getInstance().getTime());
                    optionPane.add(time);

                    JOptionPane.showMessageDialog(change, optionPane, "Add a Movie", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        if (change.getMysql().addMovie(futurMovieCheckBox.getText(), director.getText(), genre.getText(), Time.valueOf(time.getText()))) {
                            //JOptionPane.showMessageDialog(this, "You have succesfully recorded " + futurMovieCheckBox.getText(), "Add a Movie", JOptionPane.INFORMATION_MESSAGE);

                            optionPane.removeAll();

                            JTextField price = new JTextField("13.50");
                            JComboBox roomList = new JComboBox<>(change.getMysql().getRoom().toArray());
                            time.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))));
                            time.setValue(Calendar.getInstance().getTime());

                            optionPane.add(new JLabel("Room"));
                            optionPane.add(roomList);
                            optionPane.add(new JLabel("Time"));
                            optionPane.add(time);
                            optionPane.add(new JLabel("Price"));
                            optionPane.add(price);

                            JOptionPane.showMessageDialog(change, optionPane, "Add a Session", JOptionPane.INFORMATION_MESSAGE);

                            try {
                                Integer.parseInt(price.getText());
                            } catch (NumberFormatException error) {
                                price.setText("13.50");
                            }

                            if (change.getMysql().addSession(futurMovieCheckBox.getText(), roomList.getSelectedIndex() + 1, Timestamp.valueOf(time.getText()), Float.parseFloat(price.getText()), change.getMoviesList().size())) {
                                JOptionPane.showMessageDialog(change, futurMovieCheckBox.getText() + ": " + time.getText() + " succesfully recorded !", "Add a Session", JOptionPane.INFORMATION_MESSAGE);
                                update = true;
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(GUIaddRemoveSessions.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        if (update) {
            change.dispose();
        }
        else if (!checked) {
            change.getErrorModification().setText("Please select at least one checkbox movie to Add or Remove...");
        } else if (!update) {
            change.getErrorModification().setText("You can't remove this Movie, it has Session(s) in progress");
        }
    }   
}
