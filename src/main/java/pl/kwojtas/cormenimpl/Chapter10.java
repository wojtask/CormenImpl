package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Deque;
import pl.kwojtas.cormenimpl.util.DoubleStack;
import pl.kwojtas.cormenimpl.util.Queue;
import pl.kwojtas.cormenimpl.util.Stack;

public class Chapter10 {

    private Chapter10() { }

    // subchapter 10.1
    public <T> boolean stackEmpty(Stack<T> S) {
        return S.top == 0;
    }

    // subchapter 10.1
    public <T> void push(Stack<T> S, T x) {
        S.top++;
        S.set(S.top, x);
    }

    // subchapter 10.1
    public <T> T pop(Stack<T> S) {
        if (stackEmpty(S)) {
            throw new RuntimeException("underflow");
        }
        S.top--;
        return S.at(S.top + 1);
    }

    // subchapter 10.1
    public <T> void enqueue(Queue<T> Q, T x) {
        Q.set(Q.tail, x);
        if (Q.tail == Q.length) {
            Q.tail = 1;
        } else {
            Q.tail++;
        }
    }

    // subchapter 10.1
    public <T> T dequeue(Queue<T> Q) {
        T x = Q.at(Q.head);
        if (Q.head == Q.length) {
            Q.head = 1;
        } else {
            Q.head++;
        }
        return x;
    }

    // solution of 10.1-2
    public <T> boolean firstStackEmpty(DoubleStack<T> A) {
        return A.top1 == 0;
    }

    // solution of 10.1-2
    public <T> void firstStackPush(DoubleStack<T> A, T x) {
        A.top1++;
        A.set(A.top1, x);
    }

    // solution of 10.1-2
    public <T> T firstStackPop(DoubleStack<T> A) {
        if (firstStackEmpty(A)) {
            throw new RuntimeException("underflow");
        }
        A.top1--;
        return A.at(A.top1 + 1);
    }

    // solution of 10.1-2
    public <T> boolean secondStackEmpty(DoubleStack<T> A) {
        return A.top1 == A.length + 1;
    }

    // solution of 10.1-2
    public <T> void secondStackPush(DoubleStack<T> A, T x) {
        A.top2--;
        A.set(A.top2, x);
    }

    // solution of 10.1-2
    public <T> T secondStackPop(DoubleStack<T> A) {
        if (secondStackEmpty(A)) {
            throw new RuntimeException("underflow");
        }
        A.top2++;
        return A.at(A.top2 - 1);
    }

    // solution of 10.1-4
    public <T> boolean queueEmpty(Queue<T> Q) {
        return Q.head == Q.tail;
    }

    // solution of 10.1-4
    public <T> void enqueue_(Queue<T> Q, T x) {
        if (Q.head == Q.tail + 1) {
            throw new RuntimeException("overflow");
        }
        if (Q.head == 1 && Q.tail == Q.length) {
            throw new RuntimeException("overflow");
        }
        Q.set(Q.tail, x);
        if (Q.tail == Q.length) {
            Q.tail = 1;
        } else {
            Q.tail++;
        }
    }

    // solution of 10.1-4
    public <T> T dequeue_(Queue<T> Q) {
        if (queueEmpty(Q)) {
            throw new RuntimeException("underflow");
        }
        T x = Q.at(Q.head);
        if (Q.head == Q.length) {
            Q.head = 1;
        } else {
            Q.head++;
        }
        return x;
    }

    // solution of 10.1-5
    public <T> void headEnqueue(Deque<T> D, T x) {
        if (D.head == 1) {
            D.head = D.length;
        } else {
            D.head--;
        }
        D.set(D.head, x);
    }

    // solution of 10.1-5
    public <T> T headDequeue(Deque<T> D) {
        T x = D.at(D.head);
        if (D.head == D.length) {
            D.head = 1;
        } else {
            D.head++;
        }
        return x;
    }

    // solution of 10.1-5
    public <T> void tailEnqueue(Deque<T> D, T x) {
        D.set(D.tail, x);
        if (D.tail == D.length) {
            D.tail = 1;
        } else {
            D.tail++;
        }
    }

    // solution of 10.1-5
    public <T> T tailDequeue(Deque<T> D) {
        if (D.tail == 1) {
            D.tail = D.length;
        } else {
            D.tail--;
        }
        return D.at(D.tail);
    }

    // solution of 10.1-6
    public <T> void enqueueOnStacks(Stack<T> S1, Stack<T> S2, T x) {
        push(S1, x);
    }

    // solution of 10.1-6
    public <T> T dequeueOnStacks(Stack<T> S1, Stack<T> S2) {
        if (stackEmpty(S1)) {
            throw new RuntimeException("underflow");
        }
        while (!stackEmpty(S1)) {
            push(S2, pop(S1));
        }
        T x = pop(S2);
        while (!stackEmpty(S2)) {
            push(S1, pop(S2));
        }
        return x;
    }

    // solution of 10.1-7
    public <T> void pushOnQueues(Queue<T> Q1, Queue<T> Q2, T x) {
        enqueue(Q1, x);
    }

    // solution of 10.1-7
    public <T> T popOnQueues(Queue<T> Q1, Queue<T> Q2) {
        if (queueEmpty(Q1)) {
            throw new RuntimeException("underflow");
        }
        T x = null;
        while (!queueEmpty(Q1)) {
            x = dequeue(Q1);
            if (!queueEmpty(Q1)) {
                enqueue(Q2, x);
            }
        }
        while (!queueEmpty(Q2)) {
            enqueue(Q1, dequeue(Q2));
        }
        return x;
    }

}
