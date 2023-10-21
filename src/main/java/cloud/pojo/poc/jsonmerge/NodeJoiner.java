package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;

public interface NodeJoiner
{
    boolean apply( JsonNode sourceNode );

    void join( NodeJoinerContext context, CurrentPath currentPath );
}
