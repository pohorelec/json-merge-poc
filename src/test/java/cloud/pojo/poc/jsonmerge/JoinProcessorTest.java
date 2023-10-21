package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class JoinProcessorTest
{
    private final ObjectMapper mapper = new ObjectMapper();

    private final JoinProcessor processor = new JoinProcessor();

    @Test
    void join() throws IOException, JSONException
    {
        ObjectNode source = loadJson( "/source.json" );
        ObjectNode target = loadJson( "/target.json" );

        List<JoiningPath> joiningPaths = new ArrayList<>();
        joiningPaths.add( new JoiningPath( "phones", List.of( "id" ) ) );

        JsonNode result = processor.join( source, target, joiningPaths );

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