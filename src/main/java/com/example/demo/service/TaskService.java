package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskStatus.TaskStatus;
import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.TaskDto;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    TaskRepository taskRepository;

    public ApiResponse addTask(TaskDto taskDto){

        Optional<User> optionalUser = userRepository.findById(taskDto.getResId());
        if (!optionalUser.isPresent()){

            return new ApiResponse("Such responsible user not found",false);

        }

        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Set<Role> roles = userDetails.getRoles();

        for (Role roleadder:roles){

            for (Role role:optionalUser.get().getRoles()){
                if (roleadder.getLevel()>role.getLevel()){

                    return new ApiResponse("You can't add task for higher positions",false);

                }else {

                    Task task = new Task();
                    task.setName(taskDto.getName());
                    task.setDescription(taskDto.getDescription());
                    task.setDeadline(taskDto.getDeadline());
                    task.setCreatedBy(userDetails);
                    task.setResId(optionalUser.get());
                    task.setStatus(taskDto.getStatus());
                    sendEmail(userDetails.getEmail(),optionalUser.get().getEmail());

                    taskRepository.save(task);

                    return new ApiResponse("Task successfully added",true);


                }
            }

        }

return new ApiResponse("Role not added",false);

    }


    public Boolean sendEmail(String senderEmail,String receiverEmail){

        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(receiverEmail);
            mailMessage.setSubject("Vazifa");
//            mailMessage.setText("Sizga yangi Vazifa barildi ");
            mailMessage.setText("<a href='http://localhost:8080/api/giventask?email="+receiverEmail+"'>Sizga vazifa berildi.Vazifani ko'rish uchun linkni bosing.</a>");
            javaMailSender.send(mailMessage);
            return true;

        }catch (Exception e){
            System.out.println(e);
            return false;
        }


    }


    public ApiResponse givenTask(String email){


        List<Task> byResId_email = taskRepository.findByResId_Email(email);


            return new ApiResponse("Sizga biriktirilgan vazifa",true,byResId_email);

    }

    public ApiResponse taskstatuschange(Integer taskId, TaskStatus status){


        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()){
            return new ApiResponse("Such task not found",false);
        }else {


            Task task = optionalTask.get();

            User resUser = task.getResId();
            User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!resUser.getId().equals(userDetails.getId())){
                return new ApiResponse("This task does not belong to you",false);
            }

            task.setStatus(status);
            sendEmailsts(resUser.getEmail(),task.getCreatedBy().getEmail(),status,task.getName());



            return new ApiResponse("Task status successfully changed",true);


        }

    }

    public Boolean sendEmailsts(String senderEmail,String receiverEmail,TaskStatus status,String taskName){

        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(receiverEmail);
            mailMessage.setSubject("Vazifa");
            mailMessage.setText(taskName+" task proccess changed to"+status);
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }

    }

}
