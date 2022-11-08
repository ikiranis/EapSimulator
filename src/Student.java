public class Student extends Thread {
    private int id;
    private Teacher teacher;
    private Paper[] papers = new Paper[4];
    private Exam[] exams = new Exam[2];

    public Student(int id, Teacher teacher) {
        this.id = id;
        this.teacher = teacher;
    }

    @Override
    public void run() {
        writePapers();

        int papersSum = calcPapersSumGrade();

        if(papersSum >= 20) {
            writeExams();
        } else {
            setNullExams();
            System.out.println("Ο " + this + " δεν μπορεί να συμμετάσχει στις εξετάσεις, γιατί έχει συνολικό βαθμό εργασιών " + papersSum);
        }
    }

    private void setNullExams() {
        for(int i=0; i<2; i++) {
            exams[i] = new Exam(i + 1);
            exams[i].setGrade(0);
        }
    }

    private void writePapers() {
        for(int i=0; i<4; i++) {
            papers[i] = new Paper(i+1);
            System.out.println("Ο " + this + " παρέδωσε την " + papers[i]);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            teacher.setGrade(papers[i], this);

        }
    }

    private void writeExams() {
        for(int i=0; i<2; i++) {
            exams[i] = new Exam(i+1);

            if (i == 1) {
                if (exams[0].getGrade() >= 5) {
                    exams[1].setGrade(0);
                    continue;
                }
            }

            System.out.println("Ο " + this + " συμμετείχε στις " + exams[i]);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            gradeExams(Main.getGradeKind(), exams[i]);
        }
    }

    private void gradeExams(boolean gradeKind, Exam exam) {
        if (gradeKind) {
            teacher.setGrade(exam, this);
        } else {
            int sum = 0;

            for (Teacher teacher : Main.getTeachers()) {
                sum += teacher.getGrade(this, exam);
            }

            int averageGrade = sum / Main.getTeachers().size();
            System.out.println("Οι καθηγητές βαθμολόγησαν τον " + this + " στις " + exam + " με βαθμό: " + averageGrade);

            exam.setGrade(averageGrade);
        }
    }

    private int calcPapersSumGrade() {
        int sum = 0;

        for (Paper paper : papers) {
            sum += paper.getGrade();
        }

        return sum;
    }

    private int getFinalGrade() {
        if (exams[1].getGrade() != 0) {
            if (exams[1].getGrade() > exams[0].getGrade()) {
                return exams[1].getGrade();
            }

            return exams[0].getGrade();
        }

        return exams[0].getGrade();
    }

    public String getFinalGradeText() {
        if (exams[0].getGrade() == 0) {
            return "Ο " + this + " δεν μπόρεσε να συμμετάσχει στις εξετάσεις, γιατί είχε συνολικό βαθμό εργασιών " + calcPapersSumGrade();
        }

        return "Η τελική βαθμολογία του " + this + " είναι: " + getFinalGrade();
    }

    @Override
    public String toString() {
        return "Student" + id;
    }
}
