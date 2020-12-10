/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Modele.User;
import Controller.HomePage;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author ahaz1
 */
public class GUIhomePage extends JFrame {

    private static User user = null;

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final JButton confirm = new JButton("Confirm");
    private final JRadioButton employee = new JRadioButton("Employee");
    private final JRadioButton guest = new JRadioButton("Guest");
    private final JRadioButton member = new JRadioButton("Member");
    
    private final JRadioButton signup = new JRadioButton("Create an account");
    private final JLabel ugc = new JLabel("Welcome to UGC ECE");
    
    private final JPanel panel = new JPanel();
    private final JLabel background1 = new JLabel(new ImageIcon("fond_home.png"));
    private final JLabel projo = new JLabel(new ImageIcon("projo.png"));
    private final JLabel light = new JLabel(new ImageIcon("light.png"));
    
    private Icon imageIcon = new ImageIcon(this.getClass().getResource("cadre.gif"));
    private final JLabel background = new JLabel(imageIcon);

    private void build() {
        setTitle("Who are you ?");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() {
        panel.setLayout(null);
        panel.setBackground(Color.black);
        
        ugc.setFont(new java.awt.Font("Times New Roman", 3, 48));
        guest.setFont(new java.awt.Font("Times New Roman", 3, 23));
        member.setFont(new java.awt.Font("Times New Roman", 3, 23));
        employee.setFont(new java.awt.Font("Times New Roman", 3, 23));
        signup.setFont(new java.awt.Font("Times New Roman", 3, 20));
        confirm.setFont(new java.awt.Font("Times New Roman", 3, 17));
        
        ugc.setOpaque(true);
        ugc.setBackground(Color.white);
        guest.setBackground(Color.white);
        member.setBackground(Color.white);
        employee.setBackground(Color.white);
        confirm.setBackground(Color.white);
        signup.setBackground(Color.white);
        ugc.setBorder(null);
        
        signup.setBorder(BorderFactory.createLineBorder(Color.black));
        
        buttonGroup.add(guest);
        buttonGroup.add(member);
        buttonGroup.add(employee); 
        buttonGroup.add(signup);
        
        projo.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    panel.setBackground(Color.black);
                    guest.setVisible(true);
                    member.setVisible(true);
                    signup.setVisible(true);
                    employee.setVisible(true);
                    background.setVisible(true);
                    confirm.setVisible(true);
                    panel.add(light).setBounds(176, 255,626,346 );
                    light.setVisible(true);
                }
            });
        
        panel.add(ugc).setBounds(160, 60, 480, 120);
        panel.add(background1).setBounds(0, 0, 800, 250);
        panel.add(background).setBounds(420, 270, 250, 150);
        background.setVisible(false);
        panel.add(guest).setBounds(480, 300, 130, 20);
        guest.setVisible(false);
        panel.add(member).setBounds(480, 340, 130, 20);
        member.setVisible(false);
        panel.add(employee).setBounds(480, 380, 160, 25);
        employee.setVisible(false);
        panel.add(signup).setBounds(400, 460, 190, 20);
        signup.setVisible(false);
        panel.add(confirm).setBounds(600, 450, 100, 40);
        confirm.setVisible(false);
        panel.add(projo).setBounds(0, 255, 176, 340 );
        
        
        

        confirm.addActionListener(new HomePage(this));
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