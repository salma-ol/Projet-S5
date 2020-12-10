/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Signup;
import Modele.Database;
import DateCalendar.DateLabelFormatter;
import java.awt.Color;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.ImageIcon;
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

/**
 *
 * @author ahaz1
 */
public class GUIsignup extends JDialog{

    private static final Database mysql = new Database();

    private static final Calendar deadLine = Calendar.getInstance();

    private static boolean registered = false;

    private final JPanel panel = new JPanel();

    private final JLabel registrationFailed = new JLabel();
    private final JLabel accueil = new JLabel("Here is the beginning of the end");

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
        setSize(900, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() {
        
        JLabel clap = new JLabel("Confirm");
        clap.setIcon(new ImageIcon("clap.png"));
        clap.setOpaque(true);
        
        JLabel look2 = new JLabel(new ImageIcon("bande.png"));
        look2.setOpaque(true);
        panel.setLayout(null);
        panel.setBackground(Color.white);

        calendar();
        JLabel text;
        
        panel.add(accueil).setBounds(100, 10, 500, 40);
        accueil.setFont(new java.awt.Font("Times New Roman", 3, 35));
        
        panel.add(look2).setBounds(650, 0, 220, 500);
        
        text = new JLabel("Username :");
        text.setFont(new java.awt.Font("Times New Roman", 3, 15));
        panel.add(text).setBounds(30, 100, 80, 20);
        panel.add(id).setBounds(30, 120, 200, 20);
        
        text = new JLabel("Firstname :");
        text.setFont(new java.awt.Font("Times New Roman", 3, 15));
        panel.add(text).setBounds(260, 100, 80, 20);
        panel.add(firstname).setBounds(260, 120, 200, 20);
        
        text = new JLabel("Lastname :");
        text.setFont(new java.awt.Font("Times New Roman", 3, 15));
        panel.add(text).setBounds(500, 100, 80, 20);
        panel.add(lastname).setBounds(500, 120, 200, 20);
        
        text = new JLabel("Email :");
        text.setFont(new java.awt.Font("Times New Roman", 3, 15));
        panel.add(text).setBounds(260, 170, 80, 20);
        panel.add(email).setBounds(260, 190, 200, 20);
        
        text = new JLabel("Password :");
        text.setFont(new java.awt.Font("Times New Roman", 3, 15));
        panel.add(text).setBounds(500, 170, 80, 20);
        panel.add(password).setBounds(500, 190, 200, 20);
        
        text = new JLabel("Verify Password :");
        text.setFont(new java.awt.Font("Times New Roman", 3, 15));
        panel.add(text).setBounds(500, 250, 200, 20);
        panel.add(verifyPassword).setBounds(500, 270, 200, 20);
        
        text = new JLabel("Birthday :");
        text.setFont(new java.awt.Font("Times New Roman", 3, 15));
        panel.add(text).setBounds(30, 170, 80, 20);
        panel.add(birthday).setBounds(30, 190, 200, 20);

        confirm.addActionListener(new Signup(this));
        cancel.addActionListener(new Signup(this));

        panel.add(confirm).setBounds(41, 385, 160, 20);
        confirm.setBackground(Color.BLACK);
        confirm.setBorder(null);
        confirm.setForeground(Color.white);
        confirm.setFont(new java.awt.Font("Times New Roman", 3, 30));
        
        panel.add(clap).setBounds(30, 250, 185, 180);
        panel.add(cancel).setBounds(500, 400, 100, 20);
        panel.add(registrationFailed).setBounds(300, 400, 200, 20);

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

    public Database getMysql() {
        return mysql;
    }

    public Calendar getDeadLine() {
        return deadLine;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JLabel getRegistrationFailed() {
        return registrationFailed;
    }

    public JTextField getId() {
        return id;
    }

    public JTextField getEmail() {
        return email;
    }

    public JTextField getFirstname() {
        return firstname;
    }

    public JTextField getLastname() {
        return lastname;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public JPasswordField getVerifyPassword() {
        return verifyPassword;
    }

    public JDatePickerImpl getBirthday() {
        return birthday;
    }

    public JButton getConfirm() {
        return confirm;
    }

    public JButton getCancel() {
        return cancel;
    }

    public void setRegistered(boolean registered) {
        GUIsignup.registered = registered;
    }
}