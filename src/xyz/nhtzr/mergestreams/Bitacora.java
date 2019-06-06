package xyz.nhtzr.mergestreams;

import java.util.Date;

public class Bitacora {

    private final Integer id;
    private final Integer origin;
    private final Date fechaInicio;

    public Bitacora(Integer id, Integer origin, Date fechaInicio) {
        this.id = id;
        this.origin = origin;
        this.fechaInicio = fechaInicio;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOrigin() {
        return origin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }
}
