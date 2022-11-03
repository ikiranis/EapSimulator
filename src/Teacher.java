import java.util.ArrayList;

public class Teacher extends Thread {
    private int id;
    private ArrayList<Student> students = new ArrayList<>();

    public Teacher(int id) {
        this.id = id;
    }

    @Override
    public void run() {

    }

    public void addStudent(Student student) {
        students.add(student);
    }
}
