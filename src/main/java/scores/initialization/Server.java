package scores.initialization;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private static final String CONTEXT = "/";
    private static final int PORT = 8081;
    public static void init() throws IOException {

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext(CONTEXT, Handlers.getChain());
        httpServer.setExecutor(null);
        httpServer.start();

        System.out.println("Server is started and listening on port " + PORT);
    }
}
