package server;

import com.sun.net.httpserver.*;

import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

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
            File page = null;
            if (exchange.getRequestMethod().equals("GET"))  {
                page = getResponse(exchange);
            }

            if (exchange.getRequestMethod().equals("POST"))  {
                page = postResponse(exchange);
            }

            byte[] response = Files.readAllBytes(page.toPath());
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }

        public File getResponse(HttpExchange exchange) throws IOException {
            String url = exchange.getRequestURI().toString();
            if (url.equals("/")) {
                url = "html/main.html";
            }

            url = url.replace('/', '\\');
            if (url.startsWith("\\")) {
                url = url.substring(1);
            }

            return (new File(url));
        }

        public File postResponse(HttpExchange exchange) throws IOException {
            String url = exchange.getRequestURI().toString();
            if (url.equals("/")) {
                url = "html/main.html";
            }
            url = url.replace('/', '\\');
            if (url.startsWith("\\")) {
                url = url.substring(1);
            }

            return null;
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