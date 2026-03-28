package logitrack.service;

import logitrack.model.Envio;
import logitrack.model.EstadoEnvio;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EnvioService {

    private List<Envio> envios = new ArrayList<>();

    public Envio crearEnvio(Envio envio) {
        envio.setTrackingId(UUID.randomUUID().toString());
        envio.setEstadoActual(EstadoEnvio.CREADO.name());
        envio.setFechaCreacion(LocalDateTime.now());

        envios.add(envio);
        return envio;
    }

    public List<Envio> listarEnvios() {
        return envios;
    }

    public Envio obtenerEnvio(String trackingId) {
        return envios.stream()
                .filter(e -> e.getTrackingId().equals(trackingId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
    }

    public Envio actualizarEstado(String trackingId, String nuevoEstado) {
        Envio envio = obtenerEnvio(trackingId);

        envio.setEstadoActual(nuevoEstado);
        return envio;
    }

    // 🔥 ACÁ VA EL POSTCONSTRUCT (dentro de la clase)
    @PostConstruct
    public void cargarDatosIniciales() {

        Envio e1 = new Envio();
        e1.setTrackingId("TRK1001");
        e1.setRemitente("Juan Pérez");
        e1.setDestinatario("Ana López");
        e1.setOrigen("Buenos Aires");
        e1.setDestino("Rosario");
        e1.setEstadoActual(EstadoEnvio.EN_TRANSITO.name());
        e1.setFechaCreacion(LocalDateTime.now());

        Envio e2 = new Envio();
        e2.setTrackingId("TRK1002");
        e2.setRemitente("Carlos Díaz");
        e2.setDestinatario("Luis Fernández");
        e2.setOrigen("La Plata");
        e2.setDestino("Mendoza");
        e2.setEstadoActual(EstadoEnvio.EN_SUCURSAL.name());
        e2.setFechaCreacion(LocalDateTime.now());

        Envio e3 = new Envio();
        e3.setTrackingId("TRK1003");
        e3.setRemitente("Empresa X");
        e3.setDestinatario("María Gómez");
        e3.setOrigen("Córdoba");
        e3.setDestino("Buenos Aires");
        e3.setEstadoActual(EstadoEnvio.EN_CAMINO.name());
        e3.setFechaCreacion(LocalDateTime.now());

        envios.add(e1);
        envios.add(e2);
        envios.add(e3);
    }
}