/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.GUICheckData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
=======
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
>>>>>>> main
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author DELL
 */
<<<<<<< HEAD
public class CheckData implements ActionListener{
    
    private final GUICheckData check ; 
=======
public class CheckData implements ActionListener, ItemListener {

    private final GUICheckData check;
>>>>>>> main

    public CheckData(GUICheckData check) {
        this.check = check;
    }
<<<<<<< HEAD
    
    @Override
    public void actionPerformed(ActionEvent ae) {
       if(ae.getSource() == check.getMovieTicket()){
           check.getPanelChart().removeAll();
           check.getPanelChart().updateUI();
           int[][] salesPerSession ; 
           DefaultCategoryDataset dataset = new DefaultCategoryDataset();
             
            for(int n=0; n<check.getMoviesList().size() ; n++){
               try { 
                   salesPerSession = check.getMysql().getSalesOfaMovie(check.getMoviesList().get(n).getID()) ;
               
                    int numbTickets = 0 ; 
                    for(int i=0; i<salesPerSession.length; i++){
                       for(int j=0 ; j<salesPerSession[i].length ; j++){
                           numbTickets +=  salesPerSession[i][j] ;  
                       }
                    }
                    dataset.addValue(numbTickets, check.getMoviesList().get(n).getName(), " ");
                } catch (SQLException ex) {
                   Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String title = "Sales for all movies" ; 
            String Legend = "Name of the movies"; 
            String xLegend = "Tickets sold" ; 
           try {
               check.getPanelChart().add(check.chart(dataset,title,Legend,xLegend)).setBounds(200, 0, 400, 400);
           } catch (SQLException ex) {
               Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
           }
            check.getPanelChart().validate(); 
       }else if(ae.getSource() == check.getCustomerTicket()){
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
             
            for(int i=0; i<check.getMoviesList().size() ; i++){
               try {
                   dataset.addValue( check.getMysql().getSalesOfaMovieByCustomer(check.getMoviesList().get(i).getID(),
                           String.valueOf(check.getCustomers().getSelectedItem())),check.getMoviesList().get(i).getName(), " ");
               } catch (SQLException ex) {
                   Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
            String title = "Tickets bought" ; 
            String Legend = "Name of the movies"; 
            String xLegend = "Number of tickets" ; 
            try {
                check.getPanelChart().add(check.chart(dataset,title,Legend,xLegend)).setBounds(200, 0, 400, 400);
            } catch (SQLException ex) {
                Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
            }
            check.getPanelChart().validate(); 
       }
       else if(ae.getSource() == check.getSessionMovie() ){
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI();
            int[][] salesPerSessions ; 
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI(); 
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            try {
                salesPerSessions = check.getMysql().getSalesOfaMovie(check.getMoviesList().get(check.getMovies().getSelectedIndex()).getID()) ;
                for(int i=0; i<salesPerSessions.length; i++){
                 for(int j=0 ; j<salesPerSessions[i].length ; j++){
                      dataset.addValue(salesPerSessions[i][j], Integer.toString(salesPerSessions[i][0]), " ");
                 }
             }
            } catch (SQLException ex) {
                Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            String title = "Sales for each session" ; 
            String Legend = "Sessions"; 
            String xLegend = "Number of tickets sold" ; 
            try {
                check.getPanelChart().add(check.chart(dataset,title,Legend,xLegend)).setBounds(200, 0, 400, 400);
            } catch (SQLException ex) {
                Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
            }
            check.getPanelChart().validate(); 
     
        }
        else if(ae.getSource() == check.getStatMovie() ){
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI();
            
            int allTickets = 0 ; 
            int guestTickets = 0; 
            int[][] salesPerSession ; 
            DefaultPieDataset dataset = new DefaultPieDataset( );
            try {  
                guestTickets = check.getMysql().getSalesOfaMovieByCustomer(check.getMoviesList().get(check.getMovies().getSelectedIndex()).getID(),"Guest") ;
                dataset.setValue("Guests", guestTickets) ; 
=======

    @Override
    public void itemStateChanged(ItemEvent ae) {

        if (ae.getSource() == check.getCustomers()) {
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (int i = 0; i < check.getMoviesList().size(); i++) {
                try {
                    dataset.addValue(check.getMysql().getSalesOfaMovieByCustomer(check.getMoviesList().get(i).getID(),
                            String.valueOf(check.getCustomers().getSelectedItem())), check.getMoviesList().get(i).getName(), " ");
                } catch (SQLException ex) {
                    Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String title = "Tickets bought";
            String Legend = "Name of the movies";
            String xLegend = "Number of tickets";
            try {
                check.getPanelChart().add(check.chart(dataset, title, Legend, xLegend)).setBounds(200, 0, 400, 400);
            } catch (SQLException ex) {
                Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
            }
            check.getPanelChart().validate();
        } else if (ae.getSource() == check.getMovies()) {
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI();
            int[][] salesPerSessions;
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            try {
                salesPerSessions = check.getMysql().getSalesOfaMovie(check.getMoviesList().get(check.getMovies().getSelectedIndex()).getID());
                for (int[] salesPerSession : salesPerSessions) {
                    for (int j = 0; j < salesPerSession.length; j++) {
                        dataset.addValue(salesPerSession[j], Integer.toString(salesPerSession[0]), " ");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String title = "Sales for each session";
            String Legend = "Sessions";
            String xLegend = "Number of tickets sold";
            try {
                check.getPanelChart().add(check.chart(dataset, title, Legend, xLegend)).setBounds(200, 0, 400, 400);
            } catch (SQLException ex) {
                Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
            }
            check.getPanelChart().validate();

        } else if (ae.getSource() == check.getMovies2()) {
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI();

            int allTickets = 0;
            int guestTickets;
            int[][] salesPerSession;
            DefaultPieDataset dataset = new DefaultPieDataset();

            try {
                salesPerSession = check.getMysql().getSalesOfaMovie(check.getMoviesList().get(check.getMovies2().getSelectedIndex()).getID());
                for (int[] salesPerSession1 : salesPerSession) {
                    for (int j = 0; j < salesPerSession1.length; j++) {
                        allTickets += salesPerSession1[j];
                    }
                }

                guestTickets = check.getMysql().getSalesOfaMovieByCustomer(check.getMoviesList().get(check.getMovies2().getSelectedIndex()).getID(), "Guest");
                dataset.setValue("Members " + (int) (((allTickets - guestTickets) / (double) allTickets) * 100) + "%", allTickets - guestTickets);
                dataset.setValue("Guests " + (int) (((guestTickets) / (double) allTickets) * 100) + "%", guestTickets);
            } catch (SQLException ex) {
                Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
            }

            String title = "Buyers stats (Members/Guests) ";
            try {
                check.getPanelChart().add(check.pieChart(dataset, title)).setBounds(200, 0, 400, 400);
>>>>>>> main
            } catch (SQLException ex) {
                Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
            }

<<<<<<< HEAD
           try {
               salesPerSession = check.getMysql().getSalesOfaMovie(check.getMoviesList().get(check.getMovies().getSelectedIndex()).getID()) ;
                for(int i=0; i<salesPerSession.length; i++){
                    for(int j=0 ; j<salesPerSession[i].length ; j++){
                        allTickets +=  salesPerSession[i][j] ;  
                    }
                }
                dataset.setValue("Members", allTickets-guestTickets) ; 
           } catch (SQLException ex) {
               Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
           }
           String title = "Stat " ; 
           try {
                check.getPanelChart().add(check.pieChart(dataset,title)).setBounds(200, 0, 400, 400);
            } catch (SQLException ex) {
                Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
            }
            check.getPanelChart().validate(); 

        }
    }
    
=======
            check.getPanelChart().validate();

        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == check.getCancel()) {
            check.dispose();
        } else if (ae.getSource() == check.getMovieTicket()) {
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI();
            int[][] salesPerSession;
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (int n = 0; n < check.getMoviesList().size(); n++) {
                try {
                    salesPerSession = check.getMysql().getSalesOfaMovie(check.getMoviesList().get(n).getID());

                    int numbTickets = 0;
                    for (int[] salesPerSession1 : salesPerSession) {
                        for (int j = 0; j < salesPerSession1.length; j++) {
                            numbTickets += salesPerSession1[j];
                        }
                    }
                    dataset.addValue(numbTickets, check.getMoviesList().get(n).getName(), " ");
                } catch (SQLException ex) {
                    Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String title = "Sales for all movies";
            String Legend = "Name of the movies";
            String xLegend = "Tickets sold";
            try {
                check.getPanelChart().add(check.chart(dataset, title, Legend, xLegend)).setBounds(200, 0, 400, 400);
            } catch (SQLException ex) {
                Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
            }
            check.getPanelChart().validate();
        }
    }
>>>>>>> main
}
