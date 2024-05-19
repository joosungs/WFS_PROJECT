package WFS.Fire_SMS.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

@Getter
@Entity
@Table(name = "client")
@Data
public class client {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "lpnumber", length = 20)
    private String lpnumber;

    @Column(name = "pnumber", length = 20)
    private String pnumber;

    @Column(name = "parkingN")
    private int parkingN;

    @Column(name = "vnumber", length = 10)
    private String vnumber;

    @Column(name = "charging")
    private int charging;

    public client(Long cid, String clpNum, String cpNum, int pn, String vNum, int chp) {
        this.id = cid;
        this.lpnumber = clpNum;
        this.pnumber = cpNum;
        this.parkingN = pn;
        this.vnumber = vNum;
        this.charging = chp;
    }

    public client(){

    }
}
