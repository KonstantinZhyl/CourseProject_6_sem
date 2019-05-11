package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class API extends DatabaseHandler {

    static boolean checkUserExistance(HttpExchange httpExchange) throws SQLException, ClassNotFoundException {
        Headers headers = httpExchange.getRequestHeaders();
        String login = headers.getFirst("login");
        String password = headers.getFirst("password");
        return db_checkUserExistance(login, password);
    }


//    void menu() {
//        try {
//            switch (message.getRequest()) {
//                case "signup":
//                    signUp();
//                    break;
//                case "registration":
//                    registration();
//                    break;
//                case "addBrand":
//                    if (message.getRole().equals("admin")) {
//                        addBrand();
//                    }
//                    else {
//                        output.writeObject(new CommandMessage("У вас нет доступа"));
//                    }
//                    break;
//                case "addScreen":
//                    if (message.getRole().equals("admin")) {
//                        addScreen();
//                    }
//                    else {
//                        output.writeObject(new CommandMessage("У вас нет доступа"));
//                    }
//                    break;
//                case "addProcessor":
//                    if (message.getRole().equals("admin")) {
//                        addProcessor();
//                    }
//                    else {
//                        output.writeObject(new CommandMessage("У вас нет доступа"));
//                    }
//                    break;
//                case "addCamera":
//                    if (message.getRole().equals("admin")) {
//                        addCamera();
//                    }
//                    else {
//                        output.writeObject(new CommandMessage("У вас нет доступа"));
//                    }
//                    break;
//                case "addPhone":
//                    if (message.getRole().equals("admin")) {
//                        addPhone();
//                    }
//                    else {
//                        output.writeObject(new CommandMessage("У вас нет доступа"));
//                    }
//                    break;
//                case "delPhone":
//                    if (message.getRole().equals("admin")) {
//                        delPhone();
//                    }
//                    else {
//                        output.writeObject(new CommandMessage("У вас нет доступа"));
//                    }
//                    break;
//                case "addOrder":
//                    if (message.getRole().equals("user")) {
//                        addOrder();
//                    }
//                    break;
//                case "getAllBrands":
//                    if (message.getRole().equals("admin")) {
//                        getBrands();
//                    }
//                    break;
//                case "getAllScreens":
//                    if (message.getRole().equals("admin")) {
//                        getScreens();
//                    }
//                    break;
//                case "getAllProcessors":
//                    if (message.getRole().equals("admin")) {
//                        getProcessors();
//                    }
//                    break;
//                case "getAllCameras":
//                    if (message.getRole().equals("admin")) {
//                        getCameras();
//                    }
//                    break;
//                case "nextPhone":
//                    if (message.getRole().equals("user")) {
//                        nextPhone();
//                    } else {
//                        output.writeObject(new CommandMessage("У вас нет доступа"));
//                    }
//                    break;
//                case "getAllOrders":
//                    if (message.getRole().equals("user")) {
//                        getOrders();
//                    } else {
//                        output.writeObject(new CommandMessage("У вас нет доступа"));
//                    }
//                    break;
//            }
//        } catch (IOException e) {
//            System.out.println("Error");
//        }
//    }
//
//    void getBrands() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            BrandMessage[] brands = getAllBrands();
//            answer.setCount(count);
//            output.writeObject(answer);
//            for (int i = 0; i < count; i++) {
//                output.writeObject(brands[i]);
//            }
//        }
//        catch(SQLException e) {
//            answer.setRequest("Неправильные данные заказа");
//            output.writeObject(answer);
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//            output.writeObject(answer);
//        } catch (IOException e) {
//            e.printStackTrace();
//            answer.setRequest("IOException on server");
//            output.writeObject(answer);
//        }
//    }
//
//    void getCameras() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            CameraMessage[] brands = getAllCameras();
//            answer.setCount(count);
//            output.writeObject(answer);
//            for (int i = 0; i < count; i++) {
//                output.writeObject(brands[i]);
//            }
//        }
//        catch(SQLException e) {
//            answer.setRequest("Неправильные данные заказа");
//            output.writeObject(answer);
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//            output.writeObject(answer);
//        } catch (IOException e) {
//            e.printStackTrace();
//            answer.setRequest("IOException on server");
//            output.writeObject(answer);
//        }
//    }
//
//    void getProcessors() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            ProcessorMessage[] brands = getAllProcessors();
//            answer.setCount(count);
//            output.writeObject(answer);
//            for (int i = 0; i < count; i++) {
//                output.writeObject(brands[i]);
//            }
//        }
//        catch(SQLException e) {
//            answer.setRequest("Неправильные данные заказа");
//            output.writeObject(answer);
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//            output.writeObject(answer);
//        } catch (IOException e) {
//            e.printStackTrace();
//            answer.setRequest("IOException on server");
//            output.writeObject(answer);
//        }
//    }
//
//    void getScreens() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            ScreenMessage[] brands = getAllScreens();
//            answer.setCount(count);
//            output.writeObject(answer);
//            for (int i = 0; i < count; i++) {
//                output.writeObject(brands[i]);
//            }
//        }
//        catch(SQLException e) {
//            answer.setRequest("Неправильные данные заказа");
//            output.writeObject(answer);
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//            output.writeObject(answer);
//        } catch (IOException e) {
//            e.printStackTrace();
//            answer.setRequest("IOException on server");
//            output.writeObject(answer);
//        }
//    }
//
//    void getOrders() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            OrderMessage[] orders = getAllOrders();
//            answer.setRequest("success");
//            answer.setCount(orders.length);
//            output.writeObject(answer);
//            for (int i = 0; i < orders.length; i++) {
//                output.writeObject(orders[i]);
//            }
//        }
//        catch(SQLException e) {
//            answer.setRequest("Неправильные данные заказа");
//            output.writeObject(answer);
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//            output.writeObject(answer);
//        } catch (IOException e) {
//            e.printStackTrace();
//            answer.setRequest("IOException on server");
//            output.writeObject(answer);
//        }
//    }
//
//    void addOrder() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            OrderMessage order = (OrderMessage) input.readObject();
//            insertOrder(order.getUser(), order.getPhone(), order.getAmount());
//            PhoneMessage phone = getPhoneById(order.getPhone());
//            updateOrder(phone.getBrand(), phone.getProcessor(), phone.getCamera(), phone.getScreen(),
//                    order.getAmount());
//            answer.setRequest("Ваш заказ принят!");
//        }
//        catch(SQLException e) {
//            answer.setRequest("Неправильные данные заказа");
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        output.writeObject(answer);
//    }
//
//    void addPhone() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            PhoneMessage phone = (PhoneMessage) input.readObject();
//
//            File file = new File(phone.getPicName());
//            if (file.exists() && file.isFile()) {
//                insertPhone(phone.getName(), phone.getBrand(), phone.getScreen(), phone.getCamera(), phone.getProcessor(),
//                        phone.getDescription(), phone.getPicName());
//                answer.setRequest("success");
//            } else {
//                answer.setRequest("Нет картинки смартфона");
//            }
//        }
//        catch(SQLException e) {
//            answer.setRequest("Неправильные данные телефона");
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        output.writeObject(answer);
//    }
//
//    void signUp() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            AuthMessage request = (AuthMessage) input.readObject();
//            String req = checkCombination(request.getLogin(), request.getPassword());
//            if (req.equals("")) {
//                answer.setRequest("failed");
//            }
//            else {
//                answer.setRequest(req);
//            }
//        }
//        catch(SQLException e) {
//            answer.setRequest("Проблемы с базой данных сервера, попробуйте снова позже");
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        output.writeObject(answer);
//    }
//
//    void registration() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            UserMessage request = (UserMessage) input.readObject();
//            if (!checkLoginExists(request.getLogin())) {
//                insertUser(request.getLogin(), request.getPassword(), request.getName(), request.getSurname(),
//                           request.getAddress(), request.getEmail(), request.getTelephone(), request.getRole());
//                answer.setRequest("Пользователь успешно зарегестрирован");
//            }
//            else {
//                answer.setRequest("Пользователь с таким логином уже зарегестрирован");
//            }
//        }
//        catch(SQLException e) {
//            answer.setRequest("Проблемы с базой данных сервера, попробуйте снова позже");
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        output.writeObject(answer);
//    }
//
//    void addBrand() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            BrandMessage request = (BrandMessage) input.readObject();
//            insertBrand(request.getName(), request.getCoefficient());
//            answer.setRequest("Новый бренд добавлен");
//        }
//        catch(SQLException e) {
//            answer.setRequest("Такой бренд уже есть");
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        output.writeObject(answer);
//    }
//
//    void addScreen() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            ScreenMessage request = (ScreenMessage) input.readObject();
//            insertScreen(request.getSize(), request.getCoefficient());
//            answer.setRequest("Новый размер экрана добавлен");
//        }
//        catch(SQLException e) {
//            answer.setRequest("Такой размер уже экрана есть");
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        output.writeObject(answer);
//    }
//
//    void addProcessor() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            ProcessorMessage request = (ProcessorMessage) input.readObject();
//            insertProcessor(request.getName(), request.getCoefficient());
//            answer.setRequest("Новый процессор добавлен");
//        }
//        catch(SQLException e) {
//            answer.setRequest("Такой процессор уже есть");
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        output.writeObject(answer);
//    }
//
//    void addCamera() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            CameraMessage request = (CameraMessage) input.readObject();
//            insertCamera(request.getQuality(), request.getCoefficient());
//            answer.setRequest("Новый тип камеры добавлена");
//        }
//        catch(SQLException e) {
//            answer.setRequest("Такой тип камеры уже имеется");
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        output.writeObject(answer);
//    }
//
//    void nextPhone() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            PhoneMessage phone = getNextPhone(message.getCount());
//            if (phone == null) {
//                phone = getNextPhone(0);
//            }
//            phone.uploadPic();
//            output.writeObject(phone);
//        }
//        catch(SQLException e) {
//            answer.setRequest("Такой тип камеры уже имеется");
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    void delPhone() throws IOException {
//        CommandMessage answer = new CommandMessage();
//        answer.setRole("serveranswer");
//        try {
//            PhoneMessage phone = (PhoneMessage) input.readObject();
//            delPhone(phone.getId());
//            answer.setRequest("Модель телефона успешно удалена");
//        }
//        catch(SQLException e) {
//            answer.setRequest("Такой тип камеры уже имеется");
//        }
//        catch (ClassNotFoundException e) {
//            answer.setRequest("Неправильные данные");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
