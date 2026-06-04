package moe.prashast.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import moe.prashast.dto.LoginResponseDto;
import moe.prashast.security.util.JwtUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
public class AuthRedisRepository {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    public AuthRedisRepository(StringRedisTemplate redisTemplate, ObjectMapper objectMapper, JwtUtil jwtUtil) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
    }

    private String accessKey(String accessToken) {
        return "AUTH:ACCESS:" + accessToken;
    }

    private String refreshKey(String refreshToken) {
        return "AUTH:REFRESH:" + refreshToken;
    }

    private String userKey(String udiseCode, String mobile) {
        return "AUTH:USER:" + udiseCode + ":" + mobile;
    }

    public void saveTokens(String udiseCode, String mobile, LoginResponseDto responseDto) {
        try {
            String json = objectMapper.writeValueAsString(responseDto);

            redisTemplate.opsForValue().set(accessKey(responseDto.getAccessToken()), "ACTIVE",
                    jwtUtil.getAccessDuration());
            redisTemplate.opsForValue().set(refreshKey(responseDto.getRefreshToken()), "ACTIVE",
                    jwtUtil.getRefreshDuration());
            redisTemplate.opsForValue().set(userKey(udiseCode, mobile), json, jwtUtil.getRefreshDuration());

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to store auth data in Redis", e);
        }
    }

    public boolean isAccessTokenActive(String accessToken) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(accessKey(accessToken)));
    }

    public boolean isRefreshTokenActive(String refreshToken) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(refreshKey(refreshToken)));
    }

    public Optional<LoginResponseDto> getByUser(String udiseCode, String mobile) {
        try {
            String json = redisTemplate.opsForValue().get(userKey(udiseCode, mobile));
            if (json == null) {
                return Optional.empty();
            }
            return Optional.of(objectMapper.readValue(json, LoginResponseDto.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void deleteByUser(String udiseCode, String mobile) {
    	
    	//user-based key for logout lookup
        Optional<LoginResponseDto> cached = getByUser(udiseCode, mobile);

        cached.ifPresent(dto -> {
            if (dto.getAccessToken() != null) {
                redisTemplate.delete(accessKey(dto.getAccessToken()));
            }
            if (dto.getRefreshToken() != null) {
                redisTemplate.delete(refreshKey(dto.getRefreshToken()));
            }
        });

        redisTemplate.delete(userKey(udiseCode, mobile));
    }

    public void deleteByRefreshToken(String refreshToken, String udiseCode, String mobile) {
        redisTemplate.delete(refreshKey(refreshToken));
        redisTemplate.delete(userKey(udiseCode, mobile));
    }
}