package choice.university.ivan.soapapi.exception;

import choice.university.ivan.schemas.ServiceStatus;

public class ConflictException extends RuntimeException implements ServiceFaultException {
    private ServiceStatus serviceStatus;

    public ConflictException(String message) {
        super(message);
        this.serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(409);
        serviceStatus.setStatusName("CONFLICT");
        serviceStatus.setMessage(message);
    }

    @Override
    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

}
