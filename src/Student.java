public class Student extends Thread {
    private int id;
    private Teacher teacher;

    public Student(int id, Teacher teacher) {
        this.id = id;
        this.teacher = teacher;
    }

    @Override
    public void run() {

    }

    @Override
    public String toString() {

        return "Student" + id + " with " + teacher;
    }
}
