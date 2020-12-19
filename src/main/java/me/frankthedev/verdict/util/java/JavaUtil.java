package me.frankthedev.verdict.util.java;

import java.util.Deque;
import java.util.Queue;

public class JavaUtil {

    public static <T> Queue<T> trim(Queue<T> queue, int newSize) {
        for (int i = queue.size() - 1; i >= newSize; i--) {
            queue.poll();
        }

        return queue;
    }

    public static <T> Deque<T> trim(Deque<T> deque, int newSize) {
        for (int i = deque.size() - 1; i >= newSize; i--) {
            deque.poll();
        }

        return deque;
    }
}
