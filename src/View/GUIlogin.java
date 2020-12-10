/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Login;
import Modele.User;
import Modele.Database;
import java.awt.Color;
import javax.swing.ImageIcon;
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
    private final JLabel paragraph = new JLabel("You are about to enter into a universe of choice");
    
    private final JTextField id = new JTextField(15);
    private final JPasswordField password = new JPasswordField(10);

    private final JButton confirm = new JButton("CONFIRM");
    private final JButton cancel = new JButton("CANCEL");

    private void build() {
        setTitle("Login");
        setSize(545, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() {
        
        JLabel look = new JLabel(new ImageIcon("fond_home.png"));
        look.setOpaque(true);
        
        JLabel look2 = new JLabel(new ImageIcon("fond_home.png"));
        look2.setOpaque(true);
        
        panel.setLayout(null);
        panel.setBackground(new Color(221, 213, 218));
        
        paragraph.setFont(new java.awt.Font("Times New Roman", 3, 22));
        
        panel.add(look).setBounds(15, 0, 30, 300);
        panel.add(look2).setBounds(490, 0, 30, 300);
        panel.add(paragraph).setBounds(55, 5, 440, 40);
        panel.add(new JLabel("Username :")).setBounds(110, 75, 80, 20);
        panel.add(id).setBounds(210, 75, 200, 20);
        panel.add(new JLabel("Password :")).setBounds(110, 125, 80, 20);
        panel.add(password).setBounds(210, 125, 220, 20);

        confirm.addActionListener(new Login(this));
        cancel.addActionListener(new Login(this));

        panel.add(confirm).setBounds(95, 180, 100, 20);
        panel.add(cancel).setBounds(295, 180, 100, 20);
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
