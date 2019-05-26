package app.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

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
    private String receiver;

    @Column(nullable = false)
    private String detail;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate localDate;

    @Column
    private Double price;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmitter() { return emitter; }

    public void setEmitter(String sender) { this.emitter = sender; }

    public LocalDate getLocalDate() {return localDate; }

    public void setLocalDate(LocalDate localDate) { this.localDate = localDate; }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
