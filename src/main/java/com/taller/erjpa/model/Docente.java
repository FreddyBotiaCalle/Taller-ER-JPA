package com.taller.erjpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "docentes")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    private Long idDocente;

    @NotBlank(message = "Los nombres son obligatorios")
    @Column(name = "nombres_docente", nullable = false, length = 100)
    private String nombresDocente;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(name = "apellidos_docente", nullable = false, length = 100)
    private String apellidosDocente;

    @NotBlank(message = "El nombre del grupo es obligatorio")
    @Column(name = "nombre_grupo", nullable = false, length = 50)
    private String nombreGrupo;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no es valido")
    @Column(name = "correo", nullable = false, unique = true, length = 100)
    private String correo;

    @JsonIgnore
    @OneToMany(mappedBy = "docente", orphanRemoval = true)
    private Set<FormatoA> formatosA = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "docente", orphanRemoval = true)
    private Set<Historico> historicos = new HashSet<>();

    @ManyToMany(mappedBy = "docentes")
    private List<Observacion> observaciones = new ArrayList<>();

    public Docente() {
    }

    public Docente(String nombresDocente, String apellidosDocente, String nombreGrupo, String correo) {
        this.nombresDocente = nombresDocente;
        this.apellidosDocente = apellidosDocente;
        this.nombreGrupo = nombreGrupo;
        this.correo = correo;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public String getNombresDocente() {
        return nombresDocente;
    }

    public void setNombresDocente(String nombresDocente) {
        this.nombresDocente = nombresDocente;
    }

    public String getApellidosDocente() {
        return apellidosDocente;
    }

    public void setApellidosDocente(String apellidosDocente) {
        this.apellidosDocente = apellidosDocente;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Set<FormatoA> getFormatosA() {
        return formatosA;
    }

    public void setFormatosA(Set<FormatoA> formatosA) {
        this.formatosA = formatosA;
    }

    public Set<Historico> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(Set<Historico> historicos) {
        this.historicos = historicos;
    }

    public List<Observacion> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(List<Observacion> observaciones) {
        this.observaciones = observaciones;
    }
}
