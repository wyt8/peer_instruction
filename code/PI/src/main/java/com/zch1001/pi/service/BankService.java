package com.zch1001.pi.service;

import com.zch1001.pi.entity.Bank;
import com.zch1001.pi.entity.Course;
import com.zch1001.pi.entity.Question;
import com.zch1001.pi.entity.Teacher;
import com.zch1001.pi.model.*;
import com.zch1001.pi.repository.BankRepository;
import com.zch1001.pi.repository.CourseRepository;
import com.zch1001.pi.repository.QuestionRepository;
import com.zch1001.pi.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {
    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CourseRepository courseRepository;


    public QueryBankResponse queryBank(int teacherId) {
        List<Course> courseList = courseRepository.findByTeacherId(teacherId);
        QueryBankResponse queryBankResponse = new QueryBankResponse();
        queryBankResponse.setCode(0);
        queryBankResponse.setMsg("Success!");
        QueryBankResponse.Data data = queryBankResponse.new Data();
        List<QueryBankResponse.BankInfo> bank_list = new ArrayList<>();
        for (Course course: courseList) {
            Long courseId = course.getCourseId();
            Bank bank = bankRepository.findByCourseId(courseId);
            QueryBankResponse.BankInfo bankInfo = queryBankResponse.new BankInfo();
            bankInfo.setId(bank.getBankId());
            bankInfo.setName(bank.getName());
            bank_list.add(bankInfo);
        }
        data.setBankList(bank_list);
        queryBankResponse.setData(data);
        return queryBankResponse;
    }

    public QueryQuestionResponse queryQuestion(int bank_id) {
        List<Question> questions = questionRepository.findByBankId(bank_id);
        QueryQuestionResponse queryQuestionResponse = new QueryQuestionResponse();
        queryQuestionResponse.setCode(0);
        queryQuestionResponse.setMsg("Success!");
        QueryQuestionResponse.Data data = queryQuestionResponse.new Data();
        List<QueryQuestionResponse.questionInfo> question_list = new ArrayList<>();
        for (Question question : questions) {
            QueryQuestionResponse.questionInfo questionInfo = queryQuestionResponse.new questionInfo();
            questionInfo.setBank_id(bank_id);
            questionInfo.setId(question.getQuestionId());
            questionInfo.setName(question.getQuestionName());
            questionInfo.setContent(question.getQuestionContent());
            questionInfo.setOptions(question.getOptions());
            questionInfo.setAnswer(question.getQuestionAnswer());
            questionInfo.setScore(question.getQuestionScore());
            questionInfo.setType(question.getQuestionType());
            question_list.add(questionInfo);
        }
        data.setQuestionsList(question_list);
        queryQuestionResponse.setData(data);
        return queryQuestionResponse;
    }

    public CreateQuestionResponse createQuestion(int bank_id, CreateQuestionRequest createQuestionRequest) {
        Question question = new Question();
        question.setBankId(bank_id);
        question.setQuestionName(createQuestionRequest.getName());
        question.setQuestionContent(createQuestionRequest.getContent());
        question.setQuestionOptions(createQuestionRequest.getOptions());
        question.setQuestionAnswer(createQuestionRequest.getAnswer());
        question.setQuestionScore(createQuestionRequest.getScore());
        question.setQuestionType(createQuestionRequest.getType());
        questionRepository.save(question);
        CreateQuestionResponse createQuestionResponse = new CreateQuestionResponse();
        createQuestionResponse.setCode(0);
        createQuestionResponse.setMsg("Success!");
        CreateQuestionResponse.Data data = createQuestionResponse.new Data();
        data.setId(question.getQuestionId());
        createQuestionResponse.setData(data);
        return createQuestionResponse;
    }

    public UpdateQuestionResponse updateQuestion(int bank_id, int question_id, UpdateQuestionRequest updateQuestionRequest) {
        Question question = questionRepository.findById(question_id);
        question.setQuestionName(updateQuestionRequest.getName());
        question.setQuestionContent(updateQuestionRequest.getContent());
        question.setQuestionOptions(updateQuestionRequest.getOptions());
        question.setQuestionAnswer(updateQuestionRequest.getAnswer());
        question.setQuestionScore(updateQuestionRequest.getScore());
        questionRepository.save(question);
        UpdateQuestionResponse updateQuestionResponse = new UpdateQuestionResponse();
        updateQuestionResponse.setCode(0);
        updateQuestionResponse.setMsg("Success!");
        return updateQuestionResponse;
    }

    public DeleteQuestionResponse deleteQuestion(int bank_id, int question_id, DeleteQuestionRequest deleteQuestionRequest) {
        Question question = questionRepository.findById(question_id);
        questionRepository.delete(question);
        DeleteQuestionResponse deleteQuestionResponse = new DeleteQuestionResponse();
        deleteQuestionResponse.setCode(0);
        deleteQuestionResponse.setMsg("Success!");
        return deleteQuestionResponse;
    }
}
