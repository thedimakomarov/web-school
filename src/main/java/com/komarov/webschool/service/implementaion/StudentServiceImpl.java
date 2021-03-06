package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.InnerStudentDto;
import com.komarov.webschool.dto.InnerTeamDto;
import com.komarov.webschool.dto.StudentDto;
import com.komarov.webschool.entity.Student;
import com.komarov.webschool.entity.Team;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.StudentRepository;
import com.komarov.webschool.service.StudentService;
import com.komarov.webschool.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private static final String NOT_FOUND_ID_MESSAGE = "Student with id - %d was not found. Choose another or create new student with current parameters.";
    private static final String NOT_FOUND_FULL_NAME_MESSAGE =
            "Student with firstName - '%s' and lastName - '%s' was not found. Choose another or create new student with current parameters.";
    private static final String NOT_FOUND_NOT_ENOUGH_INFO = "Student was not found. Please enter valid id or valid full name and valid name of the group.";
    private final StudentRepository studentRepository;
    private final TeamService teamService;

    public StudentServiceImpl(StudentRepository studentRepository, TeamService teamService) {
        this.studentRepository = studentRepository;
        this.teamService = teamService;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<StudentDto> findDtoAll() {
        return parse(findAll());
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id)));
    }

    @Override
    public StudentDto findDtoById(Long id) {
        return parse(findById(id));
    }

    @Override
    public Student findByFullNameAndTeamName(String firstName, String lastName, String teamName) {
        return studentRepository.findByFirstNameAndLastNameAndTeamName(firstName, lastName, teamName)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_FULL_NAME_MESSAGE, firstName, lastName)));
    }

    @Override
    public StudentDto findDtoByFullNameAndTeamName(String firstName, String lastName, String teamName) {
        return parse(findByFullNameAndTeamName(firstName, lastName, teamName));
    }

    @Override
    public Student findStudentByInnerStudentDto(InnerStudentDto innerStudentDto) {
        Long id = innerStudentDto.getId();
        String firstName = innerStudentDto.getFirstName();
        String lastName = innerStudentDto.getLastName();
        String group = innerStudentDto.getTeamName();

        if(id != null) {
            return findById(id);
        } else if(firstName != null && lastName != null && group != null){
            return findByFullNameAndTeamName(firstName, lastName, group);
        } else {
            throw new NotFoundException(NOT_FOUND_NOT_ENOUGH_INFO);
        }
    }

    @Override
    public StudentDto create(StudentDto studentDto) {
        Student student = prepareForSaving(studentDto);
        return parse(studentRepository.save(student));
    }

    @Override
    public StudentDto update(Long id, StudentDto studentDto) {
        checkForExists(id);

        Student student = prepareForSaving(studentDto);
        student.setId(id);
        return parse(studentRepository.save(student));
    }

    private Student prepareForSaving(StudentDto studentDto) {
        Team team = teamService.findTeamByInnerTeamDto(studentDto.getTeam());

        Student student = parse(studentDto);
        student.setTeam(team);
        return student;
    }

    private Student parse(StudentDto studentDto) {
        return new Student(
                studentDto.getId(),
                studentDto.getFirstName(),
                studentDto.getLastName(),
                studentDto.getMobile()
        );
    }

    private StudentDto parse(Student student) {
        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getMobile(),
                InnerTeamDto.parse(student.getTeam())
        );
    }

    private List<StudentDto> parse(List<Student> students) {
        return students.stream()
                .map(this::parse)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        checkForExists(id);
        studentRepository.deleteById(id);
    }

    private void checkForExists(Long id) {
        if (notExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id));
        }
    }

    private boolean notExists(Long id) {
        return !studentRepository.existsById(id);
    }
}
