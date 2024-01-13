import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class UniversitySystem
{
    private ArrayList<User> users;
    private PrintStream printOutStream;
    
    public UniversitySystem()
    {
        this.users = new ArrayList<>();
        this.createAdminUser();
    }

    public ArrayList<User> getUsers()
    {
        return users;
    }

    public void loginUser(String username, String password, Socket clientSocket) throws IOException
    {
        printOutStream = new PrintStream(clientSocket.getOutputStream());
        for(User user : users)
        {
            if(user.getUsername().equals(username) && user.getPassword().equals(password))
            {
                printOutStream.println("Logged in successfully!");
                switch (user.getUserType()) 
                {
                    case STUDENT:
                        Student student = (Student)user;
                        studentMenu(student);
                        break;
                    case TEACHER:
                        Teacher teacher = (Teacher)user;
                        teacherMenu(teacher, clientSocket);
                        break;
                    case ADMIN:
                        Admin admin = (Admin)user;
                        adminMenu(admin, clientSocket);
                        break;
                }
                return;
            }
        }

        printOutStream.println("Invalid username or password!");
        printOutStream.println("Please, try again.");
    }

    public void adminMenu(Admin admin, Socket clientSocket) throws IOException
    {
        printOutStream.println("Welcome " + admin.getUsername() + "!");
        printOutStream.println("Creating new user...");
        Scanner scanner = new Scanner(clientSocket.getInputStream());
        printOutStream.println("Please, enter user type (1 - Student, 2 - Teacjer)");
        int userType = scanner.nextInt();
        switch(userType)
        {
            case 1:
                if(createStudentUser(scanner))
                {
                    printOutStream.println("Student created successfully!");
                }
                else
                {
                    printOutStream.println("Studen creation failed! Please, try again.");
                    createStudentUser(scanner);
                }
                break;
            case 2:
                if(createTeacherUser(scanner))
                {
                    printOutStream.println("Teacher created successfully!");
                }
                else
                {
                    printOutStream.println("Teacher creation failed! Please, try again.");
                    createTeacherUser(scanner);
                }
                break;
        }

        printOutStream.println("Thank you and goodbey " + admin.getUsername() + "!");
    }

    private boolean createStudentUser(Scanner scanner)
    {
        printOutStream.println("Enter Username: ");
        String studentUsername = scanner.nextLine();
        printOutStream.println("Enter Password: ");
        String studentPassword = scanner.nextLine();
        printOutStream.println("Enter Faculty Number: ");
        String studentFacultyNumber = scanner.nextLine();
        printOutStream.println("Enter EGN: ");
        String studentEGN = scanner.nextLine();
        if(validateStudentCreation(studentFacultyNumber, studentEGN))
        {
            Student student = new Studnet(studentUsername, studentPassword, studentFacultyNumber, studentEGN);
            users.add(student);
            return true;
        }
        
        return false;
    }

    private boolean validateStudentCreation(String facultyNumber, String egn)
    {
        String facultyNUmberRegex = "[0-9]{9}";
        String egnRegex = "[0-9]{10}";
        if(!facultyNumber.matches(facultyNUmberRegex))
        {
            printOutStream.println("Faculty Number must be 9 digits long!");
            return false;
        }
        if(!eng.matches(egnRegex))
        {
            printOutStream.println("EGN must be 10 digits long!");
            return false;
        }

        return true;
    }

    private boolean createTeacherUser(Scanner scanner)
    {
        printOutStream.println("Enter Username: ");
        String teacherUsername = scanner.nextLine();
        printOutStream.println("Enter Password: ");
        String teacherPassword = scanner.nextLine();
        printOutStream.println("Enter Email: ");
        String teachetEmail = scanner.nextLine();
        if(validateTeacherCreation(teaxherPassword, teachetEmail))
        {
            Teacher teacher = new Teacher(teacherUsername, teacherPassword, teachetEmail);
            users.add(teacher);
            return true;
        }

        return false;
    }

    boolean validateTeacherCreation(String teacherPassword, String email)
    {
        String emailRegex = "[a-z]+@tu-sofia\\.bg";
        String passwordRegex = "\\S{5,}";
        if(!teacherPassword.matches(passwordRegex))
        {
            printOutStream.println("Password must be at least 5 characters long!");
            return false;
        }
        if(!email.matches(emailRegex))
        {
            printOutStream.println("Invalid email!");
            return false;
        }

        return true;
    }

    private void teacherMenu(Teacher teacher, Socket clienSocket) throws IOException
    {
        printOutStream.println("Welcome " + teacher.getUsername() + "!");
        Scanner scanner = new Scanner(clientSocket.getInputStream());
        printOutStream.println("Adding new grade...");
        if(addStudentGrade(teacher, scanner))
        {
            printOutStream.println("Grade added successfully!");
        }
        else
        {
            printOutStream.println("Grade addition failed! Please, try again.");
            addStudentGrade(teacher, scanner);
        }

        printOutStream.println("Thank you and goodbye " + teacher.getUsername() + "!");
    }

    private boolean addStudentGrade(Teacher teacher, Scanner scanner)
    {
        printOutStream.println("Enter Faculty Number: ");
        String facultyNumber = scanner.nextLine();
        printOutStream.println("Enter grade: ");
        double grade = scanner.nextDouble();
        scanner.nextLine();
        printOutStream.println("Enter Subject: ");
        String subject = scanner.nextLine();
        printOutStream.println("Enter Semester Number: ");
        int semesterNumber = scanner.nextInt();
        scannet.nextLine();
        Student student = findStudentByFacultyNumber(facultyNumber);
        if(student == null)
        {
            printOutStream.println("Student not found!");
            return false;
        }
        else
        {
            Grade newGrade = new Grade(subject, semesterNumber, grade);
            teacher.addGrade(student, newGrade);
            return true;
        }
    }

    private Student findStudentByFacultyNumber(String facultyNumber)
    {
        for(User user : users)
        {
            if(user.getUserType() == UserType.STUDENT)
            {
                Student student = (Student)user;
                if(student.getFacultyNUmber().equals(facultyNumber))
                {
                    return student;
                }
            }
        }
        return null;
    }

    private void studentMenu(Student student)
    {
        printOutStream.println("Welcome " + student.getUsername() + "!");
        String grades = getStudentGrades(student);
        printOutStream.println(grades);
        printOutStream.println("thank you and goodbye " + student.getUsername() + "!");
    }

    private String getStudentGrades(Student student)
    {
        printOutStream.println("Your grades are: ");
        ArrayList<Grade> studentGrades = student.getStudentGrades();
        studentGrades.sort((gradeOne, gradeTwo) -> {
            if(gradeOne.getSemesterNUmber() == gradeTwo.getSemesterNUmber())
            {
                return gradeOne.getSubject().compareTo(gradeTwo.getSubject());
            }
            else
            {
                return gradeOne.getSemesterNUmber() - gradeTwo.getSemesterNUmber();
            }
        });

        StringBuilder grades = new StringBuilder();
        for(Grade grade : studentGrades)
        {
            grades.append(grade.getSubject().append(" ").append(grade.getSemesterNUmber()).
                append(" ").append(grade.getStudentGrade()).append("\n"));
        }
        return grades.toString();
    }

    private void createAdminUser()
    {
        Admin admin = new Admin("admin", "admin");
        users.add(admin);
    }
}
