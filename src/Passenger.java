public class Passenger {
    private String firstName;
    private String surname;
    private String nic;
    private int seat;
    private int secondsInQueue;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setSecondsInQueue(int secondsInQueue) {
        this.secondsInQueue = secondsInQueue;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getNic() { return nic; }

    public int getSecondsInQueue() {
        return secondsInQueue;
    }

    public int getSeat() {
        return seat;
    }
}
