package app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String description;

    @Column
    private String img;

    @Column(nullable = false)
    private int guests;

    @Column(nullable = false, columnDefinition = "Boolean default false")
    private Boolean state;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate localDate;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public int getGuests() { return guests; }

    public void setGuests(int guests) { this.guests = guests; }

    public Boolean getState() { return state; }

    public void setState(Boolean state) { this.state = state; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public LocalDate getLocalDate() { return localDate; }

    public void setLocalDate(LocalDate localDate) { this.localDate = localDate; }
}
