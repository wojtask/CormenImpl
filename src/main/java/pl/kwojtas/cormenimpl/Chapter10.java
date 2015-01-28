package pl.kwojtas.cormenimpl;

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

import static pl.kwojtas.cormenimpl.util.Util.greater;
import static pl.kwojtas.cormenimpl.util.Util.leq;
import static pl.kwojtas.cormenimpl.util.Util.less;

public final class Chapter10 {

    private Chapter10() { }

    // subchapter 10.1
    public static <T> boolean stackEmpty(Stack<T> S) {
        return S.top == 0;
    }

    // subchapter 10.1
    public static <T> void push(Stack<T> S, T x) {
        S.top++;
        S.set(S.top, x);
    }

    // subchapter 10.1
    public static <T> T pop(Stack<T> S) {
        if (stackEmpty(S)) {
            throw new RuntimeException("underflow");
        }
        S.top--;
        return S.at(S.top + 1);
    }

    // subchapter 10.1, solution of 10.1-4
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

    // subchapter 10.1, solution of 10.1-4
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

    // solution of 10.1-2
    public static <T> void firstStackPush(DoubleStack<T> A, T x) {
        A.top1++;
        A.set(A.top1, x);
    }

    // solution of 10.1-2
    public static <T> T firstStackPop(DoubleStack<T> A) {
        if (firstStackEmpty(A)) {
            throw new RuntimeException("underflow");
        }
        A.top1--;
        return A.at(A.top1 + 1);
    }

    // solution of 10.1-2
    private static <T> boolean firstStackEmpty(DoubleStack<T> A) {
        return A.top1 == 0;
    }

    // solution of 10.1-2
    public static <T> void secondStackPush(DoubleStack<T> A, T x) {
        A.top2--;
        A.set(A.top2, x);
    }

    // solution of 10.1-2
    public static <T> T secondStackPop(DoubleStack<T> A) {
        if (secondStackEmpty(A)) {
            throw new RuntimeException("underflow");
        }
        A.top2++;
        return A.at(A.top2 - 1);
    }

    // solution of 10.1-2
    private static <T> boolean secondStackEmpty(DoubleStack<T> A) {
        return A.top1 == A.length + 1;
    }

    // solution of 10.1-4
    public static <T> boolean queueEmpty(Queue<T> Q) {
        return Q.head == Q.tail;
    }

    // solution of 10.1-5
    public static <T> void headEnqueue(Deque<T> D, T x) {
        if (D.head == 1) {
            D.head = D.length;
        } else {
            D.head--;
        }
        D.set(D.head, x);
    }

    // solution of 10.1-5
    public static <T> T headDequeue(Deque<T> D) {
        T x = D.at(D.head);
        if (D.head == D.length) {
            D.head = 1;
        } else {
            D.head++;
        }
        return x;
    }

    // solution of 10.1-5
    public static <T> void tailEnqueue(Deque<T> D, T x) {
        D.set(D.tail, x);
        if (D.tail == D.length) {
            D.tail = 1;
        } else {
            D.tail++;
        }
    }

    // solution of 10.1-5
    public static <T> T tailDequeue(Deque<T> D) {
        if (D.tail == 1) {
            D.tail = D.length;
        } else {
            D.tail--;
        }
        return D.at(D.tail);
    }

    // solution of 10.1-6
    public static <T> void enqueueOnStacks(Stack<T> S1, Stack<T> S2, T x) {
        push(S1, x);
    }

    // solution of 10.1-6
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

    // solution of 10.1-7
    public static <T> void pushOnQueues(Queue<T> Q1, Queue<T> Q2, T x) {
        enqueue(Q1, x);
    }

    // solution of 10.1-7
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

    // subchapter 10.2
    public static <T> List<T>.Node listSearch(List<T> L, T k) {
        List<T>.Node x = L.head;
        while (x != null && !x.key.equals(k)) {
            x = x.next;
        }
        return x;
    }

    // subchapter 10.2
    public static <T> void listInsert(List<T> L, List<T>.Node x) {
        x.next = L.head;
        if (L.head != null) {
            L.head.prev = x;
        }
        L.head = x;
        x.prev = null;
    }

    // subchapter 10.2
    public static <T> void listDelete(List<T> L, List<T>.Node x) {
        if (x.prev != null) {
            x.prev.next = x.next;
        } else {
            L.head = x.next;
        }
        if (x.next != null) {
            x.next.prev = x.prev;
        }
    }

    // subchapter 10.2
    public static <T> void listDelete_(ListWithSentinel<T> L, ListWithSentinel<T>.Node x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
    }

    // subchapter 10.2
    public static <T> ListWithSentinel<T>.Node listSearch_(ListWithSentinel<T> L, T k) {
        ListWithSentinel<T>.Node x = L.nil.next;
        while (x != L.nil && !x.key.equals(k)) {
            x = x.next;
        }
        return x;
    }

    // subchapter 10.2
    public static <T> void listInsert_(ListWithSentinel<T> L, ListWithSentinel<T>.Node x) {
        x.next = L.nil.next;
        L.nil.next.prev = x;
        L.nil.next = x;
        x.prev = L.nil;
    }

    // solution of 10.2-1
    public static <T> void singlyLinkedListInsert(SinglyLinkedList<T> L, SinglyLinkedList<T>.Node x) {
        x.next = L.head;
        L.head = x;
    }

    // solution of 10.2-1
    public static <T> void singlyLinkedListDelete(SinglyLinkedList<T> L, SinglyLinkedList<T>.Node x) {
        if (x == L.head) {
            L.head = L.head.next;
        } else {
            SinglyLinkedList<T>.Node y = L.head;
            while (y.next != x) {
                y = y.next;
            }
            y.next = x.next;
        }
    }

    // solution of 10.2-2
    public static <T> void singlyLinkedListPush(SinglyLinkedList<T> L, T k) {
        SinglyLinkedList<T>.Node x = L.new Node(k);
        singlyLinkedListInsert(L, x);
    }

    // solution of 10.2-2
    public static <T> T singlyLinkedListPop(SinglyLinkedList<T> L) {
        if (L.head == null) {
            throw new RuntimeException("underflow");
        }
        SinglyLinkedList<T>.Node x = L.head;
        L.head = x.next;
        return x.key;
    }

    // solution of 10.2-3
    public static <T> void singlyLinkedListEnqueue(SinglyLinkedListWithTail<T> L, T k) {
        SinglyLinkedListWithTail<T>.Node x = L.new Node(k);
        if (L.tail != null) {
            L.tail.next = x;
        } else {
            L.head = x;
        }
        L.tail = x;
    }

    // solution of 10.2-3
    public static <T> T singlyLinkedListDequeue(SinglyLinkedListWithTail<T> L) {
        if (L.head == null) {
            throw new RuntimeException("underflow");
        }
        SinglyLinkedListWithTail<T>.Node x = L.head;
        L.head = x.next;
        if (L.tail == x) {
            L.tail = null;
        }
        return x.key;
    }

    // solution of 10.2-5
    public static <T> void circularListInsert(CircularList<T> L, CircularList<T>.Node x) {
        if (L.head == null) {
            L.head = x.next = x;
        } else {
            x.next = L.head.next;
            L.head.next = x;
        }
    }

    // solution of 10.2-5
    public static <T> void circularListDelete(CircularList<T> L, CircularList<T>.Node x) {
        CircularList<T>.Node y = L.head;
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

    // solution of 10.2-5
    public static <T> CircularList<T>.Node circularListSearch(CircularList<T> L, T k) {
        if (L.head == null) {
            return null;
        }
        if (L.head.key.equals(k)) {
            return L.head;
        }
        CircularList<T>.Node x = L.head.next;
        while (x != L.head) {
            if (x.key.equals(k)) {
                return x;
            }
            x = x.next;
        }
        return null;
    }

    // solution of 10.2-6
    public static <T> CircularList<T> circularListsUnion(CircularList<T> S1, CircularList<T> S2) {
        CircularList<T> S = new CircularList<>();
        if (S1.head != null && S2.head != null) {
            CircularList<T>.Node x = S1.head.next;
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

    // solution of 10.2-7
    public static <T> void singlyLinkedListReverse(SinglyLinkedList<T> L) {
        SinglyLinkedList<T> L_ = new SinglyLinkedList<>();
        L_.head = null;
        while (L.head != null) {
            SinglyLinkedList<T>.Node x = L.head;
            singlyLinkedListDelete(L, L.head);
            singlyLinkedListInsert(L_, x);
        }
        L.head = L_.head;
    }

    // subchapter 10.3
    public static <T> int allocateObject(MultipleArrayList<T> L) {
        if (L.free == null) {
            throw new RuntimeException("out of space");
        }
        Integer x = L.free;
        L.free = L.next.at(x);
        return x;
    }

    // subchapter 10.3
    public static <T> void freeObject(MultipleArrayList<T> L, int x) {
        L.next.set(x, L.free);
        L.free = x;
    }

    // solution of 10.3-2
    public static <T> int singleArrayAllocateObject(SingleArrayList<T> L) {
        if (L.free == null) {
            throw new RuntimeException("out of space");
        }
        int i = L.free;
        L.free = L.A.at(i + 1);
        return i;
    }

    // solution of 10.3-2
    public static <T> void singleArrayFreeObject(SingleArrayList<T> L, int i) {
        L.A.set(i + 1, L.free);
        L.free = i;
    }

    // solution of 10.4-3
    public static <T> void iterativeInorderTreeWalk(BinaryTree<T> T) {
        if (T.root == null) {
            return;
        }
        Stack<BinaryTree<T>.Node> S = Stack.withLength(T.getSize());
        push(S, T.root);
        while (!stackEmpty(S)) {
            BinaryTree<T>.Node x = pop(S);
            if (x.right != null) {
                push(S, x.right);
            }
            System.out.println(x.key);
            if (x.left != null) {
                push(S, x.left);
            }
        }
    }

    // solution of 10.4-4
    public static <T> void treeWalk(MultiaryTree<T>.Node x) {
        if (x != null) {
            System.out.println(x.key);
            treeWalk(x.leftChild);
            treeWalk(x.rightSibling);
        }
    }

    // solution of 10.4-5
    private static <T> BinaryTree<T>.Node stacklessInorderVisit(BinaryTree<T>.Node x) {
        System.out.println(x.key);
        if (x.right != null) {
            return x.right;
        }
        return x.p;
    }

    // solution of 10.4-5
    public static <T> void stacklessInorderTreeWalk(BinaryTree<T> T) {
        BinaryTree<T>.Node prev = null;
        BinaryTree<T>.Node curr = T.root;
        BinaryTree<T>.Node next = null;
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

    // problem 10-3
    public static <T extends Comparable> Integer compactListSearch(MultipleArrayList<T> L, int n, T k) {
        Integer i = L.L;
        while (i != null && less(L.key.at(i), k)) {
            int j = Chapter5.random(1, n);
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
