import java.util.ArrayList;
import java.util.Random;

public class Teacher extends Thread {
    private int id;
    private Random random = new Random();

    public Teacher(int id) {
        this.id = id;
    }

    @Override
    public void run() {

    }

    public synchronized void setGrade(Work work) {
        System.out.println("Ο καθηγητής " + this + " βαθμολογεί την εργασία " + work.getNumber());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        work.setGrade(random.nextInt(11 + 1) + 1);
        System.out.println("Ο καθηγητής " + this + " βαθμολόγησε την εργασία " + work.getNumber() + " με βαθμό " + work.getGrade());
    }

    @Override
    public String toString() {
        return "Teacher" + id;
    }
}
