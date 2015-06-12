package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.BinaryTree;
import pl.kwojtas.cormenimpl.util.CircularList;
import pl.kwojtas.cormenimpl.util.Deque;
import pl.kwojtas.cormenimpl.util.DoubleStack;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.ListWithSentinel;
import pl.kwojtas.cormenimpl.util.MultiaryTree;
import pl.kwojtas.cormenimpl.util.MultipleArrayList;
import pl.kwojtas.cormenimpl.util.Queue;
import pl.kwojtas.cormenimpl.util.SingleArrayList;
import pl.kwojtas.cormenimpl.util.SinglyLinkedList;
import pl.kwojtas.cormenimpl.util.SinglyLinkedListWithTail;
import pl.kwojtas.cormenimpl.util.Stack;
import pl.kwojtas.cormenimpl.util.XorLinkedList;

import static pl.kwojtas.cormenimpl.Chapter5.random;
import static pl.kwojtas.cormenimpl.Chapter6.mergeSortedLists;
import static pl.kwojtas.cormenimpl.util.Util.greater;
import static pl.kwojtas.cormenimpl.util.Util.leq;
import static pl.kwojtas.cormenimpl.util.Util.less;

/**
 * Implements algorithms and data structures from Chapter 10.
 */
public final class Chapter10 {

    private Chapter10() { }

    /**
     * Checks if a stack is empty.
     * <p><span style="font-variant:small-caps;">Stack-Empty</span> from subchapter 10.1.</p>
     *
     * @param S the stack
     * @param <T> the type of elements in {@code S}
     * @return {@code true} if {@code S} is empty, or {@code false} otherwise
     */
    public static <T> boolean stackEmpty(Stack<T> S) {
        return S.top == 0;
    }

    /**
     * Inserts an element on the top of a stack.
     * <p><span style="font-variant:small-caps;">Push</span> from subchapter 10.1.</p>
     *
     * @param S the stack
     * @param x the element to insert
     * @param <T> the type of elements in {@code S}
     */
    public static <T> void push(Stack<T> S, T x) {
        S.top++;
        S.set(S.top, x);
    }

    /**
     * Deletes the element from the top of a stack.
     * <p><span style="font-variant:small-caps;">Pop</span> from subchapter 10.1.</p>
     *
     * @param S the stack
     * @param <T> the type of elements in {@code S}
     * @return the element deleted from the top of {@code S}
     * @throws RuntimeException if {@code S} is empty
     */
    public static <T> T pop(Stack<T> S) {
        if (stackEmpty(S)) {
            throw new RuntimeException("underflow");
        }
        S.top--;
        return S.at(S.top + 1);
    }

    /**
     * Inserts an element at the tail of a queue.
     * <p><span style="font-variant:small-caps;">Enqueue</span> from subchapter 10.1 and solution to exercise 10.1-4.</p>
     *
     * @param Q the queue
     * @param x the element to insert
     * @param <T> the type of elements in {@code Q}
     * @throws RuntimeException if {@code Q} is full
     */
    public static <T> void enqueue(Queue<T> Q, T x) {
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

    /**
     * Deletes the element from the head of a queue.
     * <p><span style="font-variant:small-caps;">Dequeue</span> from subchapter 10.1 and solution to exercise 10.1-4.</p>
     *
     * @param Q the queue
     * @param <T> the type of elements in {@code Q}
     * @return the element deleted from the head of {@code Q}
     * @throws RuntimeException if {@code Q} is empty
     */
    public static <T> T dequeue(Queue<T> Q) {
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

    /**
     * Inserts an element on the top of the first stack in a "double stack in a single array" implementation.
     * <p>Solution to exercise 10.1-2.</p>
     *
     * @param A the array containing two stacks
     * @param x the element to insert
     * @param <T> the type of elements in {@code A}
     */
    public static <T> void firstStackPush(DoubleStack<T> A, T x) {
        A.top1++;
        A.set(A.top1, x);
    }

    /**
     * Deletes the element from the top of the first stack in a "double stack in a single array" implementation.
     * <p>Solution to exercise 10.1-2.</p>
     *
     * @param A the array containing two stacks
     * @param <T> the type of elements in {@code A}
     * @return the element deleted from the top of the first stack
     * @throws RuntimeException if the first stack is empty
     */
    public static <T> T firstStackPop(DoubleStack<T> A) {
        if (firstStackEmpty(A)) {
            throw new RuntimeException("underflow");
        }
        A.top1--;
        return A.at(A.top1 + 1);
    }

    /**
     * Checks if the first stack is empty in a "double stack in a single array" implementation.
     * <p>Solution to exercise 10.1-2.</p>
     *
     * @param A the array containing two stacks
     * @param <T> the type of elements in {@code A}
     * @return {@code true} if the first stack is empty, or {@code false} otherwise
     */
    public static <T> boolean firstStackEmpty(DoubleStack<T> A) {
        return A.top1 == 0;
    }

    /**
     * Inserts an element on the top of the second stack in a "double stack in a single array" implementation.
     * <p>Solution to exercise 10.1-2.</p>
     *
     * @param A the array containing two stacks
     * @param x the element to insert
     * @param <T> the type of elements in {@code A}
     */
    public static <T> void secondStackPush(DoubleStack<T> A, T x) {
        A.top2--;
        A.set(A.top2, x);
    }

    /**
     * Deletes the element from the top of the second stack in a "double stack in a single array" implementation.
     * <p>Solution to exercise 10.1-2.</p>
     *
     * @param A the array containing two stacks
     * @param <T> the type of elements in {@code A}
     * @return the deleted element from the top of the second stack
     * @throws RuntimeException if the second stack is empty
     */
    public static <T> T secondStackPop(DoubleStack<T> A) {
        if (secondStackEmpty(A)) {
            throw new RuntimeException("underflow");
        }
        A.top2++;
        return A.at(A.top2 - 1);
    }

    /**
     * Checks if the second stack is empty in a "double stack in a single array" implementation.
     * <p>Solution to exercise 10.1-2.</p>
     *
     * @param A the array containing two stacks
     * @param <T> the type of elements in {@code A}
     * @return {@code true} if the second stack is empty, or {@code false} otherwise
     */
    public static <T> boolean secondStackEmpty(DoubleStack<T> A) {
        return A.top2 == A.length + 1;
    }

    /**
     * Checks if a queue is empty.
     * <p><span style="font-variant:small-caps;">Queue-Empty</span> from solution to exercise 10.1-4.</p>
     *
     * @param Q the queue
     * @param <T> the type of elements in {@code Q}
     * @return {@code true} if {@code Q} is empty, or {@code false} otherwise
     */
    public static <T> boolean queueEmpty(Queue<T> Q) {
        return Q.head == Q.tail;
    }

    /**
     * Inserts an element at the head of a deque.
     * <p><span style="font-variant:small-caps;">Head-Enqueue</span> from solution to exercise 10.1-5.</p>
     *
     * @param D the deque
     * @param x the element to insert
     * @param <T> the type of elements in {@code D}
     */
    public static <T> void headEnqueue(Deque<T> D, T x) {
        if (D.head == 1) {
            D.head = D.length;
        } else {
            D.head--;
        }
        D.set(D.head, x);
    }

    /**
     * Deletes the element from the head of a deque.
     * <p><span style="font-variant:small-caps;">Head-Dequeue</span> from solution to exercise 10.1-5.</p>
     *
     * @param D the deque
     * @param <T> the type of elements in {@code D}
     * @return the element removed from the head of {@code D}
     */
    public static <T> T headDequeue(Deque<T> D) {
        T x = D.at(D.head);
        if (D.head == D.length) {
            D.head = 1;
        } else {
            D.head++;
        }
        return x;
    }

    /**
     * Inserts an element at the tail of a deque.
     * <p><span style="font-variant:small-caps;">Tail-Enqueue</span> from solution to exercise 10.1-5.</p>
     *
     * @param D the deque
     * @param x the element to insert
     * @param <T> the type of elements in {@code D}
     */
    public static <T> void tailEnqueue(Deque<T> D, T x) {
        D.set(D.tail, x);
        if (D.tail == D.length) {
            D.tail = 1;
        } else {
            D.tail++;
        }
    }

    /**
     * Deletes the element from the tail of a deque.
     * <p><span style="font-variant:small-caps;">Tail-Dequeue</span> from solution to exercise 10.1-5.</p>
     *
     * @param D the deque
     * @param <T> the type of elements in {@code D}
     * @return the element deleted from the tail of {@code D}
     */
    public static <T> T tailDequeue(Deque<T> D) {
        if (D.tail == 1) {
            D.tail = D.length;
        } else {
            D.tail--;
        }
        return D.at(D.tail);
    }

    /**
     * Implements the queue operation <span style="font-variant:small-caps;">Enqueue</span> using two stacks.
     * <p>Solution to exercise 10.1-6.</p>
     *
     * @param S1 the first stack
     * @param S2 the second stack
     * @param x the element to insert to the queue
     * @param <T> the type of elements in the queue
     */
    public static <T> void enqueueOnStacks(Stack<T> S1, @SuppressWarnings("unused") Stack<T> S2, T x) {
        push(S1, x);
    }

    /**
     * Implements the queue operation <span style="font-variant:small-caps;">Dequeue</span> using two stacks.
     * <p>Solution to exercise 10.1-6.</p>
     *
     * @param S1 the first stack
     * @param S2 the second stack
     * @param <T> the type of elements in {@code S1} and {@code S2}
     * @return the element deleted from the queue
     * @throws RuntimeException if the queue is empty
     */
    public static <T> T dequeueOnStacks(Stack<T> S1, Stack<T> S2) {
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

    /**
     * Implements the stack operation <span style="font-variant:small-caps;">Push</span> using two queues.
     * <p>Solution to exercise 10.1-7.</p>
     *
     * @param Q1 the first queue
     * @param Q2 the second queue
     * @param x the element to insert
     * @param <T> the type of elements on the stack
     */
    public static <T> void pushOnQueues(Queue<T> Q1, @SuppressWarnings("unused") Queue<T> Q2, T x) {
        enqueue(Q1, x);
    }

    /**
     * Implements the stack operation <span style="font-variant:small-caps;">Pop</span> using two queues.
     * <p>Solution to exercise 10.1-7.</p>
     *
     * @param Q1 the first queue
     * @param Q2 the second queue
     * @param <T> the type of elements on the stack
     * @return the element deleted from the stack
     * @throws RuntimeException if the stack is empty
     */
    public static <T> T popOnQueues(Queue<T> Q1, Queue<T> Q2) {
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

    /**
     * Finds an element in a doubly linked list.
     * <p><span style="font-variant:small-caps;">List-Search</span> from subchapter 10.2.</p>
     *
     * @param L the doubly linked list
     * @param k the key of the element to find
     * @param <T> the type of elements in {@code L}
     * @return the element of key {@code k} in list {@code L}, or {@code null} if {@code L} does not contain such element
     */
    public static <T> List.Node<T> listSearch(List<T> L, T k) {
        List.Node<T> x = L.head;
        while (x != null && !x.key.equals(k)) {
            x = x.next;
        }
        return x;
    }

    /**
     * Inserts an element into a doubly linked list.
     * <p><span style="font-variant:small-caps;">List-Insert</span> from subchapter 10.2.</p>
     *
     * @param L the doubly linked list
     * @param x the element to insert
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void listInsert(List<T> L, List.Node<T> x) {
        x.next = L.head;
        if (L.head != null) {
            L.head.prev = x;
        }
        L.head = x;
        x.prev = null;
    }

    /**
     * Deletes an element from a doubly linked list.
     * <p><span style="font-variant:small-caps;">List-Delete</span> from subchapter 10.2.</p>
     *
     * @param L the doubly linked list
     * @param x the element in {@code L} to delete
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void listDelete(List<T> L, List.Node<T> x) {
        if (x.prev != null) {
            x.prev.next = x.next;
        } else {
            L.head = x.next;
        }
        if (x.next != null) {
            x.next.prev = x.prev;
        }
    }

    /**
     * Deletes an element from a doubly linked list with a sentinel.
     * <p><span style="font-variant:small-caps;">List-Delete'</span> from subchapter 10.2.</p>
     *
     * @param L the doubly linked list with a sentinel
     * @param x the element in {@code L} to delete
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void listDelete_(@SuppressWarnings("unused") ListWithSentinel<T> L, ListWithSentinel.Node<T> x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
    }

    /**
     * Searches for an element in a doubly linked list with a sentinel.
     * <p><span style="font-variant:small-caps;">List-Search'</span> from subchapter 10.2.</p>
     *
     * @param L the doubly linked list with a sentinel
     * @param k the key of the element to find
     * @param <T> the type of elements in {@code L}
     * @return the element with key {@code k} in {@code L}, or {@code null} if {@code L} does not contain such element
     */
    public static <T> ListWithSentinel.Node<T> listSearch_(ListWithSentinel<T> L, T k) {
        ListWithSentinel.Node<T> x = L.nil.next;
        while (x != L.nil && !x.key.equals(k)) {
            x = x.next;
        }
        return x;
    }

    /**
     * Inserts an element at the head of a doubly linked list with a sentinel.
     * <p><span style="font-variant:small-caps;">List-Insert'</span> from subchapter 10.2.</p>
     *
     * @param L the doubly linked list with a sentinel
     * @param x the element to insert
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void listInsert_(ListWithSentinel<T> L, ListWithSentinel.Node<T> x) {
        x.next = L.nil.next;
        L.nil.next.prev = x;
        L.nil.next = x;
        x.prev = L.nil;
    }

    /**
     * Inserts an element at the head of a singly linked list.
     * <p><span style="font-variant:small-caps;">Singly-Linked-List-Insert</span> from solution to exercise 10.2-1.</p>
     *
     * @param L the singly linked list
     * @param x the element to insert
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void singlyLinkedListInsert(SinglyLinkedList<T> L, SinglyLinkedList.Node<T> x) {
        x.next = L.head;
        L.head = x;
    }

    /**
     * Deletes an element from a singly linked list.
     * <p><span style="font-variant:small-caps;">Singly-Linked-List-Delete</span> from solution to exercise 10.2-1.</p>
     *
     * @param L the singly linked list
     * @param x the element in {@code L} to delete
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void singlyLinkedListDelete(SinglyLinkedList<T> L, SinglyLinkedList.Node<T> x) {
        if (x == L.head) {
            L.head = L.head.next;
        } else {
            SinglyLinkedList.Node<T> y = L.head;
            while (y.next != x) {
                y = y.next;
            }
            y.next = x.next;
        }
    }

    /**
     * Implements the stack operation <span style="font-variant:small-caps;">Push</span> using a singly linked list.
     * <p><span style="font-variant:small-caps;">Singly-Linked-List-Push</span> from solution to exercise 10.2-2.</p>
     *
     * @param L the singly linked list
     * @param k the key of the element to insert onto the stack
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void singlyLinkedListPush(SinglyLinkedList<T> L, T k) {
        SinglyLinkedList.Node<T> x = new SinglyLinkedList.Node<>(k);
        singlyLinkedListInsert(L, x);
    }

    /**
     * Implements the stack operation <span style="font-variant:small-caps;">Pop</span> using a singly linked list.
     * <p><span style="font-variant:small-caps;">Singly-Linked-List-Pop</span> from solution to exercise 10.2-2.</p>
     *
     * @param L the singly linked list
     * @param <T> the type of elements in {@code L}
     * @return the element deleted from the stack
     */
    public static <T> T singlyLinkedListPop(SinglyLinkedList<T> L) {
        if (L.head == null) {
            throw new RuntimeException("underflow");
        }
        SinglyLinkedList.Node<T> x = L.head;
        L.head = x.next;
        return x.key;
    }

    /**
     * Implements the queue operation <span style="font-variant:small-caps;">Enqueue</span> using a singly linked list.
     * <p><span style="font-variant:small-caps;">Singly-Linked-List-Enqueue</span> from solution to exercise 10.2-3.</p>
     *
     * @param L the singly linked list
     * @param k the key of the element to insert into a queue
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void singlyLinkedListEnqueue(SinglyLinkedListWithTail<T> L, T k) {
        SinglyLinkedListWithTail.Node<T> x = new SinglyLinkedList.Node<>(k);
        if (L.tail != null) {
            L.tail.next = x;
        } else {
            L.head = x;
        }
        L.tail = x;
    }

    /**
     * Implements the queue operation <span style="font-variant:small-caps;">Dequeue</span> using a singly linked list.
     * <p><span style="font-variant:small-caps;">Singly-Linked-List-Dequeue</span> from solution to exercise 10.2-3.</p>
     *
     * @param L the singly linked list
     * @param <T> the type of elements in {@code L}
     * @return the element deleted from the queue
     * @throws RuntimeException if the queue is empty
     */
    public static <T> T singlyLinkedListDequeue(SinglyLinkedListWithTail<T> L) {
        if (L.head == null) {
            throw new RuntimeException("underflow");
        }
        SinglyLinkedListWithTail.Node<T> x = L.head;
        L.head = x.next;
        if (L.tail == x) {
            L.tail = null;
        }
        return x.key;
    }

    /**
     * Inserts an element at the head of a singly linked circular list.
     * <p><span style="font-variant:small-caps;">Circular-List-Insert</span> from solution to exercise 10.2-5.</p>
     *
     * @param L the singly linked circular list
     * @param x the element to insert
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void circularListInsert(CircularList<T> L, CircularList.Node<T> x) {
        if (L.head == null) {
            L.head = x.next = x;
        } else {
            x.next = L.head.next;
            L.head.next = x;
        }
    }

    /**
     * Deletes an element from a singly linked circular list.
     * <p><span style="font-variant:small-caps;">Circular-List-Delete</span> from solution to exercise 10.2-5.</p>
     *
     * @param L the singly linked circular list
     * @param x the element in {@code L} to delete
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void circularListDelete(CircularList<T> L, CircularList.Node<T> x) {
        CircularList.Node<T> y = L.head;
        while (y.next != x) {
            y = y.next;
        }
        y.next = x.next;
        if (L.head == x) {
            if (x.next == x) {
                L.head = null;
            } else {
                L.head = x.next;
            }
        }
    }

    /**
     * Searches for an element in a singly linked circular list.
     * <p><span style="font-variant:small-caps;">Circular-List-Search</span> from solution to exercise 10.2-5.</p>
     *
     * @param L the singly linked circular list
     * @param k the key of the element to find
     * @param <T> the type of elements in {@code L}
     * @return the element with key {@code k} in {@code L}, or {@code null} if {@code L} does not contain such element
     */
    public static <T> CircularList.Node<T> circularListSearch(CircularList<T> L, T k) {
        if (L.head == null) {
            return null;
        }
        if (L.head.key.equals(k)) {
            return L.head;
        }
        CircularList.Node<T> x = L.head.next;
        while (x != L.head) {
            if (x.key.equals(k)) {
                return x;
            }
            x = x.next;
        }
        return null;
    }

    /**
     * Produces the union of two disjoint sets using singly linked circular lists.
     * <p>Solution to exercise 10.2-6.</p>
     *
     * @param S1 the first set
     * @param S2 the second set, disjoint with {@code S1}
     * @param <T> the type of elements in {@code S1} and {@code S2}
     * @return the union of {@code S1} and {@code S2}
     */
    public static <T> CircularList<T> circularListsUnion(CircularList<T> S1, CircularList<T> S2) {
        CircularList<T> S = new CircularList<>();
        if (S1.head != null && S2.head != null) {
            CircularList.Node<T> x = S1.head.next;
            S1.head.next = S2.head.next;
            S2.head.next = x;
        }
        if (S1.head != null) {
            S.head = S1.head;
        } else {
            S.head = S2.head;
        }
        S1.head = S2.head = null;
        return S;
    }

    /**
     * Reverses the order of elements in a singly linked list.
     * <p><span style="font-variant:small-caps;">Singly-Linked-List-Reverse</span> from solution to exercise 10.2-7.</p>
     *
     * @param L the singly linked list
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void singlyLinkedListReverse(SinglyLinkedList<T> L) {
        SinglyLinkedList<T> L_ = new SinglyLinkedList<>();
        L_.head = null;
        while (L.head != null) {
            SinglyLinkedList.Node<T> x = L.head;
            singlyLinkedListDelete(L, L.head);
            singlyLinkedListInsert(L_, x);
        }
        L.head = L_.head;
    }

    /**
     * Finds an element in a XOR linked list.
     * <p><span style="font-variant:small-caps;">Xor-Linked-List-Search</span> from solution to exercise 10.2-8.</p>
     *
     * @param L the XOR linked list
     * @param k the key of the element to find
     * @param <T> the type of elements in {@code L}
     * @return the element of key {@code k} in list {@code L}, or {@code null} if {@code L} does not contain such element
     */
    public static <T> XorLinkedList.Node<T> xorLinkedListSearch(XorLinkedList<T> L, T k) {
        XorLinkedList.Node<T> x = L.head;
        XorLinkedList.Node<T> y = null;
        while (x != null && !x.key.equals(k)) {
            XorLinkedList.Node<T> z = L.byAddress(x.np ^ (y != null ? y.address : 0));
            y = x;
            x = z;
        }
        return x;
    }

    /**
     * Inserts an element at the head of a XOR linked list.
     * <p><span style="font-variant:small-caps;">Xor-Linked-List-Insert</span> from solution to exercise 10.2-8.</p>
     *
     * @param L the XOR linked list
     * @param x the element to insert
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void xorLinkedListInsert(XorLinkedList<T> L, XorLinkedList.Node<T> x) {
        x.np = L.head != null ? L.head.address : 0;
        if (L.head != null) {
            L.head.np ^= x.address;
        }
        L.head = x;
        if (L.tail == null) {
            L.tail = x;
        }
    }

    /**
     * Deletes an element from a XOR linked list.
     * <p><span style="font-variant:small-caps;">Xor-Linked-List-Delete</span> from solution to exercise 10.2-8.</p>
     *
     * @param L the XOR linked list
     * @param x the element in {@code L} to delete
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void xorLinkedListDelete(XorLinkedList<T> L, XorLinkedList.Node<T> x) {
        XorLinkedList.Node<T> x_ = L.head;
        XorLinkedList.Node<T> y = null;
        XorLinkedList.Node<T> z;
        while (x_ != x) {
            z = L.byAddress(x_.np ^ (y != null ? y.address : 0));
            y = x_;
            x_ = z;
        }
        z = L.byAddress(x.np ^ (y != null ? y.address : 0));
        if (y != null) {
            y.np ^= x.address ^ (z != null ? z.address : 0);
        }
        if (z != null) {
            z.np ^= x.address ^ (y != null ? y.address : 0);
        }
        if (x == L.head) {
            L.head = z;
        }
        if (x == L.tail) {
            L.tail = y;
        }
    }

    /**
     * Reverses a XOR linked list.
     * <p><span style="font-variant:small-caps;">Xor-Linked-List-Reverse</span> from solution to exercise 10.2-8.</p>
     *
     * @param L the XOR linked list
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void xorLinkedListReverse(XorLinkedList<T> L) {
        XorLinkedList.Node<T> x = L.head;
        L.head = L.tail;
        L.tail = x;
    }

    /**
     * Allocates an object in the multiple-array representation of a list.
     * <p><span style="font-variant:small-caps;">Allocate-Object</span> from subchapter 10.3.</p>
     *
     * @param L the list in the multiple-array representation
     * @param <T> the type of elements in {@code L}
     * @return the index of the allocated object in {@code L}
     * @throws RuntimeException if {@code L} is full
     */
    public static <T> int allocateObject(MultipleArrayList<T> L) {
        if (L.free == null) {
            throw new RuntimeException("out of space");
        }
        int x = L.free;
        L.free = L.next.at(x);
        return x;
    }

    /**
     * Frees an object in the multiple-array representation of a list.
     * <p><span style="font-variant:small-caps;">Free-Object</span> from subchapter 10.3.</p>
     *
     * @param L the list in a multiple-array representation
     * @param x the index of the object in {@code L} to free
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void freeObject(MultipleArrayList<T> L, int x) {
        L.next.set(x, L.free);
        L.free = x;
    }

    /**
     * Allocates an object in the single-array representation of a list.
     * <p><span style="font-variant:small-caps;">Single-Array-Allocate-Object</span> from solution to exercise 10.3-2.</p>
     *
     * @param A the list in the single-array representation
     * @return the index of the allocated object in {@code A}
     * @throws RuntimeException if {@code A} is full
     */
    public static int singleArrayAllocateObject(SingleArrayList A) {
        if (A.free == null) {
            throw new RuntimeException("out of space");
        }
        int i = A.free;
        A.free = A.at(i + 1);
        return i;
    }

    /**
     * Frees an object in the single-array representation of a list.
     * <p><span style="font-variant:small-caps;">Single-Array-Free-Object</span> from solution to exercise 10.3-2.</p>
     *
     * @param A the list in the single-array representation
     * @param i the index of the object in {@code A} to free
     */
    public static void singleArrayFreeObject(SingleArrayList A, int i) {
        A.set(i + 1, A.free);
        A.free = i;
    }

    /**
     * Allocates an object in the compact multiple-array representation of a list.
     * <p><span style="font-variant:small-caps;">Compact-List-Allocate-Object</span> from solution to exercise 10.3-4.</p>
     *
     * @param L the list in the compact multiple-array representation
     * @param <T> the type of elements in {@code L}
     * @return the index of the allocated object in {@code L}
     */
    public static <T> int compactListAllocateObject(MultipleArrayList<T> L) {
        return allocateObject(L);
    }

    /**
     * Frees an object in the compact multiple-array representation of a list.
     * <p><span style="font-variant:small-caps;">Compact-List-Free-Object</span> from solution to exercise 10.3-4.</p>
     *
     * @param L the list in the compact multiple-array representation
     * @param x the index of the object in {@code L} to free
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void compactListFreeObject(MultipleArrayList<T> L, int x) {
        int n = L.getLength();
        int y;
        if (L.free == null) {
            y = n;
        } else {
            y = L.free - 1;
        }
        L.key.set(x, L.key.at(y));
        L.next.set(x, L.next.at(y));
        L.prev.set(x, L.prev.at(y));
        if (L.next.at(y) != null && L.next.at(y) != x) {
            L.prev.set(L.next.at(y), x);
        }
        if (L.prev.at(y) != null && L.prev.at(y) != x) {
            L.next.set(L.prev.at(y), x);
        }
        if (L.L.equals(y)) {
            L.L = x;
        }
        freeObject(L, y);
    }

    /**
     * Transforms a list in the multiple-array representation into a list in the compact multiple-array representation.
     * <p><span style="font-variant:small-caps;">Compactify-List</span> from solution to exercise 10.3-5.</p>
     *
     * @param L the list in the multiple-array representation
     * @param <T> the type of elements in {@code L}
     */
    public static <T> void compactifyList(MultipleArrayList<T> L) {
        int m = L.getLength();
        setPrevFields(L, Integer.MAX_VALUE);
        Integer x = L.L;
        Integer x_ = null;
        Integer y = 1;
        while (x != null) {
            if (x <= m) {
                x_ = x;
                x = L.next.at(x);
            } else {
                while (L.prev.at(y) != null && L.prev.at(y) == Integer.MAX_VALUE) {
                    y++;
                }
                L.key.exch(x, y);
                L.next.exch(x, y);
                L.prev.exch(x, y);
                if (L.next.at(x) != null) {
                    L.prev.set(L.next.at(x), x);
                }
                if (L.prev.at(x) != null) {
                    L.next.set(L.prev.at(x), x);
                }
                if (x_ != null) {
                    L.next.set(x_, y);
                }
                if (L.L.equals(x)) {
                    L.L = y;
                }
                if (L.free.equals(y)) {
                    L.free = x;
                }
                x_ = y;
                x = L.next.at(y);
            }
        }
        fixPrevFields(L);
    }

    private static <T> void setPrevFields(MultipleArrayList<T> L, int value) {
        Integer x = L.L;
        while (x != null) {
            L.prev.set(x, value);
            x = L.next.at(x);
        }
    }

    private static <T> void fixPrevFields(MultipleArrayList<T> L) {
        if (L.L == null) {
            return;
        }
        L.prev.set(L.L, null);
        Integer x = L.L;
        Integer y = L.next.at(L.L);
        while (y != null) {
            L.prev.set(y, x);
            x = y;
            y = L.next.at(y);
        }
    }

    /**
     * Prints out keys of a binary tree performing inorder tree walk - an iterative version.
     * <p><span style="font-variant:small-caps;">Iterative-Preorder-Tree-Walk</span> from solution to exercise 10.4-3.</p>
     *
     * @param T the binary tree
     * @param <T> the type of elements in {@code T}
     */
    public static <T> void iterativePreorderTreeWalk(BinaryTree<T> T) {
        if (T.root == null) {
            return;
        }
        Stack<BinaryTree.Node<T>> S = Stack.withLength(T.getSize());
        push(S, T.root);
        while (!stackEmpty(S)) {
            BinaryTree.Node<T> x = pop(S);
            System.out.println(x.key);
            if (x.right != null) {
                push(S, x.right);
            }
            if (x.left != null) {
                push(S, x.left);
            }
        }
    }

    /**
     * Prints out keys of an arbitrary rooted tree.
     * <p><span style="font-variant:small-caps;">Tree-Walk</span> from solution to exercise 10.4-4.</p>
     *
     * @param x the root of the tree
     * @param <T> the type of elements in the tree
     */
    public static <T> void treeWalk(MultiaryTree.Node<T> x) {
        if (x != null) {
            System.out.println(x.key);
            treeWalk(x.leftChild);
            treeWalk(x.rightSibling);
        }
    }

    /**
     * Visits a node of a tree during stackless inorder tree walk by printing out its key.
     * <p><span style="font-variant:small-caps;">Stackless-Inorder-Visit</span> from solution to exercise 10.4-5.</p>
     *
     * @param x the node of the tree to visit
     * @param <T> the type of elements in the tree
     * @return the next node of the tree to visit according to inorder, or {@code null} if no such node exists
     */
    static <T> BinaryTree.Node<T> stacklessInorderVisit(BinaryTree.Node<T> x) {
        System.out.println(x.key);
        if (x.right != null) {
            return x.right;
        }
        return x.p;
    }

    /**
     * Prints out keys of a binary tree performing inorder tree walk - an iterative version not using a stack.
     * <p><span style="font-variant:small-caps;">Stackless-Inorder-Tree-Walk</span> from solution to exercise 10.4-5.</p>
     *
     * @param T the binary tree
     * @param <T> the type of elements in {@code T}
     */
    public static <T> void stacklessInorderTreeWalk(BinaryTree<T> T) {
        BinaryTree.Node<T> prev = null;
        BinaryTree.Node<T> curr = T.root;
        BinaryTree.Node<T> next = null;
        while (curr != null) {
            if (prev == curr.p) {
                if (curr.left != null) {
                    next = curr.left;
                } else {
                    next = stacklessInorderVisit(curr);
                }
            } else if (prev == curr.left) {
                next = stacklessInorderVisit(curr);
            } else {
                next = curr.p;
            }
            prev = curr;
            curr = next;
        }
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Make-Heap</span>
     * using a sorted doubly linked list.
     * <p>Solution to problem 10-2(a).</p>
     *
     * @return the empty mergeable min-heap
     */
    public static List<Integer> sortedListMakeMinHeap() {
        return new List<>();
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Insert</span>
     * using a sorted doubly linked list.
     * <p>Solution to problem 10-2(a).</p>
     *
     * @param L the sorted doubly linked list representing the mergeable min-heap
     * @param key the key of the element to insert
     */
    public static void sortedListMinHeapInsert(List<Integer> L, int key) {
        List.Node<Integer> x = new List.Node<>(key);
        if (L.head == null) {
            L.head = x;
            return;
        }
        if (key < L.head.key) {
            x.next = L.head;
            L.head.prev = x;
            L.head = x;
            return;
        }
        List.Node<Integer> y = L.head;
        while (y.next != null && y.next.key < key) {
            y = y.next;
        }
        if (y.next != null) {
            x.next = y.next;
            x.prev = y;
            y.next.prev = x;
            y.next = x;
        } else {
            x.prev = y;
            y.next = x;
        }
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Minimum</span>
     * using a sorted doubly linked list.
     * <p>Solution to problem 10-2(a).</p>
     *
     * @param L the sorted doubly linked list representing the mergeable min-heap
     * @return the smallest element of the mergeable min-heap
     * @throws RuntimeException if the mergeable min-heap is empty
     */
    public static int sortedListHeapMinimum(List<Integer> L) {
        if (L.head == null) {
            throw new RuntimeException("heap underflow");
        }
        return L.head.key;
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Extract-Min</span>
     * using a sorted doubly linked list.
     * <p>Solution to problem 10-2(a).</p>
     *
     * @param L the sorted doubly linked list representing the mergeable min-heap
     * @return the smallest element of the mergeable min-heap
     * @throws RuntimeException if the mergeable min-heap is empty
     */
    public static int sortedListHeapExtractMin(List<Integer> L) {
        if (L.head == null) {
            throw new RuntimeException("heap underflow");
        }
        int minimum = L.head.key;
        if (L.head.next != null) {
            L.head.next.prev = L.head;
        }
        L.head = L.head.next;
        return minimum;
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Union</span>
     * using a sorted doubly linked list.
     * <p>Solution to problem 10-2(a).</p>
     *
     * @param L1 the sorted doubly linked list representing the first mergeable min-heap
     * @param L2 the sorted doubly linked list representing the second mergeable min-heap
     * @return the sorted doubly linked list representing the union of {@code L1} and {@code L2}
     */
    public static List<Integer> sortedListMinHeapUnion(List<Integer> L1, List<Integer> L2) {
        return mergeSortedLists(new Array<>(L1, L2));
    }

    /**
     * Searches for an element in a sorted list in the compact multiple-array representation.
     * <p><span style="font-variant:small-caps;">Compact-List-Search</span> from problem 10-3.</p>
     *
     * @param L the list in the compact multiple-array representation
     * @param n the number of elements in {@code L}
     * @param k the key of the element to find
     * @param <T> the type of elements in {@code L}
     * @return the index {@code i} of element with key {@code k} in {@code L},
     *      or {@code null} if {@code L} does not contain such element
     */
    public static <T extends Comparable> Integer compactListSearch(MultipleArrayList<T> L, int n, T k) {
        Integer i = L.L;
        while (i != null && less(L.key.at(i), k)) {
            int j = random(1, n);
            if (less(L.key.at(i), L.key.at(j)) && leq(L.key.at(j), k)) {
                i = j;
                if (L.key.at(i).equals(k)) {
                    return i;
                }
            }
            i = L.next.at(i);
        }
        if (i == null || greater(L.key.at(i), k)) {
            return null;
        }
        return i;
    }

}
