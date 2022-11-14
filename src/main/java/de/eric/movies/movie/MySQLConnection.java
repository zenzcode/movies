package de.eric.movies.movie;
import de.eric.movies.movie.category.Category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Class.forName;


public class MySQLConnection {
    private Connection con = null;
    private Statement sqlStatement = null;
    private String dbHost = "";
    private String dbPort = "";
    private String dbName = "";
    private String dbUser = "";
    private String dbPass = "";

    public MySQLConnection(String dbHost, String dbPort, String dbName, String dbUser, String dbPass){
        this.dbHost = dbHost;
        this.dbPort = dbPort;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    public void create()
    {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?" + "user=" + dbUser + "&" + "password=" + dbPass);
            sqlStatement = con.createStatement();
            System.out.println("Verbindung erfolgreich hergestellt.");
        } catch(SQLException e){
            System.out.print("Verbindung ncht möglich");
            System.out.print("SQLException"+ e.getMessage());
            System.out.print("SQLState"+ e.getSQLState());
            System.out.print("VendrError"+e.getErrorCode());
        }
    }

    public void close() {
        try{
            if(sqlStatement != null){
                sqlStatement.close();
            }
            if (con != null){
                con.close();
                System.out.println("Datenbank erfolgreich geschlossen");
            }
        }catch (SQLException e){
            System.out.println("SQLException"+ e.getMessage());
            System.out.println("SQLState"+e.getSQLState());
            System.out.println("VendorError"+ e.getErrorCode());
        }
    }

    public void UpdateDB(String sql){
        try{
            sqlStatement.executeUpdate(sql);
        }catch (SQLException e){
            System.out.println("SQLException:"+ e.getMessage());
            System.out.println("SQLState:"+e.getSQLState());
            System.out.println("VendorError"+e.getErrorCode());
        }
    }

    public List<MovieModel> getMoviesWithLimit(int limit) throws SQLException {
        return getMoviesWithLimit(limit, 0);
    }

    public List<MovieModel> getMoviesWithLimit(int limit, int offset) throws SQLException {
        String query =  "SELECT Film.title, Film.description, Film.review_score, FilmCategory.category_id, Film.length, Film.cost, Film.release_year " +
                "FROM film AS Film INNER JOIN film_category AS FilmCategory " +
                "ON film.film_id = FilmCategory.film_id GROUP BY Film.title LIMIT " + limit + " OFFSET " + offset;
        try{
            sqlStatement = con.createStatement();
            List<MovieModel> modelList = new ArrayList<>();
            ResultSet resultSet = sqlStatement.executeQuery(query);

            while(resultSet.next())
            {
                MovieModel movieModel = new MovieModel();
                movieModel.title = resultSet.getString("title");
                movieModel.description = resultSet.getString("description");
                movieModel.price = resultSet.getDouble("cost");
                movieModel.year = resultSet.getInt("release_year");
                movieModel.length = resultSet.getInt("length");
                movieModel.category = Category.values()[resultSet.getInt("category_id") - 1];
                movieModel.ratingStars = (resultSet.getInt("review_score"))*5 / 10;
                modelList.add(movieModel);
            }

            return modelList;
        }catch (SQLException e){
            System.out.println("SQLException:"+e.getMessage());
            System.out.println("SQLState:"+e.getSQLState());
            System.out.println("VendorError"+e.getErrorCode());
            return null;
        } finally {
            sqlStatement.close();
        }
    }

}

