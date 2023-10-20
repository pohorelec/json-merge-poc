package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.stream.StreamSupport;

// TODO: implement for REPLACE. For now only MERGE is implemented
// TODO: add support for ValueNode. Currently only ObjectNode childs are supported
public class ArrayNodeJoiner
        implements NodeJoiner
{
    @Override
    public boolean apply( JsonNode sourceNode )
    {
        return sourceNode instanceof ArrayNode;
    }

    @Override
    public void join( NodeJoinerContext context )
    {
        // TODO: hardcoded - take from 'context.configuration.joiningKeys'
        // TODO: add check for path - only paths provided by 'context.configuration.joiningPath' should be merged
        String joiningKey = "id";

        context.getSourceNode()
                .forEach( childSourceNode -> {
                    String sourceJoiningKeyValue = childSourceNode.get( joiningKey ).asText();

                    ObjectNode childTargetNode = findChildTargetNode( ( ArrayNode ) context.getTargetNode(), joiningKey, sourceJoiningKeyValue );

                    context.getRecursiveFunction().execute(
                            ( ObjectNode ) childSourceNode,
                            childTargetNode
                    );
                } );
    }

    private ObjectNode findChildTargetNode( ArrayNode targetNode, String joiningKey, String sourceJoiningKeyValue )
    {
        return StreamSupport.stream( targetNode.spliterator(), false )
                .filter( childTargetNode -> childTargetNode.get( joiningKey ).asText().equals( sourceJoiningKeyValue ) )
                .findFirst()
                .map( childTargetNode -> ( ObjectNode ) childTargetNode )
                .orElseGet( () -> {
                    ObjectNode newTargetChildNode = JsonNodeFactory.instance.objectNode();
                    targetNode.add( newTargetChildNode );

                    return newTargetChildNode;
                } );

    }
}