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
            byte[] bytes = resolve(exchange.getRequestURI().toString());
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }

        public byte[] resolve(String url) throws IOException {
            System.out.println(url);
            if (url.equals("/")) {
                url = "html/main.html";
            }

            url = url.replace('/', '\\');
            if (url.startsWith("\\")) {
                url = url.substring(1);
            }

            File file = new File(url);
            return Files.readAllBytes(file.toPath());
        }
    }

    static class Auth extends Authenticator {
        @Override
        public Result authenticate(HttpExchange httpExchange) {
            if ("/forbidden".equals(httpExchange.getRequestURI().toString()))
                return new Failure(403);
            else
                return new Success(new HttpPrincipal("c0nst", "realm"));
        }
    }
}