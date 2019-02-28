package characters;

public final class CharacterFactory
{
	public static Character create(String p_name)
	{
		switch(p_name)
		{
			case "Jack":
				return new Jack();
			case "Birboi":
				return new Birboi();
			case "Edgewardo":
				return new Edgewardo();
			case "Cam":
				return new Cam();
			case "W'all":
				return new WallTheEncircler();
			case "Jimmy":
				return new Jimmy();
			case "Techie":
				return new Techie();
		}
		return new Jack();
	}
}
