package com.portfolio.inventory_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildResponse(String error, String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("error", error);
        body.put("message", message);
        body.put("status", status.value());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        return buildResponse("Internal Server Error", "Ocurrió un error inesperado. Contacte al administrador.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<Map<String, Object>> handleStockInsuficienteException(StockInsuficienteException ex) {
        return buildResponse("Error de Inventario", ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedRoleException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedRoleException(UnauthorizedRoleException ex) {
        return buildResponse("Acceso Denegado", ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmployeeStatusException.class)
    public ResponseEntity<Map<String, Object>> handleEmployeeStatusException(EmployeeStatusException ex) {
        return buildResponse("Empleado No Operativo", ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessLogicException(BusinessLogicException ex) {
        return buildResponse("Regla de Negocio Violada", ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildResponse("Recurso No Encontrado", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FiscalConfigException.class)
    public ResponseEntity<Map<String, Object>> handleFiscalConfigException(FiscalConfigException ex) {
        return buildResponse("Configuración Fiscal Pendiente", ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(SupplierConflictException.class)
    public ResponseEntity<Map<String, Object>> handleSupplierConflictException(SupplierConflictException ex) {
        return buildResponse("Proveedor Bloquedo. Revisar Historial", ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailInvalidException.class)
    public ResponseEntity<Map<String, Object>> handleEmailInvalidException(EmailInvalidException ex) {
        return buildResponse("Email Invalido", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildResponse("Error de Validación", message, HttpStatus.BAD_REQUEST);
    }
}
