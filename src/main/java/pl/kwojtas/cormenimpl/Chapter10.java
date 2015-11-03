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
import pl.kwojtas.cormenimpl.util.Pair;
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

    private Chapter10() {
    }

    /**
     * Checks if a stack is empty.
     * <p><span style="font-variant:small-caps;">Stack-Empty</span> from subchapter 10.1.</p>
     *
     * @param S   the stack
     * @param <E> the type of elements in {@code S}
     * @return {@code true} if {@code S} is empty, or {@code false} otherwise
     */
    public static <E> boolean stackEmpty(Stack<E> S) {
        return S.top == 0;
    }

    /**
     * Inserts an element on the top of a stack.
     * <p><span style="font-variant:small-caps;">Push</span> from subchapter 10.1.</p>
     *
     * @param S   the stack
     * @param x   the element to insert
     * @param <E> the type of elements in {@code S}
     */
    public static <E> void push(Stack<E> S, E x) {
        S.top++;
        S.set(S.top, x);
    }

    /**
     * Deletes the element from the top of a stack.
     * <p><span style="font-variant:small-caps;">Pop</span> from subchapter 10.1.</p>
     *
     * @param S   the stack
     * @param <E> the type of elements in {@code S}
     * @return the element deleted from the top of {@code S}
     * @throws RuntimeException if {@code S} is empty
     */
    public static <E> E pop(Stack<E> S) {
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
     * @param Q   the queue
     * @param x   the element to insert
     * @param <E> the type of elements in {@code Q}
     * @throws RuntimeException if {@code Q} is full
     */
    public static <E> void enqueue(Queue<E> Q, E x) {
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
     * @param Q   the queue
     * @param <E> the type of elements in {@code Q}
     * @return the element deleted from the head of {@code Q}
     * @throws RuntimeException if {@code Q} is empty
     */
    public static <E> E dequeue(Queue<E> Q) {
        if (queueEmpty(Q)) {
            throw new RuntimeException("underflow");
        }
        E x = Q.at(Q.head);
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
     * @param A   the array containing two stacks
     * @param x   the element to insert
     * @param <E> the type of elements in {@code A}
     */
    public static <E> void firstStackPush(DoubleStack<E> A, E x) {
        A.top1++;
        A.set(A.top1, x);
    }

    /**
     * Deletes the element from the top of the first stack in a "double stack in a single array" implementation.
     * <p>Solution to exercise 10.1-2.</p>
     *
     * @param A   the array containing two stacks
     * @param <E> the type of elements in {@code A}
     * @return the element deleted from the top of the first stack
     * @throws RuntimeException if the first stack is empty
     */
    public static <E> E firstStackPop(DoubleStack<E> A) {
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
     * @param A   the array containing two stacks
     * @param <E> the type of elements in {@code A}
     * @return {@code true} if the first stack is empty, or {@code false} otherwise
     */
    public static <E> boolean firstStackEmpty(DoubleStack<E> A) {
        return A.top1 == 0;
    }

    /**
     * Inserts an element on the top of the second stack in a "double stack in a single array" implementation.
     * <p>Solution to exercise 10.1-2.</p>
     *
     * @param A   the array containing two stacks
     * @param x   the element to insert
     * @param <E> the type of elements in {@code A}
     */
    public static <E> void secondStackPush(DoubleStack<E> A, E x) {
        A.top2--;
        A.set(A.top2, x);
    }

    /**
     * Deletes the element from the top of the second stack in a "double stack in a single array" implementation.
     * <p>Solution to exercise 10.1-2.</p>
     *
     * @param A   the array containing two stacks
     * @param <E> the type of elements in {@code A}
     * @return the deleted element from the top of the second stack
     * @throws RuntimeException if the second stack is empty
     */
    public static <E> E secondStackPop(DoubleStack<E> A) {
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
     * @param A   the array containing two stacks
     * @param <E> the type of elements in {@code A}
     * @return {@code true} if the second stack is empty, or {@code false} otherwise
     */
    public static <E> boolean secondStackEmpty(DoubleStack<E> A) {
        return A.top2 == A.length + 1;
    }

    /**
     * Checks if a queue is empty.
     * <p><span style="font-variant:small-caps;">Queue-Empty</span> from solution to exercise 10.1-4.</p>
     *
     * @param Q   the queue
     * @param <E> the type of elements in {@code Q}
     * @return {@code true} if {@code Q} is empty, or {@code false} otherwise
     */
    public static <E> boolean queueEmpty(Queue<E> Q) {
        return Q.head == Q.tail;
    }

    /**
     * Inserts an element at the head of a deque.
     * <p><span style="font-variant:small-caps;">Head-Enqueue</span> from solution to exercise 10.1-5.</p>
     *
     * @param D   the deque
     * @param x   the element to insert
     * @param <E> the type of elements in {@code D}
     */
    public static <E> void headEnqueue(Deque<E> D, E x) {
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
     * @param D   the deque
     * @param <E> the type of elements in {@code D}
     * @return the element removed from the head of {@code D}
     */
    public static <E> E headDequeue(Deque<E> D) {
        E x = D.at(D.head);
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
     * @param D   the deque
     * @param x   the element to insert
     * @param <E> the type of elements in {@code D}
     */
    public static <E> void tailEnqueue(Deque<E> D, E x) {
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
     * @param D   the deque
     * @param <E> the type of elements in {@code D}
     * @return the element deleted from the tail of {@code D}
     */
    public static <E> E tailDequeue(Deque<E> D) {
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
     * @param S1  the first stack
     * @param S2  the second stack
     * @param x   the element to insert to the queue
     * @param <E> the type of elements in the queue
     */
    public static <E> void enqueueOnStacks(Stack<E> S1, @SuppressWarnings("unused") Stack<E> S2, E x) {
        push(S1, x);
    }

    /**
     * Implements the queue operation <span style="font-variant:small-caps;">Dequeue</span> using two stacks.
     * <p>Solution to exercise 10.1-6.</p>
     *
     * @param S1  the first stack
     * @param S2  the second stack
     * @param <E> the type of elements in {@code S1} and {@code S2}
     * @return the element deleted from the queue
     * @throws RuntimeException if the queue is empty
     */
    public static <E> E dequeueOnStacks(Stack<E> S1, Stack<E> S2) {
        if (stackEmpty(S1)) {
            throw new RuntimeException("underflow");
        }
        while (!stackEmpty(S1)) {
            push(S2, pop(S1));
        }
        E x = pop(S2);
        while (!stackEmpty(S2)) {
            push(S1, pop(S2));
        }
        return x;
    }

    /**
     * Implements the stack operation <span style="font-variant:small-caps;">Push</span> using two queues.
     * <p>Solution to exercise 10.1-7.</p>
     *
     * @param Q1  the first queue
     * @param Q2  the second queue
     * @param x   the element to insert
     * @param <E> the type of elements on the stack
     */
    public static <E> void pushOnQueues(Queue<E> Q1, @SuppressWarnings("unused") Queue<E> Q2, E x) {
        enqueue(Q1, x);
    }

    /**
     * Implements the stack operation <span style="font-variant:small-caps;">Pop</span> using two queues.
     * <p>Solution to exercise 10.1-7.</p>
     *
     * @param Q1  the first queue
     * @param Q2  the second queue
     * @param <E> the type of elements on the stack
     * @return the element deleted from the stack
     * @throws RuntimeException if the stack is empty
     */
    public static <E> E popOnQueues(Queue<E> Q1, Queue<E> Q2) {
        if (queueEmpty(Q1)) {
            throw new RuntimeException("underflow");
        }
        E x = null;
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
     * @param L   the doubly linked list
     * @param k   the key of the element to find
     * @param <E> the type of elements in {@code L}
     * @return the element of key {@code k} in list {@code L}, or {@code null} if {@code L} does not contain such element
     */
    public static <E> List.Node<E> listSearch(List<E> L, E k) {
        List.Node<E> x = L.head;
        while (x != null && !x.key.equals(k)) {
            x = x.next;
        }
        return x;
    }

    /**
     * Inserts an element into a doubly linked list.
     * <p><span style="font-variant:small-caps;">List-Insert</span> from subchapter 10.2.</p>
     *
     * @param L   the doubly linked list
     * @param x   the element to insert
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void listInsert(List<E> L, List.Node<E> x) {
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
     * @param L   the doubly linked list
     * @param x   the element in {@code L} to delete
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void listDelete(List<E> L, List.Node<E> x) {
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
     * @param L   the doubly linked list with a sentinel
     * @param x   the element in {@code L} to delete
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void listDelete_(@SuppressWarnings("unused") ListWithSentinel<E> L, ListWithSentinel.Node<E> x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
    }

    /**
     * Searches for an element in a doubly linked list with a sentinel.
     * <p><span style="font-variant:small-caps;">List-Search'</span> from subchapter 10.2.</p>
     *
     * @param L   the doubly linked list with a sentinel
     * @param k   the key of the element to find
     * @param <E> the type of elements in {@code L}
     * @return the element with key {@code k} in {@code L}, or {@code null} if {@code L} does not contain such element
     */
    public static <E> ListWithSentinel.Node<E> listSearch_(ListWithSentinel<E> L, E k) {
        ListWithSentinel.Node<E> x = L.nil.next;
        while (x != L.nil && !x.key.equals(k)) {
            x = x.next;
        }
        return x;
    }

    /**
     * Inserts an element at the head of a doubly linked list with a sentinel.
     * <p><span style="font-variant:small-caps;">List-Insert'</span> from subchapter 10.2.</p>
     *
     * @param L   the doubly linked list with a sentinel
     * @param x   the element to insert
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void listInsert_(ListWithSentinel<E> L, ListWithSentinel.Node<E> x) {
        x.next = L.nil.next;
        L.nil.next.prev = x;
        L.nil.next = x;
        x.prev = L.nil;
    }

    /**
     * Inserts an element at the head of a singly linked list.
     * <p><span style="font-variant:small-caps;">Singly-Linked-List-Insert</span> from solution to exercise 10.2-1.</p>
     *
     * @param L   the singly linked list
     * @param x   the element to insert
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void singlyLinkedListInsert(SinglyLinkedList<E> L, SinglyLinkedList.Node<E> x) {
        x.next = L.head;
        L.head = x;
    }

    /**
     * Deletes an element from a singly linked list.
     * <p><span style="font-variant:small-caps;">Singly-Linked-List-Delete</span> from solution to exercise 10.2-1.</p>
     *
     * @param L   the singly linked list
     * @param x   the element in {@code L} to delete
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void singlyLinkedListDelete(SinglyLinkedList<E> L, SinglyLinkedList.Node<E> x) {
        if (x == L.head) {
            L.head = L.head.next;
        } else {
            SinglyLinkedList.Node<E> y = L.head;
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
     * @param L   the singly linked list
     * @param k   the key of the element to insert onto the stack
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void singlyLinkedListPush(SinglyLinkedList<E> L, E k) {
        SinglyLinkedList.Node<E> x = new SinglyLinkedList.Node<>(k);
        singlyLinkedListInsert(L, x);
    }

    /**
     * Implements the stack operation <span style="font-variant:small-caps;">Pop</span> using a singly linked list.
     * <p><span style="font-variant:small-caps;">Singly-Linked-List-Pop</span> from solution to exercise 10.2-2.</p>
     *
     * @param L   the singly linked list
     * @param <E> the type of elements in {@code L}
     * @return the element deleted from the stack
     */
    public static <E> E singlyLinkedListPop(SinglyLinkedList<E> L) {
        if (L.head == null) {
            throw new RuntimeException("underflow");
        }
        SinglyLinkedList.Node<E> x = L.head;
        L.head = x.next;
        return x.key;
    }

    /**
     * Implements the queue operation <span style="font-variant:small-caps;">Enqueue</span> using a singly linked list.
     * <p><span style="font-variant:small-caps;">Singly-Linked-List-Enqueue</span> from solution to exercise 10.2-3.</p>
     *
     * @param L   the singly linked list
     * @param k   the key of the element to insert into a queue
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void singlyLinkedListEnqueue(SinglyLinkedListWithTail<E> L, E k) {
        SinglyLinkedListWithTail.Node<E> x = new SinglyLinkedList.Node<>(k);
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
     * @param L   the singly linked list
     * @param <E> the type of elements in {@code L}
     * @return the element deleted from the queue
     * @throws RuntimeException if the queue is empty
     */
    public static <E> E singlyLinkedListDequeue(SinglyLinkedListWithTail<E> L) {
        if (L.head == null) {
            throw new RuntimeException("underflow");
        }
        SinglyLinkedListWithTail.Node<E> x = L.head;
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
     * @param L   the singly linked circular list
     * @param x   the element to insert
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void circularListInsert(CircularList<E> L, CircularList.Node<E> x) {
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
     * @param L   the singly linked circular list
     * @param x   the element in {@code L} to delete
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void circularListDelete(CircularList<E> L, CircularList.Node<E> x) {
        CircularList.Node<E> y = L.head;
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
     * @param L   the singly linked circular list
     * @param k   the key of the element to find
     * @param <E> the type of elements in {@code L}
     * @return the element with key {@code k} in {@code L}, or {@code null} if {@code L} does not contain such element
     */
    public static <E> CircularList.Node<E> circularListSearch(CircularList<E> L, E k) {
        if (L.head == null) {
            return null;
        }
        if (L.head.key.equals(k)) {
            return L.head;
        }
        CircularList.Node<E> x = L.head.next;
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
     * @param S1  the first set
     * @param S2  the second set, disjoint with {@code S1}
     * @param <E> the type of elements in {@code S1} and {@code S2}
     * @return the union of {@code S1} and {@code S2}
     */
    public static <E> CircularList<E> circularListsUnion(CircularList<E> S1, CircularList<E> S2) {
        CircularList<E> S = new CircularList<>();
        if (S1.head != null && S2.head != null) {
            CircularList.Node<E> x = S1.head.next;
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
     * @param L   the singly linked list
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void singlyLinkedListReverse(SinglyLinkedList<E> L) {
        SinglyLinkedList<E> L_ = new SinglyLinkedList<>();
        L_.head = null;
        while (L.head != null) {
            SinglyLinkedList.Node<E> x = L.head;
            singlyLinkedListDelete(L, L.head);
            singlyLinkedListInsert(L_, x);
        }
        L.head = L_.head;
    }

    /**
     * Finds an element in a XOR linked list.
     * <p><span style="font-variant:small-caps;">Xor-Linked-List-Search</span> from solution to exercise 10.2-8.</p>
     *
     * @param L   the XOR linked list
     * @param k   the key of the element to find
     * @param <E> the type of elements in {@code L}
     * @return the element of key {@code k} in list {@code L}, or {@code null} if {@code L} does not contain such element
     */
    public static <E> XorLinkedList.Node<E> xorLinkedListSearch(XorLinkedList<E> L, E k) {
        XorLinkedList.Node<E> x = L.head;
        XorLinkedList.Node<E> y = null;
        while (x != null && !x.key.equals(k)) {
            XorLinkedList.Node<E> z = L.byAddress(x.np ^ (y != null ? y.address : 0));
            y = x;
            x = z;
        }
        return x;
    }

    /**
     * Inserts an element at the head of a XOR linked list.
     * <p><span style="font-variant:small-caps;">Xor-Linked-List-Insert</span> from solution to exercise 10.2-8.</p>
     *
     * @param L   the XOR linked list
     * @param x   the element to insert
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void xorLinkedListInsert(XorLinkedList<E> L, XorLinkedList.Node<E> x) {
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
     * @param L   the XOR linked list
     * @param x   the element in {@code L} to delete
     * @param <E> the type of elements in {@code L}
     */
    @SuppressWarnings("all")
    public static <E> void xorLinkedListDelete(XorLinkedList<E> L, XorLinkedList.Node<E> x) {
        XorLinkedList.Node<E> x_ = L.head;
        XorLinkedList.Node<E> y = null;
        XorLinkedList.Node<E> z;
        while (x_ != x) {
            z = L.byAddress(x_.np ^ (y != null ? y.address : 0));
            y = x_;
            x_ = z;
        }
        z = L.byAddress(x.np ^ (y != null ? y.address : 0));
        if (x == L.head) {
            L.head = z;
        } else {
            y.np ^= x.address ^ (z != null ? z.address : 0);
        }
        if (x == L.tail) {
            L.tail = y;
        } else {
            z.np ^= x.address ^ (y != null ? y.address : 0);
        }
    }

    /**
     * Reverses a XOR linked list.
     * <p><span style="font-variant:small-caps;">Xor-Linked-List-Reverse</span> from solution to exercise 10.2-8.</p>
     *
     * @param L   the XOR linked list
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void xorLinkedListReverse(XorLinkedList<E> L) {
        XorLinkedList.Node<E> x = L.head;
        L.head = L.tail;
        L.tail = x;
    }

    /**
     * Allocates an object in the multiple-array representation of a list.
     * <p><span style="font-variant:small-caps;">Allocate-Object</span> from subchapter 10.3.</p>
     *
     * @param L   the list in the multiple-array representation
     * @param <E> the type of elements in {@code L}
     * @return the index of the allocated object in {@code L}
     * @throws RuntimeException if {@code L} is full
     */
    public static <E> int allocateObject(MultipleArrayList<E> L) {
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
     * @param L   the list in a multiple-array representation
     * @param x   the index of the object in {@code L} to free
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void freeObject(MultipleArrayList<E> L, int x) {
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
        int x = A.free;
        A.free = A.at(x + 1);
        return x;
    }

    /**
     * Frees an object in the single-array representation of a list.
     * <p><span style="font-variant:small-caps;">Single-Array-Free-Object</span> from solution to exercise 10.3-2.</p>
     *
     * @param A the list in the single-array representation
     * @param x the index of the object in {@code A} to free
     */
    public static void singleArrayFreeObject(SingleArrayList A, int x) {
        A.set(x + 1, A.free);
        A.free = x;
    }

    /**
     * Allocates an object in the compact multiple-array representation of a list.
     * <p><span style="font-variant:small-caps;">Compact-List-Allocate-Object</span> from solution to exercise 10.3-4.</p>
     *
     * @param L   the list in the compact multiple-array representation
     * @param <E> the type of elements in {@code L}
     * @return the index of the allocated object in {@code L}
     */
    public static <E> int compactListAllocateObject(MultipleArrayList<E> L) {
        return allocateObject(L);
    }

    /**
     * Frees an object in the compact multiple-array representation of a list.
     * <p><span style="font-variant:small-caps;">Compact-List-Free-Object</span> from solution to exercise 10.3-4.</p>
     *
     * @param L   the list in the compact multiple-array representation
     * @param x   the index of the object in {@code L} to free
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void compactListFreeObject(MultipleArrayList<E> L, int x) {
        int y;
        if (L.free == null) {
            y = L.key.length;
        } else {
            y = L.free - 1;
        }
        if (L.next.at(x) != null) {
            L.prev.set(L.next.at(x), L.prev.at(x));
        }
        if (L.prev.at(x) != null) {
            L.next.set(L.prev.at(x), L.next.at(x));
        }
        if (x != y) {
            if (L.next.at(y) != null) {
                L.prev.set(L.next.at(y), x);
            }
            if (L.prev.at(y) != null) {
                L.next.set(L.prev.at(y), x);
            }
        }
        L.key.set(x, L.key.at(y));
        L.next.set(x, L.next.at(y));
        L.prev.set(x, L.prev.at(y));
        if (L.L.equals(y)) {
            L.L = x;
        }
        freeObject(L, y);
    }

    /**
     * Transforms a list in the multiple-array representation into a list in the compact multiple-array representation.
     * <p><span style="font-variant:small-caps;">Compactify-List</span> from solution to exercise 10.3-5.</p>
     *
     * @param L   the list in the multiple-array representation
     * @param <E> the type of elements in {@code L}
     */
    public static <E> void compactifyList(MultipleArrayList<E> L) {
        int m = L.getLength();
        setPrevFields(L, -1);
        Integer x = L.L;
        Integer x_ = null;
        Integer y = 1;
        while (x != null) {
            if (x <= m) {
                x_ = x;
                x = L.next.at(x);
            } else {
                while (L.prev.at(y) != null && L.prev.at(y) == -1) {
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

    private static <E> void setPrevFields(MultipleArrayList<E> L, int value) {
        Integer x = L.L;
        while (x != null) {
            L.prev.set(x, value);
            x = L.next.at(x);
        }
    }

    private static <E> void fixPrevFields(MultipleArrayList<E> L) {
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
     * @param T   the binary tree
     * @param <E> the type of elements in {@code T}
     */
    public static <E> void iterativePreorderTreeWalk(BinaryTree<E> T) {
        if (T.root == null) {
            return;
        }
        Stack<BinaryTree.Node<E>> S = Stack.withLength(T.getSize());
        push(S, T.root);
        while (!stackEmpty(S)) {
            BinaryTree.Node<E> x = pop(S);
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
     * @param x   the root of the tree
     * @param <E> the type of elements in the tree
     */
    public static <E> void treeWalk(MultiaryTree.Node<E> x) {
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
     * @param x   the node of the tree to visit
     * @param <E> the type of elements in the tree
     * @return the next node of the tree to visit according to inorder, or {@code null} if no such node exists
     */
    static <E> BinaryTree.Node<E> stacklessInorderVisit(BinaryTree.Node<E> x) {
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
     * @param T   the binary tree
     * @param <E> the type of elements in {@code T}
     */
    public static <E> void stacklessInorderTreeWalk(BinaryTree<E> T) {
        BinaryTree.Node<E> prev = null;
        BinaryTree.Node<E> curr = T.root;
        BinaryTree.Node<E> next;
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
     * using a sorted singly linked list.
     * <p>Solution to problem 10-2(a).</p>
     *
     * @return the empty mergeable min-heap
     */
    public static SinglyLinkedList<Integer> sortedListMakeMinHeap() {
        return new SinglyLinkedList<>();
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Insert</span>
     * using a sorted singly linked list.
     * <p>Solution to problem 10-2(a).</p>
     *
     * @param L   the sorted singly linked list representing the mergeable min-heap
     * @param key the key of the element to insert
     */
    public static void sortedListMinHeapInsert(SinglyLinkedList<Integer> L, int key) {
        SinglyLinkedList.Node<Integer> x = new SinglyLinkedList.Node<>(key);
        if (L.head == null) {
            L.head = x;
            return;
        }
        if (key < L.head.key) {
            x.next = L.head;
            L.head = x;
            return;
        }
        SinglyLinkedList.Node<Integer> y = L.head;
        while (y.next != null && y.next.key < key) {
            y = y.next;
        }
        if (y.next != null) {
            x.next = y.next;
        }
        y.next = x;
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Minimum</span>
     * using a sorted singly linked list.
     * <p>Solution to problem 10-2(a).</p>
     *
     * @param L the sorted singly linked list representing the nonempty mergeable min-heap
     * @return the smallest element of the mergeable min-heap
     */
    public static int sortedListHeapMinimum(SinglyLinkedList<Integer> L) {
        return L.head.key;
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Extract-Min</span>
     * using a sorted singly linked list.
     * <p>Solution to problem 10-2(a).</p>
     *
     * @param L the sorted singly linked list representing the mergeable min-heap
     * @return the smallest element of the mergeable min-heap
     * @throws RuntimeException if the mergeable min-heap is empty
     */
    public static int sortedListHeapExtractMin(SinglyLinkedList<Integer> L) {
        if (L.head == null) {
            throw new RuntimeException("heap underflow");
        }
        int minimum = L.head.key;
        L.head = L.head.next;
        return minimum;
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Union</span>
     * using a sorted singly linked list.
     * <p>Solution to problem 10-2(a).</p>
     *
     * @param L1 the sorted singly linked list representing the first mergeable min-heap
     * @param L2 the sorted singly linked list representing the second mergeable min-heap
     * @return the sorted singly linked list representing the union of {@code L1} and {@code L2}
     */
    public static SinglyLinkedList<Integer> sortedListMinHeapUnion(SinglyLinkedList<Integer> L1, SinglyLinkedList<Integer> L2) {
        SinglyLinkedList<Integer> L = mergeSortedLists(new Array<>(L1, L2));
        SinglyLinkedList.Node<Integer> x = L.head;
        while (x != null) {
            SinglyLinkedList.Node<Integer> y = x.next;
            if (y != null && x.key.equals(y.key)) {
                x.next = y.next;
            }
            x = x.next;
        }
        return L;
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Make-Heap</span>
     * using a singly linked list.
     * <p>Solution to problems 10-2(b), 10-2(c).</p>
     *
     * @return the empty mergeable min-heap
     */
    public static SinglyLinkedListWithTail<Integer> listMakeMinHeap() {
        return new SinglyLinkedListWithTail<>();
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Insert</span>
     * using a singly linked list.
     * <p>Solution to problems 10-2(b), 10-2(c).</p>
     *
     * @param L   the sorted singly linked list representing the mergeable min-heap
     * @param key the key of the element to insert
     */
    public static void listMinHeapInsert(SinglyLinkedListWithTail<Integer> L, int key) {
        SinglyLinkedListWithTail.Node<Integer> x = new SinglyLinkedListWithTail.Node<>(key);
        if (L.head == null || less(key, L.head.key)) {
            singlyLinkedListInsert(L, x);
            if (L.tail == null) {
                L.tail = x;
            }
        } else {
            x.next = L.head.next;
            L.head.next = x;
        }
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Minimum</span>
     * using a singly linked list.
     * <p>Solution to problems 10-2(b), 10-2(c).</p>
     *
     * @param L the singly linked list representing the nonempty mergeable min-heap
     * @return the smallest element of the mergeable min-heap
     */
    public static int listHeapMinimum(SinglyLinkedListWithTail<Integer> L) {
        return L.head.key;
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Extract-Min</span>
     * using a singly linked list.
     * <p>Solution to problems 10-2(b), 10-2(c).</p>
     *
     * @param L the singly linked list representing the mergeable min-heap
     * @return the smallest element of the mergeable min-heap
     * @throws RuntimeException if the mergeable min-heap is empty
     */
    public static int listHeapExtractMin(SinglyLinkedListWithTail<Integer> L) {
        if (L.head == null) {
            throw new RuntimeException("heap underflow");
        }
        int minimum = L.head.key;
        L.head = L.head.next;
        if (L.head == null) {
            L.tail = null;
            return minimum;
        }
        SinglyLinkedListWithTail.Node<Integer> newMinimumNode = L.head;
        SinglyLinkedListWithTail.Node<Integer> x = L.head;
        while (x != null) {
            if (less(x.key, newMinimumNode.key)) {
                newMinimumNode = x;
            }
            x = x.next;
        }
        singlyLinkedListDelete(L, newMinimumNode);
        singlyLinkedListInsert(L, newMinimumNode);
        x = L.head;
        while (x.next != null) {
            x = x.next;
        }
        L.tail = x;
        return minimum;
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Union</span>
     * using a singly linked list.
     * <p>Solution to problem 10-2(b).</p>
     *
     * @param L1 the singly linked list representing the first mergeable min-heap
     * @param L2 the singly linked list representing the second mergeable min-heap
     * @return the singly linked list representing the union of {@code L1} and {@code L2}
     */
    public static SinglyLinkedListWithTail<Integer> listMinHeapUnion(SinglyLinkedListWithTail<Integer> L1, SinglyLinkedListWithTail<Integer> L2) {
        if (L1.head == null) {
            return L2;
        }
        if (L2.head == null) {
            return L1;
        }
        L1.tail.next = L2.head;
        L1.tail = L2.tail;
        listQuicksort(L1);
        SinglyLinkedListWithTail.Node<Integer> x = L1.head;
        while (x != null) {
            SinglyLinkedList.Node<Integer> y = x.next;
            if (y != null && x.key.equals(y.key)) {
                x.next = y.next;
                if (y == L1.tail) {
                    L1.tail = x;
                }
            }
            x = x.next;
        }
        return L1;
    }

    private static void listQuicksort(SinglyLinkedListWithTail<Integer> L) {
        if (L.head != null) {
            SinglyLinkedListWithTail.Node<Integer> pivot = L.head;
            L.head = L.head.next;
            Pair<SinglyLinkedListWithTail<Integer>, SinglyLinkedListWithTail<Integer>> p = listPartition(L, pivot);
            listQuicksort(p.first);
            listQuicksort(p.second);
            if (p.first.head == null) {
                L.head = pivot;
            } else {
                p.first.tail.next = pivot;
                L.head = p.first.head;
            }
            pivot.next = p.second.head;
            if (p.second.tail == null) {
                L.tail = pivot;
            } else {
                L.tail = p.second.tail;
            }
        }
    }

    private static Pair<SinglyLinkedListWithTail<Integer>, SinglyLinkedListWithTail<Integer>> listPartition(
            SinglyLinkedListWithTail<Integer> L, SinglyLinkedListWithTail.Node<Integer> pivot) {
        if (L.head == null) {
            return new Pair<>(new SinglyLinkedListWithTail<>(), new SinglyLinkedListWithTail<>());
        }
        SinglyLinkedListWithTail<Integer> L1 = new SinglyLinkedListWithTail<>();
        SinglyLinkedListWithTail<Integer> L2 = new SinglyLinkedListWithTail<>();
        SinglyLinkedListWithTail.Node<Integer> x = L.head;
        while (x != null) {
            SinglyLinkedList.Node<Integer> y = x.next;
            if (leq(x.key, pivot.key)) {
                singlyLinkedListInsert(L1, x);
                if (L1.tail == null) {
                    L1.tail = x;
                }
            } else {
                singlyLinkedListInsert(L2, x);
                if (L2.tail == null) {
                    L2.tail = x;
                }
            }
            x = y;
        }
        return new Pair<>(L1, L2);
    }

    /**
     * Implements the mergeable min-heap operation <span style="font-variant:small-caps;">Union</span>
     * using a singly linked list assuming that the dynamic sets to be merged are disjoint.
     * <p>Solution to problem 10-2(c).</p>
     *
     * @param L1 the singly linked list representing the first mergeable min-heap
     * @param L2 the singly linked list representing the second mergeable min-heap
     * @return the singly linked list representing the union of {@code L1} and {@code L2}
     */
    public static SinglyLinkedListWithTail<Integer> listMinHeapDisjointUnion(SinglyLinkedListWithTail<Integer> L1, SinglyLinkedListWithTail<Integer> L2) {
        if (L1.head == null) {
            return L2;
        }
        if (L2.head == null) {
            return L1;
        }
        if (less(L1.head.key, L2.head.key)) {
            L1.tail.next = L2.head;
            L1.tail = L2.tail;
            return L1;
        } else {
            L2.tail.next = L1.head;
            L2.tail = L1.tail;
            return L2;
        }

    }

    /**
     * Searches for an element in a sorted list in the compact multiple-array representation.
     * <p><span style="font-variant:small-caps;">Compact-List-Search</span> from problem 10-3.</p>
     *
     * @param L   the list in the compact multiple-array representation
     * @param n   the number of elements in {@code L}
     * @param k   the key of the element to find
     * @param <E> the type of elements in {@code L}
     * @return the index {@code i} of element with key {@code k} in {@code L},
     * or {@code null} if {@code L} does not contain such element
     */
    public static <E extends Comparable<? super E>> Integer compactListSearch(MultipleArrayList<E> L, int n, E k) {
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
