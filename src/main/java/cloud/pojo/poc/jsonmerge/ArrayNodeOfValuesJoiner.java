package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;

public class ArrayNodeOfValuesJoiner
        implements NodeJoiner
{
    @Override
    public boolean apply( JsonNode sourceNode )
    {
        boolean isArrayNode = sourceNode instanceof ArrayNode;
        boolean isNotEmpty = !sourceNode.isEmpty();
        boolean hasValueItems = sourceNode.get( 0 ) instanceof ValueNode;

        return isArrayNode && isNotEmpty && hasValueItems;
    }

    @Override
    public void join( NodeJoinerContext context, CurrentPath currentPath )
    {
        ArrayNode targetNode = ( ArrayNode ) context.getTargetNode();

        if ( targetNode == null )
        {
            targetNode = JsonNodeFactory.instance.arrayNode();
            ( ( ObjectNode ) context.getTargetParentNode() ).set( context.getCurrentName(), targetNode );
        }

        targetNode.removeAll();
        for ( JsonNode sourceNode : context.getSourceNode() )
        {
            targetNode.add( sourceNode );
        }
    }
}