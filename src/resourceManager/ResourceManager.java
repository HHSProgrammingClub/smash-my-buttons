package resourceManager;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager
{
	private static Map<String, Resource> m_resources = new HashMap<String, Resource>();
	/**
	 * Loads and caches a resource.</br>
	 * Usage:</br>
	 * Texture tex = ResourceManager.getResource(Texture.class, "resources/images/billyboi");
	 * 
	 * @param p_class Class of the resource.
	 * @param p_path Path of the resource.
	 * @return Returns the resource casted to the requested class type.
	 */
	public static <T> T getResource(Class<T> p_class, String p_path)
	{
		// Check if p_class is a Resource 
		if (!Resource.class.isAssignableFrom(p_class))
			throw new IllegalArgumentException("p_class is not a Resource");
		
		if (m_resources.containsKey(p_path))
		{
			// Resource already loaded
			//System.out.println("ResourceManager: Getting existing resource at path \"" + p_path + "\"");
			Resource res = m_resources.get(p_path);
			T castedRes = p_class.cast(res);
			if (castedRes == null)
				throw new IllegalArgumentException("Currently loaded resource at path \"" + p_path + "\" is not a " + p_class.getName() + " resource");
			return castedRes;
		}
		else
		{
			// Open a new resource
			//System.out.println("ResourceManager: Loading new resource at path \"" + p_path + "\"");
			try
			{
				Resource res = (Resource) p_class.newInstance();
				res.openResource(p_path);
				m_resources.put(p_path, res);
				return p_class.cast(res);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Could not load resource at path \"" + p_path + "\"");
				return null;
			}
		}
	}
}
