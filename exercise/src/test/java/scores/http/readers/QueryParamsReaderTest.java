package scores.http.readers;


import org.junit.Test;
import scores.http.readers.QueryParamsReader;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class QueryParamsReaderTest {

    private final QueryParamsReader queryParamsReader = new QueryParamsReader();

    @Test
    public void readParameters_nullString_returnsEmptyMap(){
        assertThat(queryParamsReader.readParameters(null)).isEmpty();
    }

    @Test
    public void readParameters_emptyString_returnsEmptyMap(){
        assertThat(queryParamsReader.readParameters("")).isEmpty();
    }

    @Test
    public void readParameters_oneParameter_returnsParamInMap(){
        Map<String, String> result = queryParamsReader.readParameters("test=somevalue");

        assertThat(result).hasSize(1);
        assertThat(result.get("test")).isEqualTo("somevalue");
    }

    @Test
    public void readParameters_multipleParameter_returnsAllParamInMap(){
        Map<String, String> result = queryParamsReader.readParameters("test=somevalue&othertest=someresult&pepe=lepu&otherstuff=bla");

        assertThat(result).hasSize(4);
        assertThat(result.get("test")).isEqualTo("somevalue");
        assertThat(result.get("othertest")).isEqualTo("someresult");
        assertThat(result.get("pepe")).isEqualTo("lepu");
        assertThat(result.get("otherstuff")).isEqualTo("bla");
    }


}