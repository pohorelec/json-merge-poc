package cloud.pojo.poc.jsonmerge;

import com.fasterxml.jackson.databind.node.ContainerNode;

import java.util.List;

@FunctionalInterface
public interface RecursiveFunction
{
    void execute( ContainerNode<?> sourceParentNode, ContainerNode<?> targetParentNode, CurrentPath currentPath, List<JoiningPath> joiningPaths );
}
