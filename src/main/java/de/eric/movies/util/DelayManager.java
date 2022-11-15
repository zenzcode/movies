package de.eric.movies.util;

import javafx.concurrent.Task;

public class DelayManager {
    public static Thread delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        Thread thread = new Thread(sleeper);
        thread.start();
        return thread;
    }
}
