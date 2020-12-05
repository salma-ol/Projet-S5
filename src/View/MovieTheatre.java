/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Movie_Theatre;
import Modele.Database;
import java.awt.Color;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author ahaz1
 */
public class MovieTheatre extends JFrame {

    private static MemberCustomer customer = null;
    private static final Database mysql = new Database();
    private final ArrayList<Movie> moviesList = mysql.loadMovies();
    private ArrayList<Session> sessionsList;

    private static int movieNumber = 0;

    private final JPanel panel = new JPanel();

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

    private JComboBox<String> shopping;

    private final JComboBox<Timestamp> sessionsDate = new JComboBox<>();

    private void build() throws ClassNotFoundException, SQLException {
        setTitle("Welcome to my movie theatre");
        setSize(1250, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() throws ClassNotFoundException, SQLException {
        panel.setLayout(null);
        panel.setBackground(Color.yellow);

        if (customer == null) {
            panel.add(new JLabel("WELCOME TO YOU  !")).setBounds(880, 70, 200, 20);
        } else {
            panel.add(new JLabel("WELCOME BACK  " + customer.getID().toUpperCase() + " !")).setBounds(855, 70, 200, 20);
            shopping = new JComboBox(mysql.getSales(customer.getID()).toArray());
            basket.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (Component component : panel.getComponents()) {
                        component.setVisible(false);
                    }
                    purchase.setVisible(true);
                    shopping.setVisible(true);
                    back.setVisible(true);
            
                }
            });
            back.addActionListener(new Movie_Theatre(this));

            panel.add(basket).setBounds(950, 20, 500, 20);
            panel.add(purchase).setBounds(570, 80, 140, 20);
            purchase.setVisible(false);
            panel.add(shopping).setBounds(390, 150, 500, 30);
            shopping.setVisible(false);
            panel.add(back).setBounds(570, 700, 80, 20);
            back.setVisible(false);
        }

        previous.addItemListener(new Movie_Theatre(this));
        next.addItemListener(new Movie_Theatre(this));

        previous.addActionListener(new Movie_Theatre(this));
        next.addActionListener(new Movie_Theatre(this));

        panel.add(previous).setBounds(80, 265, 100, 20);
        panel.add(next).setBounds(580, 265, 100, 20);

        panel.add(name).setBounds(210, 555, 200, 20);
        panel.add(director).setBounds(210, 595, 200, 20);
        panel.add(genre).setBounds(210, 635, 500, 20);
        panel.add(time).setBounds(210, 675, 200, 20);

        buyTickets.addActionListener(new Movie_Theatre(this));
        home.addActionListener(new Movie_Theatre(this));

        panel.add(new JLabel("Number of tickets:")).setBounds(860, 480, 200, 20);
        panel.add(numberOfTickets).setBounds(985, 480, 50, 20);
        panel.add(buyTickets).setBounds(870, 530, 150, 20);
        panel.add(home).setBounds(870, 700, 150, 20);

        panel.add(ticketError).setBounds(845, 630, 240, 20);

        nextMovie();

        remainingTickets.setText(mysql.getNumberTickets(sessionsList.get(sessionsDate.getSelectedIndex()).getRoom(), sessionsList.get(sessionsDate.getSelectedIndex()).getID()) + " Tickets remaining !");

        sessionsDate.addItemListener(new Movie_Theatre(this));
        panel.add(new JLabel("Choose a movie session :")).setBounds(785, 200, 200, 20);
        panel.add(sessionsDate).setBounds(955, 200, 150, 20);

        panel.add(remainingTickets).setBounds(880, 250, 300, 20);
        panel.add(new JLabel("TICKETING :")).setBounds(915, 430, 200, 20);

        return panel;
    }

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

    public MovieTheatre(MemberCustomer member) throws ClassNotFoundException, SQLException {
        super();
        customer = member;
        build();
    }

    public static MemberCustomer getCustomer() {
        return customer;
    }

    public static Database getMysql() {
        return mysql;
    }

    public ArrayList<Movie> getMoviesList() {
        return moviesList;
    }

    public ArrayList<Session> getSessionsList() {
        return sessionsList;
    }

    public static int getMovieNumber() {
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

    public JComboBox<String> getShopping() {
        return shopping;
    }

    public void setMovieNumber(int movieNumber) {
        MovieTheatre.movieNumber = movieNumber;
    }

    public JButton getHome() {
        return home;
    }
    
}