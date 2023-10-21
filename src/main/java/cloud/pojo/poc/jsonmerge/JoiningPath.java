package cloud.pojo.poc.jsonmerge;

import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class JoiningPath
{
    private final String path;

    private final List<String> keys;

    public JoiningPath( String path, List<String> keys )
    {
        this.path = path;
        this.keys = keys;
    }

    public String getPath()
    {
        return path;
    }

    public List<String> getKeys()
    {
        return keys;
    }
}
