package choice.university.ivan.soapapi.exception;

public class ServiceStatus {
    protected int statusCode;
    protected String statusName;
    protected String message;

    public ServiceStatus() {
    }

    public ServiceStatus(int statusCode, String statusName, String message) {
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + statusCode;
        result = prime * result + ((statusName == null) ? 0 : statusName.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ServiceStatus other = (ServiceStatus) obj;
        if (statusCode != other.statusCode)
            return false;
        if (statusName == null) {
            if (other.statusName != null)
                return false;
        } else if (!statusName.equals(other.statusName))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        return true;
    }

}
