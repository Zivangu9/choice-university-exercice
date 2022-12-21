package choice.university.ivan.service;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import choice.university.ivan.schemas.GetHotelRequest;
import choice.university.ivan.schemas.GetHotelResponse;

public class HotelService extends WebServiceGatewaySupport {
    public GetHotelResponse getHotelById(int id) {
        GetHotelRequest request = new GetHotelRequest();
        request.setId(id);

        GetHotelResponse response = (GetHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }
}
