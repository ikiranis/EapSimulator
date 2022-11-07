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
        if(work instanceof Paper) {
            System.out.println("Ο " + this + " βαθμολογεί την ΓΕ" + work.getNumber() + " του " + student);
        } else {
            System.out.println("Ο " + this + " βαθμολογεί τις εξετάσεις " + work.getNumber() + " του " + student);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        work.setGrade(random.nextInt(11 - 1) + 1);
        if(work instanceof Paper) {
            System.out.println("Ο " + this + " βαθμολόγησε την ΓΕ" + work.getNumber() + " (" + work.getGrade() + ") του " + student);
        } else {
            System.out.println("Ο " + this + " βαθμολόγησε τις εξετάσεις " + work.getNumber() + " (" + work.getGrade() + ") του " + student);
        }
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    @Override
    public String toString() {
        return "Teacher" + id;
    }
}
