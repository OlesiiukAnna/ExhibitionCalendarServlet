package ua.external.data.entity;

public class ExhibitionHall {

    private int id;
    private String name;
    private int allowableNumberOfVisitorsPerDay;

    public ExhibitionHall() {
    }

    public ExhibitionHall(String name, int allowableNumberOfVisitorsPerDay) {
        this.name = name;
        this.allowableNumberOfVisitorsPerDay = allowableNumberOfVisitorsPerDay;
    }

    public ExhibitionHall(int id, String name, int allowableNumberOfVisitorsPerDay) {
        this.id = id;
        this.name = name;
        this.allowableNumberOfVisitorsPerDay = allowableNumberOfVisitorsPerDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAllowableNumberOfVisitorsPerDay() {
        return allowableNumberOfVisitorsPerDay;
    }

    public void setAllowableNumberOfVisitorsPerDay(int allowableNumberOfVisitorsPerDay) {
        this.allowableNumberOfVisitorsPerDay = allowableNumberOfVisitorsPerDay;
    }

    @Override
    public String toString() {
        return "ExhibitionHall{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfVisitorsPerDay=" + allowableNumberOfVisitorsPerDay +
                '}';
    }
}
