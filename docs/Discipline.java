

/**
 * @author jan.horky
 * @version 1.0
 * @created 09-4-2016 18:01:25
 */
public class Discipline extends NamedEntity {

	public Subscription m_Subscription;
	public Journal m_Journal;

	public Discipline(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

}