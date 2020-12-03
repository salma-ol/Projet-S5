/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Customers;

import DBMySQL.DataBase;
import Movie_Theatre.Session;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

/**
 *
 * @author ahaz1
 */
public class GUIpayment extends JDialog implements ActionListener {

    private static final DataBase mysql = new DataBase();

    private static boolean paymentComplete = false;
    private static String email;
    private static String id;

    private static MemberCustomer customer;
    private static Session session;
    private static int tickets;
    private static float priceTicket;

    private final JPanel panel = new JPanel();

    private final JLabel invalidCard = new JLabel();

    private final JTextField cardsHolderName = new JTextField(20);
    private final JTextField cardNumber = new JTextField(16);
    private final JTextField cryptogram = new JTextField(10);

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final JRadioButton visa = new JRadioButton("", true);
    private final JRadioButton mastercard = new JRadioButton("");
    private final JRadioButton amex = new JRadioButton("");

    private JButton validate;

    private final JComboBox<Integer> month = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
    private final JComboBox<Integer> year = new JComboBox<>(new Integer[]{Integer.parseInt(new SimpleDateFormat("MM/yy").format(new Date()).split("/")[1]), Integer.parseInt(new SimpleDateFormat("MM/yy").format(new Date()).split("/")[1]) + 1, Integer.parseInt(new SimpleDateFormat("MM/yy").format(new Date()).split("/")[1]) + 2, Integer.parseInt(new SimpleDateFormat("MM/yy").format(new Date()).split("/")[1]) + 3, Integer.parseInt(new SimpleDateFormat("MM/yy").format(new Date()).split("/")[1]) + 4});

    private void build() {
        setTitle("Payment by cards");
        setSize(600, 408);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() {
        panel.setLayout(null);
        panel.setBackground(Color.pink);

        buttonGroup.add(visa);
        buttonGroup.add(mastercard);
        buttonGroup.add(amex);

        if (customer != null) {
            priceTicket -= priceTicket * customer.getDiscount();
        }

        panel.add(visa).setBounds(70, 51, 20, 20);
        panel.add(new JLabel(new ImageIcon("./Payment/visa.png"))).setBounds(75, 10, 100, 100);
        panel.add(mastercard).setBounds(250, 51, 20, 20);
        panel.add(new JLabel(new ImageIcon("./Payment/mastercard.png"))).setBounds(255, 10, 100, 100);
        panel.add(amex).setBounds(430, 51, 20, 20);
        panel.add(new JLabel(new ImageIcon("./Payment/amex.png"))).setBounds(435, 10, 100, 100);

        panel.add(new JLabel("Cardholder's name:")).setBounds(130, 115, 150, 20);
        panel.add(cardsHolderName).setBounds(255, 115, 200, 20);
        panel.add(new JLabel("Card Number:")).setBounds(100, 165, 150, 20);
        panel.add(cardNumber).setBounds(195, 165, 150, 20);
        panel.add(new JLabel("Cryptogram:")).setBounds(360, 165, 100, 20);
        panel.add(cryptogram).setBounds(445, 165, 35, 20);

        panel.add(new JLabel("Expiry Date:")).setBounds(195, 215, 100, 20);
        panel.add(month).setBounds(278, 215, 40, 20);
        panel.add(new JLabel("/")).setBounds(332, 215, 30, 20);
        panel.add(year).setBounds(350, 215, 40, 20);

        validate = new JButton("Buy " + tickets + " Ticket(s) for " + priceTicket * tickets + "€");

        validate.addActionListener(this);

        panel.add(validate).setBounds(195, 265, 200, 20);
        panel.add(invalidCard).setBounds(190, 315, 230, 20);

        return panel;
    }

    public GUIpayment(JFrame frame, boolean modal, MemberCustomer member, Session sessionSelected, int numberOfTickets, float price) {
        super(frame, modal);
        customer = member;
        session = sessionSelected;
        tickets = numberOfTickets;
        priceTicket = price;
        build();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean getPaymentComplete() {
        return paymentComplete;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (cardsHolderName.getText().length() != 0 && cardNumber.getText().length() != 0 && cryptogram.getText().length() != 0) {
            if ((cryptogram.getText().length() != 3 && !amex.isSelected()) || (cryptogram.getText().length() != 4 && amex.isSelected())) {
                invalidCard.setText("You enter an Invalid CVV ! Try again ...");
            } else if (cardNumber.getText().length() != 16) {
                invalidCard.setText("Invalid Card Number, please try again");
            } else {
                try {
                    if (new Date().after(new SimpleDateFormat("MM/yy").parse(month.getSelectedItem().toString() + "/" + year.getSelectedItem().toString()))) {
                        invalidCard.setText("Your card has expired ! Change card !");
                    } else {
                        boolean validFields = true;
                        for (Character digit : cryptogram.getText().toCharArray()) {
                            if ((int) digit < 48 || (int) digit > 57) {
                                validFields = false;
                                break;
                            }
                        }
                        if (validFields) {
                            for (Character digit : cardNumber.getText().toCharArray()) {
                                if ((int) digit < 48 || (int) digit > 57) {
                                    validFields = false;
                                    break;
                                }
                            }
                        }
                        if (validFields) {
                            if (customer == null) {
                                id = "Guest";
                                email = JOptionPane.showInputDialog(this, "Email:", "Enter your Email", JOptionPane.QUESTION_MESSAGE);
                            } else {
                                id = customer.getID();
                                email = customer.getEmail();
                            }
                            if (email != null) {
                                paymentComplete = true;
                                mysql.buyTickets(customer, session, tickets, priceTicket, Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())));
                            }
                            dispose();
                        } else {
                            invalidCard.setText("You enter an Invalid CVV ! Try again ...");
                        }
                    }
                } catch (ParseException | SQLException ex) {
                    Logger.getLogger(GUIpayment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            invalidCard.setText("Complete all fields before purchase !");
        }
    }
}
