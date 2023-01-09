package choice.university.ivan.soapapi.exception;

import choice.university.ivan.schemas.ServiceStatus;

public interface ServiceFaultException {

    public ServiceStatus getServiceStatus();

}
