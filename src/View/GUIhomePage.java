/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Modele.User;
import Controller.homePage;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author ahaz1
 */
public class GUIhomePage extends JFrame {

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

        confirm.addActionListener(new homePage(this));

        panel.add(confirm);

        return panel;
    }

    public GUIhomePage() {
        super();
        build();
    }

    public User getUser() {
        return user;
    }

    public JPanel getPanel() {
        return panel;
    }

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public JRadioButton getGuest() {
        return guest;
    }

    public JRadioButton getMember() {
        return member;
    }

    public JRadioButton getSignup() {
        return signup;
    }

    public JRadioButton getEmployee() {
        return employee;
    }

    public JButton getConfirm() {
        return confirm;
    }

    public void setUser(User user) {
        GUIhomePage.user = user;
    }
}