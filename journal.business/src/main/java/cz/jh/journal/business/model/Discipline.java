package cz.jh.journal.business.model;

import cz.jh.journal.model.NamedEntity;
import javax.persistence.Entity;

/**
 * @author jan.horky
 * @version 1.0
 * @created 09-4-2016 18:01:25
 */
@Entity
public class Discipline extends NamedEntity {

    public Discipline() {
    }

    public Discipline(String title) {
        this.setTitle(title);
    }

}
