package cz.jh.journal.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * @author jan.horky
 * @version 1.0
 * @created 09-4-2016 18:01:27
 */
@Entity
public class Subscription extends DBEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Discipline discipline;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
