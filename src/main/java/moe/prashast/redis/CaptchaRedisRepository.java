package moe.prashast.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CaptchaRedisRepository {

    private final StringRedisTemplate redisTemplate;

    public CaptchaRedisRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String textKey(String captchaId) {
        return "CAPTCHA-" + captchaId;
    }

    private String imageKey(String captchaId) {
        return "CAPTCHA-IMG-" + captchaId;
    }

    public void save(String captchaId, String captchaText, String base64Image) {
        redisTemplate.opsForValue().set(textKey(captchaId), captchaText);
        redisTemplate.opsForValue().set(imageKey(captchaId), base64Image);
    }

    public Optional<String> getText(String captchaId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(textKey(captchaId)));
    }

    public Optional<String> getImage(String captchaId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(imageKey(captchaId)));
    }

    public void delete(String captchaId) {
        redisTemplate.delete(textKey(captchaId));
        redisTemplate.delete(imageKey(captchaId));
    }
}