package moe.prashast.controller;

import jakarta.validation.Valid;
import moe.prashast.constant.Messages;
import moe.prashast.dto.ErrorResponse;
import moe.prashast.dto.ImpairmentDto;
import moe.prashast.dto.Response;
import moe.prashast.request.pojo.ImpairmentRequest;
import moe.prashast.service.Part2QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/part2-screening")
public class Part2QuestionsController {

    @Autowired
    private Part2QuestionService part2QuestionService;

    @PostMapping("/impairment/questions")
    public ResponseEntity<?> getImpairmentQuestions(@Valid @RequestBody ImpairmentRequest req) {

        try {
            List<Integer> ids = req.getImpairmentIds();

            List<ImpairmentDto> data = part2QuestionService.getImpairmentData(ids);
            return ResponseEntity.ok(new Response(Messages.SUCCESS, data));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Messages.ERROR));

        }

    }


}
