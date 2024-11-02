package com.example.tasko.controller;

import com.example.tasko.service.TaskService;
import com.example.tasko.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public String showReports(Model model) {
        // Task statistics
        long completedTasks = taskService.getCompletedTasksCount();
        long pendingTasks = taskService.getPendingTasksCount();
        double taskCompletionRate = calculatePercentage(completedTasks, completedTasks + pendingTasks);
        
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("pendingTasks", pendingTasks);
        model.addAttribute("taskCompletionRate", taskCompletionRate);

        // Attendance statistics
        long presentCount = attendanceService.getPresentCount();
        long lateCount = attendanceService.getLateCount();
        long absentCount = attendanceService.getAbsentCount();
        double attendanceRate = calculatePercentage(presentCount, presentCount + lateCount + absentCount);

        model.addAttribute("presentCount", presentCount);
        model.addAttribute("lateCount", lateCount);
        model.addAttribute("absentCount", absentCount);
        model.addAttribute("attendanceRate", attendanceRate);

        return "reports/index";
    }

    private double calculatePercentage(long value, long total) {
        return total == 0 ? 0 : Math.round((double) value / total * 100);
    }
}