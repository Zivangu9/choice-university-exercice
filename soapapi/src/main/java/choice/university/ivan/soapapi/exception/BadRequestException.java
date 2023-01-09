package choice.university.ivan.soapapi.exception;

public class BadRequestException extends RuntimeException implements ServiceFaultException {
    private ServiceStatus serviceStatus;

    public BadRequestException(String message) {
        super(message);
        this.serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(400);
        serviceStatus.setStatusName("BAD REQUEST");
        serviceStatus.setMessage(message);
    }

    @Override
    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

}
