package main.Entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "captcha_code")
public class CaptchaCodes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date time;
    private String code;
    private String secret_code;

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(@NonNull Date time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    public String getSecret_code() {
        return secret_code;
    }

    public void setSecret_code(@NonNull String secret_code) {
        this.secret_code = secret_code;
    }

}
