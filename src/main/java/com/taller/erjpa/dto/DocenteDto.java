package com.taller.erjpa.dto;

public class DocenteDto {

    private Long idDocente;
    private String nombresDocente;
    private String apellidosDocente;
    private String nombreGrupo;
    private String correo;

    public DocenteDto() {
    }

    public DocenteDto(Long idDocente, String nombresDocente, String apellidosDocente, String nombreGrupo, String correo) {
        this.idDocente = idDocente;
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
}
