package com.sebas.trimixpeople.models;

import com.sebas.trimixpeople.enums.Documento;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Personas", uniqueConstraints = {@UniqueConstraint(name = "UnicoTipoYNumeroDeDocumento", columnNames = {"perNumeroDocumento", "perTipoDocumento"})})
//No deberían haber 2 personas con el mismo tipo y número de documento simultáneamente.
public class Persona {

    @Id
    @GeneratedValue
    private Long perId;
    @Column(nullable = false)
    private String perApellido;
    @Column(nullable = false)
    private String perNombre;
    @Column(nullable = false)
    private Date perFechaNacimiento;
    @Column(nullable = false)
    private long perNumeroDocumento;
    @Column(nullable = false)
    private Documento perTipoDocumento;

    public Long getPerId() {
        return perId;
    }

    public void setPerId(Long perId) {
        this.perId = perId;
    }

    public String getPerApellido() {
        return perApellido;
    }

    public void setPerApellido(String perApellido) {
        this.perApellido = perApellido;
    }

    public String getPerNombre() {
        return perNombre;
    }

    public void setPerNombre(String perNombre) {
        this.perNombre = perNombre;
    }

    public Date getPerFechaNacimiento() {
        return perFechaNacimiento;
    }

    public void setPerFechaNacimiento(Date perFechaNacimiento) {
        this.perFechaNacimiento = perFechaNacimiento;
    }

    public long getPerNumeroDocumento() {
        return perNumeroDocumento;
    }

    public void setPerNumeroDocumento(long perNumeroDocumento) {
        this.perNumeroDocumento = perNumeroDocumento;
    }

    public Documento getPerTipoDocumento() {
        return perTipoDocumento;
    }

    public void setPerTipoDocumento(Documento perTipoDocumento) {
        this.perTipoDocumento = perTipoDocumento;
    }
}
