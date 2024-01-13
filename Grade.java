public class Grade 
{
    private String subject;
    private int semesterNumber;
    private double studentGrade;

    public String getSubject()
    {
        return this.subject;
    }
    public int getSemesterNUmber()
    {
        return this.semesterNumber;
    }
    public double getStudentGrade()
    {
        return this.studentGrade;
    }

    @Override
    public String ToString()
    {
        return "Grade{Subject: " + getSubject() + " Semester Number: " + getSemesterNUmber() + 
        " Grade: " + getStudentGrade() + "}";
    }
}
