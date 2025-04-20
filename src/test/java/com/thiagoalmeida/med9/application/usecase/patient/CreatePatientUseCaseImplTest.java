package com.thiagoalmeida.med9.application.usecase.patient;

import com.thiagoalmeida.med9.application.dto.patient.PatientRequest;
import com.thiagoalmeida.med9.domain.entity.Patient;
import com.thiagoalmeida.med9.domain.entity.User;
import com.thiagoalmeida.med9.domain.enums.Role;
import com.thiagoalmeida.med9.domain.repository.PatientRepository;
import com.thiagoalmeida.med9.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePatientUseCaseImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreatePatientUseCaseImpl createPatientUseCase;

    private PatientRequest request;
    private Patient patient;
    private User user;

    @BeforeEach
    void setUp() {
        request = new PatientRequest(
            "John Doe",
            "12345678900",
            "11999999999",
            "john@example.com",
            "johndoe",
            "password123"

        );

        patient = new Patient(
            1L,
            request.name(),
            request.cpf(),
            request.phone(),
            request.email(),
            1L
        );

        user = new User(
            1L,
            request.username(),
            "encoded_password",
            request.name(),
            request.email(),
            Role.PATIENT
        );
    }

    @Test
    void shouldCreatePatientSuccessfully() {
        // Arrange
        when(patientRepository.findByCpf(request.cpf())).thenReturn(Optional.empty());
        when(patientRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.password())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Act
        Patient result = createPatientUseCase.execute(request);

        // Assert
        assertNotNull(result);
        assertEquals(request.name(), result.name());
        assertEquals(request.cpf(), result.cpf());
        assertEquals(request.phone(), result.phone());
        assertEquals(request.email(), result.email());
        
        verify(patientRepository).findByCpf(request.cpf());
        verify(patientRepository).findByEmail(request.email());
        verify(passwordEncoder).encode(request.password());
        verify(userRepository).save(any(User.class));
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void shouldThrowExceptionWhenCpfAlreadyExists() {
        // Arrange
        when(patientRepository.findByCpf(request.cpf())).thenReturn(Optional.of(patient));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createPatientUseCase.execute(request)
        );
        assertEquals("CPF já cadastrado", exception.getMessage());
        
        verify(patientRepository).findByCpf(request.cpf());
        verify(patientRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        when(patientRepository.findByCpf(request.cpf())).thenReturn(Optional.empty());
        when(patientRepository.findByEmail(request.email())).thenReturn(Optional.of(patient));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createPatientUseCase.execute(request)
        );
        assertEquals("Email já cadastrado", exception.getMessage());
        
        verify(patientRepository).findByCpf(request.cpf());
        verify(patientRepository).findByEmail(request.email());
        verify(patientRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }
}
