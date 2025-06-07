package com.zch1001.pi.controller;

import com.zch1001.pi.model.*;
import com.zch1001.pi.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class BankController {
    @Autowired
    private BankService bankService;

    @GetMapping("/banks/query_bank/{teacher_id}")
    public QueryBankResponse queryBank(@PathVariable("teacher_id") int teacher_id) {
        return bankService.queryBank(teacher_id);
    }

    @GetMapping("/banks/{bank_id}/questions_query")
    public QueryQuestionResponse queryQuestion(@PathVariable int bank_id) {
        return bankService.queryQuestion(bank_id);
    }

    @PostMapping("/banks/{bank_id}/create_question")
    public CreateQuestionResponse createQuestion(@PathVariable int bank_id, @RequestBody CreateQuestionRequest createQuestionRequest) {
        return bankService.createQuestion(bank_id, createQuestionRequest);
    }

    @PostMapping("/banks/{bank_id}/update_question/{question_id}")
    public UpdateQuestionResponse updateQuestion(@PathVariable int bank_id, @PathVariable int question_id, @RequestBody UpdateQuestionRequest updateQuestionRequest) {
        return bankService.updateQuestion(bank_id, question_id, updateQuestionRequest);
    }

    @PostMapping("/banks/{bank_id}/delete_question/{question_id}")
    public DeleteQuestionResponse deleteQuestion(@PathVariable int bank_id, @PathVariable int question_id, @RequestBody DeleteQuestionRequest deleteQuestionRequest) {
        return bankService.deleteQuestion(bank_id, question_id, deleteQuestionRequest);
    }
}
