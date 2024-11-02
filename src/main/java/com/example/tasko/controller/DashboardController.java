package com.example.tasko.controller;

import com.example.tasko.model.*;
import com.example.tasko.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        
        if (user.getRole() == UserRole.ADMIN) {
            return setupAdminDashboard(model, user);
        } else {
            return setupUserDashboard(model, user);
        }
    }

    private String setupAdminDashboard(Model model, User user) {
        Enterprise enterprise = user.getEnterprise();
        model.addAttribute("user", user);
        model.addAttribute("enterprise", enterprise);
        model.addAttribute("userCount", userService.countUsersByEnterprise(enterprise));
        model.addAttribute("activeTaskCount", taskService.countActiveTasksByEnterprise(enterprise));
        model.addAttribute("todayAttendanceCount", attendanceService.countTodayAttendanceByEnterprise(enterprise));
        model.addAttribute("recentTasks", taskService.getRecentTasksByEnterprise(enterprise));
        model.addAttribute("lastBackup", LocalDateTime.now());

        return "dashboard/admin";
    }

    private String setupUserDashboard(Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("todayAttendance", attendanceService.getTodayAttendance(user));
        model.addAttribute("recentTasks", taskService.getRecentTasksByUser(user));

        return "dashboard/user";
    }
}