import java.util.ArrayList;

public class Teacher extends Thread {
    private int id;
    private String name;
    private ArrayList<Student> students = new ArrayList<>();

    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void run() {

    }

    public void addStudent(Student student) {
        students.add(student);
    }
}
