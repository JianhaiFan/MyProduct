package com.xiaofan.product.pool;


public abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {

    private int priority;

    public PriorityRunnable(int priority) {
        if (priority  < 0)
        throw new IllegalArgumentException();
        this.priority = priority;
    }

    @Override
    public int compareTo(PriorityRunnable another) {
        int my = this.getPriority();
        int other = another.getPriority();

        if (my > other) {
            return -1;
        }else if(my == other) {
            return 0;
        }else {
            return 1;
        }
    }


    @Override
    public void run() {
        doSth();
    }

    public abstract void doSth();

    public int getPriority() {
        return priority;
    }
}