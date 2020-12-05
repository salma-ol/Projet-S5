/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.AddRemoveMovies;
import Modele.Database;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author ahaz1
 */
public class GUIaddRemoveMovies extends JDialog {

    private static final Database mysql = new Database();

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

        addMovies.addActionListener(new AddRemoveMovies(this));
        removeMovies.addActionListener(new AddRemoveMovies(this));
        cancel.addActionListener(new AddRemoveMovies(this));

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

    public static Database getMysql() {
        return mysql;
    }

    public ArrayList<Movie> getMoviesList() {
        return moviesList;
    }

    public ArrayList<String> getFuturMoviesList() {
        return futurMoviesList;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JLabel getErrorModification() {
        return errorModification;
    }

    public ArrayList<JCheckBox> getMoviesCheckBox() {
        return moviesCheckBox;
    }

    public ArrayList<JCheckBox> getFuturMoviesCheckBox() {
        return futurMoviesCheckBox;
    }

    public JButton getAddMovies() {
        return addMovies;
    }

    public JButton getRemoveMovies() {
        return removeMovies;
    }

    public JButton getCancel() {
        return cancel;
    }
    
    
}
