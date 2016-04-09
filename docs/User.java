

/**
 * @author jan.horky
 * @version 1.0
 * @created 09-4-2016 17:39:59
 */
public class User extends DBEntity {
	private UserRole role;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	public Subscription m_Subscription;
	private boolean active;
	public Journal m_Journal;
	public Article m_Article;
}