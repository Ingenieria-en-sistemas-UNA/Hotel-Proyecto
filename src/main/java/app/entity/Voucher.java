package app.entity;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String emitter;

    @Column(nullable = false)
    private Client client;

    @Column
    private LocalDate localDate;

    @Column
    private Double price;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmitter() { return emitter; }

    public void setEmitter(String sender) { this.emitter = sender; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public LocalDate getLocalDate() {return localDate; }

    public void setLocalDate(LocalDate localDate) { this.localDate = localDate; }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
