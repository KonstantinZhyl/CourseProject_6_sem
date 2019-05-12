package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

public class API extends DatabaseHandler {
    private HttpExchange exchange;
    private Headers headers;
    private String url;
    private String command;

    public API(HttpExchange exchange) {
        this.exchange = exchange;
        this.headers = exchange.getRequestHeaders();
        this.url = getUrl(exchange.getRequestURI().toString());
        this.command = getCommand(headers);
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

    void processGetRequest() throws IOException, SQLException, ClassNotFoundException {
        switch (this.command) {
            case "registration":
                registrate();
                break;
            default:
                sendResponse(200);

        }
    }

    void processPostRequest() throws IOException, SQLException, ClassNotFoundException {
        switch (this.command) {
            case "registration":
                registrate();
                break;
            default:
                sendResponse(200);
        }
    }

    void registrate() throws SQLException, ClassNotFoundException, IOException {
        String login = this.headers.getFirst("login");
        String password = this.headers.getFirst("password");
        if (dbCheckLoginExists(login)) {
            String name = this.headers.getFirst("name");
            String surname = this.headers.getFirst("surname");
            String birthdate = this.headers.getFirst("birthdate");
            String email = this.headers.getFirst("email");
            dbInsertUser(login, password, name, surname, birthdate, email, "user");
            sendResponse(200);
        }
        else {
            this.exchange.setAttribute("message", "User already exists");
            sendResponse(500);
        }
    }

    void sendResponse(int status) throws IOException {
        File page = new File(this.url);
        byte[] response = Files.readAllBytes(page.toPath());
        this.exchange.sendResponseHeaders(status, response.length);
        OutputStream os = this.exchange.getResponseBody();
        os.write(response);
        os.close();
    }
}
