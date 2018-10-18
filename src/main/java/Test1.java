import java.util.Objects;

public class Test1 {
    private int num;
    private String date;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Test1 test1 = (Test1) obj;
        return this.num == test1.num && (this.date == test1.date||(this.date!=null && this.date.equals(test1)));
    }

     @Override
    public int hashCode() {
        return Objects.hash(num, date);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
