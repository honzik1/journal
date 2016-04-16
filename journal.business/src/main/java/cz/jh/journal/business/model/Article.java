package cz.jh.journal.business.model;

import cz.jh.journal.model.NamedEntity;
import cz.jh.journal.rest.view.View;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 * @author jan.horky
 * @version 1.0
 * @created 09-4-2016 17:26:58
 */
@Entity
@XmlRootElement
public class Article extends NamedEntity {

    @JsonView({View.Detail.class})
    private String authors;
    @JsonView({View.Detail.class})
    private String abst;
    @JsonView({View.Detail.class})
    private String doi;
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull(message = "publishDate is required.")
    @Column(nullable = false)
    @JsonView({View.Detail.class})
    private Date publishDate;
    @JsonView({View.Detail.class})
    private String copyright;
    @JsonView({View.Detail.class})
    private String fileId;
    private String filePath;
    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    @JsonBackReference
    private User owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    @JsonBackReference
    private Journal journal;

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getAbst() {
        return abst;
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }
}
