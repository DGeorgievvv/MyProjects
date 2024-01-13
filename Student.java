public class Student extends User
{
    private ArrayList<Grade> studentGrades;
    private string facultyNUmber;
    private String egn;
    
    public Student(String username, String password, String facultyNumber, String egn)
    {
        super(username, password);
        studentGrades = new ArrayList<Grade>();
        this.facultyNumber = facultyNumber;
        this.egn = egn;
    }

    @Override
    public UserType getUserType()
    {
        return UserType.STUDENT;
    }

    public String getFacultyNUmber()
    {
        return this.facultyNUmber;
    }

    public String getEgn()
    {
        return this.egn;
    }

    public ArrayList<Grade> getStudentGrades()
    {
        synchronized(studentGrades)
        {
            return studentGrades;
        }
    }

    @Override
    public String ToString()
    {
        return "Student{Username: " + getUsername() + " Student Grades: " + getStudentGrades() + 
            " Faculty Number: " + getFacultyNUmber() + " EGN: " + getEgn() + "}";
    }
}
