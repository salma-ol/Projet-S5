/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Modele.Session;
import Modele.Movie;
import Modele.MemberCustomer;
import Controller.MovieTheatre;
import Modele.Database;
import java.awt.Color;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.awt.Component;
<<<<<<< HEAD
=======
import java.awt.Dimension;
>>>>>>> main
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
<<<<<<< HEAD
=======
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
>>>>>>> main

/**
 *
 * @author ahaz1
 */
public class GUIMovieTheatre extends JFrame {

    private static MemberCustomer customer = null;
    private static final Database mysql = new Database();
    private final ArrayList<Movie> moviesList = mysql.loadMovies();
    private ArrayList<Session> sessionsList;

    private static int movieNumber = 0;

    private final JPanel panel = new JPanel();

<<<<<<< HEAD
=======
    private ChartPanel CP = new ChartPanel(null);

>>>>>>> main
    private JLabel movie;
    private final JLabel name = new JLabel();
    private final JLabel director = new JLabel();
    private final JLabel genre = new JLabel();
    private final JLabel time = new JLabel();
    private final JLabel remainingTickets = new JLabel();
    private final JLabel ticketError = new JLabel();
    private final JLabel basket = new JLabel(new ImageIcon("panier.png"));
    private final JLabel purchase = new JLabel("COMMAND LIST:");

    private final JTextField numberOfTickets = new JTextField(10);

    private final JButton next = new JButton("NEXT");
    private final JButton previous = new JButton("PREVIOUS");
    private final JButton buyTickets = new JButton("BUY TICKETS");
    private final JButton back = new JButton("BACK");
    private final JButton home = new JButton("Home Page");

    private JComboBox shopping;

    private final JComboBox<Timestamp> sessionsDate = new JComboBox<>();

<<<<<<< HEAD
    private void build() throws ClassNotFoundException, SQLException {
=======
    private void build() throws ClassNotFoundException, SQLException, Throwable {
>>>>>>> main
        setTitle("Welcome to my movie theatre");
        setSize(1250, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

<<<<<<< HEAD
    private JPanel buildContentPane() throws ClassNotFoundException, SQLException {
=======
    private JPanel buildContentPane() throws ClassNotFoundException, SQLException, Throwable {
>>>>>>> main
        panel.setLayout(null);
        panel.setBackground(Color.yellow);

        if (customer == null) {
            panel.add(new JLabel("WELCOME TO YOU  !")).setBounds(880, 70, 200, 20);
        } else {
            panel.add(new JLabel("WELCOME BACK  " + customer.getID().toUpperCase() + " !")).setBounds(855, 70, 200, 20);
            shopping = new JComboBox<>(mysql.getSales(customer.getID()).toArray());
<<<<<<< HEAD
=======
            CP = chart();
>>>>>>> main
            basket.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (Component component : panel.getComponents()) {
                        component.setVisible(false);
                    }
                    purchase.setVisible(true);
                    shopping.setVisible(true);
                    back.setVisible(true);
<<<<<<< HEAD
            
                }
            });
            back.addActionListener(new MovieTheatre(this));

            panel.add(basket).setBounds(950, 20, 500, 20);
            panel.add(purchase).setBounds(570, 80, 140, 20);
            purchase.setVisible(false);
            panel.add(shopping).setBounds(390, 150, 500, 30);
=======
                    CP.setVisible(true);
                }
            });
            back.addActionListener(new MovieTheatre(this));
            panel.add(basket).setBounds(950, 20, 500, 20);
            
            panel.add(CP).setBounds(410, 230, 400, 400);
            CP.setVisible(false);
            panel.add(purchase).setBounds(570, 80, 140, 20);
            purchase.setVisible(false);
            panel.add(shopping).setBounds(370, 150, 500, 30);
>>>>>>> main
            shopping.setVisible(false);
            panel.add(back).setBounds(570, 700, 80, 20);
            back.setVisible(false);
        }

        previous.addItemListener(new MovieTheatre(this));
        next.addItemListener(new MovieTheatre(this));

        previous.addActionListener(new MovieTheatre(this));
        next.addActionListener(new MovieTheatre(this));

        panel.add(previous).setBounds(80, 265, 100, 20);
        panel.add(next).setBounds(580, 265, 100, 20);

        panel.add(name).setBounds(210, 555, 200, 20);
        panel.add(director).setBounds(210, 595, 200, 20);
        panel.add(genre).setBounds(210, 635, 500, 20);
        panel.add(time).setBounds(210, 675, 200, 20);

        buyTickets.addActionListener(new MovieTheatre(this));
        home.addActionListener(new MovieTheatre(this));

        panel.add(new JLabel("Number of tickets:")).setBounds(860, 480, 200, 20);
        panel.add(numberOfTickets).setBounds(985, 480, 50, 20);
        panel.add(buyTickets).setBounds(870, 530, 150, 20);
        panel.add(home).setBounds(870, 700, 150, 20);

        panel.add(ticketError).setBounds(845, 630, 240, 20);

        nextMovie();

        remainingTickets.setText(mysql.getNumberTickets(sessionsList.get(sessionsDate.getSelectedIndex()).getRoom(), sessionsList.get(sessionsDate.getSelectedIndex()).getID()) + " Tickets remaining !");

        sessionsDate.addItemListener(new MovieTheatre(this));
        panel.add(new JLabel("Choose a movie session :")).setBounds(785, 200, 200, 20);
        panel.add(sessionsDate).setBounds(955, 200, 150, 20);

        panel.add(remainingTickets).setBounds(880, 250, 300, 20);
        panel.add(new JLabel("TICKETING :")).setBounds(915, 430, 200, 20);

        return panel;
    }

<<<<<<< HEAD
=======
    public ChartPanel chart() throws SQLException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < moviesList.size(); i++) {
            dataset.addValue(mysql.getSalesOfaMovieByCustomer(moviesList.get(i).getID(),
                    String.valueOf(customer.getID())), moviesList.get(i).getName(), " ");
        }
        JFreeChart barChart = ChartFactory.createBarChart("Tickets bought", "Name of the movies",
                "Number of tickets", dataset, PlotOrientation.VERTICAL, true, true, false);
        return new ChartPanel(barChart) { // this is the trick to manage setting the size of a chart into a panel!
            public Dimension getPreferredSize() {
                return new Dimension(400, 400);
            }
        };
    }

>>>>>>> main
    public void nextMovie() throws SQLException {
        name.setText("Name:  " + moviesList.get(movieNumber).getName());
        director.setText("Director:  " + moviesList.get(movieNumber).getDirector());
        genre.setText("Genre:  " + moviesList.get(movieNumber).getGenre());
        time.setText("Time:  " + moviesList.get(movieNumber).getTime().toString());
        ticketError.setText("");

        if (movie != null) {
            panel.remove(movie);
        }

        sessionsList = mysql.getSessions(moviesList.get(movieNumber).getID());

        movie = new JLabel(new ImageIcon("./Movies/" + moviesList.get(movieNumber).getName() + ".jpg"));
        panel.add(movie).setBounds(130, 35, 500, 500);

        sessionsDate.removeAllItems();
        for (Session hours : sessionsList) {
            sessionsDate.addItem(hours.getDate());
        }

        repaint();
    }

<<<<<<< HEAD
    public GUIMovieTheatre(MemberCustomer member) throws ClassNotFoundException, SQLException {
=======
    public GUIMovieTheatre(MemberCustomer member) throws ClassNotFoundException, SQLException, Throwable {
>>>>>>> main
        super();
        customer = member;
        build();
    }

    public MemberCustomer getCustomer() {
        return customer;
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

    public int getMovieNumber() {
        return movieNumber;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JLabel getMovie() {
        return movie;
    }

    public JLabel getTime() {
        return time;
    }

    public JLabel getRemainingTickets() {
        return remainingTickets;
    }

    public JLabel getTicketError() {
        return ticketError;
    }

    public JTextField getNumberOfTickets() {
        return numberOfTickets;
    }

    public JButton getNext() {
        return next;
    }

    public JButton getPrevious() {
        return previous;
    }

    public JButton getBuyTickets() {
        return buyTickets;
    }

    public JButton getBack() {
        return back;
    }

    public JComboBox<Timestamp> getSessionsDate() {
        return sessionsDate;
    }

    public JLabel getPurchase() {
        return purchase;
    }

    public JComboBox getShopping() {
        return shopping;
    }

    public void setMovieNumber(int movieNumber) {
        GUIMovieTheatre.movieNumber = movieNumber;
    }

    public JButton getHome() {
        return home;
<<<<<<< HEAD
    } 
}
=======
    }

    public ChartPanel getCP() {
        return CP;
    }

}
>>>>>>> main
