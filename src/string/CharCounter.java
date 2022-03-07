package string;

public class CharCounter {
    char ch;
    int count;

    public CharCounter(char ch, int count) {
        this.ch = ch;
        this.count = count;
    }

    @Override
    public String toString() {
        return "CharCounter{" +
                "ch=" + ch +
                ", count=" + count +
                '}';
    }
}
