package cz.jh.journal.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * @author jan.horky
 * @version 1.0
 * @created 09-4-2016 17:23:00
 */
@MappedSuperclass
public class NamedEntity extends DBEntity<Long> {

    @NotNull
    @Column(nullable = false)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
