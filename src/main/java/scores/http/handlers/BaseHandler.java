package scores.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import scores.http.exceptions.InvalidSessionException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public abstract class BaseHandler implements HttpHandler {

    private Optional<HttpHandler> next;

    public BaseHandler() {
        this(empty());
    }

    public BaseHandler(HttpHandler handler) {
        this(ofNullable(handler));
    }

    private BaseHandler(Optional<HttpHandler> next) {
        this.next = next;
    }

    protected abstract boolean  canHandle(HttpExchange exchange) ;
    protected abstract String doHandle(HttpExchange exchange);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (canHandle(exchange)) {
            try {
                sendResponse(exchange, doHandle(exchange), 200);
            }catch (InvalidSessionException invalidSessionException){
                sendResponse(exchange, "Invalid session token",403);

            }catch (Exception e){
                sendResponse(exchange, "An error has occurred: "+ e.getMessage(),500);
            }
        }else{
            if(next.isPresent()){
                next.get().handle(exchange);
            }else{
                sendResponse(exchange, "Resource not found ", 404);
            }
        }
    }

    private void sendResponse(HttpExchange exchange, String response, int code) throws IOException {
        System.out.println(response);
        exchange.getResponseHeaders().set("Content-type", "text/plain");
        exchange.sendResponseHeaders(code, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    protected List<String> getPaths(HttpExchange exchange) {
        return stream(exchange.getRequestURI().getPath().split("/"))
                .filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }
}
