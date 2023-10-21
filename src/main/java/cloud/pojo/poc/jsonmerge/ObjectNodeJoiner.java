package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
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
    public void join( NodeJoinerContext context, CurrentPath currentPath )
    {
        ObjectNode sourceNode = ( ObjectNode ) context.getSourceNode();
        ObjectNode targetNode = ( ObjectNode ) context.getTargetNode();

        if ( targetNode == null )
        {
            targetNode = JsonNodeFactory.instance.objectNode();
            ( ( ObjectNode ) context.getTargetParentNode() ).set( context.getCurrentName(), targetNode );
        }

        context.getRecursiveFunction().execute(
                sourceNode,
                targetNode,
                currentPath,
                context.getJoiningPaths()
        );
    }
}