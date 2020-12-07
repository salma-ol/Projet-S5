/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CheckData;
import Modele.Database;
import Modele.Movie;
<<<<<<< HEAD
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.* ;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.* ;
import org.jfree.chart.* ;
=======
import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
>>>>>>> main
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

<<<<<<< HEAD
/**
 *
 * @author DELL
 */

    // tout en anglais 
    // un truc avec le panier dans customer
    // graphe: film par ticket ; 
    // customer a achete cb de ticket pour chaque film  
    //nbre de ticket vendue par session pour un film 
    //le taux de membre et d'invite qui on achete des tickets par film 

=======
>>>>>>> main
public class GUICheckData extends JDialog{
    
    private static final Database mysql = new Database(); 
    
<<<<<<< HEAD
    private final ArrayList<Movie> moviesList = mysql.loadMovies(); ; 
    private final ArrayList<String> customersList = mysql.getCustomers() ; 
    
    
    
    private final JPanel panel = new JPanel(null);
    private final JPanel panelChart = new JPanel(null);
    private final JButton movieTicket = new JButton("Movie per Ticket");
    private final JButton customerTicket = new JButton("Customer Tickets");
    private final JButton sessionMovie = new JButton("Sales by sessions for a movie");
    private final JButton statMovie = new JButton("Movie Statistic");
    
    private final JComboBox<String> customers = new JComboBox<>();
    private final JComboBox<String> movies = new JComboBox<>();
    private ChartPanel CP = new ChartPanel(null);
=======
    private final ArrayList<Movie> moviesList = mysql.loadMovies();  
    private final ArrayList<String> customersList = mysql.getCustomers() ; 
    
    private final JPanel panel = new JPanel(null);
    private final JPanel panelChart = new JPanel(null);
    private final JButton movieTicket = new JButton("Sales for all Movies");
    private final JButton cancel = new JButton("CANCEL");
    
    private final JComboBox<String> customers = new JComboBox<>();
    private final JComboBox<String> movies = new JComboBox<>();
    private final JComboBox<String> movies2 = new JComboBox<>();
    
    private JLabel message ;
    private final ChartPanel CP = new ChartPanel(null);
>>>>>>> main

    public GUICheckData(JFrame frame, boolean modal) throws ClassNotFoundException, SQLException
    {
        super(frame, modal) ; 
        build() ;
    }
    private void build() throws ClassNotFoundException, SQLException {
        setTitle("Check Data");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setResizable(false);
<<<<<<< HEAD
=======
        panelChart.setBackground(Color.orange);
        panel.setBackground(Color.orange);
>>>>>>> main
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(null);
        
        panelChart.setBounds(0,200,800,500) ;
        buildContentPane() ; 
        add(panel) ; 
        add(panelChart) ; 

        setVisible(true);
    }

    private void buildContentPane() throws ClassNotFoundException, SQLException {
        panel.setBounds(0,0,800,200);
<<<<<<< HEAD
        movieTicket.addActionListener(new CheckData(this));
        customerTicket.addActionListener(new CheckData(this));
        sessionMovie.addActionListener(new CheckData(this));
        statMovie.addActionListener(new CheckData(this));
=======
>>>>>>> main
        
        for (String customer : customersList) {
            customers.addItem(customer);
        }
        for (Movie movie : moviesList) {
            movies.addItem(movie.getName());
<<<<<<< HEAD
        }
        
        
        panel.add(customerTicket).setBounds(40, 20, 130, 20);
        panel.add(customers).setBounds(55, 50, 100, 20);
        panel.add(movieTicket).setBounds(190, 20, 130, 20);
        panel.add(sessionMovie).setBounds(340, 20, 200, 20) ; 
        panel.add(movies).setBounds(370, 50, 150, 20);
        panel.add(statMovie).setBounds(560, 20, 150, 20);
=======
            movies2.addItem(movie.getName());
        }
        
        movieTicket.addActionListener(new CheckData(this));
        customers.addItemListener(new CheckData(this));
        movies.addItemListener(new CheckData(this));
        movies2.addItemListener(new CheckData(this));
        cancel.addActionListener(new CheckData(this));
        
        message = new JLabel("Sales per Movie for customer X Chart") ;
        panel.add(message).setBounds(100, 20, 250, 20);
        panel.add(customers).setBounds(155, 50, 100, 20);
        
        message = new JLabel("Sales per Session for movie X Chart") ;
        panel.add(message).setBounds(100, 90, 250, 20);
        panel.add(movies).setBounds(140, 120, 130, 20);
        
        message = new JLabel("Buyers statistics for movie X Chart") ;
        panel.add(message).setBounds(500, 20, 250, 20);
        panel.add(movies2).setBounds(525, 50, 150, 20);
        
        panel.add(movieTicket).setBounds(525, 100, 150, 20);
        
        panel.add(cancel).setBounds(560, 150, 80, 20);
>>>>>>> main
    }
    
    public ChartPanel chart(DefaultCategoryDataset dataset, String title, String legend, String xLegend) throws SQLException {
                
            JFreeChart barChart = ChartFactory.createBarChart(title, legend, 
                  xLegend, dataset, PlotOrientation.VERTICAL, true, true, false); 
<<<<<<< HEAD
                //ChartFrame frame =new ChartFrame("Bar Chart for the number of tickets sold by session",barChart) ; 
               
            CP.setChart(barChart);
        return new ChartPanel(barChart) { // this is the trick to manage setting the size of a chart into a panel!
=======
               
            CP.setChart(barChart);
        return new ChartPanel(barChart) { // this is the trick to manage setting the size of a chart into a panel!
            @Override
>>>>>>> main
            public Dimension getPreferredSize() {
                return new Dimension(400, 400) ;
            }
        }   ;
    } 
    public ChartPanel pieChart(DefaultPieDataset dataset, String title) throws SQLException {
                
            JFreeChart pieChart = ChartFactory.createPieChart(title,dataset, 
                    true, true, false); 
                //ChartFrame frame =new ChartFrame("Bar Chart for the number of tickets sold by session",barChart) ; 
<<<<<<< HEAD
               
            CP.setChart(pieChart);
        return new ChartPanel(pieChart) { // this is the trick to manage setting the size of a chart into a panel!
=======
            CP.setChart(pieChart);
        return new ChartPanel(pieChart) { // this is the trick to manage setting the size of a chart into a panel!
            @Override
>>>>>>> main
            public Dimension getPreferredSize() {
                return new Dimension(400, 400) ;
            }
        }   ;
    }

    public Database getMysql() {
        return mysql;
    }

    public ArrayList<Movie> getMoviesList() {
        return moviesList;
    }

    public ArrayList<String> getCustomersList() {
        return customersList;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JPanel getPanelChart() {
        return panelChart;
    }

    public JButton getMovieTicket() {
        return movieTicket;
    }

<<<<<<< HEAD
    public JButton getCustomerTicket() {
        return customerTicket;
    }

    public JButton getSessionMovie() {
        return sessionMovie;
    }

    public JButton getStatMovie() {
        return statMovie;
    }

=======
>>>>>>> main
    public JComboBox<String> getCustomers() {
        return customers;
    }

    public JComboBox<String> getMovies() {
        return movies;
    }

<<<<<<< HEAD
=======
    public JComboBox<String> getMovies2() {
        return movies2;
    }

>>>>>>> main
    public ChartPanel getCP() {
        return CP;
    }
    
<<<<<<< HEAD
    
=======
    public JButton getCancel() {
        return cancel;
    }
>>>>>>> main
}

