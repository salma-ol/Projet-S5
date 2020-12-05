/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author noemi
 */
public class EmployeeController implements ActionListener{
    
     private final GUIemployee change;
    
    public EmployeeController(GUIemployee change){
        this.change = change;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == change.getCancel()) {
            change.dispose();
        } else if (change.getAddRemoveMovies().isSelected()) {
             try {
                GUIaddRemoveMovies modifyMovies = new GUIaddRemoveMovies(change, true);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (change.getAddRemoveSessions().isSelected()) {
            try {
                GUIaddRemoveSessions modifySessions = new GUIaddRemoveSessions(change, true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (change.getChangeRoom().isSelected()) {
            try {
                GUIchangeRoom tryChangeRoom = new GUIchangeRoom(change, true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
