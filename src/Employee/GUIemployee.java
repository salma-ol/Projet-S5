/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author ahaz1
 */
public class GUIemployee extends JFrame implements ActionListener {//, ItemListener {

    private final JPanel panel = new JPanel();

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final JRadioButton addRemoveMovies = new JRadioButton("Add/Remove Movies", true);
    private final JRadioButton addRemoveSessions = new JRadioButton("Add/Remove Sessions");
    private final JRadioButton changeRoom = new JRadioButton("Change the Room of a Movie Session");
    private final JRadioButton selectDiscount = new JRadioButton("Choose a discount");
    private final JRadioButton checkData = new JRadioButton("Check the DataBase Data");


    private final JButton confirm = new JButton("CONFIRM");
    private final JButton cancel = new JButton("CANCEL");

    private void build() throws ClassNotFoundException, SQLException {
        setTitle("Employee Interface");
        setSize(600, 360);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(buildContentPane());

        setVisible(true);
    }

    private JPanel buildContentPane() {
        panel.setLayout(null);
        panel.setBackground(Color.green);

        buttonGroup.add(addRemoveMovies);
        buttonGroup.add(addRemoveSessions);
        buttonGroup.add(changeRoom);
        buttonGroup.add(selectDiscount);
        buttonGroup.add(checkData);

        panel.add(addRemoveMovies).setBounds(80, 50, 145, 20);
        panel.add(addRemoveSessions).setBounds(380, 50, 155, 20);
        panel.add(changeRoom).setBounds(180, 120, 240, 20);
        panel.add(selectDiscount).setBounds(80, 190, 135, 20);
        panel.add(checkData).setBounds(345, 190, 170, 20);

        confirm.addActionListener(this);
        cancel.addActionListener(this);

        panel.add(confirm).setBounds(240, 260, 100, 20);

        return panel;
    }

    public GUIemployee() throws ClassNotFoundException, SQLException {
        super();
        build();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == cancel) {
            dispose();
        } else if (addRemoveMovies.isSelected()) {
             try {
                GUIaddRemoveMovies modifyMovies = new GUIaddRemoveMovies(this, true);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (addRemoveSessions.isSelected()) {
            try {
                GUIaddRemoveSessions modifySessions = new GUIaddRemoveSessions(this, true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (changeRoom.isSelected()) {
            try {
                GUIchangeRoom tryChangeRoom = new GUIchangeRoom(this, true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
