package fr.polytech.info4.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Coursier.
 */
@Entity
@Table(name = "coursier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Coursier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull
    @Column(name = "transport_mean", nullable = false)
    private String transportMean;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "[0-9]*")
    @Column(name = "phone", length = 10, nullable = false)
    private String phone;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coursier name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Coursier surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTransportMean() {
        return transportMean;
    }

    public Coursier transportMean(String transportMean) {
        this.transportMean = transportMean;
        return this;
    }

    public void setTransportMean(String transportMean) {
        this.transportMean = transportMean;
    }

    public String getPhone() {
        return phone;
    }

    public Coursier phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public Coursier user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coursier)) {
            return false;
        }
        return id != null && id.equals(((Coursier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Coursier{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", transportMean='" + getTransportMean() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
