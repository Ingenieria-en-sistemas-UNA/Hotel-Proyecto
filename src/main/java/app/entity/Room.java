package app.entity;

import javax.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private int guests;

    @Column(nullable = false, columnDefinition = "Boolean default false")
    private Boolean state;

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
}
