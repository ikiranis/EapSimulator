public class Exam implements Work {
    private int number;
    private int grade;

    public Exam(int number) {
        this.number = number;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getNumber() {
        return number;
    }

    public int getGrade() {
        return grade;
    }
}
