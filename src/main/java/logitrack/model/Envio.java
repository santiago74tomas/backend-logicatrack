package logitrack.model;

import java.time.LocalDateTime;

public class Envio {

    private String trackingId;
    private String remitente;
    private String destinatario;
    private String origen;
    private String destino;
    private String prioridad;
    private String estadoActual;
    private LocalDateTime fechaCreacion;

    // Getters y Setters
    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }

    public String getRemitente() { return remitente; }
    public void setRemitente(String remitente) { this.remitente = remitente; }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getEstadoActual() { return estadoActual; }
    public void setEstadoActual(String estadoActual) { this.estadoActual = estadoActual; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public String getPrioridad() {
    return prioridad;
}

public void setPrioridad(String prioridad) {
    this.prioridad = prioridad;
}
}