package cz.jh.journal.business.model;

import cz.jh.journal.model.NamedEntity;
import cz.jh.journal.rest.view.View;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 * @author jan.horky
 * @version 1.0
 * @created 09-4-2016 16:39:47
 */
@Entity
@XmlRootElement
public class Journal extends NamedEntity {

    @JsonView(View.Detail.class)
    private String abbrTitle;
    @JsonView(View.Detail.class)
    private String language;
    @JsonView({View.Summary.class, View.Detail.class})
    private String publisher;
    @Temporal(javax.persistence.TemporalType.DATE)
    @JsonView({View.Summary.class, View.Detail.class})
    private Date publicFrom;
    @Temporal(javax.persistence.TemporalType.DATE)
    @JsonView({View.Summary.class, View.Detail.class})
    private Date publicTo;
    @Enumerated(EnumType.STRING)
    @JsonView(View.Detail.class)
    private Frequency frequency;
    @JsonView(View.Detail.class)
    private Float impactFactor;
    @JsonView(View.Detail.class)
    private Integer impactFactorYear;
    @JsonView(View.Detail.class)
    private String issn;
    @JsonView(View.Detail.class)
    private String lccn;
    @JsonView(View.Detail.class)
    private String coden;
    @JsonView(View.Detail.class)
    private String oclc;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView({View.Summary.class, View.Detail.class})
    private Discipline discipline;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView({View.Summary.class, View.Detail.class})
    private User editedBy;

    public String getAbbrTitle() {
        return abbrTitle;
    }

    public void setAbbrTitle(String abbrTitle) {
        this.abbrTitle = abbrTitle;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublicFrom() {
        return publicFrom;
    }

    public void setPublicFrom(Date publicFrom) {
        this.publicFrom = publicFrom;
    }

    public Date getPublicTo() {
        return publicTo;
    }

    public void setPublicTo(Date publicTo) {
        this.publicTo = publicTo;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Float getImpactFactor() {
        return impactFactor;
    }

    public void setImpactFactor(Float impactFactor) {
        this.impactFactor = impactFactor;
    }

    public Integer getImpactFactorYear() {
        return impactFactorYear;
    }

    public void setImpactFactorYear(Integer impactFactorYear) {
        this.impactFactorYear = impactFactorYear;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getLccn() {
        return lccn;
    }

    public void setLccn(String lccn) {
        this.lccn = lccn;
    }

    public String getCoden() {
        return coden;
    }

    public void setCoden(String coden) {
        this.coden = coden;
    }

    public String getOclc() {
        return oclc;
    }

    public void setOclc(String oclc) {
        this.oclc = oclc;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public User getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(User editedBy) {
        this.editedBy = editedBy;
    }
}
