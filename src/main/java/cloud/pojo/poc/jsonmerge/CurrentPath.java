package cloud.pojo.poc.jsonmerge;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class CurrentPath
        extends ArrayList<String>
{
    public CurrentPath()
    {
    }

    public static CurrentPath copy( CurrentPath currentPath )
    {
        CurrentPath copy = new CurrentPath();
        copy.addAll( currentPath );
        return copy;
    }

    public CurrentPath append( String path )
    {
        add( path );
        return this;
    }

    public JoiningPath getActive( List<JoiningPath> paths )
    {
        return paths.stream().filter( p -> p.getPath().equals( toString() ) ).findFirst().orElse( null );
    }

    @Override
    public String toString()
    {
        return String.join( ".", this );
    }
}
