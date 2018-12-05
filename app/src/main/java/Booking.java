import com.example.vekshan.myapplication.Availability;

public class Booking {
    private String serviceName;
    private String providerName;
    private String bookingId;
    private Availability availability;
    private String homeOwnerName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public String getHomeOwnerName() {
        return homeOwnerName;
    }

    public void setHomeOwnerName(String homeOwnerName) {
        this.homeOwnerName = homeOwnerName;
    }

    public Booking(String serviceName, String providerName, String bookingId, Availability availability, String homeOwnerName) {
        this.serviceName = serviceName;
        this.providerName = providerName;
        this.bookingId = bookingId;
        this.availability = availability;
        this.homeOwnerName = homeOwnerName;

    }
}
