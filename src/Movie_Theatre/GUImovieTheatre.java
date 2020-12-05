/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Movie_Theatre;

import Customers.MemberCustomer;
import DBMySQL.DataBase;
import Customers.GUIpayment;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.itextpdf.text.DocumentException;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ahaz1
 */
public class GUImovieTheatre extends JFrame implements ActionListener, ItemListener {

    private static MemberCustomer customer = null;
    private static final DataBase mysql = new DataBase();
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

    private JComboBox shopping;

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
            shopping = new JComboBox<>(mysql.getSales(customer.getID()).toArray());
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
            back.addActionListener(this);

            panel.add(basket).setBounds(950, 20, 500, 20);
            panel.add(purchase).setBounds(570, 80, 140, 20);
            purchase.setVisible(false);
            panel.add(shopping).setBounds(390, 150, 500, 30);
            shopping.setVisible(false);
            panel.add(back).setBounds(570, 700, 80, 20);
            back.setVisible(false);
        }

        previous.addItemListener(this);
        next.addItemListener(this);

        previous.addActionListener(this);
        next.addActionListener(this);

        panel.add(previous).setBounds(80, 265, 100, 20);
        panel.add(next).setBounds(580, 265, 100, 20);

        panel.add(name).setBounds(210, 555, 200, 20);
        panel.add(director).setBounds(210, 595, 200, 20);
        panel.add(genre).setBounds(210, 635, 500, 20);
        panel.add(time).setBounds(210, 675, 200, 20);

        buyTickets.addActionListener(this);

        panel.add(new JLabel("Number of tickets:")).setBounds(860, 480, 200, 20);
        panel.add(numberOfTickets).setBounds(985, 480, 50, 20);
        panel.add(buyTickets).setBounds(870, 530, 150, 20);

        panel.add(ticketError).setBounds(845, 630, 240, 20);

        nextMovie();

        remainingTickets.setText(mysql.getNumberTickets(sessionsList.get(sessionsDate.getSelectedIndex()).getRoom(), sessionsList.get(sessionsDate.getSelectedIndex()).getID()) + " Tickets remaining !");

        sessionsDate.addItemListener(this);
        panel.add(new JLabel("Choose a movie session :")).setBounds(785, 200, 200, 20);
        panel.add(sessionsDate).setBounds(955, 200, 150, 20);

        panel.add(remainingTickets).setBounds(880, 250, 300, 20);
        panel.add(new JLabel("TICKETING :")).setBounds(915, 430, 200, 20);

        return panel;
    }

    private void nextMovie() throws SQLException {
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

    public GUImovieTheatre(MemberCustomer member) throws ClassNotFoundException, SQLException {
        super();
        customer = member;
        build();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == next || event.getSource() == previous) {
            if (event.getSource() == next) {
                movieNumber++;
                if (movieNumber > moviesList.size() - 1) {
                    movieNumber = 0;
                }
            } else {
                movieNumber--;
                if (movieNumber < 0) {
                    movieNumber = moviesList.size() - 1;
                }
            }
            try {
                nextMovie();
            } catch (SQLException ex) {
                Logger.getLogger(GUImovieTheatre.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getSource() == buyTickets && numberOfTickets.getText().length() != 0) {
            boolean validNumberOfTickets = true;
            for (Character digit : numberOfTickets.getText().toCharArray()) {
                if (((int) digit < 48 || (int) digit > 57) || (numberOfTickets.getText().length() == 1 && (int) digit == 48)) {
                    validNumberOfTickets = false;
                    ticketError.setText("Please enter a VALID number of Tickets !");
                    break;
                }
            }
            if (validNumberOfTickets && Integer.parseInt(remainingTickets.getText().split(" ")[0]) < Integer.parseInt(numberOfTickets.getText())) {
                validNumberOfTickets = false;
                ticketError.setText("There are not enough Tickets left !");
            }

            if (validNumberOfTickets) {
                GUIpayment payment = new GUIpayment(this, true, customer, sessionsList.get(sessionsDate.getSelectedIndex()), Integer.parseInt(numberOfTickets.getText()), sessionsList.get(sessionsDate.getSelectedIndex()).getPrice());
                if (payment.getPaymentComplete()) {
                    if (payment.getEmail() != null && payment.getId() != null) {
                        JOptionPane.showMessageDialog(this, "The purchase of your Ticket(s) has been completed successfully.\n\nAn Email has been sent to " + payment.getEmail() + " with your Ticket(s)", "Purchase complete !", JOptionPane.INFORMATION_MESSAGE);
                        MailIo pdf = new MailIo();
                        for (Movie movies : moviesList) {
                            if (movies.getID() == sessionsList.get(sessionsDate.getSelectedIndex()).getIDMovie()) {
                                try {
                                    pdf.envoyer_reservation(payment.getEmail(), payment.getId(), movies.getName(), sessionsList.get(sessionsDate.getSelectedIndex()).getDate(), Integer.parseInt(numberOfTickets.getText()), sessionsList.get(sessionsDate.getSelectedIndex()).getRoom(), sessionsList.get(sessionsDate.getSelectedIndex()).getPrice());
                                } catch (DocumentException ex) {
                                    Logger.getLogger(GUImovieTheatre.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        dispose();
                    }
                }
            }
        } else if (event.getSource() == back) {
            for (Component component : panel.getComponents()) {
                component.setVisible(true);
            }
            purchase.setVisible(false);
            shopping.setVisible(false);
            back.setVisible(false);
        } else {
            ticketError.setText("Please enter a number of tickets !");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        if (sessionsDate.getSelectedIndex() != -1) {
            try {
                remainingTickets.setText(mysql.getNumberTickets(sessionsList.get(sessionsDate.getSelectedIndex()).getRoom(), sessionsList.get(sessionsDate.getSelectedIndex()).getID()) + " Tickets remaining !");
            } catch (SQLException ex) {
                Logger.getLogger(GUImovieTheatre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
