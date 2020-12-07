/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CheckData;
import Modele.Database;
import Modele.Movie;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.* ;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.* ;
import org.jfree.chart.* ;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

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

public class GUICheckData extends JDialog{
    
    private static final Database mysql = new Database(); 
    
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
        movieTicket.addActionListener(new CheckData(this));
        customerTicket.addActionListener(new CheckData(this));
        sessionMovie.addActionListener(new CheckData(this));
        statMovie.addActionListener(new CheckData(this));
        
        for (String customer : customersList) {
            customers.addItem(customer);
        }
        for (Movie movie : moviesList) {
            movies.addItem(movie.getName());
        }
        
        
        panel.add(customerTicket).setBounds(40, 20, 130, 20);
        panel.add(customers).setBounds(55, 50, 100, 20);
        panel.add(movieTicket).setBounds(190, 20, 130, 20);
        panel.add(sessionMovie).setBounds(340, 20, 200, 20) ; 
        panel.add(movies).setBounds(370, 50, 150, 20);
        panel.add(statMovie).setBounds(560, 20, 150, 20);
    }
    
    public ChartPanel chart(DefaultCategoryDataset dataset, String title, String legend, String xLegend) throws SQLException {
                
            JFreeChart barChart = ChartFactory.createBarChart(title, legend, 
                  xLegend, dataset, PlotOrientation.VERTICAL, true, true, false); 
                //ChartFrame frame =new ChartFrame("Bar Chart for the number of tickets sold by session",barChart) ; 
               
            CP.setChart(barChart);
        return new ChartPanel(barChart) { // this is the trick to manage setting the size of a chart into a panel!
            public Dimension getPreferredSize() {
                return new Dimension(400, 400) ;
            }
        }   ;
    } 
    public ChartPanel pieChart(DefaultPieDataset dataset, String title) throws SQLException {
                
            JFreeChart pieChart = ChartFactory.createPieChart(title,dataset, 
                    true, true, false); 
                //ChartFrame frame =new ChartFrame("Bar Chart for the number of tickets sold by session",barChart) ; 
               
            CP.setChart(pieChart);
        return new ChartPanel(pieChart) { // this is the trick to manage setting the size of a chart into a panel!
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

    public JButton getCustomerTicket() {
        return customerTicket;
    }

    public JButton getSessionMovie() {
        return sessionMovie;
    }

    public JButton getStatMovie() {
        return statMovie;
    }

    public JComboBox<String> getCustomers() {
        return customers;
    }

    public JComboBox<String> getMovies() {
        return movies;
    }

    public ChartPanel getCP() {
        return CP;
    }
    
    
}

