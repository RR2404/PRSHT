package moe.prashast.controller;

import moe.prashast.constant.Messages;
import moe.prashast.dto.Response;
import moe.prashast.request.pojo.CaptchaValidationRequest;
import moe.prashast.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/captcha")
    public ResponseEntity<?> getCaptcha(HttpServletRequest request) throws IOException {

        Map<String, String> captcha = captchaService.generateCaptcha(request);
        return ResponseEntity.ok(new Response(Messages.CAPTCHA_GENERATED,captcha ));
    }

    @PostMapping("/validate-captcha")
    public ResponseEntity<?> validateCaptcha(@RequestBody CaptchaValidationRequest request) {

        boolean isValid = captchaService.validateCaptcha(request.getCaptchaId(), request.getCaptchaValue());

        if (isValid) {
            return ResponseEntity.ok( new Response(Messages.CAPTCHA_VERIFIED));
        } else {
            return ResponseEntity .badRequest().body(new Response(Messages.INVALID_CAPTCHA));
        }
    }


}
