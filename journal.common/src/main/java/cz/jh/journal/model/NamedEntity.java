package cz.jh.journal.model;

import cz.jh.journal.rest.view.View;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 * @author jan.horky
 * @version 1.0
 * @created 09-4-2016 17:23:00
 */
@MappedSuperclass
public class NamedEntity extends DBEntity<Long> {

    @NotNull
    @Column(nullable = false)
    @JsonView({View.Summary.class,View.Detail.class})
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
