/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import DBMySQL.DataBase;
import Movie_Theatre.Movie;
import Movie_Theatre.Session;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
public class GUIaddRemoveSessions extends JDialog implements ActionListener {

    private static final DataBase mysql = new DataBase();

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

        addSessions.addActionListener(this);
        removeSessions.addActionListener(this);
        cancel.addActionListener(this);

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

    @Override
    public void actionPerformed(ActionEvent event) {
        boolean update = false;
        if (event.getSource() == cancel) {
            dispose();
        } else if (event.getSource() == removeSessions) {
            for (int i = 0; i < allSessionsList.size(); i++) {
                try {
                    if (sessionsCheckBox.get(i).isSelected() && mysql.removeSession(allSessionsList.get(i).getID(), allSessionsList.get(i).getIDMovie())) {
                        JOptionPane.showMessageDialog(this, "You have succesfully deleted this session : " + allSessionsList.get(i).getDate(), "Remove a Session", JOptionPane.INFORMATION_MESSAGE);
                        update = true;
                    }
                    if (!update) {
                        errorModification.setText("You can't remove this Session, because it already has reservations");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GUIaddRemoveSessions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (event.getSource() == addSessions) {
            try {
                JPanel optionPane = new JPanel();
                JComboBox<Object> movieList = new JComboBox<>(mysql.loadMovies().toArray());

                JTextField price = new JTextField("13.50");
                JFormattedTextField time = new JFormattedTextField();

                optionPane.add(new JLabel("Name"));
                optionPane.add(movieList);
                optionPane.add(new JLabel("Room"));

                JComboBox roomList = new JComboBox<>(mysql.getRoom().toArray());
                optionPane.add(roomList);

                optionPane.add(new JLabel("Time"));

                time.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))));
                time.setValue(Calendar.getInstance().getTime());
                optionPane.add(time);

                optionPane.add(new JLabel("Price"));
                optionPane.add(price);

                JOptionPane.showMessageDialog(this, optionPane, "Add a Session", JOptionPane.INFORMATION_MESSAGE);

                try {
                    Integer.parseInt(price.getText());
                } catch (NumberFormatException error) {
                    price.setText("13.50");
                }

                if (mysql.addSession(movieList.getSelectedItem().toString(), roomList.getSelectedIndex() + 1, Timestamp.valueOf(time.getText()), Float.parseFloat(price.getText()), moviesList.size())) {
                    JOptionPane.showMessageDialog(this, movieList.getSelectedItem() + ": " + time.getText() + " succesfully recorded !", "Add a Session", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GUIaddRemoveSessions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(update) {
            dispose();
        }
    }
}
