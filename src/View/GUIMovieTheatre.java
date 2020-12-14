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
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

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

    private ChartPanel CP = new ChartPanel(null);

    private final JLabel movie = new JLabel();
    private final JLabel name = new JLabel();
    private final JLabel director = new JLabel();
    private final JLabel genre = new JLabel();
    private final JLabel time = new JLabel();
    private final JLabel remainingTickets = new JLabel();
    private final JLabel ticketError = new JLabel();
    private final JLabel basket = new JLabel(new ImageIcon("panier.png"));
    private final JLabel purchase = new JLabel("COMMAND LIST:");
    
    private JLabel text = new JLabel();

    private final JTextField numberOfTickets = new JTextField(10);
    private final JLabel background = new JLabel(new ImageIcon("rideau.png"));

    private final JButton next = new JButton("NEXT");
    private final JButton previous = new JButton("PREVIOUS");
    private final JButton buyTickets = new JButton("BUY TICKETS");
    private final JButton back = new JButton("BACK");
    private final JButton home = new JButton("Home Page");
    
    private final Icon imageIcon = new ImageIcon(this.getClass().getResource("confetti.gif"));
    private final JLabel git = new JLabel(imageIcon);
    
    private final Icon imageIcon2 = new ImageIcon(this.getClass().getResource("confetti.gif"));
    private final JLabel git2 = new JLabel(imageIcon2);
    
    private final Icon imageIcon3 = new ImageIcon(this.getClass().getResource("popcorn.gif"));
    private final JLabel git3 = new JLabel(imageIcon3);

    private JComboBox shopping;

    private final JComboBox<Timestamp> sessionsDate = new JComboBox<>();

    private void build() throws ClassNotFoundException, SQLException, Throwable {
        setTitle("Welcome to my movie theatre");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() throws ClassNotFoundException, SQLException, Throwable {
        panel.setLayout(null);
        panel.setBackground(new Color(208, 203, 203));
        purchase.setForeground(Color.white);
        purchase.setFont(new java.awt.Font("Times New Roman", 3, 20));

        if (customer == null) {
            text = new JLabel("WELCOME TO YOU  !");
            panel.add(text).setBounds(450, 130, 350, 20);
            text.setFont(new java.awt.Font("Times New Roman", 3, 30));
        } else {
            text = new JLabel("WELCOME BACK  " + customer.getID().toUpperCase() + " !");
            panel.add(text).setBounds(385, 130, 500, 20);
            text.setFont(new java.awt.Font("Times New Roman", 3, 30));
            shopping = new JComboBox<>(mysql.getSales(customer.getID()).toArray());
            CP = chart();
            basket.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (Component component : panel.getComponents()) {
                        component.setVisible(false);
                    }
                    panel.setBackground(Color.black);
                    purchase.setVisible(true);
                    shopping.setVisible(true);
                    back.setVisible(true);
                    CP.setVisible(true);
                    git.setVisible(true);
                    git2.setVisible(true);
                }
            });
            
            back.addActionListener(new MovieTheatre(this));
            panel.add(basket).setBounds(590, 110, 500, 20);
            panel.add(git).setBounds(10, 10, 400, 400);
            git.setVisible(false);
            panel.add(git2).setBounds(780, 10, 400, 400);
            git2.setVisible(false);
            panel.add(CP).setBounds(410, 130, 400, 400);
            CP.setVisible(false);
            panel.add(purchase).setBounds(540, 20, 300, 20);
            purchase.setVisible(false);
            panel.add(shopping).setBounds(370, 70, 500, 30);
            shopping.setVisible(false);
            panel.add(back).setBounds(570, 550, 80, 20);
            back.setVisible(false);
        }

        previous.addItemListener(new MovieTheatre(this));
        next.addItemListener(new MovieTheatre(this));

        previous.addActionListener(new MovieTheatre(this));
        next.addActionListener(new MovieTheatre(this));

        panel.add(previous).setBounds(360, 365, 100, 20);
        panel.add(next).setBounds(720, 365, 100, 20);

        panel.add(name).setBounds(720, 440, 200, 20);
        panel.add(director).setBounds(720, 460, 200, 20);
        panel.add(genre).setBounds(720, 480, 500, 20);
        panel.add(time).setBounds(720, 500, 200, 20);

        buyTickets.addActionListener(new MovieTheatre(this));
        home.addActionListener(new MovieTheatre(this));

        JLabel nb = new JLabel("Number of tickets");
        panel.add(nb).setBounds(640, 600, 200, 20);
        nb.setFont(new java.awt.Font("Times New Roman", 3, 20));
        panel.add(numberOfTickets).setBounds(660, 630, 50, 20);
        panel.add(buyTickets).setBounds(540, 680, 150, 20);
        panel.add(home).setBounds(870, 680, 150, 20);

        panel.add(ticketError).setBounds(530, 657, 240, 20);

        nextMovie();
        panel.add(movie).setBounds(480, 135, 500, 500);

        remainingTickets.setText(mysql.getNumberTickets(sessionsList.get(sessionsDate.getSelectedIndex()).getRoom(), sessionsList.get(sessionsDate.getSelectedIndex()).getID()) + " Tickets remaining !");

        sessionsDate.addItemListener(new MovieTheatre(this));
        JLabel ticket = new JLabel("Choose a movie session");
        ticket.setFont(new java.awt.Font("Times New Roman", 3, 20));
        panel.add(ticket).setBounds(390, 600, 200, 20);
        panel.add(sessionsDate).setBounds(405, 630, 150, 20);

        panel.add(remainingTickets).setBounds(505, 180, 300, 20);
        remainingTickets.setFont(new java.awt.Font("Times New Roman", 3, 20));
        JLabel ticketing = new JLabel("TICKETING :");
        ticketing.setFont(new java.awt.Font("Times New Roman", 3, 20));
        panel.add(ticketing).setBounds(555, 570, 200, 20);
        panel.add(git3).setBounds(200, 450, 200, 200);
        panel.add(background).setBounds(0, 0, 1200, 700);
        

        return panel;
    }

    public ChartPanel chart() throws SQLException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Movie moviesList1 : moviesList) {
            dataset.addValue(mysql.getSalesOfaMovieByCustomer(moviesList1.getID(), String.valueOf(customer.getID())), moviesList1.getName(), " ");
        }
        JFreeChart barChart = ChartFactory.createBarChart("Tickets bought", "Name of the movies",
                "Number of tickets", dataset, PlotOrientation.VERTICAL, true, true, false);
        return new ChartPanel(barChart) { // this is the trick to manage setting the size of a chart into a panel!
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(400, 400);
            }
        };
    }

    public void nextMovie() throws SQLException {
        name.setText("Name:  " + moviesList.get(movieNumber).getName());
        director.setText("Director:  " + moviesList.get(movieNumber).getDirector());
        genre.setText("Genre:  " + moviesList.get(movieNumber).getGenre());
        time.setText("Time:  " + moviesList.get(movieNumber).getTime().toString());
        ticketError.setText("");

        sessionsList = mysql.getSessions(moviesList.get(movieNumber).getID());

        movie.setIcon(new ImageIcon("./Movies/" + moviesList.get(movieNumber).getName() + ".jpg"));
        
        sessionsDate.removeAllItems();
        for (Session hours : sessionsList) {
            sessionsDate.addItem(hours.getDate());
        }

        repaint();
    }

    public GUIMovieTheatre(MemberCustomer member) throws ClassNotFoundException, SQLException, Throwable {
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
    
    public void reiniatiliserDate() {
        sessionsDate.removeAllItems();
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
    }

    public ChartPanel getCP() {
        return CP;
    }
    
    public JLabel getGit() {
        return git;
    }

    public JLabel getGit2() {
        return git2;
    }
}
