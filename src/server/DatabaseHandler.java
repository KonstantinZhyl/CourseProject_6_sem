package server;

import Models.*;
import org.json.simple.JsonObject;
import org.json.simple.JsonArray;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseHandler extends Configs {
    private static Connection dbConnection;
    private static Configs config = new Configs();
    protected int count;


    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + config.dbHost + ":"
                + config.dbPort + "/" + config.dbName
                + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, config.dbUser, config.dbPass);
        return dbConnection;
    }

    public static boolean dbCheckLoginExists(String login) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT login FROM " + USER_TABLE);
        while (rs.next()) {
            if (login.equals(rs.getString("login"))) {
                return true;
            }
        }
        return false;
    }

    public static boolean dbCheckUserExistance(String login, String password) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT login, password FROM " + USER_TABLE);
        while (rs.next()) {
            if (login.equals(rs.getString("login")) && password.equals(rs.getString("password"))) {
                return true;
            }
        }
        return false;
    }

    public boolean dbInsertUser(JsonObject object) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + USER_TABLE + "(login, password, name, surname, email, role, birthdate)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setString(1, object.getString("login"));
        prSt.setString(2, object.getString("password"));
        prSt.setString(3, object.getString("name"));
        prSt.setString(4, object.getString("surname"));
        prSt.setString(5, object.getString("email"));
        prSt.setString(6, object.getString("role"));
        prSt.setDate(7, Date.valueOf(object.getString("birthdate")));
        prSt.executeUpdate();
        return true;
    }

//    public boolean dbGetUser(int user_id) throws SQLException, ClassNotFoundException {
//        Connection connection = getDbConnection();
//        Statement stmt = connection.createStatement();
//        ResultSet rs;
//        JsonObject result = new JsonObject();
//        JsonArray result_array = new JsonArray();
//        Map object;
//
//        rs = stmt.executeQuery("SELECT id, name, surname, login, password, role, email, birthdate, diet_id FROM " + USER_TABLE);
//        while (rs.next()) {
//            object = new LinkedHashMap(4);
//            object.put("id", rs.getInt("id"));
//            object.put("type", rs.getString("type"));
//            object.put("description", rs.getString("description"));
//            object.put("calories", rs.getInt("calories"));
//            result_array.add(object);
//        }
//        return result;
//    }

    public boolean dbInsertIndicator(JsonObject object) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + INDICATORS_TABLE + "(weight, height, pulse, last_updated, user_id)" +
                " VALUES(?, ?, ?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setInt(1, object.getInteger("weight"));
        prSt.setInt(2, object.getInteger("height"));
        prSt.setInt(3, object.getInteger("pulse"));
        prSt.setDate(4, Date.valueOf(object.getString("last_updated")));
        prSt.setInt(5, object.getInteger("user_id"));
        prSt.executeUpdate();
        return true;
    }

    public boolean dbInsertActivity(JsonObject object) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + ACTIVITY_TABLE + "(user_id, steps, training_calories, sleep, activity_calories, training_id)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setInt(1, object.getInteger("user_id"));
        prSt.setInt(2, object.getInteger("steps"));
        prSt.setInt(2, object.getInteger("training_calories"));
        prSt.setInt(4, object.getInteger("sleep"));
        prSt.setInt(5, object.getInteger("activity_calories"));
        prSt.setInt(6, object.getInteger("training_id"));
        prSt.executeUpdate();
        return true;
    }

    public boolean dbInsertExercise(JsonObject object) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + EXERCISE_TABLE + "(exercise_id, training_id, count)" +
                " VALUES(?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setInt(1, object.getInteger("exercise_id"));
        prSt.setInt(2, object.getInteger("training_id"));
        prSt.setInt(3, object.getInteger("count"));
        prSt.executeUpdate();
        return true;
    }

    public boolean dbInsertExerciseInfo(JsonObject object) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + EXERCISE_INFO_TABLE + "(name, count, calories_for_one)" +
                " VALUES(?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setString(1, object.getString("name"));
        prSt.setInt(2, object.getInteger("count"));
        prSt.setInt(3, object.getInteger("calories_for_one"));
        prSt.executeUpdate();
        return true;
    }

    public boolean dbInsertTraining(JsonObject object) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + TRAINING_TABLE + "(training_id, duration, calories)" +
                " VALUES(?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setInt(1, object.getInteger("training_id"));
        prSt.setInt(2, object.getInteger("duration"));
        prSt.setInt(3, object.getInteger("calories"));
        prSt.executeUpdate();
        return true;
    }

    public boolean dbInsertTrainingInfo(JsonObject object) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + TRAINING_INFO_TABLE + "(description, type, intensity)" +
                " VALUES(?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setString(1, object.getString("description"));
        prSt.setString(2, object.getString("type"));
        prSt.setInt(3, object.getInteger("intensity"));
        prSt.executeUpdate();
        return true;
    }

    public boolean dbDelRow(int id, String table_name) throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String delete = "DELETE FROM " + table_name + " WHERE id = ?";
        PreparedStatement prSt = connection.prepareStatement(delete);
        prSt.setInt(1, id);
        prSt.executeUpdate();
        return true;
    }



    public boolean dbUpdateDiet(int id, String type, String description, int calories)
            throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String update = "UPDATE " + DIET_TABLE + " SET type = ?, description = ?, calories = ? WHERE id = ?";
        PreparedStatement prSt = connection.prepareStatement(update);
        prSt.setString(1, type);
        prSt.setString(2, description);
        prSt.setInt(3, calories);
        prSt.setInt(4, id);
        prSt.executeUpdate();
        return true;
    }

    public boolean dbUpdateTrainingInfo(int id, String description, String type, int intensity)
            throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String update = "UPDATE " + TRAINING_INFO_TABLE + " SET description = ?, type = ?, intensity = ? WHERE id = ?";
        PreparedStatement prSt = connection.prepareStatement(update);
        prSt.setString(1, description);
        prSt.setString(2, type);
        prSt.setInt(3, intensity);
        prSt.setInt(4, id);
        prSt.executeUpdate();
        return true;
    }

    public boolean dbUpdateExerciseInfo(int id, String name, int count, int calories_for_one)
            throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String update = "UPDATE " + EXERCISE_INFO_TABLE + " SET name = ?, count = ?, calories_for_one = ? WHERE id = ?";
        PreparedStatement prSt = connection.prepareStatement(update);
        prSt.setString(1, name);
        prSt.setInt(2, count);
        prSt.setInt(3, calories_for_one);
        prSt.setInt(4, id);
        prSt.executeUpdate();
        return true;
    }

    public int getUserIdByLogin(String login) throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT id FROM " + USER_TABLE + " WHERE login = " + login);

        int result = -1;
        while (rs.next()) {
            result = rs.getInt("id");
            break;
        }
        return result;
    }

    public JsonObject dbGetAllDiet() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs;
        JsonObject result = new JsonObject();
        JsonArray result_array = new JsonArray();
        Map object;

        rs = stmt.executeQuery("SELECT id, type, description, calories FROM " + DIET_TABLE);
        while (rs.next()) {
            object = new LinkedHashMap(4);
            object.put("id", rs.getInt("id"));
            object.put("type", rs.getString("type"));
            object.put("description", rs.getString("description"));
            object.put("calories", rs.getInt("calories"));
            result_array.add(object);
        }
        return result;
    }

    public JsonObject dbGetAllProducts() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs;
        JsonObject result = new JsonObject();
        JsonArray result_array = new JsonArray();
        Map object;

        rs = stmt.executeQuery("SELECT id, name, calories, protein, fat, carbons FROM " + PRODUCT_TABLE);
        while (rs.next()) {
            object = new LinkedHashMap(6);
            object.put("id", rs.getInt("id"));
            object.put("name", rs.getString("name"));
            object.put("calories", rs.getString("calories"));
            object.put("protein", rs.getInt("protein"));
            object.put("fat", rs.getInt("fat"));
            object.put("carbons", rs.getInt("carbons"));
            result_array.add(object);
        }
        result.put("Products", result_array);
        return result;
    }

    public JsonObject dbGetAllExersises() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs;
        JsonObject result = new JsonObject();
        JsonArray result_array = new JsonArray();
        Map object;

        rs = stmt.executeQuery("SELECT id, name, count, calories_for_one  FROM " + EXERCISE_INFO_TABLE);
        while (rs.next()) {
            object = new LinkedHashMap(4);
            object.put("id", rs.getInt("id"));
            object.put("name", rs.getString("name"));
            object.put("", rs.getInt("count"));
            object.put("id", rs.getInt("calories_for_one"));
            result_array.add(object);
        }
        result.put("Exercises", result_array);
        return result;
    }

    public JsonObject dbGetAllTrainings() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs;
        JsonObject result = new JsonObject();
        JsonArray result_array = new JsonArray();
        Map object;

        rs = stmt.executeQuery("SELECT id, description, type, intensity FROM " + TRAINING_INFO_TABLE);
        while (rs.next()) {
            object = new LinkedHashMap(4);
            object.put("id", rs.getInt("id"));
            object.put("description", rs.getString("description"));
            object.put("type", rs.getString("type"));
            object.put("intensity", rs.getInt("intensity"));
            result_array.add(object);
        }
        result.put("Trainings", result_array);
        return result;
    }

    public JsonObject dbGetAllExersises(int user_id, int exercise_id) throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs;
        JsonObject result = new JsonObject();
        JsonArray result_array = new JsonArray();
        Map object;

        rs = stmt.executeQuery("select a.id, a.count from " + EXERCISE_TABLE + " as a inner join " +
                TRAINING_TABLE + " as b on a.training_id = b.id inner join " + ACTIVITY_TABLE + " as c " +
                "on c.training_id = b.id WHERE c.user_id = " + Integer.toString(user_id) + " and " +
                "a.exercise_id = " + Integer.toString(exercise_id));

        while (rs.next()) {
            object = new LinkedHashMap(2);
            object.put("id", rs.getInt("id"));
            object.put("count", rs.getInt("count"));
            result_array.add(object);
        }
        result.put("Exercises", result_array);
        return result;
    }
}
