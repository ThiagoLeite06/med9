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
@RequestMapping("/api/medical-history")
@RequiredArgsConstructor
public class MedicalHistoryController {

    // Simulação de dados de histórico médico para demonstração
    private static final Map<Long, Map<String, Object>> MEDICAL_HISTORY = new HashMap<>();
    static {
        Map<String, Object> history1 = new HashMap<>();
        history1.put("patientId", 4L); // ID do paciente José
        history1.put("patientName", "Paciente José");
        history1.put("description", "Histórico de consultas e tratamentos do paciente José");
        history1.put("lastUpdate", "15/05/2025");
        MEDICAL_HISTORY.put(1L, history1);
        
        Map<String, Object> history2 = new HashMap<>();
        history2.put("patientId", 5L);
        history2.put("patientName", "Paciente Maria");
        history2.put("description", "Histórico de consultas e tratamentos da paciente Maria");
        history2.put("lastUpdate", "12/05/2025");
        MEDICAL_HISTORY.put(2L, history2);
    }

    // Endpoint para listar todo o histórico médico (acessível por médicos, enfermeiros e admin)
    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getAllMedicalHistory() {
        List<Map<String, Object>> historyList = MEDICAL_HISTORY.entrySet().stream()
            .map(entry -> {
                Map<String, Object> history = new HashMap<>(entry.getValue());
                history.put("id", entry.getKey());
                return history;
            })
            .toList();
        
        return ResponseEntity.ok(historyList);
    }

    // Endpoint para obter histórico médico específico por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getMedicalHistoryById(@PathVariable Long id) {
        if (!MEDICAL_HISTORY.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> history = new HashMap<>(MEDICAL_HISTORY.get(id));
        history.put("id", id);
        
        return ResponseEntity.ok(history);
    }

    // Endpoint para pacientes verem apenas seu próprio histórico médico
    @GetMapping("/my")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Map<String, Object>> getMyMedicalHistory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Simulação: encontrar o histórico do paciente logado (neste caso, o paciente José)
        Map<String, Object> patientHistory = MEDICAL_HISTORY.entrySet().stream()
            .filter(entry -> {
                Map<String, Object> history = entry.getValue();
                return "Paciente José".equals(history.get("patientName"));
            })
            .findFirst()
            .map(entry -> {
                Map<String, Object> history = new HashMap<>(entry.getValue());
                history.put("id", entry.getKey());
                return history;
            })
            .orElse(new HashMap<>());
        
        return ResponseEntity.ok(patientHistory);
    }

    // Endpoint para atualizar histórico médico (acessível apenas por médicos e admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> updateMedicalHistory(
            @PathVariable Long id, 
            @RequestBody Map<String, Object> historyData) {
        
        if (!MEDICAL_HISTORY.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> existingHistory = MEDICAL_HISTORY.get(id);
        existingHistory.put("description", historyData.get("description"));
        existingHistory.put("lastUpdate", "17/05/2025"); // Data atual simulada
        
        Map<String, Object> response = new HashMap<>(existingHistory);
        response.put("id", id);
        response.put("message", "Histórico médico atualizado com sucesso");
        
        return ResponseEntity.ok(response);
    }

    // Endpoint para adicionar novas entradas ao histórico (acessível por médicos, enfermeiros e admin)
    @PostMapping("/{id}/entries")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> addHistoryEntry(
            @PathVariable Long id, 
            @RequestBody Map<String, String> entryData) {
        
        if (!MEDICAL_HISTORY.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> existingHistory = MEDICAL_HISTORY.get(id);
        String currentDescription = (String) existingHistory.get("description");
        existingHistory.put("description", currentDescription + "\n- " + entryData.get("entry"));
        existingHistory.put("lastUpdate", "17/05/2025"); // Data atual simulada
        
        Map<String, Object> response = new HashMap<>(existingHistory);
        response.put("id", id);
        response.put("message", "Nova entrada adicionada ao histórico médico");
        
        return ResponseEntity.ok(response);
    }
}
