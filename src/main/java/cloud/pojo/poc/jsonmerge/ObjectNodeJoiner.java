package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectNodeJoiner
        implements NodeJoiner
{
    @Override
    public boolean apply( JsonNode sourceNode )
    {
        return sourceNode instanceof ObjectNode;
    }

    @Override
    public void join( NodeJoinerContext context )
    {
        context.getRecursiveFunction().execute(
                ( ObjectNode ) context.getSourceNode(),
                ( ObjectNode ) context.getTargetNode() // TODO: if targetNode is null - create empty ObjectNode
        );
    }
}