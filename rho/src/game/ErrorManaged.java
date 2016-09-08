package game;

public class ErrorManaged {
	private static boolean m_managed;
	
	public static boolean getErrorManaged()
	{
		return m_managed;
	}
	
	public static void setErrorManaged(boolean errorManaged)
	{
		m_managed = errorManaged;
	}
	
}
