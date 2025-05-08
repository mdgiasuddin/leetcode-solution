package linkedlist;

import java.util.ArrayList;
import java.util.List;

// Leetcode problem: 1472

/**
 * Design Browser History.
 * */
public class BrowserHistory {
    List<String> history;
    int currentIndex;
    int len;

    public BrowserHistory(String homepage) {
        history = new ArrayList<>();
        history.add(homepage);
        currentIndex = 0;
        len = 1;
    }

    public void visit(String url) {
        if (currentIndex == history.size() - 1) {
            history.add(url);
            currentIndex += 1;
            len = history.size();
            return;
        }

        history.set(currentIndex + 1, url);
        currentIndex += 1;
        len = currentIndex + 1;
    }

    public String back(int steps) {
        currentIndex = Math.max(currentIndex - steps, 0);
        return history.get(currentIndex);
    }

    public String forward(int steps) {
        currentIndex = Math.min(currentIndex + steps, len - 1);
        return history.get(currentIndex);
    }
}
