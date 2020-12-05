/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ChangeRoom;
import Modele.Database;
import java.awt.Color;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author ahaz1
 */
public class GUIchangeRoom extends JDialog {

    private static final Database mysql = new Database();

    private final ArrayList<Movie> moviesList = mysql.loadMovies();
    private ArrayList<Session> sessionsList = new ArrayList<>();
    private final ArrayList<Integer> roomsList = mysql.getRoom();

    private final JPanel panel = new JPanel();

    private final JLabel errorMessage = new JLabel();

    private final JLabel capacity = new JLabel();
    private final JLabel newCapacity = new JLabel();

    private final JButton confirm = new JButton("CONFIRM");
    private final JButton cancel = new JButton("CANCEL");
    private final JButton graph = new JButton("Graph");

    private final JComboBox<String> movies = new JComboBox<>();
    private final JComboBox<Timestamp> sessions = new JComboBox<>();
    private final JComboBox<Integer> rooms = new JComboBox<>();

    private void build() throws ClassNotFoundException, SQLException {
        setTitle("Change Movie Session Room");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() throws SQLException {
        panel.setLayout(null);
        panel.setBackground(Color.orange);

        panel.add(new JLabel("Choose the movie session:")).setBounds(115, 60, 200, 20);

        for (Movie movie : moviesList) {
            movies.addItem(movie.getName());
        }
        movies.addItemListener(new ChangeRoom(this));
        panel.add(movies).setBounds(295, 60, 180, 20);

        sessionsList = mysql.getSessions(moviesList.get(movies.getSelectedIndex()).getID());
        for (Session session : sessionsList) {
            sessions.addItem(session.getDate());
        }
        sessions.addItemListener(new ChangeRoom(this));
        panel.add(sessions).setBounds(125, 130, 150, 20);

        panel.add(capacity).setBounds(305, 120, 300, 40);
        capacity.setText("Room: " + sessionsList.get(sessions.getSelectedIndex()).getRoom() + "         Capacity: " + mysql.getCapacity(sessionsList.get(sessions.getSelectedIndex()).getRoom()));

        panel.add(new JLabel("Choose the new Room:")).setBounds(150, 250, 150, 20);
        rooms.addItemListener(new ChangeRoom(this));
        panel.add(rooms).setBounds(300, 250, 40, 20);
        for (Integer room : roomsList) {
            if (room != sessionsList.get(sessions.getSelectedIndex()).getRoom()) {
                rooms.addItem(room);
            }
        }

        panel.add(newCapacity).setBounds(360, 250, 100, 20);
        newCapacity.setText("Capacity: " + mysql.getCapacity(rooms.getItemAt(rooms.getSelectedIndex())));

        confirm.addActionListener(new ChangeRoom(this));
        cancel.addActionListener(new ChangeRoom(this));
        graph.addActionListener(new ChangeRoom(this));
        
        panel.add(graph).setBounds(240, 315, 100, 20); ;
        panel.add(confirm).setBounds(160, 360, 100, 20);
        panel.add(cancel).setBounds(320, 360, 100, 20);
 
        panel.add(errorMessage).setBounds(55, 380, 500, 20);

        return panel;
    }

    public GUIchangeRoom(JFrame frame, boolean modal) throws ClassNotFoundException, SQLException {
        super(frame, modal);
        build();
    }

    public Database getMysql() {
        return mysql;
    }

    public ArrayList<Movie> getMoviesList() {
        return moviesList;
    }

    public ArrayList<Session> getSessionsList() {
        return sessionsList;
    }

    public ArrayList<Integer> getRoomsList() {
        return roomsList;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JLabel getErrorMessage() {
        return errorMessage;
    }

    public JLabel getCapacity() {
        return capacity;
    }

    public JLabel getNewCapacity() {
        return newCapacity;
    }

    public JButton getConfirm() {
        return confirm;
    }

    public JButton getCancel() {
        return cancel;
    }

    public JButton getGraph() {
        return graph;
    }

    public JComboBox<String> getMovies() {
        return movies;
    }

    public JComboBox<Timestamp> getSessions() {
        return sessions;
    }

    public JComboBox<Integer> getRooms() {
        return rooms;
    }

    public void setSessionsList(ArrayList<Session> sessionsList) {
        this.sessionsList = sessionsList;
    }
    
}
