package hu.mora.web.model;

import javax.persistence.*;

@Entity
@Table(name = "ELHELEMENTS")
public class ElhElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME_EN")
    private String nameEn;

    @Column(name = "NAME_HU")
    private String nameHu;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameHu() {
        return nameHu;
    }

    public void setNameHu(String nameHu) {
        this.nameHu = nameHu;
    }

    @Override
    public String toString() {
        return "ElhElement{" +
                "id=" + id +
                ", nameEn='" + nameEn + '\'' +
                ", nameHu='" + nameHu + '\'' +
                '}';
    }
}
