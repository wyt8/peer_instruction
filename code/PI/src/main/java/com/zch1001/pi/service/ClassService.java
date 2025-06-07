package com.zch1001.pi.service;

import com.zch1001.pi.entity.*;
import com.zch1001.pi.entity.Class;
import com.zch1001.pi.model.*;
import com.zch1001.pi.repository.*;
import org.hibernate.action.internal.CollectionRecreateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassService {
    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private StudentClassRepository studentClassRepository;
    @Autowired
    private StudentCourseRepository studentCourseRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentTestRepository studentTestRepository;

    public QueryClassResponse queryClass(int student_id, Long course_id) {
        Optional<Student> student = studentRepository.findById(student_id);
        List<Class> classList = classRepository.findByCourseId(course_id);
        List<QueryClassResponse.ClassInfo> return_list = new ArrayList<>();
        QueryClassResponse queryClassResponse = new QueryClassResponse();
        for (Class cls : classList) {
            if (cls.getCourseId().equals(course_id) && cls.getStatus() >= 3) {
                QueryClassResponse.ClassInfo classInfo = queryClassResponse.new ClassInfo();
                classInfo.setId(cls.getClassId());
                classInfo.setName(cls.getClassName());
                classInfo.setStartTime(cls.getStartTime());
                return_list.add(classInfo);
            }
        }
        QueryClassResponse.Data data = queryClassResponse.new Data();
        data.setClassList(return_list);
        queryClassResponse.setData(data);
        queryClassResponse.setCode(0);
        queryClassResponse.setMsg("Success!");
        queryClassResponse.setData(data);
        return queryClassResponse;
    }

    public JoinClassResponse joinClass(int student_id, int course_id, int class_id, JoinClassRequest joinClassRequest) {
        StudentClass studentClass = new StudentClass();
        studentClass.setClassId(class_id);
        studentClass.setStudentId(student_id);
        studentClass.setCreatedTime(LocalDateTime.now());
        studentClassRepository.save(studentClass);
        JoinClassResponse joinClassResponse = new JoinClassResponse();
        joinClassResponse.setCode(0);
        joinClassResponse.setMsg("Success!");
        return joinClassResponse;
    }

    public SubmitQuestionResponse submitQuestion(Long student_id, int exercise_id, SubmitQuestionRequest submitQuestionRequest) {
        StudentTest studentTest = studentTestRepository.findByStudentIdAndTestIdAndTestType(student_id, exercise_id, submitQuestionRequest.getIndex() + 1);
        System.out.println(student_id);
        System.out.println(exercise_id);
        System.out.println(submitQuestionRequest.getIndex());
        System.out.println(submitQuestionRequest.getOption());
        studentTest.setMyOption(submitQuestionRequest.getOption());
        studentTest.setStatus(2);
        if (studentTest.getMyOption().equals(studentTest.getRightOption())) {
            Test test = testRepository.findByTestId(studentTest.getTestId()).get();
            Question question = questionRepository.findById(test.getQuestionId()).get();
            studentTest.setTestScore(question.getQuestionScore());
        }
        else {
            studentTest.setTestScore(0);
        }
        studentTestRepository.save(studentTest);
        SubmitQuestionResponse submitQuestionResponse = new SubmitQuestionResponse();
        submitQuestionResponse.setCode(0);
        submitQuestionResponse.setMsg("Success!");
        return submitQuestionResponse;
    }

    public GetQuestionResponse getQuestion(int student_id, int course_id, int class_id) {
        GetQuestionResponse getQuestionResponse = new GetQuestionResponse();
        getQuestionResponse.setCode(0);
        getQuestionResponse.setMsg("Success!");
        GetQuestionResponse.Data data = getQuestionResponse.new Data();
        List<GetQuestionResponse.TestInfo> testInfoList =  new ArrayList<>();
        List<Test> testList = testRepository.findByClassId(class_id);
        for (Test test: testList) {
            Integer testId = test.getTestId();
            GetQuestionResponse.TestInfo testInfo = getQuestionResponse.new TestInfo();
            testInfo.setTestId(testId);
            Question question = questionRepository.findById(test.getQuestionId()).get();
            testInfo.setContent(question.getQuestionContent());
            testInfo.setOptions(question.getOptions());
            List<StudentTest> studentTestList = studentTestRepository.findByStudentIdAndTestId(student_id, testId);
            for (StudentTest studentTest : studentTestList) {
                GetQuestionResponse.AnswerInfo answerInfo = getQuestionResponse.new AnswerInfo();
                answerInfo.setStatus(studentTest.getStatus());
                answerInfo.setStartTime(studentTest.getStartTime());
                answerInfo.setLimitTime(studentTest.getLimitTime() - 28800);
                if (studentTest.getStatus() == 2) {
                    answerInfo.setMyOption(studentTest.getMyOption());
                    answerInfo.setRightOption(studentTest.getRightOption());
                }
                if (studentTest.getTestType() == 1) testInfo.setFirstAnswer(answerInfo);
                else if (studentTest.getTestType() == 2) testInfo.setSecondAnswer(answerInfo);
            }
            testInfoList.add(testInfo);
        }
        for (GetQuestionResponse.TestInfo testInfo: testInfoList) {
            if (testInfo.getFirstAnswer() == null) {
                GetQuestionResponse.AnswerInfo answerInfo = getQuestionResponse.new AnswerInfo();
                answerInfo.setStatus(0);
                testInfo.setFirstAnswer(answerInfo);
            }
            if (testInfo.getSecondAnswer() == null) {
                GetQuestionResponse.AnswerInfo answerInfo = getQuestionResponse.new AnswerInfo();
                answerInfo.setStatus(0);
                testInfo.setSecondAnswer(answerInfo);
            }
        }
        data.setTestInfoList(testInfoList);
        getQuestionResponse.setData(data);
        return getQuestionResponse;
    }

    public SignInResponse signIn(int student_id, int course_id, int class_id) {
        SignInResponse signInResponse = new SignInResponse();
        signInResponse.setCode(0);
        signInResponse.setMsg("Success!");
        SignInResponse.Data data = signInResponse.new Data();
        Optional<StudentClass> studentClass = studentClassRepository.findByStudentIdAndClassId(student_id, class_id);
        if (studentClass.isPresent()) {
            data.setAttended(true);
        }
        else {
            data.setAttended(false);
        }
        Class cls = classRepository.findById(class_id).get();
        data.setClassName(cls.getClassName());
        data.setStartTime(cls.getStartTime());
        data.setClassId(cls.getClassId());
        signInResponse.setData(data);
        return signInResponse;
    }

    public CreateClassResponse createClass(CreateClassRequest createClassRequest) {
        CreateClassResponse createClassResponse = new CreateClassResponse();
        Optional<Course> course = courseRepository.findById(createClassRequest.getCourseId());
        if (course.isEmpty()) {
            createClassResponse.setCode(-1);
            createClassResponse.setMsg("课程不存在!");
        }
        else {
            Class cls = new Class();
            classRepository.save(cls);
            cls.setCourseId(createClassRequest.getCourseId());
            cls.setClassName(createClassRequest.getClassName());
            cls.setCreatedTime(ZonedDateTime.now().plusHours(8));
            cls.setStartTime(createClassRequest.getStartTime().plusHours(8));
            cls.setEndTime(createClassRequest.getEndTime().plusHours(8));
            cls.setStatus(1);
            classRepository.save(cls);

            CreateClassResponse.Data data = createClassResponse.new Data();
            CreateClassResponse.TeacherInfo teacherInfo = createClassResponse.new TeacherInfo();
            Optional<Teacher> teacher = teacherRepository.findById(course.get().getTeacherId());
            teacherInfo.setTeacherName(teacher.get().getName());
            teacherInfo.setTeacherAvatar(teacher.get().getAvatar());
            teacherInfo.setTeacherId(teacher.get().getId());
            data.setTeacherInfo(teacherInfo);
            data.setClassId(cls.getClassId());
            data.setCreateTime(cls.getCreatedTime());
            data.setCourseId(cls.getCourseId());
            List<StudentCourses> studentCourses = studentCourseRepository.findByCourseId(cls.getCourseId());
            List<Integer> studentIdList = new ArrayList<>();
            List<String> studentNameList = new ArrayList<>();
            for (StudentCourses studentCourse : studentCourses) {
                Integer StudentId = studentCourse.getStudentId();
                Optional<Student> student = studentRepository.findById(StudentId);
                if (student.isPresent()) {
                    studentNameList.add(student.get().getName());
                    studentIdList.add(StudentId);
                }
            }
            data.setStudentId(studentIdList);
            data.setStudentName(studentNameList);

            createClassResponse.setCode(0);
            createClassResponse.setMsg("Success!");
            createClassResponse.setData(data);
        }
        return createClassResponse;
    }

    public TeacherQueryClassResponse teacherQueryClass(Long courseId) {
        TeacherQueryClassResponse teacherQueryClassResponse = new TeacherQueryClassResponse();
        teacherQueryClassResponse.setCode(0);
        teacherQueryClassResponse.setMsg("Success!");
        List<Class> classList = classRepository.findByCourseId(courseId);
        List<TeacherQueryClassResponse.ClassInfo> classInfoList = new ArrayList<>();
        for (Class cls : classList) {
            TeacherQueryClassResponse.ClassInfo classInfo = teacherQueryClassResponse.new ClassInfo();
            classInfo.setCourseId(cls.getCourseId());
            classInfo.setClassId(cls.getClassId());
            classInfo.setClassName(cls.getClassName());
            classInfo.setStartTime(cls.getStartTime());
            classInfo.setStatus(cls.getStatus());
            classInfo.setEndTime(cls.getEndTime());
            classInfoList.add(classInfo);
        }
        TeacherQueryClassResponse.Data data = teacherQueryClassResponse.new Data();
        data.setClassInfoList(classInfoList);
        teacherQueryClassResponse.setData(data);
        return teacherQueryClassResponse;
    }

    public TeacherQueryQuestionResponse teacherQueryQuestion(Long courseId) {
        Bank bank = bankRepository.findByCourseId(courseId);
        List<Question> questions = questionRepository.findByBankId(bank.getBankId());
        TeacherQueryQuestionResponse teacherQueryQuestionResponse = new TeacherQueryQuestionResponse();
        teacherQueryQuestionResponse.setCode(0);
        teacherQueryQuestionResponse.setMsg("Success!");
        TeacherQueryQuestionResponse.Data data = teacherQueryQuestionResponse.new Data();
        List<TeacherQueryQuestionResponse.questionInfo> question_list = new ArrayList<>();
        for (Question question : questions) {
            TeacherQueryQuestionResponse.questionInfo questionInfo = teacherQueryQuestionResponse.new questionInfo();
            questionInfo.setQuestionId(question.getQuestionId());
            questionInfo.setName(question.getQuestionName());
            question_list.add(questionInfo);
        }
        data.setQuestionsList(question_list);
        teacherQueryQuestionResponse.setData(data);
        return teacherQueryQuestionResponse;
    }

    public AddTestResponse addTest(AddTestRequest addTestRequest) {
        AddTestResponse addTestResponse = new AddTestResponse();
        Class cls = classRepository.findByClassId(addTestRequest.getClassId());
        cls.setStatus(2);
        classRepository.save(cls);
        Course course = courseRepository.findById(cls.getCourseId()).get();
        Teacher teacher = teacherRepository.findById(course.getTeacherId()).get();
        List<Integer> QuestionList = addTestRequest.getTestId();
        List<Integer> testList = new ArrayList<>();
        for (Integer questionId : QuestionList) {
            Test test = new Test();
            testRepository.save(test);
            test.setClassId(addTestRequest.getClassId());
            test.setQuestionId(questionId);
            test.setCreatedTime(LocalDateTime.now());
            test.setCreator(teacher.getId());
            testRepository.save(test);
            testList.add(test.getTestId());
        }
        AddTestResponse.Data data = addTestResponse.new Data();
        data.setExerciseList(testList);
        addTestResponse.setCode(0);
        addTestResponse.setMsg("Success!");
        addTestResponse.setData(data);
        return addTestResponse;
    }

    public PublishClassResponse publishClass(PublishClassRequest publishClassRequest) {
        PublishClassResponse publishClassResponse = new PublishClassResponse();
        Class cls = classRepository.findByClassId(publishClassRequest.getClassId());
        cls.setStartTime(ZonedDateTime.now());
        cls.setStatus(3);
        classRepository.save(cls);
        PublishClassResponse.Data data = publishClassResponse.new Data();
        List<PublishClassResponse.ClassInfo> classInfoList = new ArrayList<>();
        PublishClassResponse.ClassInfo classInfo = publishClassResponse.new ClassInfo();
        classInfo.setClassId(cls.getClassId());
        classInfo.setClassName(cls.getClassName());
        classInfo.setStartTime(cls.getStartTime());
        classInfoList.add(classInfo);
        data.setClassInfoList(classInfoList);
        publishClassResponse.setCode(0);
        publishClassResponse.setMsg("Success!");
        publishClassResponse.setData(data);
        return publishClassResponse;
    }

    public PublishSignResponse publishSign(PublishSignRequest publishSignRequest) {
        PublishSignResponse publishSignResponse = new PublishSignResponse();
        Class cls = classRepository.findByClassId(publishSignRequest.getClassId());
        cls.setStartTime(ZonedDateTime.now());
        cls.setStatus(4);
        classRepository.save(cls);
        PublishSignResponse.Data data = publishSignResponse.new Data();
        List<PublishSignResponse.ClassInfo> classInfoList = new ArrayList<>();
        PublishSignResponse.ClassInfo classInfo = publishSignResponse.new ClassInfo();
        classInfo.setClassId(cls.getClassId());
        classInfo.setClassName(cls.getClassName());
        classInfo.setStartTime(cls.getStartTime());
        classInfoList.add(classInfo);
        data.setClassInfoList(classInfoList);
        publishSignResponse.setCode(0);
        publishSignResponse.setMsg("Success!");
        publishSignResponse.setData(data);
        return publishSignResponse;
    }

    public PublishTestResponse publishTest(PublishTestRequest publishTestRequest) {
        PublishTestResponse publishTestResponse = new PublishTestResponse();
        Class cls = classRepository.findByClassId(publishTestRequest.getClassId());
        cls.setStatus(5);
        classRepository.save(cls);
        Test test = testRepository.findByTestId(publishTestRequest.getTestId()).get();
        Question question = questionRepository.getById(test.getQuestionId());
        Course course = courseRepository.findById(cls.getCourseId()).get();
        List<StudentCourses> studentCoursesList = studentCourseRepository.findByCourseId(course.getCourseId());
        for (StudentCourses studentCourses : studentCoursesList) {
            StudentTest studentTest = new StudentTest();
            studentTest.setClassId(cls.getClassId());
            studentTest.setTestId(test.getTestId());
            studentTest.setTestType(publishTestRequest.getIndex());
            studentTest.setContent(question.getQuestionContent());
            studentTest.setAnswer(question.getQuestionAnswer());
            studentTest.setRightOption(question.getQuestionAnswer().charAt(0) - 'A');
            studentTest.setTestScore(0);
            studentTest.setStudentId(studentCourses.getStudentId());
            studentTest.setStatus(1);
            studentTest.setLimitTime(120);
            studentTest.setStartTime(ZonedDateTime.now().plusHours(8));
            studentTestRepository.save(studentTest);
        }
        publishTestResponse.setCode(0);
        publishTestResponse.setMsg("Success!");
        return publishTestResponse;
    }

    public ClassDetailResponse classDetail(Integer classId) {
        ClassDetailResponse classDetailResponse = new ClassDetailResponse();
        ClassDetailResponse.ClassInfo classInfo = classDetailResponse.new ClassInfo();
        Class cls = classRepository.findByClassId(classId);
        Course course = courseRepository.findById(cls.getCourseId()).get();
        Teacher teacher = teacherRepository.findById(course.getTeacherId()).get();
        List<StudentClass> studentClassList = studentClassRepository.findByClassId(classId);
        List<Test> testList = testRepository.findByClassId(classId);
        List<Integer> exerciseList = new ArrayList<>();
        for (Test test : testList) {
            exerciseList.add(test.getTestId());
        }
        List<StudentTest> studentTestList = studentTestRepository.findByClassIdAndStatus(cls.getClassId(), 1);
        Set<Integer> set = new HashSet<>();
        for (StudentTest studentTest : studentTestList) {
            set.add(studentTest.getStudentId());
        }
        List<StudentTest> studentTestList2 = studentTestRepository.findByClassId(cls.getClassId());
        classInfo.setCourseId(cls.getCourseId());
        classInfo.setClassId(cls.getClassId());
        classInfo.setClassName(cls.getClassName());
        classInfo.setTeacherId(course.getTeacherId());
        classInfo.setTeacherName(teacher.getName());
        classInfo.setSignNum(studentClassList.size());
        classInfo.setExamingNum(set.size());
        classInfo.setFinishedNum(studentTestList2.size() - set.size());
        classInfo.setStatus(cls.getStatus());
        classInfo.setStartTime(cls.getStartTime());
        classInfo.setEndTime(cls.getEndTime());
        classInfo.setExerciseList(exerciseList);
        classDetailResponse.setCode(0);
        classDetailResponse.setMsg("Success!");
        ClassDetailResponse.Data data = classDetailResponse.new Data();
        data.setClassInfo(classInfo);
        classDetailResponse.setData(data);
        return classDetailResponse;
    }

    public QueryStudentResponse queryStudent(Integer classId) {
        QueryStudentResponse queryStudentResponse = new QueryStudentResponse();
        QueryStudentResponse.Data data = queryStudentResponse.new Data();
        List<QueryStudentResponse.StudentInfo> studentInfoList = new ArrayList<>();
        Class cls = classRepository.findByClassId(classId);

        List<StudentCourses> studentCoursesList = studentCourseRepository.findByCourseId(cls.getCourseId());
        for (StudentCourses studentCourses : studentCoursesList) {
            Student student = studentRepository.findById(studentCourses.getStudentId()).get();
            QueryStudentResponse.StudentInfo studentInfo = queryStudentResponse.new StudentInfo();

            studentInfo.setStudentId(student.getId());
            studentInfo.setStudentName(student.getName());

            Optional<StudentClass> studentClass = studentClassRepository.findByStudentIdAndClassId(student.getId(), classId);
            if (studentClass.isPresent()) {
                studentInfo.setSigned(true);
            }
            else {
                studentInfo.setSigned(false);
            }

            List<StudentTest> studentTestList = studentTestRepository.findByStudentIdAndClassId(student.getId(), cls.getClassId());
            int exerciseNum = 0;
            int score = 0;
            for (StudentTest studentTest : studentTestList) {
                if (studentTest.getStatus() == 2) {
                    exerciseNum += 1;
                    score += studentTest.getTestScore();
                }
            }
            studentInfo.setExerciseNum(exerciseNum);
            studentInfo.setScore(score);
            studentInfoList.add(studentInfo);
        }
        data.setStudentInfoList(studentInfoList);
        queryStudentResponse.setCode(0);
        queryStudentResponse.setMsg("Success!");
        queryStudentResponse.setData(data);
        return queryStudentResponse;
    }

    public ReviewClassResponse reviewClass(Integer classId) {
        ReviewClassResponse reviewClassResponse = new ReviewClassResponse();
        ReviewClassResponse.Data data = reviewClassResponse.new Data();
        ReviewClassResponse.ReviewInfo reviewInfo = reviewClassResponse.new ReviewInfo();
        Class cls = classRepository.findByClassId(classId);
        reviewInfo.setCourseId(cls.getCourseId());
        reviewInfo.setClassId(classId);
        reviewInfo.setClassName(cls.getClassName());
        reviewInfo.setStartTime(cls.getStartTime());
        reviewInfo.setStatus(cls.getStatus());
        reviewInfo.setEndTime(cls.getEndTime());
        List<ReviewClassResponse.TestInfo> testInfoList = new ArrayList<>();
        List<Test> testList = testRepository.findByClassId(classId);
        int totalCorrect1 = 0, totalCorrect2 = 0;
        int totalIncorrect1 = 0, totalIncorrect2 = 0;
        for (Test test : testList) {
            ReviewClassResponse.TestInfo testInfo = reviewClassResponse.new TestInfo();
            testInfo.setTestId(test.getTestId());
            List<StudentTest> studentTestList = studentTestRepository.findByClassIdAndStatus(cls.getClassId(), 2);
            int correct1 = 0, correct2 = 0;
            int incorrect1 = 0, incorrect2 = 0;
            for (StudentTest studentTest : studentTestList) {
                if (studentTest.getMyOption().equals(studentTest.getRightOption())) {
                    if (studentTest.getTestType() == 1) correct1 += 1;
                    else correct2 += 1;
                }
                else {
                    if (studentTest.getTestType() == 1) incorrect1 += 1;
                    else incorrect2 += 1;
                }
            }
            if (correct1 + incorrect1 == 0) testInfo.setTest1Rate(0);
            else testInfo.setTest1Rate(correct1 * 100 / (correct1 + incorrect1));
            if (correct2 + incorrect2 == 0) testInfo.setTest2Rate(0);
            else testInfo.setTest2Rate(correct2 * 100 / (correct2 + incorrect2));
            testInfoList.add(testInfo);
            totalCorrect1 += correct1;
            totalCorrect2 += correct2;
            totalIncorrect1 += incorrect1;
            totalIncorrect2 += incorrect2;
        }
        ReviewClassResponse.TotalInfo totalInfo = reviewClassResponse.new TotalInfo();
        if (totalCorrect1 + totalIncorrect1 == 0) totalInfo.setTest1Rate(0);
        else totalInfo.setTest1Rate(totalCorrect1 * 100 / (totalCorrect1 + totalIncorrect1));
        if (totalCorrect2 + totalIncorrect2 == 0) totalInfo.setTest2Rate(0);
        else totalInfo.setTest2Rate(totalCorrect2 * 100 / (totalCorrect2 + totalIncorrect2));
        reviewInfo.setTestInfoList(testInfoList);
        reviewInfo.setTotalInfo(totalInfo);
        data.setReviewInfo(reviewInfo);
        reviewClassResponse.setCode(0);
        reviewClassResponse.setMsg("Success!");
        reviewClassResponse.setData(data);
        return reviewClassResponse;
    }

    public EndClassResponse endClass(Integer classId) {
        EndClassResponse endClassResponse = new EndClassResponse();
        Class cls = classRepository.findByClassId(classId);
        cls.setStatus(6);
        classRepository.save(cls);
        endClassResponse.setCode(0);
        endClassResponse.setMsg("Success!");
        return endClassResponse;
    }

    public Map<String, Object> addClassSignIn(Integer studentId, Integer classId) {
        StudentClass studentClass = new StudentClass();
        studentClass.setStudentId(studentId);
        studentClass.setClassId(classId);
        Date date = new Date();

        // 转换为java.time.Instant
        Instant instant = date.toInstant();
        // 转换为java.time.LocalDateTime
        // 使用默认时区
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        studentClass.setCreatedTime(localDateTime);
        studentClassRepository.save(studentClass);
        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code",0);
        stringObjectHashMap.put("msg","成功");
        Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息
        stringObjectHashMap.put("data",stringObjectHashMap1);
        return  stringObjectHashMap;
    }

}
