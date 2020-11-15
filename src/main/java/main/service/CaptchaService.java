package main.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import main.api.response.CaptchaResponce;
import main.entity.CaptchaCodes;
import main.entity.CaptchaCodesRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

@Service
public class CaptchaService {
    @Autowired
    CaptchaCodesRepository captchaCodesRepository;

    public CaptchaResponce getCode() throws IOException {
        //captchaCodesRepository.deleteAll();
        int countCaptcha = 5;
        if (captchaCodesRepository.count() != countCaptcha){
            generateCaptcha(6,countCaptcha);
        }
        CaptchaResponce captchaResponce = new CaptchaResponce();
        int a = 1;
        CaptchaCodes captchaCodes = captchaCodesRepository.getOne(a + (int) (Math.random() * countCaptcha));
        captchaResponce.setSecret(captchaCodes.getSecret_code());
        captchaResponce.setImage(captchaCodes.getCode());
        return captchaResponce;
    }

   /* protected static String generate(Cage cage, int num, String namePrefix,
                                   String namePostfix, String text) throws IOException {
        for (int fi = 0; fi < num; fi++) {
            OutputStream os = new FileOutputStream(namePrefix + fi
                    + namePostfix, false);
            try {
                cage.draw(
                        text != null ? text : cage.getTokenGenerator().next(),
                        os);
            } finally {
                os.close();

            }
        }
        byte[] fileContent = FileUtils.readFileToByteArray(new File(namePrefix + "0" + namePostfix));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }
    */
   public void generateCaptcha(int n, int count) throws IOException {
       for (int i = 1; i < count; i++){
       String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
       StringBuilder sb = new StringBuilder(n);
       for (int z = 0; z < n; z++) {
           int index = (int)(AlphaNumericString.length() * Math.random());
           sb.append(AlphaNumericString.charAt(index));
       }
       Cage cage = new Cage();
       Image image = cage.drawImage(sb.toString());
       Image captcha = createResizedCopy(image,100,35,true);
       File file = new File("captcha.jpg");
       try {
           ImageIO.write((RenderedImage) captcha, "JPEG", file);
       } catch ( Exception ex ) {
           ex.printStackTrace();
       }
       byte[] fileContent = FileUtils.readFileToByteArray(file);
       String encodedString = Base64.getEncoder().encodeToString(fileContent);
       CaptchaCodes captchaCodes = new CaptchaCodes();
       captchaCodes.setSecret_code(sb.toString());
       captchaCodes.setCode("data:image/png;base64, " + encodedString);
       captchaCodes.setTime(new Date());
       captchaCodes.setId(i);
       captchaCodesRepository.save(captchaCodes);
       file.delete();
       System.out.println("Generate...");
       }
   }

   public static BufferedImage createResizedCopy(Image originalImage,
                                    int scaledWidth, int scaledHeight,
                                    boolean preserveAlpha)
    {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }


}
