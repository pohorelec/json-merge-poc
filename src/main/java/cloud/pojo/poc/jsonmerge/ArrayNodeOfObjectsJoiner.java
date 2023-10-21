package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;
import java.util.stream.StreamSupport;

public class ArrayNodeOfObjectsJoiner
        implements NodeJoiner
{
    @Override
    public boolean apply( JsonNode sourceNode )
    {
        boolean isArrayNode = sourceNode instanceof ArrayNode;
        boolean isNotEmpty = !sourceNode.isEmpty();
        boolean hasObjectItems = sourceNode.get( 0 ) instanceof ObjectNode;

        return isArrayNode && isNotEmpty && hasObjectItems;
    }

    @Override
    public void join( NodeJoinerContext context, CurrentPath currentPath )
    {
        JoiningPath joiningPath = currentPath.getActive( context.getJoiningPaths() );

        // --- MERGE
        if ( joiningPath != null )
        {
            context.getSourceNode()
                    .forEach( childSourceNode -> {
                        ArrayNode targetNode = ( ArrayNode ) context.getTargetNode();

                        if ( targetNode == null )
                        {
                            targetNode = JsonNodeFactory.instance.arrayNode();
                            ( ( ObjectNode ) context.getTargetParentNode() ).set( context.getCurrentName(), targetNode );
                        }

                        String joiningKey = joiningPath.getKeys().get( 0 ); // TODO: implement for each key
                        String sourceJoiningKeyValue = Optional.ofNullable( childSourceNode.get( joiningKey ) ).map( JsonNode::asText ).orElse( null );
                        ObjectNode childTargetNode = findChildTargetNode( targetNode, joiningKey, sourceJoiningKeyValue );

                        context.getRecursiveFunction().execute(
                                ( ObjectNode ) childSourceNode,
                                childTargetNode,
                                currentPath,
                                context.getJoiningPaths()
                        );
                    } );
        }

        // --- REPLACE
        else
        {
            ( ( ObjectNode ) context.getTargetParentNode() ).replace( context.getCurrentName(), context.getSourceNode() );
        }
    }

    private ObjectNode findChildTargetNode( ArrayNode targetNode, String joiningKey, String sourceJoiningKeyValue )
    {
        if ( sourceJoiningKeyValue == null )
        {
            return newObjectNode( targetNode );
        }

        return StreamSupport.stream( targetNode.spliterator(), false )
                .filter( c -> c.get( joiningKey ) != null )
                .filter( childTargetNode -> childTargetNode.get( joiningKey ).asText().equals( sourceJoiningKeyValue ) )
                .findFirst()
                .map( childTargetNode -> ( ObjectNode ) childTargetNode )
                .orElseGet( () -> newObjectNode( targetNode ) );

    }

    private ObjectNode newObjectNode( ArrayNode targetNode )
    {
        ObjectNode newTargetChildNode = JsonNodeFactory.instance.objectNode();
        targetNode.add( newTargetChildNode );

        return newTargetChildNode;
    }
}