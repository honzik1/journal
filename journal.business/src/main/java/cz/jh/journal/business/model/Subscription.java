package cz.jh.journal.business.model;

import cz.jh.journal.model.DBEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * @author jan.horky
 * @version 1.0
 * @created 09-4-2016 18:01:27
 */
@Entity
public class Subscription extends DBEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    private Discipline discipline;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
