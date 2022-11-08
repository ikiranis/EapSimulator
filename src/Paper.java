public class Paper implements Work {
    private int number;
    private int grade;

    public Paper(int number) {
        this.number = number;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "ΓΕ" + number;
    }
}
