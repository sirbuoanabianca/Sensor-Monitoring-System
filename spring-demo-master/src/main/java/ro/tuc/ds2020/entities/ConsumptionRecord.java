package ro.tuc.ds2020.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class ConsumptionRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Getter
    @Setter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Getter
    @Setter
    @Column(name = "time_stamp", nullable = false)
    private Timestamp timestamp;

    @Getter
    @Setter
    @Column(name = "consumption", nullable = false)
    private double consumption;

    public ConsumptionRecord(UUID id, Timestamp timestamp, double consumption) {
        this.id = id;
        this.timestamp = timestamp;
        this.consumption = consumption;
    }
}
