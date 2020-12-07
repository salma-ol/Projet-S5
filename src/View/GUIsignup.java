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

        confirm.addActionListener(new Signup(this));
        cancel.addActionListener(new Signup(this));

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