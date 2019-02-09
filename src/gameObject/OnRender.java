package gameObject;

import graphics.pages.Renderer;

public class OnRender implements Message
{
	private Renderer m_renderer;
	
	public OnRender(Renderer p_renderer)
	{
		m_renderer = p_renderer;
	}
	
	public Renderer getRenderer()
	{
		return m_renderer;
	}
}
