/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.login;
import Modele.User;
import Modele.Database;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ahaz1
 */
public class GUIlogin extends JDialog{

    private static User user = null;
    private static boolean isCustomer;
    private static final Database mysql = new Database();

    private final JPanel panel = new JPanel();

    private final JLabel errorAuthentification = new JLabel("");

    private final JTextField id = new JTextField(15);
    private final JPasswordField password = new JPasswordField(10);

    private final JButton confirm = new JButton("CONFIRM");
    private final JButton cancel = new JButton("CANCEL");

    private void build() {
        setTitle("Login");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() {
        panel.setLayout(null);
        panel.setBackground(Color.blue);

        panel.add(new JLabel("Username :")).setBounds(95, 45, 80, 20);
        panel.add(id).setBounds(195, 45, 200, 20);
        panel.add(new JLabel("Password :")).setBounds(95, 95, 80, 20);
        panel.add(password).setBounds(195, 95, 200, 20);

        confirm.addActionListener(new login(this));
        cancel.addActionListener(new login(this));

        panel.add(confirm).setBounds(95, 150, 100, 20);
        panel.add(cancel).setBounds(295, 150, 100, 20);
        panel.add(errorAuthentification).setBounds(135, 200, 300, 20);
        
        return panel;
    }

    public GUIlogin(JFrame frame, boolean modal, boolean customer) {
        super(frame, modal);
        isCustomer = customer;
        build();
    }

    public User getUser() {
        return user;
    }

    public boolean getIsCustomer() {
        return isCustomer;
    }

    public Database getMysql() {
        return mysql;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JLabel getErrorAuthentification() {
        return errorAuthentification;
    }

    public JTextField getId() {
        return id;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public JButton getConfirm() {
        return confirm;
    }

    public JButton getCancel() {
        return cancel;
    }

    public void setUser(User user) {
        GUIlogin.user = user;
    } 
}
