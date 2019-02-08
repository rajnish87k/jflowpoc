package com.spr.jflow.poc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A JournalR.
 */
@Entity
@Table(name = "journal_r")
public class JournalR implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "object_id")
    private String objectId;

    @Column(name = "name_of_society")
    private String nameOfSociety;

    @Column(name = "online_services")
    private String onlineServices;

    @ManyToOne
    @JsonIgnoreProperties("journalRS")
    private JournalS required;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public JournalR objectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getNameOfSociety() {
        return nameOfSociety;
    }

    public JournalR nameOfSociety(String nameOfSociety) {
        this.nameOfSociety = nameOfSociety;
        return this;
    }

    public void setNameOfSociety(String nameOfSociety) {
        this.nameOfSociety = nameOfSociety;
    }

    public String getOnlineServices() {
        return onlineServices;
    }

    public JournalR onlineServices(String onlineServices) {
        this.onlineServices = onlineServices;
        return this;
    }

    public void setOnlineServices(String onlineServices) {
        this.onlineServices = onlineServices;
    }

    public JournalS getRequired() {
        return required;
    }

    public JournalR required(JournalS journalS) {
        this.required = journalS;
        return this;
    }

    public void setRequired(JournalS journalS) {
        this.required = journalS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JournalR journalR = (JournalR) o;
        if (journalR.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journalR.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JournalR{" +
            "id=" + getId() +
            ", objectId='" + getObjectId() + "'" +
            ", nameOfSociety='" + getNameOfSociety() + "'" +
            ", onlineServices='" + getOnlineServices() + "'" +
            "}";
    }
}
