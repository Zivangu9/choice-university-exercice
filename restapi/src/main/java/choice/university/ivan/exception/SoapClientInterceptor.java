package choice.university.ivan.exception;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import javax.xml.soap.*;
import java.util.Iterator;

public class SoapClientInterceptor implements ClientInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        WebServiceMessage message = messageContext.getResponse();
        SaajSoapMessage saajSoapMessage = (SaajSoapMessage) message;
        SOAPMessage soapMessage = saajSoapMessage.getSaajMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        try {
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
            SOAPBody soapBody = soapEnvelope.getBody();
            SOAPFault soapFault = soapBody.getFault();
            Detail soapErrorDetail = soapFault.getDetail();
            Iterator<?> detailEntries = soapErrorDetail.getDetailEntries();
            DetailEntry statusDetail = (DetailEntry) detailEntries.next();
            detailEntries.next(); // name
            DetailEntry messageDetail = (DetailEntry) detailEntries.next();

            String serviceStatus = statusDetail.getValue();
            String errorMessage = messageDetail.getValue();

            if (serviceStatus.equals("400")) {
                throw new BadRequestException(errorMessage);
            }
            if (serviceStatus.equals("404")) {
                throw new NotFoundException(errorMessage);
            }
            if (serviceStatus.equals("409")) {
                throw new ConflictException(errorMessage);
            }
        } catch (SOAPException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    public void afterCompletion(MessageContext arg0, Exception arg1) throws WebServiceClientException {
    }
}
