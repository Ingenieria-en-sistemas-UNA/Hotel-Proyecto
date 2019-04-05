package app.entity;

import javax.persistence.*;

@Entity
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Package customPackage;

    @Column
    private Double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Package getCustomPackage() {
        return customPackage;
    }

    public void setCustomPackage(Package customPackage) {
        this.customPackage = customPackage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
