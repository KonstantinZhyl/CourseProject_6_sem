package server;

import Models.DietEntity;
import Models.ExerciseInfoEntity;
import Models.TrainingInfoEntity;
import Models.UserEntity;

import java.io.IOException;
import java.sql.*;

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

    public boolean dbInsertUser(String login, String password, String name, String surname, String birthdate,
                              String email, String role) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + USER_TABLE + "(login, password, name, surname, email, role, birthdate)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setString(1, login);
        prSt.setString(2, password);
        prSt.setString(3, name);
        prSt.setString(4, surname);
        prSt.setString(5, email);
        prSt.setString(6, role);
        prSt.setDate(7, Date.valueOf(birthdate));
        prSt.executeUpdate();
        return true;
    }

    public boolean dbInsertIndicator(int weight, int height, int pulse,
                              String last_updated, int user_id) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + INDICATORS_TABLE + "(weight, height, pulse, last_updated, user_id)" +
                " VALUES(?, ?, ?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setInt(1, weight);
        prSt.setInt(2, height);
        prSt.setInt(2, pulse);
        prSt.setDate(4, Date.valueOf(last_updated));
        prSt.setInt(5, user_id);
        prSt.executeUpdate();
        return true;
    }

    public boolean dbInsertActivity(int user_id, int steps, int training_calories, int sleep,
                                  int activity_calories, int training_id) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + ACTIVITY_TABLE + "(user_id, steps, training_calories, sleep, activity_calories, training_id)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setInt(1, user_id);
        prSt.setInt(2, steps);
        prSt.setInt(2, training_calories);
        prSt.setInt(4, sleep);
        prSt.setInt(5, activity_calories);
        prSt.setInt(6, training_id);
        prSt.executeUpdate();
        return true;
    }

    public boolean dbInsertExercise(int exercise_id, int training_id, int count) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + EXERCISE_TABLE + "(exercise_id, training_id, count)" +
                " VALUES(?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setInt(1, exercise_id);
        prSt.setInt(2, training_id);
        prSt.setInt(3, count);
        prSt.executeUpdate();
        return true;
    }

    public boolean dbInsertExerciseInfo(String name, int count, int calories_for_one) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + EXERCISE_INFO_TABLE + "(name, count, calories_for_one)" +
                " VALUES(?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setString(1, name);
        prSt.setInt(2, count);
        prSt.setInt(3, calories_for_one);
        prSt.executeUpdate();
        return true;
    }

    public boolean dbInsertTraining(int training_id, int duration, int calories) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + TRAINING_TABLE + "(training_id, duration, calories)" +
                " VALUES(?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setInt(1, training_id);
        prSt.setInt(2, duration);
        prSt.setInt(3, calories);
        prSt.executeUpdate();
        return true;
    }

    public boolean dbInsertTrainingInfo(String description, String type, int intensity) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String insert = "INSERT INTO " + TRAINING_INFO_TABLE + "(description, type, intensity)" +
                " VALUES(?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insert);
        prSt.setString(1, description);
        prSt.setString(2, type);
        prSt.setInt(3, intensity);
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

    public DietEntity[] dbGetAllDiet() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        int count = 0;
        ResultSet rs = stmt.executeQuery("select count(*) from " + DIET_TABLE);
        while (rs.next()) {
            count =  rs.getInt(1);
        }

        rs = stmt.executeQuery("SELECT id, type, description, calories FROM " + DIET_TABLE);
        int i = 0;
        DietEntity[] result = new DietEntity[count];
        while (rs.next()) {
            result[i] = new DietEntity();
            result[i].setId(rs.getInt("id"));
            result[i].setType(rs.getString("type"));
            result[i].setDescription(rs.getString("description"));
            result[i].setCalories(rs.getInt("calories"));
            i++;
        }
        return result;
    }

    public ExerciseInfoEntity[] dbGetAllExersises() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        int count = 0;
        ResultSet rs = stmt.executeQuery("select count(*) from " + EXERCISE_INFO_TABLE);
        while (rs.next()) {
            count =  rs.getInt(1);
        }

        rs = stmt.executeQuery("SELECT id, name, count, calories_for_one  FROM " + EXERCISE_INFO_TABLE);
        int i = 0;
        ExerciseInfoEntity[] result = new ExerciseInfoEntity[count];
        while (rs.next()) {
            result[i] = new ExerciseInfoEntity();
            result[i].setId(rs.getInt("id"));
            result[i].setName(rs.getString("name"));
            result[i].setCount(rs.getInt("count"));
            result[i].setCaloriesForOne(rs.getInt("calories_for_one"));
            i++;
        }
        return result;
    }

    public TrainingInfoEntity[] dbGetAllTrainings() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        int count = 0;
        ResultSet rs = stmt.executeQuery("select count(*) from " + TRAINING_INFO_TABLE);
        while (rs.next()) {
            count =  rs.getInt(1);
        }

        rs = stmt.executeQuery("SELECT id, description, type, intensity FROM " + TRAINING_INFO_TABLE);
        int i = 0;
        TrainingInfoEntity[] result = new TrainingInfoEntity[count];
        while (rs.next()) {
            result[i] = new TrainingInfoEntity();
            result[i].setId(rs.getInt("id"));
            result[i].setDescription(rs.getString("description"));
            result[i].setType(rs.getString("type"));
            result[i].setIntensity(rs.getInt("intensity"));
            i++;
        }
        return result;
    }

    public ExerciseInfoEntity[] dbGetAllExersises(int user_id, int exersice_id) throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        int count = 0;
        ResultSet rs = stmt.executeQuery("select count(*) from " + EXERCISE_INFO_TABLE + "WHERE ");
         while (rs.next()) {
            count =  rs.getInt(1);
        }

        rs = stmt.executeQuery("SELECT id, name, count, calories_for_one  FROM " + EXERCISE_INFO_TABLE);
        int i = 0;
        ExerciseInfoEntity[] result = new ExerciseInfoEntity[count];
        while (rs.next()) {
            result[i] = new ExerciseInfoEntity();
            result[i].setId(rs.getInt("id"));
            result[i].setName(rs.getString("name"));
            result[i].setCount(rs.getInt("count"));
            result[i].setCaloriesForOne(rs.getInt("calories_for_one"));
            i++;
        }
        return result;
    }

    public TrainingInfoEntity[] dbGetAllTrainings() throws IOException, SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement stmt = connection.createStatement();
        int count = 0;
        ResultSet rs = stmt.executeQuery("select count(*) from " + TRAINING_INFO_TABLE);
        while (rs.next()) {
            count =  rs.getInt(1);
        }

        rs = stmt.executeQuery("SELECT id, description, type, intensity FROM " + TRAINING_INFO_TABLE);
        int i = 0;
        TrainingInfoEntity[] result = new TrainingInfoEntity[count];
        while (rs.next()) {
            result[i] = new TrainingInfoEntity();
            result[i].setId(rs.getInt("id"));
            result[i].setDescription(rs.getString("description"));
            result[i].setType(rs.getString("type"));
            result[i].setIntensity(rs.getInt("intensity"));
            i++;
        }
        return result;
    }


//
//    public synchronized boolean insertPhone(String name, String brand, double screen, int camera, String processor,
//                               String description, String picName) throws SQLException, ClassNotFoundException {
//        Connection connection = getDbConnection();
//        String insert = "INSERT INTO " + PHONE_TABLE + "(name, brand, screen, camera, processor, description, picName)" +
//                " VALUES(?, ?, ?, ?, ?, ?, ?)";
//        PreparedStatement prSt = connection.prepareStatement(insert);
//        prSt.setString(1, name);
//        prSt.setString(2, brand);
//        prSt.setDouble(3, screen);
//        prSt.setInt(4, camera);
//        prSt.setString(5, processor);
//        prSt.setString(6, description);
//        prSt.setString(7, picName);
//        prSt.executeUpdate();
//        return true;
//    }
//
//    public boolean updatePhone(int id, String name, String brand, int screen, int camera, String processor,
//                               String description, String picName) throws SQLException, ClassNotFoundException {
//        Connection connection = getDbConnection();
//        String insert = "UPDATE " + PHONE_TABLE + " SET (name, brand, screen, camera, processor, description, picName)" +
//                " VALUES(?, ?, ?, ?, ?, ?, ?) WHERE idModel = ?";
//        PreparedStatement prSt = connection.prepareStatement(insert);
//        prSt.setString(1, name);
//        prSt.setString(2, brand);
//        prSt.setDouble(3, screen);
//        prSt.setInt(4, camera);
//        prSt.setString(5, processor);
//        prSt.setString(6, description);
//        prSt.setString(7, picName);
//        prSt.setInt(8, id);
//        prSt.executeUpdate();
//        return true;
//    }
//
//    public PhoneMessage getNextPhone(int id) throws IOException, SQLException, ClassNotFoundException {
//        Connection connection = getDbConnection();
//        PhoneMessage answer = new PhoneMessage();
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT idModel, name, brand, screen, camera, processor, " +
//                "description, picName FROM " + PHONE_TABLE + " WHERE idModel > " + id);
//        while (rs.next()) {
//            answer.setId(rs.getInt("idModel"));
//            answer.setName(rs.getString("name"));
//            answer.setBrand(rs.getString("brand"));
//            answer.setScreen(rs.getDouble("screen"));
//            answer.setProcessor(rs.getString("processor"));
//            answer.setCamera(rs.getInt("camera"));
//            answer.setDescription(rs.getString("description"));
//            answer.setPicName(rs.getString("picName"));
//            return answer;
//        }
//        return null;
//    }
//
//    public boolean delPhone(int id) throws IOException, SQLException, ClassNotFoundException {
//        Connection connection = getDbConnection();
//        String insert = "DELETE FROM " + PHONE_TABLE + " WHERE idModel = ?";
//        PreparedStatement prSt = connection.prepareStatement(insert);
//        prSt.setInt(1, id);
//        prSt.executeUpdate();
//        return true;
//    }
//
//    public BrandMessage[] getAllBrands() throws IOException, SQLException, ClassNotFoundException {
//        Connection connection = getDbConnection();
//        Statement stmt = connection.createStatement();
//        count = 0;
//        ResultSet rs = stmt.executeQuery("select count(*) from " + BRAND_TABLE);
//        while (rs.next()) {
//            count =  rs.getInt(1);
//        }
//
//        rs = stmt.executeQuery("SELECT name, coefficient FROM " + BRAND_TABLE);
//        int i = 0;
//        BrandMessage[] result = new BrandMessage[count];
//        while (rs.next()) {
//            result[i] = new BrandMessage(rs.getString("name"), rs.getDouble("coefficient"));
//            i++;
//        }
//        return result;
//    }
//
//    public ProcessorMessage[] getAllProcessors() throws IOException, SQLException, ClassNotFoundException {
//        Connection connection = getDbConnection();
//        Statement stmt = connection.createStatement();
//        count = 0;
//        ResultSet rs = stmt.executeQuery("select count(*) from " + PROCESSOR_TABLE);
//        while (rs.next()) {
//            count =  rs.getInt(1);
//        }
//
//        rs = stmt.executeQuery("SELECT name, coefficient FROM " + PROCESSOR_TABLE);
//        int i = 0;
//        ProcessorMessage[] result = new ProcessorMessage[count];
//        while (rs.next()) {
//            result[i] = new ProcessorMessage(rs.getString("name"), rs.getDouble("coefficient"));
//            i++;
//        }
//        return result;
//    }
//
//    public CameraMessage[] getAllCameras() throws IOException, SQLException, ClassNotFoundException {
//        Connection connection = getDbConnection();
//        Statement stmt = connection.createStatement();
//        count = 0;
//        ResultSet rs = stmt.executeQuery("select count(*) from " + CAMERA_TABLE);
//        while (rs.next()) {
//            count =  rs.getInt(1);
//        }
//
//        rs = stmt.executeQuery("SELECT quality, coefficient FROM " + CAMERA_TABLE);
//        int i = 0;
//        CameraMessage[] result = new CameraMessage[count];
//        while (rs.next()) {
//            result[i] = new CameraMessage(rs.getInt("quality"), rs.getDouble("coefficient"));
//            i++;
//        }
//        return result;
//    }
//
//    public ScreenMessage[] getAllScreens() throws IOException, SQLException, ClassNotFoundException {
//        Connection connection = getDbConnection();
//        Statement stmt = connection.createStatement();
//        count = 0;
//        ResultSet rs = stmt.executeQuery("select count(*) from " + SCREEN_TABLE);
//        while (rs.next()) {
//            count =  rs.getInt(1);
//        }
//
//        rs = stmt.executeQuery("SELECT size, coefficient FROM " + SCREEN_TABLE);
//        int i = 0;
//        ScreenMessage[] result = new ScreenMessage[count];
//        while (rs.next()) {
//            result[i] = new ScreenMessage(rs.getInt("size"), rs.getDouble("coefficient"));
//            i++;
//        }
//        return result;
//    }
//
//    public OrderMessage[] getAllOrders() throws IOException, SQLException, ClassNotFoundException {
//        Connection connection = getDbConnection();
//        Statement stmt = connection.createStatement();
//        int count = 0;
//        ResultSet rs = stmt.executeQuery("select count(*) from " + ORDER_TABLE);
//        while (rs.next()) {
//            count =  rs.getInt(1);
//        }
//
//        rs = stmt.executeQuery("SELECT idOrder, user, phone, amount FROM " + ORDER_TABLE);
//        int i = 0;
//        OrderMessage[] result = new OrderMessage[count];
//        while (rs.next()) {
//            result[i] = new OrderMessage(rs.getInt("idOrder"), rs.getString("user"),
//                    rs.getInt("phone"), rs.getInt("amount"));
//            i++;
//        }
//        return result;
//    }
//
}
