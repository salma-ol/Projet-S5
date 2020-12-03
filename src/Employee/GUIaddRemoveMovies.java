/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import DBMySQL.DataBase;
import Movie_Theatre.Movie;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 *
 * @author ahaz1
 */
public class GUIaddRemoveMovies extends JDialog implements ActionListener {

    private static final DataBase mysql = new DataBase();

    private final ArrayList<Movie> moviesList;
    private final ArrayList<String> futurMoviesList;

    private final JPanel panel = new JPanel();

    private final JLabel errorModification = new JLabel();

    private final ArrayList<JCheckBox> moviesCheckBox = new ArrayList<>();
    private final ArrayList<JCheckBox> futurMoviesCheckBox = new ArrayList<>();
   
    private final JButton addMovies = new JButton("ADD MOVIES");
    private final JButton removeMovies = new JButton("REMOVE MOVIES");
    private final JButton cancel = new JButton("CANCEL");

    private void build() throws ClassNotFoundException, SQLException {
        setTitle("Add/Remove Movies");
        setSize(700, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() throws ClassNotFoundException, SQLException {
        panel.setLayout(null);
        panel.setBackground(Color.orange);      

        addMovies.addActionListener(this);
        removeMovies.addActionListener(this);
        cancel.addActionListener(this);

        panel.add(new JLabel("Movies:")).setBounds(95, 50, 100, 20);
        for (int i = 0; i < moviesList.size(); i++) {
            moviesCheckBox.add(new JCheckBox(moviesList.get(i).getName()));
            panel.add(moviesCheckBox.get(i)).setBounds(95, 110 + 60 * i, 200, 20);
        }

        panel.add(new JLabel("Futur Movies:")).setBounds(395, 50, 100, 20);
        for (int i = 0; i < futurMoviesList.size(); i++) {
            futurMoviesCheckBox.add(new JCheckBox(futurMoviesList.get(i).split(".jpg")[0]));
            panel.add(futurMoviesCheckBox.get(i)).setBounds(395, 110 + 60 * i, 200, 20);
        }

        panel.add(addMovies).setBounds(445, 200 + 60 * moviesList.size(), 150, 20);
        panel.add(removeMovies).setBounds(95, 200 + 60 * moviesList.size(), 150, 20);
        panel.add(cancel).setBounds(270, 200 + 60 * moviesList.size(), 150, 20);

        panel.add(errorModification).setBounds(175, 300 + 60 * moviesList.size(), 400, 20);

        return panel;
    }

    public GUIaddRemoveMovies(JFrame frame, boolean modal) throws ClassNotFoundException, SQLException {
        super(frame, modal);
        moviesList = mysql.loadMovies();
        futurMoviesList = mysql.loadFuturMovies();
        build();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        boolean update = false;
        boolean checked = false;
        if (event.getSource() == cancel) {
            dispose();
        } else if (event.getSource() == removeMovies) {
            int movieRemoved = 1;
            for (JCheckBox movieCheckBox : moviesCheckBox) {
                try {
                    if (movieCheckBox.isSelected()) {
                        checked = true;
                        if (movieCheckBox.isSelected() && mysql.checkSessions(moviesCheckBox.indexOf(movieCheckBox) + movieRemoved)) {
                            movieRemoved--;
                            update = true;
                            mysql.removeMovie(movieCheckBox.getText());
                            JOptionPane.showMessageDialog(this, "You have succesfully deleted " + movieCheckBox.getText(), "Remove a Movie", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GUIaddRemoveSessions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (event.getSource() == addMovies) {
            for (JCheckBox futurMovieCheckBox : futurMoviesCheckBox) {
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

                    JOptionPane.showMessageDialog(this, optionPane, "Add a Movie", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        if (mysql.addMovie(futurMovieCheckBox.getText(), director.getText(), genre.getText(), Time.valueOf(time.getText()))) {
                            //JOptionPane.showMessageDialog(this, "You have succesfully recorded " + futurMovieCheckBox.getText(), "Add a Movie", JOptionPane.INFORMATION_MESSAGE);

                            optionPane.removeAll();

                            JTextField price = new JTextField("13.50");
                            JComboBox roomList = new JComboBox<>(mysql.getRoom().toArray());
                            time.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))));
                            time.setValue(Calendar.getInstance().getTime());

                            optionPane.add(new JLabel("Room"));
                            optionPane.add(roomList);
                            optionPane.add(new JLabel("Time"));
                            optionPane.add(time);
                            optionPane.add(new JLabel("Price"));
                            optionPane.add(price);

                            JOptionPane.showMessageDialog(this, optionPane, "Add a Session", JOptionPane.INFORMATION_MESSAGE);

                            try {
                                Integer.parseInt(price.getText());
                            } catch (NumberFormatException error) {
                                price.setText("13.50");
                            }

                            if (mysql.addSession(futurMovieCheckBox.getText(), roomList.getSelectedIndex() + 1, Timestamp.valueOf(time.getText()), Float.parseFloat(price.getText()), moviesList.size())) {
                                JOptionPane.showMessageDialog(this, futurMovieCheckBox.getText() + ": " + time.getText() + " succesfully recorded !", "Add a Session", JOptionPane.INFORMATION_MESSAGE);
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
            dispose();
        }
        else if (!checked) {
            errorModification.setText("Please select at least one checkbox movie to Add or Remove...");
        } else if (!update) {
            errorModification.setText("You can't remove this Movie, it has Session(s) in progress");
        }
    }
}
