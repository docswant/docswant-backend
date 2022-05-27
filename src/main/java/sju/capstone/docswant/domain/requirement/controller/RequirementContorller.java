package sju.capstone.docswant.domain.requirement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sju.capstone.docswant.common.annotation.CurrentUser;
import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.common.format.ResponseFormat;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.requirement.model.dto.RequirementDto;
import sju.capstone.docswant.domain.requirement.service.RequirementService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RequirementContorller {

        private final RequirementService requirementService;

        @PostMapping("/patient/{code}/requirement")
        public ResponseEntity<ResponseFormat<RequirementDto.Response>> registerApi(
                @PathVariable(name = "code") String code, @Valid @RequestBody RequirementDto.Request requestDto) {
            RequirementDto.Response responseDto = requirementService.register(code, requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseFormat.of(responseDto));
        }

        @PatchMapping("/patient/{code}/requirement/{id}/content")
        public ResponseEntity<ResponseFormat<RequirementDto.Response>> updateApi(
               @PathVariable(name = "code") String code, @PathVariable(name = "id") Long requirementId, @RequestBody @Valid RequirementDto.UpdateRequest requestDto) {
            RequirementDto.Response responseDto = requirementService.updateContent(requirementId, requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(responseDto));
        }

        @DeleteMapping("/patient/{code}/requirement/{id}")
        public ResponseEntity<ResponseFormat> deleteApi(
                @PathVariable(name = "code") String code, @PathVariable(name = "id") Long requirementId) {
            requirementService.delete(requirementId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of());
        }

        @GetMapping("/patient/{code}/requirement/{id}")
        public ResponseEntity<ResponseFormat<RequirementDto.Response>> findApi(
                @PathVariable(name = "code") String code, @PathVariable(name = "id") Long id, @CurrentUser Account account) {
            RequirementDto.Response responseDto = requirementService.find(account, id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(responseDto));
        }

        @GetMapping("/patient/{code}/requirement")
        public ResponseEntity<ResponseFormat<PageFormat.Response>> findAllApi(
                @PathVariable(name = "code") String code, PageFormat.Request pageRequest) {
            PageFormat.Response<List<RequirementDto.Response>> pageResponse = requirementService.findAll(code, pageRequest);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(pageResponse));
        }
}
