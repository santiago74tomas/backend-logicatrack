package logitrack.service;

import logitrack.model.Envio;
import logitrack.model.EstadoEnvio;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class EnvioService {

private String obtenerPrioridadML(Envio envio) {
    try {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://ml-service-production-cd6f.up.railway.app/priorizar";

        Map<String, Object> request = new HashMap<>();
        request.put("distancia_km", envio.getDistanciaKm());
        request.put("tipo_envio", envio.getTipoEnvio());
        request.put("ventana_horaria", envio.getVentanaHoraria());
        request.put("fragil", envio.isFragil() ? 1 : 0);
        request.put("frio", envio.isFrio() ? 1 : 0);
        request.put("saturacion_ruta", envio.getSaturacionRuta());

        Map response = restTemplate.postForObject(url, request, Map.class);

        return response.get("prioridad").toString();

    } catch (Exception e) {
        return "MEDIA"; // fallback
    }
}
    private List<Envio> envios = new ArrayList<>();

public Envio crearEnvio(Envio envio) {
    envio.setTrackingId(UUID.randomUUID().toString());
    envio.setEstadoActual(EstadoEnvio.CREADO.name());
    envio.setFechaCreacion(LocalDateTime.now());

    String prioridad = obtenerPrioridadML(envio);
    envio.setPrioridad(prioridad);

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

    // 🔥 datos ML
    e1.setDistanciaKm(300);
    e1.setTipoEnvio("normal");
    e1.setVentanaHoraria("tarde");
    e1.setVolumen(0.5);
    e1.setFragil(false);
    e1.setFrio(false);
    e1.setSaturacionRuta(0.6);

    e1.setPrioridad(obtenerPrioridadML(e1));

    // ------------------------

    Envio e2 = new Envio();
    e2.setTrackingId("TRK1002");
    e2.setRemitente("Carlos Díaz");
    e2.setDestinatario("Luis Fernández");
    e2.setOrigen("La Plata");
    e2.setDestino("Mendoza");
    e2.setEstadoActual(EstadoEnvio.EN_SUCURSAL.name());
    e2.setFechaCreacion(LocalDateTime.now());

    // 🔥 datos ML
    e2.setDistanciaKm(1000);
    e2.setTipoEnvio("express");
    e2.setVentanaHoraria("mañana");
    e2.setVolumen(0.2);
    e2.setFragil(true);
    e2.setFrio(false);
    e2.setSaturacionRuta(0.9);

    e2.setPrioridad(obtenerPrioridadML(e2));

    // ------------------------

    Envio e3 = new Envio();
    e3.setTrackingId("TRK1003");
    e3.setRemitente("Empresa X");
    e3.setDestinatario("María Gómez");
    e3.setOrigen("Córdoba");
    e3.setDestino("Buenos Aires");
    e3.setEstadoActual(EstadoEnvio.EN_CAMINO.name());
    e3.setFechaCreacion(LocalDateTime.now());

    // 🔥 datos ML
    e3.setDistanciaKm(700);
    e3.setTipoEnvio("normal");
    e3.setVentanaHoraria("noche");
    e3.setVolumen(1.0);
    e3.setFragil(false);
    e3.setFrio(true);
    e3.setSaturacionRuta(0.4);

    e3.setPrioridad(obtenerPrioridadML(e3));

    // ------------------------

    envios.add(e1);
    envios.add(e2);
    envios.add(e3);
}
}