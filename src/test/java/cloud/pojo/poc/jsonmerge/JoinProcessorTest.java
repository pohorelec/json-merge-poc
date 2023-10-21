package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;

class JoinProcessorTest
{
    private final ObjectMapper mapper = new ObjectMapper();

    private final JoinProcessor processor = new JoinProcessor();

    @Test
    void join() throws IOException, JSONException
    {
        ObjectNode source = loadJson( "/source.json" );
        ObjectNode target = loadJson( "/target.json" );

        JsonNode result = processor.join( source, target );

        String actualResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString( result );
        String expectedResult = mapper.writeValueAsString( loadJson( "/result.json" ) );

        System.out.println( actualResult );

        JSONAssert.assertEquals( expectedResult, actualResult, JSONCompareMode.STRICT );
    }

    private ObjectNode loadJson( String path ) throws IOException
    {
        return ( ObjectNode ) mapper.readTree( JoinProcessorTest.class.getResource( path ) );
    }
}