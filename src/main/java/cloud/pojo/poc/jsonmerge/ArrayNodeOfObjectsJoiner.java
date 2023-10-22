package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Objects;
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

                        ObjectNode childTargetNode = null;
                        Iterator<String> keys = joiningPath.getKeys().iterator();

                        while ( keys.hasNext() && childTargetNode == null )
                        {
                            String joiningKey = keys.next();
                            String sourceJoiningKeyValue = Optional.ofNullable( childSourceNode.get( joiningKey ) ).map( JsonNode::asText ).orElse( null );
                            if ( sourceJoiningKeyValue != null )
                            {
                                childTargetNode = findChildTargetNode( targetNode, joiningKey, sourceJoiningKeyValue );
                            }
                        }

                        if ( childTargetNode == null )
                        {
                            childTargetNode = newObjectNode( targetNode );
                        }

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
        return ( ObjectNode ) StreamSupport.stream( targetNode.spliterator(), false )
                .filter( c -> Objects.nonNull( c.get( joiningKey ) ) )
                .filter( c -> c.get( joiningKey ).asText().equals( sourceJoiningKeyValue ) )
                .findFirst()
                .orElse( null );

    }

    private ObjectNode newObjectNode( ArrayNode targetNode )
    {
        ObjectNode newTargetChildNode = JsonNodeFactory.instance.objectNode();
        targetNode.add( newTargetChildNode );

        return newTargetChildNode;
    }
}