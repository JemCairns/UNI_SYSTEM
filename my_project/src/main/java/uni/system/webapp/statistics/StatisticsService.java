package uni.system.webapp.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.StaffRepository;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.tables.Staff;
import uni.system.webapp.tables.Student;

import java.util.*;

@Service
public class StatisticsService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StaffRepository staffRepository;

    public String getUserName(String ID){
        if(ID.endsWith("STU")){
            return studentRepository.getOne(ID).getFirst_name();
        }
        else if(ID.endsWith("STA")){
            return staffRepository.getOne(ID).getFirst_name();
        }
        return "";
    }

    public TreeMap<String, Integer> getStudentGendersAndCounts() {
        TreeMap<String, Integer> studentGendersAndCounts = new TreeMap<>();

        for(Student student : studentRepository.findAll()){
            String gender = student.getGender();
            if(studentGendersAndCounts.containsKey(gender)){
                studentGendersAndCounts.put(gender, studentGendersAndCounts.get(gender)+1);
            }
            else{
                studentGendersAndCounts.put(gender, 1);
            }
        }

        return studentGendersAndCounts;
    }
    public TreeMap<String, Integer> getStaffGendersAndCounts() {
        TreeMap<String, Integer> staffGendersAndCounts = new TreeMap<>();

        for(Staff staff : staffRepository.findAll()){
            String gender = staff.getGender();
            if(staffGendersAndCounts.containsKey(gender)){
                staffGendersAndCounts.put(gender, staffGendersAndCounts.get(gender)+1);
            }
            else{
                staffGendersAndCounts.put(gender, 1);
            }
        }

        return staffGendersAndCounts;
    }

    public int getNumStudents() {
        return studentRepository.findAll().size();
    }

    public int getNumStaff() {
        return staffRepository.findAll().size();
    }

    public TreeMap<Integer, Integer> getStudentIndexedAges() {

        TreeMap<Integer, Integer> indexedAges = new TreeMap<>();

        Date currentDate = Calendar.getInstance().getTime();
        int dayCurr = currentDate.getDay();
        int monthCurr = currentDate.getMonth();
        int yearCurr = currentDate.getYear();
        for(Student student : studentRepository.findAll()){
            Date userDate = student.getDate_of_birth();
            int dayPrev = userDate.getDay();
            int monthPrev = userDate.getMonth();
            int yearPrev = userDate.getYear();

            int age = yearCurr-yearPrev;
            if(monthCurr < monthPrev){
                age--;
            }
            else if(monthCurr == monthPrev){
                if(dayCurr < dayPrev){
                    age--;
                }
            }

            if(indexedAges.containsKey(age)){
                indexedAges.put(age, indexedAges.get(age)+1);
            }
            else{
                indexedAges.put(age, 1);
            }
        }

        return indexedAges;
    }
    public TreeMap<Integer, Integer> getStaffIndexedAges() {

        TreeMap<Integer, Integer> indexedAges = new TreeMap<>();

        Date currentDate = Calendar.getInstance().getTime();
        int dayCurr = currentDate.getDay();
        int monthCurr = currentDate.getMonth();
        int yearCurr = currentDate.getYear();
        for(Staff staff : staffRepository.findAll()){
            Date userDate = staff.getDate_of_birth();
            int dayPrev = userDate.getDay();
            int monthPrev = userDate.getMonth();
            int yearPrev = userDate.getYear();

            int age = yearCurr-yearPrev;
            if(monthCurr < monthPrev){
                age--;
            }
            else if(monthCurr == monthPrev){
                if(dayCurr < dayPrev){
                    age--;
                }
            }

            if(indexedAges.containsKey(age)){
                indexedAges.put(age, indexedAges.get(age)+1);
            }
            else{
                indexedAges.put(age, 1);
            }
        }

        return indexedAges;
    }

    public TreeMap<String, Integer> getStudentStagesAndCounts() {
        TreeMap<String, Integer> studentStagesAndCounts = new TreeMap<>();

        for(Student student : studentRepository.findAll()){
            String stage = student.getStage();
            if(studentStagesAndCounts.containsKey(stage)){
                studentStagesAndCounts.put(stage, studentStagesAndCounts.get(stage)+1);
            }
            else{
                studentStagesAndCounts.put(stage, 1);
            }
        }

        return studentStagesAndCounts;
    }

}
