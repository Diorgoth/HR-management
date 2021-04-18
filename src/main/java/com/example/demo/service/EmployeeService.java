package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.entity.TaskStatus.TaskStatus;
import com.example.demo.entity.enums.RoleName;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.EmployeeResponse;
import com.example.demo.payload.SalaryDto;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TurniketRepository turniketRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    SalaryRepository salaryRepository;


       public ApiResponse allEmployees(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {

            User user = (User) authentication.getPrincipal();
            Set<Role> roles = user.getRoles();

            boolean matchRole = false;
            for (Role role : roles) {
                if (role.getRoleName().name().equals("DIRECTOR") || role.getRoleName().name().equals("HR_MANAGER")) {
                    matchRole = true;
                    break;
                }
            }

            if (!matchRole)
                return new ApiResponse("Role not match", false);

            Optional<Role> optionalRole = roleRepository.findByRoleName(RoleName.EMPLOYEE);
            if (!optionalRole.isPresent())
                return new ApiResponse("Role not found!", false);

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(optionalRole.get());
            List<User> employeeList = userRepository.findAllByRolesIn(Collections.singleton(roleSet));

            return new ApiResponse("Success!", true, employeeList);

    }

        return new ApiResponse("Authorization empty!", false);

}


    public EmployeeResponse byData(UUID id, Timestamp start, Timestamp finish){


        Optional<User> optionalEmployee = userRepository.findById(id);
        if (!optionalEmployee.isPresent())
            return new EmployeeResponse("Such employee id not found!", false);

        Set<Role> roles = optionalEmployee.get().getRoles();
        boolean checkEmployeeRole = false;

        for (Role role:roles ) {

            if (role.getLevel().equals(4)){
                checkEmployeeRole = true;
                break;
            }

        }
        List<Turniket> turniketList =
                turniketRepository.findAllByCreatedByAndEnterDateTimeAndExitDateTimeBefore(id,start.toLocalDateTime(), finish.toLocalDateTime());

        if (turniketList.isEmpty())
            return new EmployeeResponse("Data not found!", false);

         EmployeeResponse empResponse = new EmployeeResponse();
        empResponse.setTurniketList(turniketList);

        if (checkEmployeeRole) {
            List<Task> taskList = taskRepository.findAllByStatusAndResponsibleId(TaskStatus.READY, id);
            empResponse.setTaskList(taskList);
        }

        return empResponse;



    }



    public ApiResponse payMonthly(SalaryDto salaryDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();
            Set<Role> roles = user.getRoles();

            boolean checkRole = false;
            for (Role role : roles) {
                if (role.getLevel().equals(1) || role.getLevel().equals(3)) {//har bir rolega darajasiga nisbatan level berilgan level 1 director,3 hr
                    checkRole = true;
                    break;
                }
            }
            if (!checkRole)
                return new ApiResponse("You don't have access for this operation", false);

            Optional<User> optionalEmployee = userRepository.findById(salaryDto.getEmployeeId());
            if (!optionalEmployee.isPresent())
                return new ApiResponse("Such Employee was not found!", false);

            Salary salary = new Salary();
            salary.setEmployee(optionalEmployee.get());
            salary.setSalaryAmount(salaryDto.getSalaryAmount());
            salary.setWorkStartDate(salaryDto.getWorkStartDate());
            salary.setWorkEndDate(salaryDto.getWorkEndDate());

            salaryRepository.save(salary);

            return new ApiResponse("Salary Saved! To: " + optionalEmployee.get().getFirstName(), true);
        }
        return new ApiResponse("Authorization empty!", false);
    }


    public ApiResponse getSalariesByUserId(UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();
            Set<Role> roles = user.getRoles();

            boolean checkRole = false;
            for (Role role : roles) {
                if (role.getLevel().equals(1) || role.getLevel().equals(3)) {
                    checkRole = true;
                    break;
                }
            }
            if (!checkRole)
                return new ApiResponse("You don't have access for this operation", false);
            List<Salary> salaryHistoryList = salaryRepository.findAllByEmployeeId(id);
            if (salaryHistoryList.size() == 0)
                return new ApiResponse("Such employee did not get salary!", false);

            return new ApiResponse("Success!", true, salaryHistoryList);
        }
        return new ApiResponse("Authorization empty!", false);
    }


    public ApiResponse getSalariesByMonth(String year, Integer monthNumber) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();
            Set<Role> roles = user.getRoles();

            boolean checkRole = false;
            for (Role role : roles) {
                if (role.getLevel().equals(1) || role.getLevel().equals(3)) {
                    checkRole = true;
                    break;
                }
            }

            if (!checkRole)
                return new ApiResponse("You don't have access for this operation", false);
            String month = monthNumber + "";
            if (monthNumber < 10)
                month = "0" + monthNumber;

            String full = year + "-" + month + "-01 05:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(full, formatter);

            Timestamp start = Timestamp.valueOf(dateTime);
            List<Salary> salaryHistoryList = salaryRepository.findAllByWorkStartDate(start);
            if (salaryHistoryList.size() == 0)
                return new ApiResponse("Salary list empty!", false);

            return new ApiResponse("Success!", true, salaryHistoryList);
        }
        return new ApiResponse("Authorization empty!", false);
        }







}
