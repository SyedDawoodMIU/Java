import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
    private int courseId;
    private String courseName;
    private List<Student> students;
    private List<Assignment> assignments;
    
    public Course(int courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.students = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }
    
    public List<Student> getStudents() {
        return students;
    }
    
    public List<Assignment> getAssignments() {
        return assignments;
    }
}

public class Student {
    private int studentId;
    private String studentName;
    private List<Submission> submissions;
    
    public Student(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.submissions = new ArrayList<>();
    }
    
    public void submitAssignment(Assignment assignment, List<Attachment> attachments) {
        Submission submission = new Submission(this, assignment, new Date(), attachments);
        submissions.add(submission);
    }
    
    public List<Submission> getSubmissions() {
        return submissions;
    }
}

public class Assignment {
    private int assignmentId;
    private String title;
    private Date openDate;
    private Date dueDate;
    private String instructions;
    private List<Attachment> attachments;
    private List<Assignment> subAssignments;
    private List<Submission> submissions;
    
    public Assignment(int assignmentId, String title, Date openDate, Date dueDate, String instructions) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.openDate = openDate;
        this.dueDate = dueDate;
        this.instructions = instructions;
        this.attachments = new ArrayList<>();
        this.subAssignments = new ArrayList<>();
        this.submissions = new ArrayList<>();
    }
    
    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }
    
    public void addSubAssignment(Assignment subAssignment) {
        subAssignments.add(subAssignment);
    }
    
    public List<Attachment> getAttachments() {
        return attachments;
    }
    
    public List<Assignment> getSubAssignments() {
        return subAssignments;
    }
    
    public List<Submission> getSubmissions() {
        return submissions;
    }
}

public class Submission {
    private int submissionId;
    private Student student;
    private Assignment assignment;
    private Date submissionDate;
    private List<Attachment> attachments;
    private int grade;
    
    public Submission(Student student, Assignment assignment, Date submissionDate, List<Attachment> attachments) {
        this.student = student;
        this.assignment = assignment;
        this.submissionDate = submissionDate;
        this.attachments = attachments;
    }
    
    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }
    
    public void setGrade(int grade) {
        this.grade = grade;
    }
}

public class Attachment {
    private int attachmentId;
    private String fileName;
    private String fileLocation;
    
    public Attachment(int attachmentId, String fileName, String fileLocation) {
        this.attachmentId = attachmentId;
        this.fileName = fileName;
        this.fileLocation = fileLocation;
    }
}


public class Main {
    public static void main(String[] args) {
        // Create a course
        Course course = new Course(1, "Programming 101");

        // Create students
        Student student1 = new Student(1, "John");
        Student student2 = new Student(2, "Emma");

        // Add students to the course
        course.addStudent(student1);
        course.addStudent(student2);

        // Create an assignment
        Assignment assignment1 = new Assignment(1, "Assignment 1", new Date(), new Date(), "Complete the exercises.");

        // Add attachments to the assignment
        Attachment attachment1 = new Attachment(1, "Attachment 1", "/path/to/attachment1");
        Attachment attachment2 = new Attachment(2, "Attachment 2", "/path/to/attachment2");
        assignment1.addAttachment(attachment1);
        assignment1.addAttachment(attachment2);

        // Add the assignment to the course
        course.addAssignment(assignment1);

        // Students submit their solutions
        student1.submitAssignment(assignment1, student1.getAttachments());
        student2.submitAssignment(assignment1, student2.getAttachments());

        // Professor grades the submissions
        Submission submission1 = assignment1.getSubmissions().get(0);
        Submission submission2 = assignment1.getSubmissions().get(1);

        submission1.setGrade(80);
        submission2.setGrade(90);

        // Display the grades
        System.out.println("Grades for Assignment 1:");
        for (Submission submission : assignment1.getSubmissions()) {
            System.out.println("Student: " + submission.getStudent().getStudentName());
            System.out.println("Grade: " + submission.getGrade());
        }
    }
}