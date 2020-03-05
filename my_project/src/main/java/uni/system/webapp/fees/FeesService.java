package uni.system.webapp.fees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.tables.Student;

@Service
public class FeesService {

    @Autowired
    private StudentRepository studentRepository;

    public Student getStudent(String id) {
        return studentRepository.getOne(id);
    }

    public String updateStudentFees(Student s, double amount) {
        if(amount < 0) {
            return "Please enter a valid number!";
        } else if(amount > s.getFees_due()) {
            return "Please enter a valid amount!";
        } else if(s.getFees_due() == 0) {
            return "You have paid all of your fess!";
        } else {
            s.setFees_due(s.getFees_due() - amount);
            s.setFees_paid(s.getFees_paid() + amount);
            studentRepository.save(s);
            return "ok";
        }
    }
}
