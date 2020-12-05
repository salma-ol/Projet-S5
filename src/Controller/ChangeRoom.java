/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author noemi
 */
public class ChangeRoom implements ActionListener, ItemListener{
    
    private final GUIchangeRoom change;
    
    public ChangeRoom(GUIchangeRoom change){
        this.change = change;
    }
    
    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getSource() == change.getMovies()) {
            change.getSessions().removeAllItems();
            try {
                change.setSessionsList( change.getMysql().getSessions(change.getMoviesList().get(change.getMovies().getSelectedIndex()).getID()));
                for (Session session : change.getSessionsList()) {
                    change.getSessions().addItem(session.getDate());
                }
                change.getCapacity().setText("Room: " + change.getSessionsList().get(change.getSessions().getSelectedIndex()).getRoom() + "         Capacity: " + change.getMysql().getCapacity(change.getSessionsList().get(change.getSessions().getSelectedIndex()).getRoom()));
                change.getNewCapacity().setText("Capacity: " + change.getMysql().getCapacity(change.getRooms().getItemAt(change.getRooms().getSelectedIndex())));
            } catch (SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getSource() == change.getSessions() && change.getSessions().getSelectedIndex() != -1) {
            change.getRooms().removeAllItems();
            for (Integer room : change.getRoomsList()) {
                if (room != change.getSessionsList().get(change.getSessions().getSelectedIndex()).getRoom()) {
                    change.getRooms().addItem(room);
                }
            }
            try {
                change.getCapacity().setText("Room: " + change.getSessionsList().get(change.getSessions().getSelectedIndex()).getRoom() + "         Capacity: " + change.getMysql().getCapacity(change.getSessionsList().get(change.getSessions().getSelectedIndex()).getRoom()));
                change.getNewCapacity().setText("Capacity: " + change.getMysql().getCapacity(change.getRooms().getItemAt(change.getRooms().getSelectedIndex())));
            } catch (SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getSource() == change.getRooms() && change.getRooms().getSelectedIndex() != -1) {
            try {
                change.getNewCapacity().setText("Capacity: " + change.getMysql().getCapacity(change.getRooms().getItemAt(change.getRooms().getSelectedIndex())));
            } catch (SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == change.getCancel()) {
            change.dispose();
        }
        else if (change.getMovies().getItemCount() != 0 && event.getSource() == change.getConfirm() ) {
            try {
                if (change.getMysql().changeRoom(change.getSessionsList().get(change.getSessions().getSelectedIndex()), Integer.parseInt(change.getNewCapacity().getText().split(" ")[1]), Integer.parseInt(change.getRooms().getSelectedItem().toString()))) {
                    System.out.println("Successful room change");
                    change.dispose();
                } else {
                    change.getErrorMessage().setText("Capacity of the New Room is not sufficient to accommodate the number of seats sold !");
                }
            } catch (SQLException ex) {
                Logger.getLogger(GUIemployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (event.getSource() == change.getGraph()){
            int[][] salesPerSession ; 
            try { 
                salesPerSession = change.getMysql().getSalesOfaMovie(change.getMoviesList().get(change.getMovies().getSelectedIndex()).getID()) ; 
                DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 

                for(int i=0; i<salesPerSession.length; i++){
                   for(int j=0 ; j<salesPerSession[i].length ; j++){
                       dataset.addValue(salesPerSession[i][j], Integer.toString(salesPerSession[i][0]), " "); 
                   }
                }
                JFreeChart barChart = ChartFactory.createBarChart("Vente pour chaque Session", "session", 
                  "Nombre de place vendue", dataset, PlotOrientation.VERTICAL, true, true, false); 
                ChartFrame frame =new ChartFrame("Bar Chart for the number of tickets sold by session",barChart) ;  
                frame.setVisible(true) ; 
                frame.setSize(400, 400);
                frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
            }catch (SQLException ex) {
                Logger.getLogger(GUIchangeRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
