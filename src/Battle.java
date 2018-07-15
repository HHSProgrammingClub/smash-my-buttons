import java.util.ArrayList;
public class Battle {
	// Oh look! A stub!
	private ArrayList<Hitbox> m_hitboxes;
	public Hitbox[] getHitboxes() { return (Hitbox[]) m_hitboxes.toArray(); }
	public int addHitbox(Hitbox h) {
		m_hitboxes.add(h);
		// NOTE: not sure what this should return in terms of an int.
		return m_hitboxes.size();
	}
}
