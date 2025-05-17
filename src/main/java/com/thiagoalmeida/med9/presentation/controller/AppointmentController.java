package com.thiagoalmeida.med9.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    // Simulação de dados de consultas para demonstração
    private static final Map<Long, String> APPOINTMENTS = new HashMap<>();
    static {
        APPOINTMENTS.put(1L, "Consulta do paciente José com Dr. João - 10/05/2025");
        APPOINTMENTS.put(2L, "Consulta do paciente Maria com Dr. João - 12/05/2025");
        APPOINTMENTS.put(3L, "Consulta do paciente Carlos com Dra. Ana - 15/05/2025");
    }

    // Endpoint para listar todas as consultas (acessível por médicos, enfermeiros e admin)
    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getAllAppointments() {
        // Simulação de resposta com dados de consultas
        List<Map<String, Object>> appointments = APPOINTMENTS.entrySet().stream()
            .map(entry -> {
                Map<String, Object> appointment = new HashMap<>();
                appointment.put("id", entry.getKey());
                appointment.put("description", entry.getValue());
                return appointment;
            })
            .toList();
        
        return ResponseEntity.ok(appointments);
    }

    // Endpoint para pacientes verem apenas suas próprias consultas
    @GetMapping("/my")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Map<String, Object>>> getMyAppointments() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Simulação: filtrar apenas consultas do paciente logado (neste caso, apenas a primeira)
        List<Map<String, Object>> patientAppointments = APPOINTMENTS.entrySet().stream()
            .filter(entry -> entry.getValue().contains("paciente José")) // Simulando filtro para o paciente logado
            .map(entry -> {
                Map<String, Object> appointment = new HashMap<>();
                appointment.put("id", entry.getKey());
                appointment.put("description", entry.getValue());
                return appointment;
            })
            .toList();
        
        return ResponseEntity.ok(patientAppointments);
    }

    // Endpoint para registrar uma nova consulta (acessível por enfermeiros e admin)
    @PostMapping
    @PreAuthorize("hasAnyRole('NURSE', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> createAppointment(@RequestBody Map<String, String> appointmentData) {
        // Simulação de criação de consulta
        Long newId = APPOINTMENTS.size() + 1L;
        APPOINTMENTS.put(newId, appointmentData.get("description"));
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", newId);
        response.put("description", appointmentData.get("description"));
        response.put("message", "Consulta registrada com sucesso");
        
        return ResponseEntity.ok(response);
    }

    // Endpoint para atualizar uma consulta (acessível por médicos e admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> updateAppointment(
            @PathVariable Long id, 
            @RequestBody Map<String, String> appointmentData) {
        
        if (!APPOINTMENTS.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        
        APPOINTMENTS.put(id, appointmentData.get("description"));
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("description", appointmentData.get("description"));
        response.put("message", "Consulta atualizada com sucesso");
        
        return ResponseEntity.ok(response);
    }
}
