package org.examples.api;

import org.examples.func.CaptchaVerifier;
import org.examples.func.ChallengeGenerator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController()
@RequestMapping("/captcha")
public class CaptchaController {
    private static final String CAPTCHA_ID_HEADER_NAME = "x-captcha-id";
    private final ChallengeGenerator challengeGenerator;
    private final CaptchaVerifier captchaVerifier;

    public CaptchaController(ChallengeGenerator challengeGenerator,
                             CaptchaVerifier captchaVerifier) {
        this.challengeGenerator = challengeGenerator;
        this.captchaVerifier = captchaVerifier;
    }


    @GetMapping()
    public ResponseEntity<StreamingResponseBody> generate() {
        ChallengeGenerator.Challenge challenge = challengeGenerator.get();
        return ResponseEntity.ok()
                .header(CAPTCHA_ID_HEADER_NAME, challenge.captchaId())
                .contentType(MediaType.IMAGE_PNG)
                .body((outputStream) -> StreamUtils.copy(challenge.captchaImage(), outputStream));
    }

    @PostMapping
    public ResponseEntity<?> verify(@RequestParam String captchaId,
                                    @RequestParam String captchaResponse) {
        return captchaVerifier
                .andThen(result -> result ? ResponseEntity.ok("Ok") : ResponseEntity.badRequest().build())
                .apply(captchaId, captchaResponse);
    }
}
