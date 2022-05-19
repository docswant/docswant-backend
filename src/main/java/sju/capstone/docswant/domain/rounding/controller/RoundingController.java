package sju.capstone.docswant.domain.rounding.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sju.capstone.docswant.common.format.ResponseFormat;
import sju.capstone.docswant.domain.rounding.model.dto.RoundingDto;
import sju.capstone.docswant.domain.rounding.service.RoundingService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RoundingController {

    private final RoundingService roundingService;

    @PostMapping("/doctor/{code}/rounding")
    public ResponseEntity<ResponseFormat<RoundingDto.Response>> createApi(
            @PathVariable(name = "code") String code, @RequestBody @Valid RoundingDto.Request requestDto) {
        RoundingDto.Response responseDto = roundingService.create(code, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseFormat.of(responseDto));
    }

    @PatchMapping("/doctor/{code}/rounding/{id}")
    public ResponseEntity<ResponseFormat<RoundingDto.Response>> updateApi(
            @PathVariable(name = "code") String code, @PathVariable(name = "id") Long id, @RequestBody @Valid RoundingDto.UpdateRequest requestDto) {
        RoundingDto.Response responseDto = roundingService.update(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(responseDto));
    }

    @DeleteMapping("/doctor/{code}/rounding/{id}")
    public ResponseEntity<ResponseFormat> deleteApi(@PathVariable(name = "code") String code, @PathVariable(name = "id") Long id) {
        roundingService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of());
    }

    @DeleteMapping("/doctor/{code}/rounding")
    public ResponseEntity<ResponseFormat> deleteAllApi(@PathVariable(name = "code") String code, @RequestParam(name = "ids") List<Long> ids) {
        roundingService.deleteAll(ids);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of());
    }

    @PatchMapping("/doctor/{code}/rounding/{id}/status")
    public ResponseEntity<ResponseFormat<RoundingDto.Response>> changeStatusApi(@PathVariable(name = "code") String code, @PathVariable(name = "id") Long id) {
        RoundingDto.Response responseDto = roundingService.changeStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(responseDto));
    }

    @GetMapping("/doctor/{code}/rounding/{id}")
    public ResponseEntity<ResponseFormat<RoundingDto.Response>> findApi(@PathVariable(name = "code") String code, @PathVariable(name = "id") Long id) {
        RoundingDto.Response responseDto = roundingService.find(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(responseDto));
    }

    @GetMapping("/doctor/{code}/rounding")
    public ResponseEntity<ResponseFormat<List<RoundingDto.ListResponse>>> findAllByDateApi(
            @PathVariable(name = "code") String code, @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate roundingDate) {
        List<RoundingDto.ListResponse> listResponseDto = roundingService.findAllByDate(code, roundingDate);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(listResponseDto));
    }
}
