package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class JoinProcessorTest
{
    private final ObjectMapper mapper = new ObjectMapper();

    private final JoinProcessor processor = new JoinProcessor();

    @Test
    void join() throws IOException
    {
        ObjectNode source = loadJson( "/source.json" );
        ObjectNode target = loadJson( "/target.json" );

        JsonNode result = processor.join( source, target );

        System.out.println( mapper.writerWithDefaultPrettyPrinter().writeValueAsString( result ) );
    }

    private ObjectNode loadJson( String path ) throws IOException
    {
        return ( ObjectNode ) mapper.readTree( JoinProcessorTest.class.getResource( path ) );
    }
}