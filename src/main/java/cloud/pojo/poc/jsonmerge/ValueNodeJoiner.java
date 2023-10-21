package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;

public class ValueNodeJoiner
        implements NodeJoiner
{
    @Override
    public boolean apply( JsonNode sourceNode )
    {
        return sourceNode instanceof ValueNode;
    }

    @Override
    public void join( NodeJoinerContext context, CurrentPath currentPath )
    {
        ( ( ObjectNode ) context.getTargetParentNode() ).replace( context.getCurrentName(), context.getSourceNode() );
    }
}