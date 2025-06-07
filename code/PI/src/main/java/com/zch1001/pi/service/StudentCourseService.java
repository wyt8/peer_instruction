package com.zch1001.pi.service;

import com.zch1001.pi.entity.*;
import com.zch1001.pi.entity.Class;
import com.zch1001.pi.entity.VO.CourseVO;
import com.zch1001.pi.model.StudentCoursesResponse;
import com.zch1001.pi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private  StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private StudentClassRepository studentClassRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private  ReviewRepository reviewRepository;

    @Autowired
    private StudentTestRepository studentTestRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private  QuestionRepository questionRepository;

    public StudentCoursesResponse getCoursesByStudentId(Integer studentId) {
        List<StudentCourses> studentCourses = studentCourseRepository.findByStudentId(studentId);
        List<StudentCoursesResponse.CourseData> courseDataList = new ArrayList<>();

        for (StudentCourses studentCourse : studentCourses) {
            Course course = courseRepository.findById(studentCourse.getCourseId()).orElse(null);
            if (course!=null) {
                long id=course.getTeacherId();
                Optional<Teacher> teacher = teacherRepository.findById(id);
                courseDataList.add(new StudentCoursesResponse.CourseData(
                        course,
                        studentCourse.getCreatedTime().toString(),
                        teacher.get()
                ));
            }
        }

        return new StudentCoursesResponse(0, "查询成功", courseDataList);
    }

    public Map<String,Object> addStudentCourses(Integer  studentId, CourseVO courseVO) {
        List<StudentCourses> existingCourses = studentCourseRepository.findByStudentIdAndCourseId(
                studentId, Long.valueOf(courseVO.getCourse_code())
        );

        if (!existingCourses.isEmpty()) {
            // 学生已经加入课程，返回错误信息
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 1); // 错误码，表示失败
            errorResponse.put("msg", "学生已加入该课程");
            return errorResponse;
        }
        StudentCourses studentCourses = new StudentCourses();
        studentCourses.setStudentId(studentId);
        studentCourses.setCourseId(Long.valueOf(courseVO.getCourse_code()));
        // 创建一个java.util.Date对象
        Date date = new Date();

        // 转换为java.time.Instant
        Instant instant = date.toInstant();
        // 转换为java.time.LocalDateTime
        // 使用默认时区
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        studentCourses.setCreatedTime(localDateTime);
        StudentCourses save = studentCourseRepository.save(studentCourses);//添加数据  学生id 与课程id中间表
        Optional<Course> byId = courseRepository.findById(Long.valueOf(courseVO.getCourse_code()));//获取课程
        Optional<Teacher> byId1 = teacherRepository.findById(byId.get().getTeacherId());//根据老师id查询老师信息


        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code",0);
        stringObjectHashMap.put("msg","成功");

        Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息
        stringObjectHashMap1.put("course_id",byId.get().getCourseId());
        stringObjectHashMap1.put("course_name",byId.get().getCourseName());
        stringObjectHashMap1.put("course_image_url",byId.get().getCourseImageUrl());
        stringObjectHashMap1.put("join_time",save.getCreatedTime());


        Map<String, Object> stringObjectHashMap2 = new HashMap<>();//老师信息
        stringObjectHashMap2.put("teacher_id",byId1.get().getId());
        stringObjectHashMap2.put("teacher_name",byId1.get().getName());
        stringObjectHashMap2.put("teacher_avatar",byId1.get().getAvatar());
        stringObjectHashMap1.put("teacher",stringObjectHashMap2);

        stringObjectHashMap.put("data",stringObjectHashMap1);





        return stringObjectHashMap;
    }

    public Map<String, Object> selectStudentMessage(Long courseId, Integer studentId) {

        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code",0);
        stringObjectHashMap.put("msg","成功");
        Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息



        List<Class> all = classRepository.findAll();//课堂数
        System.out.println("333："+all);
        List<Class> collect5 = all.stream().filter(t->t.getCourseId().equals(courseId) && t.getStatus() >= 3).collect(Collectors.toList());
        System.out.println("222："+courseId);
        System.out.println("111："+collect5);
        //all=all.stream().filter(t->t.getCourseId().equals((long)courseId)).collect(Collectors.toList());
        stringObjectHashMap1.put("class_num",collect5.size());


        List<StudentClass> all1 = studentClassRepository.findAll();

        Integer num=0;
        for(Class aClass:collect5){
            List<StudentClass> collect = all1.stream().filter(t -> t.getStudentId().equals(studentId)&&t.getClassId().equals(aClass.getClassId())).collect(Collectors.toList());
            num=num+collect.size();
        }
        List<StudentClass> collect = all1.stream().filter(t -> t.getStudentId().equals(studentId)).collect(Collectors.toList());//签到数量
        stringObjectHashMap1.put("attendence_num",num);

        List<Discussion> all2 = discussionRepository.findAll();
        List<Discussion> collect1 = all2.stream().filter(t -> t.getPosterId().equals(studentId)&&t.getCourseId().equals(courseId)).collect(Collectors.toList());//发帖数量
        System.out.println(collect1);
        stringObjectHashMap1.put("discussion_num",collect1.size());


        List<Review> all3 = reviewRepository.findAll();
        Integer review_num=0;
        for(Discussion discussion:collect1){
            List<Review> collect2=all3.stream().filter(t -> t.getReviewerId().equals(studentId)&&t.getDiscussionId().equals(discussion.getDiscussionId())).collect(Collectors.toList());
            review_num+= collect2.size();
        }


        //回复数量
        stringObjectHashMap1.put("review_num",review_num);

        List<StudentTest> all4 = studentTestRepository.findAll();
        List<StudentTest> collect3 = all4.stream().filter(t -> t.getStudentId().equals(studentId)).collect(Collectors.toList());//所有课程的练习

        List<HashMap> hashMaps = new ArrayList<>();
        for (Class aClass : collect5) {
            System.out.println("aclass："+aClass);
            List<StudentTest> collect4 = collect3.stream().filter(t -> t.getClassId().equals(aClass.getClassId())).collect(Collectors.toList());
            HashMap<String, Object> stringObjectHashMap2 = new HashMap<>();
            //List<StudentTest> all3 = studentTestRepository.findAll()
            /*List<StudentClass> stclass=studentClassRepository.findAll();
            stclass=stclass.stream().filter(t -> t.getStudentId().equals(studentId)).collect(Collectors.toList());*/

            List<StudentTest> collect6 = collect4.stream().filter(t -> t.getTestType().equals(0)).collect(Collectors.toList());
            collect6=collect6.stream().filter(t -> t.getTestScore().compareTo(0)> 0).collect(Collectors.toList());
            //System.out.println("cao"+collect6);
            List<StudentTest> collect7 = collect4.stream().filter(t -> t.getTestType().equals(1)).collect(Collectors.toList());
            collect7=collect7.stream().filter(t -> t.getTestScore().compareTo(0)> 0).collect(Collectors.toList());


            stringObjectHashMap2.put("first_right_num",collect6.size());//一次正确率
            stringObjectHashMap2.put("second_right_num",collect7.size());//二次正确率
            stringObjectHashMap2.put("exercise_num",collect4.size());//这堂课的总练习数
            hashMaps.add(stringObjectHashMap2);

        }

        stringObjectHashMap1.put("class_statistics",hashMaps);

        stringObjectHashMap.put("data",stringObjectHashMap1);
        return  stringObjectHashMap;

    }

    public Map<String, Object> selectStudentMessageOne(Long courseId, Integer studentId, String fromDate, String toDate) {
        Optional<Student> byId = studentRepository.findById((long)studentId);

        Map<String, Object> stringObjectHashMap = new HashMap<>();//返回数据结构
        stringObjectHashMap.put("code",0);
        stringObjectHashMap.put("msg","成功");

        Map<String, Object> stringObjectHashMap1 = new HashMap<>();//返回数据信息

        stringObjectHashMap1.put("student_id",String.valueOf(byId.get().getId()));
        stringObjectHashMap1.put("student_name",byId.get().getName());

        List<Map> maps2 = new ArrayList<>();

        List<Class> all = classRepository.findAll();//课堂数 根据课程算出课堂
        List<Class> collect5 = all.stream().filter(t -> t.getCourseId().equals(courseId)).collect(Collectors.toList());
        List<Test> allTest = testRepository.findAll();//测试

        List<Question> all2 = questionRepository.findAll();//试题
        List<StudentTest> all3 = studentTestRepository.findAll();//学生测试分数

        List<Class> all0 = classRepository.findAll();//课堂数
        List<StudentTest> all41 = studentTestRepository.findAll();
        List<StudentTest> collect31 = all41.stream().filter(t -> t.getStudentId().equals(studentId)).collect(Collectors.toList());//所有课程的练习

        for (Class aClass : collect5) {
            Map<String, Object> stringObjectHashMap2 = new HashMap<>();//返回数据信息quizzes
            List<StudentTest> collect41 = collect31.stream().filter(t -> t.getClassId().equals(aClass.getClassId())).collect(Collectors.toList());



            List<StudentTest> collect61 = collect41.stream().filter(t -> t.getTestType().equals(0)).collect(Collectors.toList());
            collect61=collect61.stream().filter(t -> t.getTestScore().compareTo(0)> 0).collect(Collectors.toList());

            List<StudentTest> collect71 = collect41.stream().filter(t -> t.getTestType().equals(1)).collect(Collectors.toList());
            collect71=collect71.stream().filter(t -> t.getTestScore().compareTo(0)> 0).collect(Collectors.toList());


            stringObjectHashMap2.put("first_right_num",collect61.size());//一次正确率
            stringObjectHashMap2.put("second_right_num",collect71.size());//二次正确率
            stringObjectHashMap2.put("exercise_num",collect41.size());//这堂课的总练习数
            maps2.add(stringObjectHashMap2);

        }

        //damn
        stringObjectHashMap1.put("quizzes",maps2);
        List<Map> maps3 = new ArrayList<>();
        Map<String, Object> stringObjectHashMap3 = new HashMap<>();//返回数据信息attendance
        List<Class> allone = classRepository.findAll();//课堂数

        List<Class> collect5one = allone.stream().filter(t -> t.getCourseId().equals(courseId)).collect(Collectors.toList());
        stringObjectHashMap3.put("total_classes",collect5one.size());
        List<StudentClass> alln = studentClassRepository.findAll();

        Integer num2=0;
        for(Class aClass:collect5one){
            List<StudentClass> collectn = alln.stream().filter(t -> t.getStudentId().equals(studentId)&&t.getClassId().equals(aClass.getClassId())).collect(Collectors.toList());
            num2=num2+collectn.size();
        }
        //List<StudentClass> collect = alln.stream().filter(t -> t.getStudentId().equals(studentId)).collect(Collectors.toList());//签到数量

        stringObjectHashMap3.put("attended_classes",num2);
        maps3.add(stringObjectHashMap3);
        stringObjectHashMap1.put("attendance",maps3);
        alln = studentClassRepository.findAll();
        System.out.println("alln："+alln);
        System.out.println("5one:"+collect5one);

        List<Map> maps4 = new ArrayList<>();
        for(Class aClass:collect5one){
            Map<String, Object> stringObjectHashMap4 = new HashMap<>();//返回数据信息 records
            Optional<StudentClass> matchedClass = alln.stream()
                    .filter(t -> t.getStudentId().equals(studentId) && t.getClassId().equals(aClass.getClassId()))
                    .findFirst(); // 只需第一个匹配项
            System.out.println("match："+matchedClass);
            if (matchedClass.isPresent()) {
                stringObjectHashMap4.put("status", 1);
                stringObjectHashMap4.put("date", matchedClass.get().getCreatedTime());
            } else {

                stringObjectHashMap4.put("status", 0); // 表示未找到
                stringObjectHashMap4.put("date", aClass.getCreatedTime()); // 或其他默认值
            }

            maps4.add(stringObjectHashMap4);
        }
        stringObjectHashMap1.put("records",maps4);

        stringObjectHashMap.put("data",stringObjectHashMap1);
        return  stringObjectHashMap;
    }

}
/*for (Class aClass : collect5) {
            List<Test> collect = allTest.stream().filter(t -> t.getClassId().equals(aClass.getClassId())).collect(Collectors.toList());//测试
            for (Test test : collect) {
                //计算totalScore 分数
                List<QuestionTest> collect1 = all1.stream().filter(t -> t.getTestId().equals(test.getTestId())).collect(Collectors.toList());//测试试题中间表
                Double totalScore=0.0;
                for (QuestionTest questionTest : collect1) {
                    List<Question> collect2 = all2.stream().filter(t -> t.getQuestionId().equals(questionTest.getQuestionId())).collect(Collectors.toList());
                    if (collect2.size()>0) {
                        totalScore=totalScore+Double.valueOf(String.valueOf(collect2.get(0).getQuestionScore()));
                    }
                }
                //计算score
                List<StudentTest> collect2 = all3.stream().filter(t -> t.getTestId().equals(test.getTestId()) && t.getStudentId().equals(Integer.valueOf(studentId.toString()))).collect(Collectors.toList());//test 想同的 学生测试分数



*//*                stringObjectHashMap2.put("quiz_id",test.getTestId());
                stringObjectHashMap2.put("quiz_name",test.getTestName());
                stringObjectHashMap2.put("score",collect2.get(0).getTestScore());
                stringObjectHashMap2.put("total_score",totalScore);
                stringObjectHashMap2.put("completed_time",collect2.get(0).getTestTime());
                maps2.add(stringObjectHashMap2);*//*

            }

        }*/