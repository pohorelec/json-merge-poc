package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;

public class NodeJoinerContext
{
    private final String currentName;

    private final JsonNode sourceNode;

    private final JsonNode targetNode;

    private final JsonNode targetParentNode;

    private final RecursiveFunction recursiveFunction;

    public NodeJoinerContext( String currentName, JsonNode sourceNode, JsonNode targetNode, JsonNode targetParentNode, RecursiveFunction recursiveFunction )
    {
        this.currentName = currentName;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.targetParentNode = targetParentNode;
        this.recursiveFunction = recursiveFunction;
    }

    public String getCurrentName()
    {
        return currentName;
    }

    public JsonNode getSourceNode()
    {
        return sourceNode;
    }

    public JsonNode getTargetNode()
    {
        return targetNode;
    }

    public JsonNode getTargetParentNode()
    {
        return targetParentNode;
    }

    public RecursiveFunction getRecursiveFunction()
    {
        return recursiveFunction;
    }
}
