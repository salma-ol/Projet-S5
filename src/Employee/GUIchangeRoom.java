/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import DBMySQL.DataBase;
import Movie_Theatre.Movie;
import Movie_Theatre.Session;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author ahaz1
 */
public class GUIchangeRoom extends JDialog implements ActionListener, ItemListener {

    private static final DataBase mysql = new DataBase();

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
        movies.addItemListener(this);
        panel.add(movies).setBounds(295, 60, 180, 20);

        sessionsList = mysql.getSessions(moviesList.get(movies.getSelectedIndex()).getID());
        for (Session session : sessionsList) {
            sessions.addItem(session.getDate());
        }
        sessions.addItemListener(this);
        panel.add(sessions).setBounds(125, 130, 150, 20);

        panel.add(capacity).setBounds(305, 120, 300, 40);
        capacity.setText("Room: " + sessionsList.get(sessions.getSelectedIndex()).getRoom() + "         Capacity: " + mysql.getCapacity(sessionsList.get(sessions.getSelectedIndex()).getRoom()));

        panel.add(new JLabel("Choose the new Room:")).setBounds(150, 250, 150, 20);
        rooms.addItemListener(this);
        panel.add(rooms).setBounds(300, 250, 40, 20);
        for (Integer room : roomsList) {
            if (room != sessionsList.get(sessions.getSelectedIndex()).getRoom()) {
                rooms.addItem(room);
            }
        }

        panel.add(newCapacity).setBounds(360, 250, 100, 20);
        newCapacity.setText("Capacity: " + mysql.getCapacity(rooms.getItemAt(rooms.getSelectedIndex())));

        confirm.addActionListener(this);
        cancel.addActionListener(this);
        graph.addActionListener(this);
        
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

    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getSource() == movies) {
            sessions.removeAllItems();
            try {
                sessionsList = mysql.getSessions(moviesList.get(movies.getSelectedIndex()).getID());
                for (Session session : sessionsList) {
                    sessions.addItem(session.getDate());
                }
                capacity.setText("Room: " + sessionsList.get(sessions.getSelectedIndex()).getRoom() + "         Capacity: " + mysql.getCapacity(sessionsList.get(sessions.getSelectedIndex()).getRoom()));
                newCapacity.setText("Capacity: " + mysql.getCapacity(rooms.getItemAt(rooms.getSelectedIndex())));
            } catch (SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getSource() == sessions && sessions.getSelectedIndex() != -1) {
            rooms.removeAllItems();
            for (Integer room : roomsList) {
                if (room != sessionsList.get(sessions.getSelectedIndex()).getRoom()) {
                    rooms.addItem(room);
                }
            }
            try {
                capacity.setText("Room: " + sessionsList.get(sessions.getSelectedIndex()).getRoom() + "         Capacity: " + mysql.getCapacity(sessionsList.get(sessions.getSelectedIndex()).getRoom()));
                newCapacity.setText("Capacity: " + mysql.getCapacity(rooms.getItemAt(rooms.getSelectedIndex())));
            } catch (SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getSource() == rooms && rooms.getSelectedIndex() != -1) {
            try {
                newCapacity.setText("Capacity: " + mysql.getCapacity(rooms.getItemAt(rooms.getSelectedIndex())));
            } catch (SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == cancel) {
            dispose();
        }
        else if (movies.getItemCount() != 0 && event.getSource() == confirm ) {
            try {
                if (mysql.changeRoom(sessionsList.get(sessions.getSelectedIndex()), Integer.parseInt(newCapacity.getText().split(" ")[1]), Integer.parseInt(rooms.getSelectedItem().toString()))) {
                    System.out.println("Successful room change");
                    dispose();
                } else {
                    errorMessage.setText("Capacity of the New Room is not sufficient to accommodate the number of seats sold !");
                }
            } catch (SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (event.getSource() == graph){
            int[][] salesPerSession ; 
            try { 
                salesPerSession = mysql.getSalesOfaMovie(moviesList.get(movies.getSelectedIndex()).getID()) ; 
                DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 

                for(int i=0; i<salesPerSession.length; i++){
                   for(int j=0 ; j<salesPerSession[i].length ; j++){
                       dataset.addValue(salesPerSession[i][j], Integer.toString(salesPerSession[i][0]), " "); 
                   }
                }
                JFreeChart barChart = ChartFactory.createBarChart("Vente pour chaque Session", "session", 
                  "Nombre de place vendue", dataset, PlotOrientation.VERTICAL, true, true, false); 
                ChartFrame frame =new ChartFrame("Bar Chart for the number of tickets sold by session",barChart) ;  
                frame.setVisible(true) ; 
                frame.setSize(400, 400);
                frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
            }catch (SQLException ex) {
                Logger.getLogger(GUIchangeRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
