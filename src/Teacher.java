import java.util.ArrayList;
import java.util.Random;

public class Teacher extends Thread {
    private int id;
    private ArrayList<Student> students = new ArrayList<>();

    private Random random = new Random();

    public Teacher(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for(Student student : students) {
            student.start();
        }

        for (Student student : students) {
            try {
                student.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void setGrade(Work work, Student student) {
        System.out.println("Ο " + this + " βαθμολογεί "
                + ((work instanceof Paper) ? "την " : "τις ")
                + work + " του " + student);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        work.setGrade(random.nextInt(11 - 1) + 1);

        System.out.println("Ο " + this + " βαθμολόγησε "
                + ((work instanceof Paper) ? "την " : "τις ") + work
                + " (" + work.getGrade() + ") του " + student);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    @Override
    public String toString() {
        return "Teacher" + id;
    }
}
