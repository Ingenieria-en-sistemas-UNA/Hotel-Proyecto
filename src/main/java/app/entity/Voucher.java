package app.entity;


import javax.persistence.*;
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
    private Date date;

    @Column
    private Double price;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmitter() { return emitter; }

    public void setEmitter(String sender) { this.emitter = sender; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
