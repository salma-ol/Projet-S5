/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Customers;

import DBMySQL.DataBase;
import DateCalendar.DateLabelFormatter;
import Movie_Theatre.MailIo;
import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.JOptionPane;

/**
 *
 * @author ahaz1
 */
public class GUIsignup extends JDialog implements ActionListener {

    private static final DataBase mysql = new DataBase();

    private static final Calendar deadLine = Calendar.getInstance();

    private static boolean registered = false;

    private final JPanel panel = new JPanel();

    private final JLabel registrationFailed = new JLabel();

    private final JTextField id = new JTextField(25);
    private final JTextField email = new JTextField(25);
    private final JTextField firstname = new JTextField(25);
    private final JTextField lastname = new JTextField(25);
    private final JPasswordField password = new JPasswordField(25);
    private final JPasswordField verifyPassword = new JPasswordField(25);

    private JDatePickerImpl birthday;

    private final JButton confirm = new JButton("CONFIRM");
    private final JButton cancel = new JButton("CANCEL");

    private void build() {
        setTitle("Sign up");
        setSize(500, 710);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() {
        panel.setLayout(null);
        panel.setBackground(Color.blue);

        calendar();

        panel.add(new JLabel("Username :")).setBounds(77, 60, 80, 20);
        panel.add(id).setBounds(210, 60, 200, 20);
        panel.add(new JLabel("Password :")).setBounds(77, 125, 80, 20);
        panel.add(password).setBounds(210, 125, 200, 20);
        panel.add(new JLabel("Verify Password :")).setBounds(60, 190, 200, 20);
        panel.add(verifyPassword).setBounds(208, 190, 200, 20);
        panel.add(new JLabel("Email :")).setBounds(88, 255, 80, 20);
        panel.add(email).setBounds(212, 255, 200, 20);
        panel.add(new JLabel("Firstname :")).setBounds(76, 320, 80, 20);
        panel.add(firstname).setBounds(210, 320, 200, 20);
        panel.add(new JLabel("Lastname :")).setBounds(76, 385, 80, 20);
        panel.add(lastname).setBounds(210, 385, 200, 20);
        panel.add(new JLabel("Birthday :")).setBounds(80, 450, 80, 20);
        panel.add(birthday).setBounds(211, 450, 200, 20);

        confirm.addActionListener(this);
        cancel.addActionListener(this);

        panel.add(confirm).setBounds(125, 525, 100, 20);
        panel.add(cancel).setBounds(265, 525, 100, 20);
        panel.add(registrationFailed).setBounds(165, 595, 200, 20);

        return panel;
    }

    private void calendar() {
        deadLine.add(Calendar.YEAR, -3);

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        birthday = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), properties), new DateLabelFormatter());
    }

    public GUIsignup(JFrame frame, boolean modal) {
        super(frame, modal);
        build();
    }

    public boolean getRegistered() {
        return registered;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        int code;
        if (event.getSource() == cancel) {
            dispose();
        } else if (event.getSource() == confirm && id.getText().length() != 0 && password.getPassword().length != 0 && verifyPassword.getPassword().length != 0 && email.getText().length() != 0 && firstname.getText().length() != 0 && lastname.getText().length() != 0 && birthday.getModel().getValue() != null) {
            if (birthday.getJFormattedTextField().getText().compareTo(new SimpleDateFormat("yyyy-MM-dd").format(deadLine.getTime())) > 0) {
                registrationFailed.setText("You are too young to be registered !");
            } else if (!new String(password.getPassword()).equals(new String(password.getPassword()))) {
                registrationFailed.setText("Wrong Password ! Please try again...");
            } else if (email.getText().length() - email.getText().replace("@", "").length() != 1 || email.getText().length() - email.getText().replace(".", "").length() < 1 || email.getText().length() < 5) {
                registrationFailed.setText("Invalid Email ! Please try again...");
            } else {
                try {
                    if (mysql.checkUniqueField(id.getText(), email.getText())) {
                        MailIo test = new MailIo();
                        int confirmation = test.envoyer_confirmation(email.getText(), firstname.getText(), lastname.getText());
                        do {
                            code = 0;
                            try {
                                code = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the code", "Verify authentification", JOptionPane.INFORMATION_MESSAGE));
                            } catch (NumberFormatException error) {
                            }
                            if (confirmation != code) {
                                JOptionPane.showMessageDialog(this, "Your code is not valable", "Try again", JOptionPane.ERROR_MESSAGE);
                                registered = true;
                            }
                        } while (code != confirmation);
                        mysql.addMemberCustomer(id.getText(), new String(password.getPassword()), email.getText(), firstname.getText(), lastname.getText(), Date.valueOf(birthday.getJFormattedTextField().getText()));
                        System.out.println("Member registered !");
                        registered = true;
                        dispose();
                    } else {
                        registrationFailed.setText("ID or Email is already use ! Please try again...");
                    }
                } catch (SQLException | NoSuchAlgorithmException | DocumentException ex) {
                    Logger.getLogger(GUIsignup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            registrationFailed.setText("Please complete all fields !");
        }
    }
}
