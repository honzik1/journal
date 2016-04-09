package cz.jh.journal.model;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author jan.horky
 * @version 1.0
 * @param <ID> generic primary key type
 * @created 09-4-2016 16:58:14
 */
@MappedSuperclass
public class DBEntity<ID extends Serializable & Comparable<ID>> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ID id;
    /**
     * Creation time of entity (automatically filled)
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @NotNull(message = "createTime is required.")
    @Column(updatable = false)
    private Date createTime;
    /**
     * Update time of entity (automatically filled)
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(insertable = false)
    private Date updateTime = null;

    /**
     * Creator ID of entity.
     */
    @Column(nullable = false, updatable = false)
    @NotNull(message = "createdBy is required.")
    @XmlTransient
    private ID createdBy;

    /**
     * Update ID of entity.
     */
    @Column(nullable = true, updatable = true, insertable = false)
    @XmlTransient
    private ID updatedBy;

    public DBEntity() {
        createTime = new Date();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ID createdBy) {
        this.createdBy = createdBy;
    }

    public ID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(ID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setDBElement(DBEntity<ID> el) {
        if (el.getId() != null) {
            this.setId(el.getId());
        }
        if (el.getCreatedBy() != null) {
            this.setCreatedBy(el.getCreatedBy());
        }
    }

    @PrePersist
    public void onPrePersist() {
        this.createTime = new Date();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updateTime = new Date();
    }

    protected boolean equalsById(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof DBEntity)) {
            return false;
        }
        final DBEntity<ID> other = (DBEntity<ID>) obj;
        return this.getId() == other.getId() || (this.getId() != null && this.getId().equals(other.getId()));
    }

    protected int hashCodeByid() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return equalsById(obj);
    }

    @Override
    public int hashCode() {
        return hashCodeByid();
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}(id = {1})", getClass().getSimpleName(), id != null ? id.toString() : null);
    }

}
