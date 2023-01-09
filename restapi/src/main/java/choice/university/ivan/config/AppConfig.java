package choice.university.ivan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import choice.university.ivan.client.HotelClient;
import choice.university.ivan.exception.SoapClientInterceptor;

@Configuration
public class AppConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("choice.university.ivan.schemas");
        return marshaller;
    }

    @Bean
    public HotelClient hotelClient(Jaxb2Marshaller marshaller) {
        HotelClient client = new HotelClient();
        client.setDefaultUri("http://localhost:8081/ws/hotels");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        ClientInterceptor[] clientInterceptors = { new SoapClientInterceptor() };
        client.setInterceptors(clientInterceptors);
        return client;
    }

}
