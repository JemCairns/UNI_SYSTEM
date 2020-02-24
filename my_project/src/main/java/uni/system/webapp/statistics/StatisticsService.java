package uni.system.webapp.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.StaffRepository;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.tables.Student;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StaffRepository staffRepository;

    public int getNumGender(String gender) {
        List<Student> students = studentRepository.findAll();
        int num=0;
//        System.out.println("gender: "+gender);
        for(Student student : students){
//            System.out.println(student.getGender());
            if(student.getGender().equalsIgnoreCase(gender)){
                num++;
            }
        }
//        System.out.println("count: " + num + "\n");
        return num;
    }

    public int getNumStudents() {
        return studentRepository.findAll().size();
    }

    public int getNumStaff() {
        return staffRepository.findAll().size();
    }


}
