package com.qindel.repository;

import com.qindel.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio de datos para la entidad Price. Extiende JpaRepository para obtener
 * funcionalidades CRUD básicas y define métodos específicos de consulta.
 */
public interface PriceRepository extends JpaRepository<Price, Long> {

    /**
     * Busca precios por Brand ID, Product ID y Start Date, ordenados por prioridad de manera descendente.
     *
     * @param brandId       ID de la marca.
     * @param productId     ID del producto.
     * @param startDate     Fecha de inicio para la cual se busca el precio.
     * @return Lista de precios que coinciden con los parámetros de búsqueda, ordenados por prioridad descendente.
     */
   Price findByBrandIdAndProductIdAndStartDateOrderByPriorityDesc(
            Long brandId, Long productId, LocalDateTime startDate);
}
