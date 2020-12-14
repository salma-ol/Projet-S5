/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.GUICheckData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author DELL
 */
public class CheckData implements ActionListener, ItemListener {

    private final GUICheckData check;

    public CheckData(GUICheckData check) {
        this.check = check;
    }

    @Override
    public void itemStateChanged(ItemEvent ae) {
        /*
         If the customers JComboBox is modified: 
            we create a DefaultCategoryDataset 
            for every movie: 
                we set dataset with the purchases of the customer for the movie, the customer name and the movie name 
            We create variables for the title, lengend and the x axis legend 
            we add the chart(dataset, title, Legend, xLegend) to our PanelChart() 
        */
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
        
        } 
        /*
         If the movies JComboBox is modified: 
            we create a DefaultCategoryDataset and salesPerSessions matrix
            we set salesPerSession with getSalesOfaMovie(idMovie) here idMovie corresponds 
            to the id of the movie selected in movies JComboBox
            For every session in salesPerSession 
                we set dataset with the session's sales and the session's Id.
            We create variables for the title, lengend and the x axis legend 
            we add the chart(dataset, title, Legend, xLegend) to our PanelChart() 
        */
        else if (ae.getSource() == check.getMovies()) {
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI();
            int[][] salesPerSessions;

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            try {
                salesPerSessions = check.getMysql().getSalesOfaMovie(check.getMoviesList().get(check.getMovies().getSelectedIndex()).getID());
                for (int[] salesPerSession : salesPerSessions) {
                        dataset.addValue(salesPerSession[1], Integer.toString(salesPerSession[0]), " "); 
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

        } 
        /*
         If the movies2 JComboBox is modified: 
            we set salesPerSession with getSalesOfaMovie(idMovie) here idMovie corresponds 
            to the id of th movie selected in movies2 JComboBox
            For every session in salesPerSession 
                we add to allTickets the sales of the sesssion.
            We set guestTickets with getSalesOfaMovieByCustomer(idMovie,idCustomer): here idMovie corresponds to the movie
            selected in movie2 JComboBox and idCustomer corresponds to the "Guest" customer
            We add to dataset 2 values using guestTickets and AllTickets
            We create a variable for the title
            we add the check.pieChart(dataset, title)) to our PanelChart() 
        */
        else if (ae.getSource() == check.getMovies2()) {
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI();

            int allTickets = 0;
            int guestTickets;
            int[][] salesPerSession;
            DefaultPieDataset dataset = new DefaultPieDataset();

            try {
                salesPerSession = check.getMysql().getSalesOfaMovie(check.getMoviesList().get(check.getMovies2().getSelectedIndex()).getID());
                for (int[] salesPerSession1 : salesPerSession) {
                        allTickets += salesPerSession1[1];
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
            } catch (SQLException ex) {
                Logger.getLogger(GUICheckData.class.getName()).log(Level.SEVERE, null, ex);
            }

            check.getPanelChart().validate();

        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == check.getCancel()) {
            check.dispose();
        } 
        /*
        If the "Sales for all movies" is selected: 
            For every movie:
                we set salesPerSession with getSalesOfaMovie(idMovie) here idMovie corresponds 
                to the current movie in our for 
                For every session in salesPerSession 
                    we add to numbTickets the sales of the sesssion.
                We add to dataset (numbTickets, movie Name)
            We create variables for the title, lengend and the x axis legend 
            we add the chart(dataset, title, Legend, xLegend) to our PanelChart() 
        */
        else if (ae.getSource() == check.getMovieTicket()) {
            check.getPanelChart().removeAll();
            check.getPanelChart().updateUI();
            int[][] salesPerSession;
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (int n=0; n<check.getMoviesList().size(); n++) {
                try {
                    salesPerSession = check.getMysql().getSalesOfaMovie(check.getMoviesList().get(n).getID());

                    int numbTickets = 0;
                    for (int[] salesPerSession1 : salesPerSession) {
                            numbTickets += salesPerSession1[1];
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
}
