package moe.prashast.service;

//import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.http.HttpServletRequest;
import moe.prashast.redis.CaptchaRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class CaptchaService {
//    @Autowired
//    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private CaptchaRedisRepository captchaRedisRepository;


//    public Map<String, String> generateCaptcha() throws IOException {
//
//        String captchaText = defaultKaptcha.createText();
//        BufferedImage image = defaultKaptcha.createImage(captchaText);
//
//        String captchaId = UUID.randomUUID().toString();
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(image, "jpg", baos);
//
//        String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
//
//        // safe cleanup before save
//        captchaRedisRepository.delete(captchaId);
//        captchaRedisRepository.save(captchaId, captchaText, base64Image);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("captchaId", captchaId);
//        response.put("image", base64Image);
//
//        return response;
//    }


    public Map<String, String> generateCaptcha(HttpServletRequest request) throws IOException {

        int width = 175;
        int height = 45;

//        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder captchaText = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            captchaText.append(chars.charAt(random.nextInt(chars.length())));
        }

        String code = captchaText.toString();

        // Generate captchaId
        String captchaId = UUID.randomUUID().toString();

        // Save captcha in session
        request.getSession().setAttribute("captcha", code);

        // Create image
        BufferedImage bufferedImage =new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        // Background
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, width, height);

        // Font
        g2d.setFont(new Font("Verdana", Font.BOLD, 34));

        // Smooth text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );

        // Draw characters
        for (int i = 0; i < code.length(); i++) {

            // Random text color
            g2d.setColor(new Color(
                    random.nextInt(150),
                    random.nextInt(150),
                    random.nextInt(150)));

            int x = 10 + (i * 26);
            int y = 32 + random.nextInt(5);

            g2d.drawString(String.valueOf(code.charAt(i)), x, y);
        }

        // Optional small dots noise
        for (int i = 0; i < 30; i++) {

            int x = random.nextInt(width);
            int y = random.nextInt(height);

            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(x, y, 2, 2);
        }
        g2d.dispose();
        // Convert image to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, "png", baos);

        byte[] imageBytes = baos.toByteArray();

        // Convert to Base64
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Save in redis/db if needed
        captchaRedisRepository.delete(captchaId);
        captchaRedisRepository.save(captchaId, code, base64Image);

        // Response map
        Map<String, String> response = new HashMap<>();
        response.put("captchaId", captchaId);
        response.put("image", base64Image);
        return response;

    }


    public boolean validateCaptcha(String captchaId, String userInput) {

        String storedCaptcha = captchaRedisRepository.getText(captchaId).orElse(null);

        if (storedCaptcha != null && storedCaptcha.equals(userInput)) {
            captchaRedisRepository.delete(captchaId); // one-time use
            return true;
        }

        return false;
    }

}
