package app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.MERGE)
    private Room room;

    @OneToOne(cascade = CascadeType.MERGE)
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    private Voucher voucher;

    @Column(nullable = false)
    private Boolean alive;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate localDate;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room = room; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public Voucher getVoucher() { return voucher; }

    public void setVoucher(Voucher voucher) { this.voucher = voucher; }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }
    public LocalDate getLocalDate() { return localDate; }

    public void setLocalDate(LocalDate localDate) { this.localDate = localDate; }
}
