package server;

import Models.ProductEntity;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class API extends DatabaseHandler {
    private HttpExchange exchange;
    private Headers headers;
    private String url;
    private String command;
    private JSONObject body;

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

    public API(HttpExchange exchange) throws IOException, ParseException {
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

        Object obj = new JSONParser().parse(body.toString());
        this.body = (JSONObject) obj;
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
        String response = body;
        if (body == null) {
            File page = new File(this.url);
            response = Files.readAllBytes(page.toPath()).toString();
        }

        this.exchange.sendResponseHeaders(status, response.length());
        OutputStream os = this.exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    void processGetRequest() throws IOException, SQLException, ClassNotFoundException {
        switch (this.command) {
            case "registration":
                registrate();
                break;
            case "getAllProducts":
                getAllProducts();
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
            default:
                sendResponse(200, null);
        }
    }

    private void registrate() throws SQLException, ClassNotFoundException, IOException {
        String login = (String) this.body.get("login");
        String password = (String) this.body.get("password");

        if (!dbCheckLoginExists(login)) {
            String name = (String) this.body.get("name");
            String surname = (String) this.body.get("surname");
            String birthdate = (String) this.body.get("date");
            String email = (String) this.body.get("mail");
            dbInsertUser(login, password, name, surname, birthdate, email, "user");
            sendResponse(200, null);
        }
        else {
            this.exchange.setAttribute("message", "User already exists");
            sendResponse(500, null);
        }
    }

    private void getAllProducts() throws SQLException, ClassNotFoundException, IOException {
        JSONObject products = dbGetAllProducts();
        sendResponse(500, products.toString());
    }
}
