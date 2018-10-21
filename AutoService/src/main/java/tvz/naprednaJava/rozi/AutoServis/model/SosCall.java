package tvz.naprednaJava.rozi.AutoServis.model;


import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Audited
@Table(name = "sos_calls")
@Data
public class SosCall extends BaseObject implements Serializable {

    private static final long serialVersionUID = -4819287527605601001L;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private String coordinates;

    @Column
    private Date time;

}
