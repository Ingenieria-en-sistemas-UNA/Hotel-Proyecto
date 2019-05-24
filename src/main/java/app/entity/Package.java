package app.entity;

import javax.persistence.*;

@Entity
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Room room;

    @OneToOne(cascade = CascadeType.ALL)
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    private Voucher voucher;

    @Column(nullable = false)
    private int numberNight;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room = room; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public Voucher getVoucher() { return voucher; }

    public void setVoucher(Voucher voucher) { this.voucher = voucher; }

    public int getNumberNight() { return numberNight; }

    public void setNumberNight(int numberNight) { this.numberNight = numberNight; }
}
