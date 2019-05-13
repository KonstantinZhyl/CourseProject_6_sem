package server;

import com.sun.net.httpserver.*;
import org.json.simple.DeserializationException;
import org.json.simple.parser.ParseException;

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
            API api = null;
            try {
                api = new API(exchange);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (DeserializationException e) {
                e.printStackTrace();
            }
            if (exchange.getRequestMethod().equals("GET"))  {
                try {
                    api.processGetRequest();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if (exchange.getRequestMethod().equals("POST"))  {
                try {
                    api.processPostRequest();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Auth extends Authenticator {
        String[] save_list = {"/html/main.html", "/images", "/css", "/favicon", "/html/aush.html", "/", "/html/reg.html"};

        @Override
        public Result authenticate(HttpExchange httpExchange) {
            String url = httpExchange.getRequestURI().toString();
            try {
                boolean safe = false;
                for (int i = 0; i < this.save_list.length; i++) {
                    if (url.startsWith(this.save_list[i])) {
                        safe = true;
                    }
                }
                if (safe || API.checkUserExistance(httpExchange)) {
                    return new Success(new HttpPrincipal("c0nst", "realm"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Failure(403);
        }
    }
}

