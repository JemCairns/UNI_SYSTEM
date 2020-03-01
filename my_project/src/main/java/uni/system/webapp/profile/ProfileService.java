package uni.system.webapp.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.StaffRepository;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.tables.Staff;
import uni.system.webapp.tables.Student;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StaffRepository staffRepository;

    public Student getStudent(String ID) {
        return studentRepository.getOne(ID);
    }
    public Staff getStaff(String ID) {
        return staffRepository.getOne(ID);
    }

    public String getUserName(String ID){
        if(ID.endsWith("STU")){
            return studentRepository.getOne(ID).getFirst_name();
        }
        else if(ID.endsWith("STA")){
            return staffRepository.getOne(ID).getFirst_name();
        }
        return "";
    }

    public List<Student> getAllStudents() { return studentRepository.findAll(); }

}
