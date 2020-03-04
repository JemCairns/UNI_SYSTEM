package uni.system.webapp.grades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.ModuleRegistrationRepository;
import uni.system.webapp.repositories.StaffRepository;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.tables.ModuleRegistration;
import uni.system.webapp.tables.Student;

import java.util.*;

@Service
public class GradesService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ModuleRegistrationRepository moduleRegistrationRepository;

    private String[] validGrades = {"NG", "F", "E", "D-", "D", "D+", "C-", "C", "C+", "B-", "B", "B+", "A-", "A", "A+"};
//    private Map<String, Integer> validGradesMap = Map.ofEntries(Map.entry("NG", 10), Map.entry("F", 25), Map.entry("E", 40), Map.entry("D-", 45), Map.entry("D", 50), Map.entry("D+", 55), Map.entry("C-", 60), Map.entry("C", 65), Map.entry("C+", 70), Map.entry("B-", 75), Map.entry("B", 80), Map.entry("B+", 85), Map.entry("A-", 90), Map.entry("A", 95), Map.entry("A+", 101));

    public String getUserName(String ID){
        if(ID.endsWith("STU")){
            return studentRepository.getOne(ID).getFirst_name();
        }
        else if(ID.endsWith("STA")){
            return staffRepository.getOne(ID).getFirst_name();
        }
        return "";
    }

    public List<ModuleRegistration> getAllModuleRegistrations() {
        return moduleRegistrationRepository.findAll();
    }

    public List<Student> getAllStudentsRegisteredToModule(String moduleID) {
        List<ModuleRegistration> moduleRegistrations = moduleRegistrationRepository.findAll();
        List<Student> students = new LinkedList<>();

        for(ModuleRegistration m : moduleRegistrations) {
            if(m.getModule_ID().equals(moduleID)) {
                students.add(studentRepository.getOne(m.getStudent_ID()));
            }
        }

        return students;
    }

    public boolean updateGrades(String moduleID, String[] grades, String[] percents) {
        List<ModuleRegistration> moduleRegistrations = getAllModuleRegistrations();

        List<String> validGradesList = Arrays.asList(validGrades);
        int i = 0;
        for(ModuleRegistration m : moduleRegistrations) {
            if(m.getModule_ID().equals(moduleID)) {

//                if(validGradesList.contains(grades[i]) && grades[i].equals("NG") && (Double.parseDouble(percents[i]) < validGradesMap.get(grades[i]))) {
//                    m.setLetterGrade(grades[i]);
//                    m.setPercentage(Double.parseDouble(percents[i]));
//                    moduleRegistrationRepository.save(m);
//                    i++;
//                } else if(validGradesList.contains(grades[i]) && (Double.parseDouble(percents[i]) < validGradesMap.get(grades[i])) && Double.parseDouble(percents[i]) >= validGradesMap.get(validGrades[validGradesList.indexOf(grades[i]) - 1])) {
//                    m.setLetterGrade(grades[i]);
//                    m.setPercentage(Double.parseDouble(percents[i]));
//                    moduleRegistrationRepository.save(m);
//                    i++;
//                } else {
//                    return false;
//                }
            }
        }

        return true;
    }
}
