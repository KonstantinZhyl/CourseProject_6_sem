package server;

import com.sun.net.httpserver.*;

import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;


public class SimpleHttpServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8765), 0);

        HttpContext context = server.createContext("/", new EchoHandler());
        context.setAuthenticator(new Auth());

        server.setExecutor(null);
        server.start();
    }

    static class EchoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            API api = new API(exchange);
            if (exchange.getRequestMethod().equals("GET"))  {
                try {
                    api.processGetRequest(exchange);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if (exchange.getRequestMethod().equals("POST"))  {
//                page = processPostRequest(exchange);
            }
        }
    }

    static class Auth extends Authenticator {
        @Override
        public Result authenticate(HttpExchange httpExchange) {
            String url = httpExchange.getRequestURI().toString();
            try {
                if (url.equals("/") || url.startsWith("/images") || url.startsWith("/css") || url.startsWith("/favicon")
                        || url.startsWith("/html/aush.html") || API.checkUserExistance(httpExchange))
                    return new Success(new HttpPrincipal("c0nst", "realm"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Failure(403);
        }
    }
}