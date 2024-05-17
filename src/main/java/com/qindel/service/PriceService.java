package com.qindel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import com.qindel.model.Price;
import com.qindel.repository.PriceRepository;

@Service
@Slf4j
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    /**
     * Obtiene un único precio filtrado por prioridad.
     *
     * @param params Parámetros para la obtención del precio.
     * @return El precio filtrado por prioridad.
     * @throws RuntimeException Si ocurre un error durante la ejecución.
     */
    public Price getSinglePrice(Map<String, Object> params) {
        try {
            // Extraer parámetros del mapa
            Long brandId = getLongParameter(params, "brandId");
            Long productId = getLongParameter(params, "productId");
            LocalDateTime applicationDate = parseStringToLocalDateTime((String) params.get("applicationDate"));

            log.info("Getting single price for Brand ID {}, Product ID {}, Application Date {}", brandId, productId, applicationDate);

            // Delegar al repositorio con orden descendente por prioridad y límite 1
            Price price = priceRepository.findByBrandIdAndProductIdAndStartDateOrderByPriorityDesc(
                    brandId, productId, applicationDate);

            if (price != null) {
                // Devolver el primer resultado (mayor prioridad)
                return price;
            } else {
                log.warn("No prices found for the given parameters");
                return null;
            }
        } catch (Exception e) {
            log.error("Error processing getSinglePrice request", e);
            throw new RuntimeException("Error processing getSinglePrice request", e);
        }
    }

    /**
     * Convierte un valor del mapa a un Long.
     *
     * @param params    El mapa de parámetros.
     * @param paramName El nombre del parámetro.
     * @return El valor del parámetro convertido a Long.
     * @throws IllegalArgumentException Si el valor no es un número.
     */
    private Long getLongParameter(Map<String, Object> params, String paramName) {
        Object paramValue = params.get(paramName);
        if (paramValue instanceof Number) {
            return ((Number) paramValue).longValue();
        } else {
            throw new IllegalArgumentException(paramName + " must be a number");
        }
    }

    /**
     * Convierte una cadena de fecha a LocalDateTime.
     *
     * @param dateString La cadena de fecha.
     * @return LocalDateTime correspondiente a la cadena de fecha.
     */
    private LocalDateTime parseStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        return LocalDateTime.parse(dateString, formatter);
    }
}
