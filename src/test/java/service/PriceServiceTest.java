package service;

import com.qindel.model.Price;
import com.qindel.repository.PriceRepository;
import com.qindel.service.PriceService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    @ParameterizedTest
    @CsvSource({
            "1, 35455, 2023-12-14-10.00.00, 1",
            "1, 35455, 2023-12-14-16.00.00, 1",
            "1, 35455, 2023-12-14-21.00.00, 1",
            "1, 35455, 2023-12-15-10.00.00, 1",
            "1, 35455, 2023-12-16-21.00.00, 1"
    })
    void testGetSinglePrice(Long brandId, Long productId, String dateTime, int invocationTimes) {
        // Configurar el comportamiento simulado del repositorio
        when(priceRepository.findByBrandIdAndProductIdAndStartDateOrderByPriorityDesc(brandId, productId, parseStringToLocalDateTime(dateTime)))
                .thenReturn(new Price());

        // Llamar al método del servicio bajo prueba
        Map<String, Object> params = new HashMap<>();
        params.put("brandId", brandId);
        params.put("productId", productId);
        params.put("applicationDate", dateTime);

        Price result = priceService.getSinglePrice(params);

        // Verificar que el repositorio fue llamado con los parámetros correctos
        verify(priceRepository, times(invocationTimes)).findByBrandIdAndProductIdAndStartDateOrderByPriorityDesc(brandId, productId, parseStringToLocalDateTime(dateTime));

        // Verificar que el resultado del servicio coincide con la respuesta simulada del repositorio
        assertEquals(new Price(), result);
    }

    // Método para convertir una cadena de fecha y hora a LocalDateTime
    private LocalDateTime parseStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        return LocalDateTime.parse(dateString, formatter);
    }
}
