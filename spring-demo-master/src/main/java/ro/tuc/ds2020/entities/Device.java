package ro.tuc.ds2020.entities;

import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Device implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @Column(nullable = false)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Getter
    @Setter
    @Column(name = "description", nullable = false)
    private String description;

    @Getter
    @Setter
    @Column(name = "address", nullable = false)
    private String address;

    @Getter
    @Setter
    @Column(name = "max_consumption", nullable = false)
    private int max_consumption;

    @Getter
    @Setter
    @OneToMany(mappedBy = "device")
    private Set<ConsumptionRecord> records = new java.util.LinkedHashSet<>();

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    private Client user;

    public Device() {
    }

    public Device(UUID id, String description, String address, int max_consumption) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.max_consumption = max_consumption;
    }

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }

    public Set<ConsumptionRecord> getRecords() {
        return records;
    }

    public Device(String description, String address, int max_consumption) {
        this.description = description;
        this.address = address;
        this.max_consumption = max_consumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMax_consumption() {
        return max_consumption;
    }

    public void setMax_consumption(int max_consumption) {
        this.max_consumption = max_consumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Device device = (Device) o;
        return id != null && Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
