/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.EmployeeController;
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class GUIemployee extends JFrame {

    private final JPanel panel = new JPanel();

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final JRadioButton addRemoveMovies = new JRadioButton("Add/Remove Movies", true);
    private final JRadioButton addRemoveSessions = new JRadioButton("Add/Remove Sessions");
    private final JRadioButton changeRoom = new JRadioButton("Change the Room of a Movie Session");
    private final JRadioButton checkData = new JRadioButton("Check the DataBase Data");

    private final JButton confirm = new JButton("CONFIRM");
    private final JButton cancel = new JButton("CANCEL");
    
     private JLabel choice = new JLabel("Make your Choice");

    private void build() throws ClassNotFoundException, SQLException {
        setTitle("Employee Interface");
        setSize(470, 470);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() {
        panel.setLayout(null);
        panel.setBackground(new Color(235, 220, 240));

        addRemoveMovies.setBackground(new Color(235, 220, 240));
        addRemoveSessions.setBackground(new Color(235, 220, 240));
        changeRoom.setBackground(new Color(235, 220, 240));
        checkData.setBackground(new Color(235, 220, 240));
        
        addRemoveMovies.setOpaque(true);
        addRemoveSessions.setOpaque(true);
        changeRoom.setOpaque(true);
        checkData.setOpaque(true);
        
        buttonGroup.add(addRemoveMovies);
        buttonGroup.add(addRemoveSessions);
        buttonGroup.add(changeRoom);
        buttonGroup.add(checkData);
        
        addRemoveMovies.setBorder(BorderFactory.createLineBorder(Color.black));
        addRemoveSessions.setBorder(BorderFactory.createLineBorder(Color.black));
        changeRoom.setBorder(BorderFactory.createLineBorder(Color.black));
        checkData.setBorder(BorderFactory.createLineBorder(Color.black));

        panel.add(choice).setBounds(110, 30, 400, 30);
        choice.setFont(new java.awt.Font("Times New Roman", 3, 30));
        
        panel.add(addRemoveMovies).setBounds(159, 120, 145, 20);
        panel.add(addRemoveSessions).setBounds(156, 160, 155, 20);
        panel.add(changeRoom).setBounds(115, 200, 240, 20);
        panel.add(checkData).setBounds(150, 240, 170, 20);

        confirm.addActionListener(new EmployeeController(this));
        cancel.addActionListener(new EmployeeController(this));

        panel.add(confirm).setBounds(185, 320, 100, 20);

        return panel;
    }

    public GUIemployee() throws ClassNotFoundException, SQLException {
        super();
        build();
    }

    public JPanel getPanel() {
        return panel;
    }

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public JRadioButton getAddRemoveMovies() {
        return addRemoveMovies;
    }

    public JRadioButton getAddRemoveSessions() {
        return addRemoveSessions;
    }

    public JRadioButton getChangeRoom() {
        return changeRoom;
    }

    public JRadioButton getCheckData() {
        return checkData;
    }

    public JButton getConfirm() {
        return confirm;
    }

    public JButton getCancel() {
        return cancel;
    } 
}
