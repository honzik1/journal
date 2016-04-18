/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.api.model;

import cz.jh.journal.model.DBEntity;
import cz.jh.journal.rest.view.View;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 *
 * @author jan.horky
 * @param <Entity>
 */
@XmlRootElement
public class ListEnvelope<Entity extends DBEntity> {

    @JsonView({View.Summary.class, View.Detail.class})
    private long total;
    @JsonView({View.Summary.class, View.Detail.class})
    private List<Entity> records;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Entity> getRecords() {
        return records;
    }

    public void setRecords(List<Entity> records) {
        this.records = records;
    }
}
