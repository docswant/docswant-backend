package sju.capstone.docswant.domain.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.common.format.ResponseFormat;
import sju.capstone.docswant.domain.question.model.dto.QuestionDto;
import sju.capstone.docswant.domain.question.service.QuestionService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/patient/{code}/question")
    public ResponseEntity<ResponseFormat<QuestionDto.Response>> createApi(
            @PathVariable(name = "code") String code, @RequestBody @Valid QuestionDto.Request requestDto) {
        QuestionDto.Response responseDto = questionService.create(code, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseFormat.of(responseDto));
    }

    @PatchMapping("/patient/{code}/question/{id}/content")
    public ResponseEntity<ResponseFormat<QuestionDto.Response>> updateApi(
            @PathVariable(name = "code") String code, @PathVariable(name = "id") Long id, @RequestBody @Valid QuestionDto.UpdateRequest requestDto) {
        QuestionDto.Response responseDto = questionService.update(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(responseDto));
    }

    @PatchMapping("/patient/{code}/question/{id}/answer")
    public ResponseEntity<ResponseFormat<QuestionDto.Response>> answerApi(
            @PathVariable(name = "code") String code, @PathVariable(name = "id") Long id, @RequestBody @Valid QuestionDto.AnswerRequest requestDto) {
        QuestionDto.Response responseDto = questionService.answer(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(responseDto));
    }

    @DeleteMapping("/patient/{code}/question/{id}")
    public ResponseEntity<ResponseFormat> deleteApi(
            @PathVariable(name = "code") String code, @PathVariable(name = "id") Long id) {
        questionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of());
    }

    @GetMapping("/patient/{code}/question/{id}")
    public ResponseEntity<ResponseFormat<QuestionDto.Response>> findApi(
            @PathVariable(name = "code") String code, @PathVariable(name = "id") Long id) {
        QuestionDto.Response responseDto = questionService.find(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(responseDto));
    }

    @GetMapping("/patient/{code}/question")
    public ResponseEntity<ResponseFormat<PageFormat.Response>> findAllApi(
            @PathVariable(name = "code") String code, PageFormat.Request pageRequest) {
        PageFormat.Response<List<QuestionDto.Response>> pageResponse = questionService.findAll(code, pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(pageResponse));
    }
}
