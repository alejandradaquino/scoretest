package scores.http;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public class QueryParamsReader {
    private static final String AND_DELIMITER = "&";
    private static final String EQUAL_DELIMITER = "=";
    private static final Integer LEFT = 0;
    private static final Integer RIGHT = 1;

    public Map<String, String> readParameters(String query) {
        return ofNullable(query).map(this::getResults).orElse(new HashMap<>());
    }

    private Map<String, String> getResults(String query) {
        return stream(query.split(AND_DELIMITER))
                .map(s -> s.split(EQUAL_DELIMITER))
                .filter(p -> p.length == 2)
                .collect(toMap(p -> p[LEFT], p -> p[RIGHT]));
    }
}
