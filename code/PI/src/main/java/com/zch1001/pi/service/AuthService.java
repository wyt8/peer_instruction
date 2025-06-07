package com.zch1001.pi.service;

import com.zch1001.pi.entity.Admin;
import com.zch1001.pi.entity.Student;
import com.zch1001.pi.entity.Teacher;
import com.zch1001.pi.entity.VerificationCode;
import com.zch1001.pi.model.*;
import com.zch1001.pi.repository.AdminRepository;
import com.zch1001.pi.repository.StudentRepository;
import com.zch1001.pi.repository.TeacherRepository;
import com.zch1001.pi.repository.VerificationCodeRepository;
import com.zch1001.pi.utils.EmailUtils;
import com.zch1001.pi.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        int role = loginRequest.getRole();
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        LoginResponse loginResponse = new LoginResponse();
        if (role == 2) {
            Optional<Teacher> teacher = teacherRepository.findByEmail(email);
            if (teacher.isEmpty()) {
                loginResponse.setCode(-1);
                loginResponse.setMsg("用户不存在!");
            }
            else if (!teacher.get().getPassword().equals(password)) {
                loginResponse.setCode(-1);
                loginResponse.setMsg("密码错误!");
            }
            else {
                loginResponse.setCode(0);
                loginResponse.setMsg("登录成功!");
                LoginResponse.UserData userData = loginResponse.new UserData();
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", teacher.get().getId());
                claims.put("role", role);
                String Jwt = JwtUtils.generateJwt(claims);
                userData.setUserId(teacher.get().getId());
                userData.setUserName(teacher.get().getName());
                userData.setUserAvatar(teacher.get().getAvatar());
                userData.setToken(Jwt);
                loginResponse.setData(userData);
            }
        }
        else if (role == 3) {
            Optional<Student> student = studentRepository.findByEmail(email);
            if (student.isEmpty()) {
                loginResponse.setCode(-1);
                loginResponse.setMsg("用户不存在!");
            }
            else if (!student.get().getPassword().equals(password)) {
                loginResponse.setCode(-1);
                loginResponse.setMsg("密码错误!");
            }
            else {
                loginResponse.setCode(0);
                loginResponse.setMsg("登录成功!");
                LoginResponse.UserData userData = loginResponse.new UserData();
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", student.get().getId());
                claims.put("role", role);
                String Jwt = JwtUtils.generateJwt(claims);
                userData.setUserId(student.get().getId());
                userData.setUserName(student.get().getName());
                userData.setUserAvatar(student.get().getAvatar());
                userData.setToken(Jwt);
                loginResponse.setData(userData);
            }
        }
        return loginResponse;
    }

     public RegisterResponse register(RegisterRequest registerRequest) {
         int inputCode = registerRequest.getVerificationCode();
         int role = registerRequest.getRole();
         String email = registerRequest.getEmail();
         String password = registerRequest.getPassword();
         RegisterResponse registerResponse = new RegisterResponse();
         RegisterResponse.UserData userData = registerResponse.new UserData();
         if (role == 2) {
             Optional<Teacher> teacher0 = teacherRepository.findByEmail(email);
             Optional<VerificationCode> verificationCode = verificationCodeRepository.findByEmail(email);
             if (teacher0.isPresent()) {
                 registerResponse.setCode(-1);
                 registerResponse.setMsg("该邮箱已注册过!");
             }
             else if (verificationCode.isEmpty()) {
                 registerResponse.setCode(-1);
                 registerResponse.setMsg("未发送验证码!");
             }
             else if (verificationCode.get().getCode() != inputCode) {
                 registerResponse.setCode(-1);
                 registerResponse.setMsg("验证码错误!");
             }
             else {
                 Teacher teacher = new Teacher();
                 teacher.setEmail(email);
                 teacher.setPassword(password);
                 Random random = new Random();
                 int avatarNum = random.nextInt(8) + 1;
                 String avatar = "http://116.205.181.81:8081/" + avatarNum + ".png";
                 teacher.setAvatar(avatar);
                 teacherRepository.save(teacher);
                 teacher.setName("教师" + teacher.getId());
                 teacherRepository.save(teacher);
                 Map<String, Object> claims = new HashMap<>();
                 claims.put("id", teacher.getId());
                 claims.put("role", role);
                 String Jwt = JwtUtils.generateJwt(claims);
                 userData.setUserId(teacher.getId());
                 userData.setUserName(teacher.getName());
                 userData.setUserAvatar(teacher.getAvatar());
                 userData.setToken(Jwt);
                 registerResponse.setCode(0);
                 registerResponse.setMsg("注册成功!");
                 registerResponse.setData(userData);
             }
         }
         else if (role == 3) {
             Optional<Student> student0 = studentRepository.findByEmail(email);
             Optional<VerificationCode> verificationCode = verificationCodeRepository.findByEmail(email);
             if (student0.isPresent()) {
                 registerResponse.setCode(-1);
                 registerResponse.setMsg("该邮箱已注册过!");
             }
             else if (verificationCode.isEmpty()) {
                 registerResponse.setCode(-1);
                 registerResponse.setMsg("未发送验证码!");
             }
             else if (verificationCode.get().getCode() != inputCode) {
                 registerResponse.setCode(-1);
                 registerResponse.setMsg("验证码错误!");
             }
             else {
                 Student student = new Student();
                 student.setEmail(email);
                 student.setPassword(password);
                 Random random = new Random();
                 int avatarNum = random.nextInt(8) + 1;
                 String avatar = "http://116.205.181.81:8081/" + avatarNum + ".png";
                 student.setAvatar(avatar);
                 studentRepository.save(student);
                 student.setName("学生" + student.getId());
                 studentRepository.save(student);
                 Map<String, Object> claims = new HashMap<>();
                 claims.put("id", student.getId());
                 claims.put("role", role);
                 String Jwt = JwtUtils.generateJwt(claims);
                 userData.setUserId(student.getId());
                 userData.setUserName(student.getName());
                 userData.setUserAvatar(student.getAvatar());
                 userData.setToken(Jwt);
                 registerResponse.setCode(0);
                 registerResponse.setMsg("注册成功!");
                 registerResponse.setData(userData);
             }
         }
         return registerResponse;
     }

     public EmailVerifyResponse emailVerify(EmailVerifyRequest emailVerifyRequest) {
         int role = emailVerifyRequest.getRole();
         String email = emailVerifyRequest.getEmail();
         Random random = new Random();
         Long code = (long) random.nextInt(900000) + 100000;
         EmailUtils emailUtils = new EmailUtils();
         emailUtils.send(email, code);
         verificationCodeRepository.deleteByEmail(email);
         VerificationCode verificationCode = new VerificationCode();;
         verificationCode.setCode(code);
         verificationCode.setEmail(email);
         verificationCodeRepository.save(verificationCode);
         EmailVerifyResponse emailVerifyResponse = new EmailVerifyResponse();
         emailVerifyResponse.setCode(0);
         emailVerifyResponse.setMsg("验证码发送成功!");
         return emailVerifyResponse;
     }

     public ModifyPasswordResponse modifyPassword(ModifyPasswordRequest modifyPasswordRequest) {
         int inputCode = modifyPasswordRequest.getVerificationCode();
         int role = modifyPasswordRequest.getRole();
         String email = modifyPasswordRequest.getEmail();
         String password = modifyPasswordRequest.getPassword();
         ModifyPasswordResponse modifyPasswordResponse = new ModifyPasswordResponse();
         ModifyPasswordResponse.UserData userData = modifyPasswordResponse.new UserData();
         if (role == 2) {
             Optional<Teacher> teacher = teacherRepository.findByEmail(email);
             Optional<VerificationCode> verificationCode = verificationCodeRepository.findByEmail(email);
             if (teacher.isEmpty()) {
                 modifyPasswordResponse.setCode(-1);
                 modifyPasswordResponse.setMsg("用户不存在!");
             }
             else if (verificationCode.isEmpty()) {
                 modifyPasswordResponse.setCode(-1);
                 modifyPasswordResponse.setMsg("未发送验证码!");
             }
             else if (verificationCode.get().getCode() != inputCode) {
                 modifyPasswordResponse.setCode(-1);
                 modifyPasswordResponse.setMsg("验证码错误!");
             }
             else {
                 teacher.get().setPassword(password);
                 teacherRepository.save(teacher.get());
                 Map<String, Object> claims = new HashMap<>();
                 claims.put("id", teacher.get().getId());
                 claims.put("role", 2);
                 String Jwt = JwtUtils.generateJwt(claims);
                 userData.setUserId(teacher.get().getId());
                 userData.setUserName(teacher.get().getName());
                 userData.setUserAvatar(teacher.get().getAvatar());
                 userData.setToken(Jwt);
                 modifyPasswordResponse.setCode(0);
                 modifyPasswordResponse.setMsg("修改密码成功!");
                 modifyPasswordResponse.setData(userData);
             }
         }
         else if (role == 3) {
             Optional<Student> student = studentRepository.findByEmail(email);
             Optional<VerificationCode> verificationCode = verificationCodeRepository.findByEmail(email);
             if (student.isEmpty()) {
                 modifyPasswordResponse.setCode(-1);
                 modifyPasswordResponse.setMsg("用户不存在!");
             }
             else if (verificationCode.isEmpty()) {
                 modifyPasswordResponse.setCode(-1);
                 modifyPasswordResponse.setMsg("未发送验证码!");
             }
             else if (verificationCode.get().getCode() != inputCode) {
                 modifyPasswordResponse.setCode(-1);
                 modifyPasswordResponse.setMsg("验证码错误!");
             }
             else {
                 student.get().setPassword(password);
                 studentRepository.save(student.get());
                 Map<String, Object> claims = new HashMap<>();
                 claims.put("id", student.get().getId());
                 claims.put("role", 3);
                 String Jwt = JwtUtils.generateJwt(claims);
                 userData.setUserId(student.get().getId());
                 userData.setUserName(student.get().getName());
                 userData.setUserAvatar(student.get().getAvatar());
                 userData.setToken(Jwt);
                 modifyPasswordResponse.setCode(0);
                 modifyPasswordResponse.setMsg("修改密码成功!");
                 modifyPasswordResponse.setData(userData);
             }
         }
         return modifyPasswordResponse;
     }

     public ModifyStudentInfoResponse modifyStudentInfo(int id, ModifyStudentInfoRequest modifyStudentInfoRequest) {
         String name = modifyStudentInfoRequest.getName();
         String avatar = modifyStudentInfoRequest.getAvatar();
         String password = modifyStudentInfoRequest.getPassword();
         Optional<Student> student = studentRepository.findById(id);
         ModifyStudentInfoResponse modifyStudentInfoResponse = new ModifyStudentInfoResponse();
         if (student.isEmpty()) {
             modifyStudentInfoResponse.setCode(-1);
             modifyStudentInfoResponse.setMsg("学生不存在!");
         }
         else {
             if (name != null) student.get().setName(name);
             if (avatar != null) student.get().setAvatar(avatar);
             if (password != null) student.get().setPassword(password);
             studentRepository.save(student.get());
             modifyStudentInfoResponse.setCode(0);
             modifyStudentInfoResponse.setMsg("学生信息修改成功!");
         }
         return modifyStudentInfoResponse;
     }

}
