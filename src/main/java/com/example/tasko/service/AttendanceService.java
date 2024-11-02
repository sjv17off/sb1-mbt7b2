package com.example.tasko.service;

import com.example.tasko.model.Attendance;
import com.example.tasko.model.AttendanceStatus;
import com.example.tasko.model.Enterprise;
import com.example.tasko.model.User;
import com.example.tasko.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Transactional
    public Attendance logAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Transactional(readOnly = true)
    public List<Attendance> getUserAttendance(User user, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Attendance> getMonthlyAttendance(LocalDate month) {
        LocalDate startDate = month.withDayOfMonth(1);
        LocalDate endDate = month.withDayOfMonth(month.lengthOfMonth());
        return attendanceRepository.findByDateBetween(startDate, endDate);
    }

    public long countTodayAttendanceByEnterprise(Enterprise enterprise) {
        return attendanceRepository.countByUserEnterpriseAndDate(enterprise, LocalDate.now());
    }

    public List<Attendance> getRecentAttendanceByEnterprise(Enterprise enterprise) {
        return attendanceRepository.findTop5ByUserEnterpriseOrderByDateDesc(enterprise);
    }

    public Attendance getTodayAttendance(User user) {
        return attendanceRepository.findByUserAndDate(user, LocalDate.now());
    }

    public long getPresentCount() {
        return attendanceRepository.countByStatus(AttendanceStatus.PRESENT);
    }

    public long getLateCount() {
        return attendanceRepository.countByStatus(AttendanceStatus.LATE);
    }

    public long getAbsentCount() {
        return attendanceRepository.countByStatus(AttendanceStatus.ABSENT);
    }
}