package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Util
{
	public static String getStreamAsString(InputStream p_inputStream) throws IOException
	{
		BufferedReader buf = null;
		try
		{
			buf = new BufferedReader(new InputStreamReader(p_inputStream));
			return buf.lines().collect(Collectors.joining("\n"));
		}
		finally
		{
			buf.close();
		}
	}
	
	public static String getResourceAsString(String p_path) throws Exception
	{
		InputStream stream = ClassLoader.getSystemResourceAsStream(p_path);
		if (stream == null)
			throw new Exception("Cannot load resoufce \"" + p_path + "\" from Jar file.");
		return getStreamAsString(stream);
	}
}
