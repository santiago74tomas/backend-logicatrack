package logitrack.controller;

import logitrack.model.Envio;
import logitrack.service.EnvioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @PostMapping
    public Envio crearEnvio(@RequestBody Envio envio) {
        return envioService.crearEnvio(envio);
    }

    @GetMapping
    public List<Envio> listarEnvios() {
        return envioService.listarEnvios();
    }

    @GetMapping("/{trackingId}")
    public Envio obtenerEnvio(@PathVariable String trackingId) {
        return envioService.obtenerEnvio(trackingId);
    }

    @PatchMapping("/{trackingId}/estado")
    public Envio actualizarEstado(
            @PathVariable String trackingId,
            @RequestBody Map<String, String> body
    ) {
        return envioService.actualizarEstado(trackingId, body.get("estado"));
    }
}