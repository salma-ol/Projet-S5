/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Modele.Session;
import Modele.Movie;
import Controller.AddRemoveSession;
import Modele.Database;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ahaz1
 */
public class GUIaddRemoveSessions extends JDialog{

    private static final Database mysql = new Database();

    private final ArrayList<Movie> moviesList;
    private final ArrayList<Session> allSessionsList = new ArrayList<>();
    private ArrayList<Session> sessionsList = new ArrayList<>();

    private final JPanel panel = new JPanel();

    private final JLabel errorModification = new JLabel();

    private final ArrayList<JCheckBox> sessionsCheckBox = new ArrayList<>();

    private final JButton addSessions = new JButton("ADD SESSIONS");
    private final JButton removeSessions = new JButton("REMOVE SESSIONS");
    private final JButton cancel = new JButton("CANCEL");

    private void build() throws ClassNotFoundException, SQLException {
        setTitle("Add/Remove Sessions");
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() throws ClassNotFoundException, SQLException {
        panel.setLayout(null);
        panel.setBackground(Color.orange);

        addSessions.addActionListener(new AddRemoveSession(this));
        removeSessions.addActionListener(new AddRemoveSession(this));
        cancel.addActionListener(new AddRemoveSession(this));

        int size = 0;

        for (int i = 0; i < moviesList.size(); i++) {
            int y = 60 + 60 * i;
            sessionsList = mysql.getSessions(moviesList.get(i).getID());
            if (i != 0) {
                y = 60 + 140 * i;
            }
            panel.add(new JLabel(moviesList.get(i).getName())).setBounds(60, y, 200, 20);
            for (int j = 0; j < sessionsList.size(); j++) {
                allSessionsList.add(sessionsList.get(j));
                sessionsCheckBox.add(new JCheckBox(sessionsList.get(j).getDate().toString()));
                panel.add(sessionsCheckBox.get(j + size)).setBounds(60 + 200 + 200 * (j / 2), y + 60 * (j % 2), 150, 20);
            }
            size += sessionsList.size();
        }

        panel.add(addSessions).setBounds(1200, 350, 150, 20);
        panel.add(removeSessions).setBounds(1200, 450, 150, 20);
        panel.add(cancel).setBounds(1225, 400, 100, 20);

        panel.add(errorModification).setBounds(1000, 600, 400, 20);

        return panel;
    }

    public GUIaddRemoveSessions(JFrame frame, boolean modal) throws ClassNotFoundException, SQLException {
        super(frame, modal);
        moviesList = mysql.loadMovies();
        build();
    }

    public Database getMysql() {
        return mysql;
    }

    public ArrayList<Movie> getMoviesList() {
        return moviesList;
    }

    public ArrayList<Session> getAllSessionsList() {
        return allSessionsList;
    }

    public ArrayList<Session> getSessionsList() {
        return sessionsList;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JLabel getErrorModification() {
        return errorModification;
    }

    public ArrayList<JCheckBox> getSessionsCheckBox() {
        return sessionsCheckBox;
    }

    public JButton getAddSessions() {
        return addSessions;
    }

    public JButton getRemoveSessions() {
        return removeSessions;
    }

    public JButton getCancel() {
        return cancel;
    }  
}
