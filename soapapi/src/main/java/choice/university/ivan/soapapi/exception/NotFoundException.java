package choice.university.ivan.soapapi.exception;

import choice.university.ivan.schemas.ServiceStatus;

public class NotFoundException extends RuntimeException implements ServiceFaultException {
    private ServiceStatus serviceStatus;

    public NotFoundException(String message) {
        super(message);
        this.serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(404);
        serviceStatus.setStatusName("NOT FOUND");
        serviceStatus.setMessage(message);
    }

    @Override
    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

}
