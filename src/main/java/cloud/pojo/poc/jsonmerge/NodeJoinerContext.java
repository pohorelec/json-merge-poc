package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class NodeJoinerContext
{
    private final String currentName;

    private final JsonNode sourceNode;

    private final JsonNode targetNode;

    private final JsonNode targetParentNode;

    private final RecursiveFunction recursiveFunction;

    private final List<JoiningPath> joiningPaths;

    public NodeJoinerContext( String currentName,
                              JsonNode sourceNode,
                              JsonNode targetNode,
                              JsonNode targetParentNode,
                              List<JoiningPath> joiningPaths,
                              RecursiveFunction recursiveFunction )
    {
        this.currentName = currentName;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.targetParentNode = targetParentNode;
        this.joiningPaths = joiningPaths;
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

    public List<JoiningPath> getJoiningPaths()
    {
        return joiningPaths;
    }

    public RecursiveFunction getRecursiveFunction()
    {
        return recursiveFunction;
    }
}
