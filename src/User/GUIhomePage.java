/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Customers.GUIsignup;
import Employee.GUIemployee;
import Customers.MemberCustomer;
import Movie_Theatre.GUImovieTheatre;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author ahaz1
 */
public class GUIhomePage extends JFrame implements ActionListener {

    private static User user = null;

    private final JPanel panel = new JPanel();

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final JRadioButton guest = new JRadioButton("GUEST", true);
    private final JRadioButton member = new JRadioButton("MEMBER");
    private final JRadioButton signup = new JRadioButton("SIGN UP");
    private final JRadioButton employee = new JRadioButton("EMPLOYEE");

    private final JButton confirm = new JButton("CONFIRM");

    private void build() {
        setTitle("Who are you ?");
        setSize(800, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 60));
        panel.setBackground(Color.red);

        buttonGroup.add(guest);
        buttonGroup.add(member);
        buttonGroup.add(signup);
        buttonGroup.add(employee);

        panel.add(guest);
        panel.add(signup);
        panel.add(member);
        panel.add(employee);

        confirm.addActionListener(this);

        panel.add(confirm);

        return panel;
    }

    public GUIhomePage() {
        super();
        build();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if ((member.isSelected() || employee.isSelected()) && user == null) {
            GUIlogin login = new GUIlogin(this, true, member.isSelected());
            user = login.getUser();
            if (user != null) {
                confirm.doClick();
            }
        } else if (signup.isSelected()) {
            GUIsignup inscription = new GUIsignup(this, true);
            if (inscription.getRegistered()) {
                member.setSelected(true);
                confirm.doClick();
            }
        } else if (guest.isSelected() || (user != null && member.isSelected() || employee.isSelected())) {
            try {
                if (member.isSelected() || guest.isSelected()) {
                    GUImovieTheatre movieTheatre = new GUImovieTheatre((MemberCustomer) user);
                } else {
                    GUIemployee employeeInterface = new GUIemployee();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GUIhomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            dispose();
        }
    }
}
