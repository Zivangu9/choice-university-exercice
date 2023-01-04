package choice.university.ivan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import choice.university.ivan.service.HotelService;

@Configuration
public class AppConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("choice.university.ivan.schemas");
        return marshaller;
    }

    @Bean
    public HotelService hotelClient(Jaxb2Marshaller marshaller) {
        HotelService client = new HotelService();
        client.setDefaultUri("http://localhost:8081/ws/hotels");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
