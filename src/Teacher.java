import java.util.ArrayList;

public class Teacher extends Thread {
    private int id;

    public Teacher(int id) {
        this.id = id;
    }

    @Override
    public void run() {

    }

    @Override
    public String toString() {
        return "Teacher" + id;
    }
}
