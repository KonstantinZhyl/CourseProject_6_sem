package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class API extends DatabaseHandler {
    private HttpExchange exchange;
    private Headers headers;
    private String url;
    private String command;
    private JsonObject body;

    String getUrl(String uri) {
        if(uri.equals("/")) {
            uri = "html/main.html";
        }

        uri =uri.replace('/','\\');
        if(uri.startsWith("\\")) {
            uri = uri.substring(1);
        }
        return uri;
    }

    String getCommand(Headers headers) {
        String command = headers.getFirst("command");
        if (command == null) {
            command = "";
        }
        return command;
    }

    public API(HttpExchange exchange) throws IOException, ParseException, DeserializationException {
        this.exchange = exchange;
        this.headers = exchange.getRequestHeaders();
        this.url = getUrl(exchange.getRequestURI().toString());
        this.command = getCommand(headers);

        InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);
        int b;
        StringBuilder body = new StringBuilder(1024);
        while ((b = br.read()) != -1) {
            body.append((char) b);
        }
        br.close();
        isr.close();

        if (body.equals("")) {
            this.body = (JsonObject) Jsoner.deserialize(body.toString());
        } else {
            this.body = null;
        }
    }

    static boolean checkUserExistance(HttpExchange httpExchange) throws SQLException, ClassNotFoundException {
        Headers headers = httpExchange.getRequestHeaders();
        List<String> cookies = headers.get("Cookie");
        String login = "", password = "";
        for (String cookie : cookies) {
            if (cookie.contains("login")) {
                login = cookie.split(";")[0].split("=")[1];
            }
            if (cookie.contains("password")) {
                password = cookie.split(";")[1].split("=")[1];
            }
        }
        return dbCheckUserExistance(login, password);
    }

    void sendResponse(int status, String body) throws IOException {
        byte[] response;
        if (body == null) {
            File page = new File(this.url);
            response = Files.readAllBytes(page.toPath());
        }
        else {
            response = body.getBytes();
        }

        this.exchange.sendResponseHeaders(status, response.length);
        OutputStream os = this.exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    void processGetRequest() throws IOException, SQLException, ClassNotFoundException {
        switch (this.command) {
            case "getAllProducts":
                getAllProducts();
                break;
            case "getUserDiet":
                getUserDiet();
                break;
            case "getAllDiets":
                getAllDiets();
                break;
            default:
                sendResponse(200, null);
        }
    }

    void processPostRequest() throws IOException, SQLException, ClassNotFoundException {
        switch (this.command) {
            case "registration":
                registrate();
                break;
            case "addProduct":
                addProducts();
                break;
            case "updateDiet":
                updateDiet();
            default:
                sendResponse(200, null);
        }
    }

    private void registrate() throws SQLException, ClassNotFoundException, IOException {
        String login = (String) this.body.get("login");

        if (!dbCheckLoginExists(login)) {
            dbInsertUser(this.body);
            sendResponse(200, null);
        }
        else {
            this.exchange.setAttribute("message", "User already exists");
            sendResponse(500, null);
        }
    }

    private void getAllProducts() throws SQLException, ClassNotFoundException, IOException {
        JsonObject products = dbGetAllProducts();
        sendResponse(200, products.toJson());
    }

    private void addProducts() throws SQLException, ClassNotFoundException, IOException {
        JsonArray products = (JsonArray) this.body.get("Products");
        for (int i=0; i < products.size(); i++) {
            dbInsertProduct((JsonObject) products.get(i));
        }
    }

    private void getUserDiet() throws SQLException, ClassNotFoundException, IOException {
        String login = headers.getFirst("login");
        int user_id = getUserIdByLogin(login);
        JsonObject user = dbGetUser(user_id);
        String st = user.toJson();
        int diet_id = (int) user.get("diet_id");
        JsonObject diet = dbGetDiet(diet_id);
        sendResponse(200, diet.toJson());
    }

    private void getAllDiets() throws SQLException, IOException, ClassNotFoundException {
        JsonObject diets = dbGetAllDiets();
        sendResponse(200, diets.toJson());
    }

    private void updateDiet() throws SQLException, ClassNotFoundException, IOException {
        dbUpdateDiet(this.body);
        sendResponse(200, null);
    }
}