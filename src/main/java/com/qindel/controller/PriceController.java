package com.qindel.controller;

import com.qindel.model.Price;
import com.qindel.service.PriceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "Price", description = "Endpoints para gestionar precios")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @PostMapping(value = "/getPrice", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "Obtener lista de precios",
            notes = "Obtiene una lista de precios según los parámetros proporcionados.",
            response = Price.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operación exitosa. Devuelve la lista de precios.", response = Price.class, responseContainer = "List"),
            @ApiResponse(code = 204, message = "Sin contenido. No se encontraron precios para los parámetros proporcionados."),
            @ApiResponse(code = 400, message = "Solicitud incorrecta. Puede deberse a parámetros inválidos."),
            @ApiResponse(code = 403, message = "Acceso prohibido. No tienes los permisos necesarios."),
            @ApiResponse(code = 500, message = "Error interno del servidor. Consulta los logs para más detalles.")
    })
    public ResponseEntity<Price> getPriceList(
            @ApiParam(value = "Parámetros para la obtención de la lista de precios. Ejemplo: {\"productId\": 35455, \"brandId\": 2, \"applicationDate\": \"2020-06-14-15.00.00\"}", required = true)
            @RequestBody Map<String, Object> params) {

        try {
            Price prices = priceService.getSinglePrice(params);

            if (prices != null) {
                return ResponseEntity.ok(prices);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
