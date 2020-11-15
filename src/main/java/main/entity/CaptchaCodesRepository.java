package main.entity;

import main.service.CaptchaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptchaCodesRepository extends JpaRepository<CaptchaCodes,Integer> {
    //@Modifying
   // @Query(name = "INSERT INTO captcha_code(id, code, secret_code) VALUES (:id, :code, :secret)",
   // nativeQuery = true)
    //CaptchaCodes saveCaptcha(CaptchaCodes captchaCodes);
}
