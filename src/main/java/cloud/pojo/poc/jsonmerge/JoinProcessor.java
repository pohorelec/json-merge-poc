package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JoinProcessor
{
    private final List<NodeJoiner> joiners = List.of(
            new ValueNodeJoiner(),
            new ObjectNodeJoiner(),
            new ArrayNodeOfObjectsJoiner(),
            new ArrayNodeOfValuesJoiner()
    );

    public JsonNode join( ObjectNode sourceRoot, ObjectNode targetRoot )
    {
        traverseNode( sourceRoot, targetRoot, new CurrentPath() );
        return targetRoot;
    }

    private void traverseNode( ContainerNode<?> sourceParentNode, ContainerNode<?> targetParentNode, CurrentPath currentPath )
    {
        Iterator<Map.Entry<String, JsonNode>> fields = sourceParentNode.fields();

        while ( fields.hasNext() )
        {
            Map.Entry<String, JsonNode> currentSourceNodeEntry = fields.next();
            String currentName = currentSourceNodeEntry.getKey();

            JsonNode sourceNode = currentSourceNodeEntry.getValue();
            JsonNode targetNode = targetParentNode.get( currentName );

            // construct new instance of path
            CurrentPath currentPathNew = CurrentPath.copy( currentPath ).append( currentName );

            joiners.stream()
                    .filter( joiner -> joiner.apply( sourceNode ) )
                    .forEach( joiner -> joiner.join(
                            new NodeJoinerContext(
                                    currentName,
                                    sourceNode,
                                    targetNode,
                                    targetParentNode,
                                    this::traverseNode
                            ), currentPathNew
                    ) );
        }
    }
}
