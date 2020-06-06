package ua.external.data.entity;

import java.time.LocalDate;

public class Exhibition {

    private int id;
    private String name;
    private String description;
    private LocalDate beginDate;
    private LocalDate endDate;
    private int fullTicketPrice;
    private ExhibitionHall exhibitionHall;

    public Exhibition() {
    }

    public Exhibition(String name, String description,
                      LocalDate beginDate, LocalDate endDate,
                      int fullTicketPrice, ExhibitionHall exhibitionHall) {
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.fullTicketPrice = fullTicketPrice;
        this.exhibitionHall = exhibitionHall;
    }

    public Exhibition(int id, String name, String description,
                      LocalDate beginDate, LocalDate endDate,
                      int fullTicketPrice, ExhibitionHall exhibitionHall) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.fullTicketPrice = fullTicketPrice;
        this.exhibitionHall = exhibitionHall;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getFullTicketPrice() {
        return fullTicketPrice;
    }

    public void setFullTicketPrice(int fullTicketPrice) {
        this.fullTicketPrice = fullTicketPrice;
    }

    public ExhibitionHall getExhibitionHall() {
        return exhibitionHall;
    }

    public void setExhibitionHall(ExhibitionHall exhibitionHall) {
        this.exhibitionHall = exhibitionHall;
    }

    @Override
    public String toString() {
        return "Exhibition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", fullTicketPrice=" + fullTicketPrice +
                ", exhibitionHall=" + exhibitionHall +
                '}';
    }
}
