package com.qindel.config;
import com.qindel.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.qindel.repository.*;
import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@Slf4j
public class DataLoadConfig {

    @Autowired
    private PriceRepository priceRepository;

    @PostConstruct
    public void loadData() throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");

        // Datos de ejemplo del enunciado
        Price price1 = new Price(1L, LocalDateTime.parse("2020-06-14-00.00.00", formatter), LocalDateTime.parse("2020-12-31-23.59.59", formatter), 1L, 35455L, 0, 35.50, "EUR");
        Price price2 = new Price(2L, LocalDateTime.parse("2020-06-14-15.00.00", formatter), LocalDateTime.parse("2020-06-14-18.30.00", formatter), 2L, 35455L, 1, 25.45, "EUR");
        Price price3 = new Price(3L, LocalDateTime.parse("2020-06-15-00.00.00", formatter), LocalDateTime.parse("2020-06-15-11.00.00", formatter), 3L, 35455L, 1, 30.50, "EUR");
        Price price4 = new Price(4L, LocalDateTime.parse("2020-06-15-16.00.00", formatter), LocalDateTime.parse("2020-12-31-23.59.59", formatter), 4L, 35455L, 1, 38.95, "EUR");

        // Guardar en la base de datos
        priceRepository.save(price1);
        priceRepository.save(price2);
        priceRepository.save(price3);
        priceRepository.save(price4);
    }
}
