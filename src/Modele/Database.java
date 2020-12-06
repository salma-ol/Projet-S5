/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahaz1
 */
public class Database {

    private static User user;

    private static Connection connection = null;
    private static PreparedStatement state = null;
    private static ResultSet result = null;

    private void connectionDatabase() throws SQLException {
        //System.out.println("Connexion");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_theatre?autoReconnect=true&useSSL=false", "root", "");
    }

    private boolean deconnectionDatabase() throws SQLException {
        //System.out.println("Déconnexion");
        connection.close();
        state.close();
        if (result != null) {
            result.close();
        }
        return true;
    }

    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        sha256.update(password.getBytes());

        byte byteData[] = sha256.digest();

        StringBuilder hashPassword = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            hashPassword.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return hashPassword.toString();
    }

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getCustomer() {
        return user;
    }

    public boolean checkLogin(String idCustomer, String passwordCustomer, boolean isCustomer) throws SQLException, NoSuchAlgorithmException {
        connectionDatabase();

        if (isCustomer) {
            state = connection.prepareStatement("SELECT * from `customers` WHERE ID = ? AND Password = ?");
        } else {
            state = connection.prepareStatement("SELECT * from `employees` WHERE ID = ? AND Password = ?");
        }
        state.setString(1, idCustomer);
        state.setString(2, hashPassword(passwordCustomer));
        result = state.executeQuery();

        if (result.next()) {
            if (isCustomer) {
                user = new MemberCustomer(result.getString("ID"), result.getString("Password"), result.getString("Email"), result.getString("Firstname"), result.getString("Lastname"), result.getDate("Birthday"));
            } else {
                user = new Employee(result.getString("ID"), result.getString("Password"), result.getString("Email"), result.getString("Firstname"), result.getString("Lastname"), result.getDate("Birthday"));
            }
            return deconnectionDatabase();
        }
        return !deconnectionDatabase();
    }

    public boolean checkUniqueField(String idCustomer, String emailCustomer) throws SQLException {
        connectionDatabase();

        state = connection.prepareStatement("SELECT * from `customers` WHERE ID = ? OR Email = ?");
        state.setString(1, idCustomer);
        state.setString(2, emailCustomer);
        result = state.executeQuery();

        if (!result.next()) {
            return deconnectionDatabase();
        }
        return !deconnectionDatabase();
    }

    public void addMemberCustomer(String ID, String password, String email, String firstname, String lastname, Date birthday) throws SQLException, NoSuchAlgorithmException {
        connectionDatabase();

        state = connection.prepareStatement("INSERT INTO `customers` (ID, Password, Email, Firstname, Lastname, Birthday) VALUES (?, ?, ?, ?, ?, ?)");
        state.setString(1, ID);
        state.setString(2, hashPassword(password));
        state.setString(3, email);
        state.setString(4, firstname);
        state.setString(5, lastname);
        state.setDate(6, birthday);
        state.executeUpdate();

        deconnectionDatabase();
    }

    public ArrayList<Movie> loadMovies() throws SQLException {
        connectionDatabase();
        ArrayList<Movie> moviesList = new ArrayList<>();

        state = connection.prepareStatement("SELECT DISTINCT * from `movies` RIGHT JOIN `sessions` ON movies.ID = sessions.ID_Movie GROUP BY movies.ID");
        result = state.executeQuery();

        while (result.next()) {
            moviesList.add(new Movie(result.getInt("ID"), result.getString("Name"), result.getString("Director"), result.getString("Genre"), Time.valueOf(result.getString("Time"))));
        }
        deconnectionDatabase();

        return moviesList;
    }

    public ArrayList<Session> getSessions(int id) throws SQLException {
        connectionDatabase();
        ArrayList<Session> sessionList = new ArrayList<>();

        state = connection.prepareStatement("SELECT * from `sessions` WHERE ID_Movie = ?");
        state.setInt(1, id);
        result = state.executeQuery();

        while (result.next()) {
            sessionList.add(new Session(result.getInt("ID"), result.getInt("ID_Movie"), result.getInt("ID_Room"), result.getTimestamp("Date"), result.getFloat("Price")));
        }
        deconnectionDatabase();

        return sessionList;
    }

    public boolean buyTickets(MemberCustomer customer, Session session, int tickets, float price, Timestamp today) throws SQLException {
        connectionDatabase();

        state = connection.prepareStatement("INSERT INTO `sales` (ID_Customer, ID_Session, Number, Total_Price, Date) VALUES (?, ?, ?, ?, ?)");
        if (customer == null) {
            state.setString(1, "Guest");
        } else {
            state.setString(1, customer.getID());
        }
        state.setInt(2, session.getID());
        state.setInt(3, tickets);
        state.setFloat(4, price * tickets);
        state.setTimestamp(5, today);
        state.executeUpdate();

        return deconnectionDatabase();
    }

    public int getNumberTickets(int room, int id) throws SQLException {
        connectionDatabase();

        state = connection.prepareStatement("SELECT Capacity from `movie_rooms` WHERE movie_rooms.ID = ?");
        state.setInt(1, room);
        result = state.executeQuery();
        result.next();
        int capacity = result.getInt("Capacity");

        state = connection.prepareStatement("SELECT SUM(Number) AS NumberTotal from `sales` WHERE ID_Session = ?");
        state.setInt(1, id);
        result = state.executeQuery();
        try {
            result.next();
            capacity -= result.getInt("NumberTotal");
        } catch (SQLException error) {
        }

        deconnectionDatabase();
        return capacity;
    }

    public ArrayList<Integer> getRoom() throws SQLException {
        connectionDatabase();
        ArrayList<Integer> rooms = new ArrayList<>();

        state = connection.prepareStatement("SELECT ID from `movie_rooms`");
        result = state.executeQuery();
        while (result.next()) {
            rooms.add(result.getInt("ID"));
        }
        deconnectionDatabase();

        return rooms;
    }

    public int getCapacity(int id) throws SQLException {
        connectionDatabase();

        state = connection.prepareStatement("SELECT Capacity from `movie_rooms` INNER JOIN `sessions` ON movie_rooms.ID = ? ");
        state.setInt(1, id);
        result = state.executeQuery();
        result.next();
        int capacity = result.getInt("Capacity");
        deconnectionDatabase();

        return capacity;
    }

    public boolean changeRoom(Session session, int newCapacity, int newId) throws SQLException {
        if (getCapacity(session.getRoom()) - getNumberTickets(session.getRoom(), session.getID()) < newCapacity) {
            connectionDatabase();
            state = connection.prepareStatement("UPDATE `sessions` SET ID_Room = ? WHERE ID = ?");
            state.setInt(1, newId);
            state.setInt(2, session.getID());
            state.executeUpdate();
            return deconnectionDatabase();
        }
        return false;
    }

    public ArrayList<String> loadFuturMovies() throws SQLException {
        File repertoire = new File("./Movies/");
        ArrayList<String> futurMovies = new ArrayList<>(Arrays.asList(repertoire.list()));
        ArrayList<Movie> movies = loadMovies();

        for (Movie movie : movies) {
            if (futurMovies.contains(movie.getName() + ".jpg")) {
                futurMovies.remove(movie.getName() + ".jpg");
            }
        }
        return futurMovies;
    }

    public boolean addMovie(String name, String director, String genre, Time time) throws SQLException {
        connectionDatabase();

        state = connection.prepareStatement("INSERT INTO `movies` (Name, Director, Genre, Time) VALUES (?, ?, ?, ?)");
        state.setString(1, name);
        state.setString(2, director);
        state.setString(3, genre);
        state.setTime(4, time);
        state.executeUpdate();

        return deconnectionDatabase();
    }

    public void removeMovie(String name) throws SQLException {
        connectionDatabase();

        state = connection.prepareStatement("SELECT ID from `movies` WHERE Name = ?");
        state.setString(1, name);
        result = state.executeQuery();
        result.next();
        int id = result.getInt("ID");

        state = connection.prepareStatement("DELETE from `sessions` WHERE sessions.ID_Movie = ?");
        state.setInt(1, id);
        state.executeUpdate();

        state = connection.prepareStatement("DELETE from `movies` WHERE Name = ?");
        state.setString(1, name);
        state.executeUpdate();
        state = connection.prepareStatement("ALTER TABLE `movies` AUTO_INCREMENT=0");
        state.executeUpdate();

        deconnectionDatabase();
    }

    public boolean removeSession(int id, int idMovie) throws SQLException {
        connectionDatabase();

        state = connection.prepareStatement("SELECT * FROM `sales` WHERE ID_Session = ?");
        state.setInt(1, id);
        result = state.executeQuery();
        if (!result.next()) {
            state = connection.prepareStatement("DELETE FROM `sessions` WHERE ID = ?");
            state.setInt(1, id);
            state.executeUpdate();
            state = connection.prepareStatement("ALTER TABLE `sessions` AUTO_INCREMENT=0");
            state.executeUpdate();

            state = connection.prepareStatement("SELECT * from `sessions` WHERE ID_Movie = ?");
            state.setInt(1, idMovie);
            result = state.executeQuery();
            if (!result.next()) {
                state = connection.prepareStatement("DELETE FROM `movies` WHERE ID = ?");
                state.setInt(1, idMovie);
                state.executeUpdate();
                state = connection.prepareStatement("ALTER TABLE `movies` AUTO_INCREMENT=0");
                state.executeUpdate();
            }
            return deconnectionDatabase();
        }
        return !deconnectionDatabase();
    }

    public boolean addSession(String movie, int room, Timestamp date, float price, int idLastMovie) throws SQLException {
        connectionDatabase();

        state = connection.prepareStatement("SELECT ID from `movies` WHERE Name = ?");
        state.setString(1, movie);
        result = state.executeQuery();
        int movieId = idLastMovie + 1;
        if (result.next()) {
            movieId = result.getInt("ID");
        }

        state = connection.prepareStatement("INSERT INTO `sessions` (ID_Movie, ID_Room, Date, Price) VALUES (?, ?, ?, ?)");
        state.setInt(1, movieId);
        state.setInt(2, room);
        state.setTimestamp(3, date);
        state.setFloat(4, price);
        state.executeUpdate();

        return deconnectionDatabase();
    }

    public boolean checkSessions(int id) throws SQLException {
        connectionDatabase();

        ArrayList<Session> sessionList = new ArrayList<>();

        state = connection.prepareStatement("SELECT * from `sessions` WHERE ID_Movie = ?");
        state.setInt(1, id);
        result = state.executeQuery();

        while (result.next()) {
            sessionList.add(new Session(result.getInt("ID"), result.getInt("ID_Movie"), result.getInt("ID_Room"), result.getTimestamp("Date"), result.getFloat("Price")));
        }

        for (Session session : sessionList) {
            state = connection.prepareStatement("SELECT * from `sales` WHERE ID_Session = ?");
            state.setInt(1, session.getID());
            result = state.executeQuery();
            if (result.next()) {
                return !deconnectionDatabase();
            }
        }

        for (Session session : sessionList) {
            state = connection.prepareStatement("DELETE FROM `sessions` WHERE ID = ?");
            state.setInt(1, session.getID());
            state.executeUpdate();
            state = connection.prepareStatement("ALTER TABLE `sessions` AUTO_INCREMENT = 0");
            state.executeUpdate();
        }

        return deconnectionDatabase();
    }

    public ArrayList<String> getSales(String id) throws SQLException {
        connectionDatabase();

        ArrayList<String> sales = new ArrayList<>();

        state = connection.prepareStatement("SELECT movies.Name, sales.Number, sales.Total_Price, sales.Date FROM `movies`, `sales`, `sessions` WHERE movies.ID = sessions.ID_Movie AND sessions.ID = sales.ID_Session AND sales.ID_Customer = ?");
        state.setString(1, id);
        result = state.executeQuery();
        while (result.next()) {
            sales.add(result.getString("movies.Name") + ", " + Integer.toString(result.getInt("sales.Number")) + " Ticket(s), " + Float.toString(result.getFloat("sales.Total_Price")) + "€, Purchase Date: " + result.getTimestamp("sales.Date").toString());
        }
        deconnectionDatabase();
        return sales;
    }
    
    public int[][] getSalesOfaMovie(int idMovie) throws SQLException {
        connectionDatabase();
        
        //Tableau des sessions du film
        ArrayList<Integer> sessionList = new ArrayList<>();

        state = connection.prepareStatement("SELECT * from `sessions` WHERE ID_Movie = ?");
        state.setInt(1, idMovie);
        result = state.executeQuery();

        while (result.next()) {
            sessionList.add(result.getInt("ID")) ;
        }
        
        //tableau des ventes pour chaqye session
        ArrayList<Integer> sales = new ArrayList<>();
        for(int i=0; i<sessionList.size() ; i++){
            state = connection.prepareStatement("SELECT sales.Number FROM `sales`WHERE sales.ID_Session = ?");
            state.setInt(1, sessionList.get(i));
            result = state.executeQuery();
            int tot = 0; 
            while (result.next()) {
                tot += result.getInt("sales.Number");
            }
            sales.add(tot) ; 
        }
        int[][] salesPerSessions = new int[sessionList.size()][2] ;
        for(int i=0; i<sessionList.size();i++){
            salesPerSessions[i][0] = sessionList.get(i) ; 
        }
        for(int i=0;i<sessionList.size(); i++){
            salesPerSessions[i][1] = sales.get(i) ; 
        }
        deconnectionDatabase();
        return salesPerSessions ;
    }
}