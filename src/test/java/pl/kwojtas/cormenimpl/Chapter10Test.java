package pl.kwojtas.cormenimpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;

public class Chapter10Test {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpOutStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanupOutStream() {
        System.setOut(null);
    }

    private String[] splitOutContent() {
        return outContent.toString().split(System.getProperty("line.separator"));
    }

    @Test
    public void shouldDetectEmptyStack() {
        // given
        Stack<String> stack = Stack.withLength(6);

        // when
        boolean actualEmpty = Chapter10.stackEmpty(stack);

        // then
        assertTrue(actualEmpty);
    }

    @Test
    public void shouldDetectNonemptyStack() {
        // given
        Stack<String> stack = Stack.withLength(6);
        stack.top = 4;

        // when
        boolean actualEmpty = Chapter10.stackEmpty(stack);

        // then
        assertFalse(actualEmpty);
    }

    @Test
    public void shouldInsertToStack() {
        // given
        Stack<String> stack = Stack.withLength(6);
        stack.top = 4;
        String element = "xyz";
        int topBeforeInserting = stack.top;

        // when
        Chapter10.push(stack, element);

        // then
        assertEquals(topBeforeInserting + 1, stack.top);
        assertEquals(element, stack.at(stack.top));
    }

    @Test
     public void shouldRemoveFromStack() {
        // given
        Stack<String> stack = Stack.withLength(6);
        stack.set(4, "xyz");
        stack.top = 4;
        int topBeforeRemoving = stack.top;
        String expectedElement = "xyz";

        // when
        String actualElement = Chapter10.pop(stack);

        // then
        assertEquals(topBeforeRemoving - 1, stack.top);
        assertEquals(expectedElement, actualElement);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingFromEmptyStack() {
        // given
        Stack<String> stack = Stack.withLength(6);

        try {
            // when
            Chapter10.pop(stack);
        } catch (RuntimeException e) {
            // then
            assertEquals("underflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldInsertToQueue() {
        // given
        Queue<String> queue = Queue.withLength(6);
        queue.head = 4;
        queue.tail = 2;
        String element = "xyz";
        int tailBeforeInserting = queue.tail;

        // when
        Chapter10.enqueue(queue, element);

        // then
        assertEquals(tailBeforeInserting + 1, queue.tail);
        assertEquals(element, queue.at(queue.tail - 1));
    }

    @Test
    public void shouldInsertToQueueWithTailEqualsLength() {
        // given
        Queue<String> queue = Queue.withLength(6);
        queue.head = 4;
        queue.tail = 6;
        String element = "xyz";

        // when
        Chapter10.enqueue(queue, element);

        // then
        assertEquals(1, queue.tail);
        assertEquals(element, queue.at(queue.length));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInsertingToFullQueue() {
        // given
        Queue<String> queue = Queue.withLength(6);
        queue.head = 3;
        queue.tail = 2;
        String element = "xyz";

        try {
            // when
            Chapter10.enqueue(queue, element);
        } catch (RuntimeException e) {
            // then
            assertEquals("overflow", e.getMessage());
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInsertingToFullQueueWithTailEqualsLength() {
        // given
        Queue<String> queue = Queue.withLength(6);
        queue.head = 1;
        queue.tail = 6;
        String element = "xyz";

        try {
            // when
            Chapter10.enqueue(queue, element);
        } catch (RuntimeException e) {
            // then
            assertEquals("overflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldRemoveFromQueue() {
        // given
        Queue<String> queue = Queue.withLength(6);
        queue.set(4, "xyz");
        queue.head = 4;
        queue.tail = 6;
        int headBeforeRemoving = queue.head;
        String expectedElement = "xyz";

        // when
        String actualElement = Chapter10.dequeue(queue);

        // then
        assertEquals(headBeforeRemoving + 1, queue.head);
        assertEquals(expectedElement, actualElement);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingFromEmptyQueue() {
        // given
        Queue<String> queue = Queue.withLength(6);

        try {
            // when
            Chapter10.dequeue(queue);
        } catch (RuntimeException e) {
            // then
            assertEquals("underflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldDetectEmptyQueue() {
        // given
        Queue<String> queue = Queue.withLength(6);

        // when
        boolean actualEmpty = Chapter10.queueEmpty(queue);

        // then
        assertTrue(actualEmpty);
    }

    @Test
    public void shouldDetectNonemptyQueue() {
        // given
        Queue<String> queue = Queue.withLength(6);
        queue.head = 2;
        queue.tail = 5;

        // when
        boolean actualEmpty = Chapter10.queueEmpty(queue);

        // then
        assertFalse(actualEmpty);
    }

    @Test
    public void shouldInsertToFirstStackOfDoubleStack() {
        // given
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.top1 = 2;
        doubleStack.top2 = 4;
        String element = "xyz";
        int top1BeforeInserting = doubleStack.top1;

        // when
        Chapter10.firstStackPush(doubleStack, element);

        // then
        assertEquals(top1BeforeInserting + 1, doubleStack.top1);
        assertEquals(element, doubleStack.at(doubleStack.top1));
    }

    @Test
    public void shouldRemoveFromFirstStackOfDoubleStack() {
        // given
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.set(2, "xyz");
        doubleStack.top1 = 2;
        doubleStack.top2 = 4;
        int top1BeforeRemoving = doubleStack.top1;
        String expectedElement = "xyz";

        // when
        String actualElement = Chapter10.firstStackPop(doubleStack);

        // then
        assertEquals(top1BeforeRemoving - 1, doubleStack.top1);
        assertEquals(expectedElement, actualElement);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingFromEmptyFirstStackOfDoubleStack() {
        // given
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.top2 = 3;

        try {
            // when
            Chapter10.firstStackPop(doubleStack);
        } catch (RuntimeException e) {
            // then
            assertEquals("underflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldInsertToSecondStackOfDoubleStack() {
        // given
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.top1 = 2;
        doubleStack.top2 = 4;
        String element = "xyz";
        int top2BeforeInserting = doubleStack.top2;

        // when
        Chapter10.secondStackPush(doubleStack, element);

        // then
        assertEquals(top2BeforeInserting - 1, doubleStack.top2);
        assertEquals(element, doubleStack.at(doubleStack.top2));
    }

    @Test
    public void shouldRemoveFromSecondStackOfDoubleStack() {
        // given
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.set(4, "xyz");
        doubleStack.top1 = 2;
        doubleStack.top2 = 4;
        int top2BeforeRemoving = doubleStack.top2;
        String expectedElement = "xyz";

        // when
        String actualElement = Chapter10.secondStackPop(doubleStack);

        // then
        assertEquals(top2BeforeRemoving + 1, doubleStack.top2);
        assertEquals(expectedElement, actualElement);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingFromEmptySecondStackOfDoubleStack() {
        // given
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.top1 = 4;

        try {
            // when
            Chapter10.secondStackPop(doubleStack);
        } catch (RuntimeException e) {
            // then
            assertEquals("underflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldInsertToBeginningOfDeque() {
        // given
        Deque<String> deque = Deque.withLength(6);
        deque.head = 2;
        deque.tail = 4;
        int headBeforeInserting = deque.head;
        String element = "xyz";

        // when
        Chapter10.headEnqueue(deque, element);

        // then
        assertEquals(headBeforeInserting - 1, deque.head);
        assertEquals(element, deque.at(deque.head));
    }

    @Test
    public void shouldInsertToBeginningOfDequeWithHeadEquals1() {
        // given
        Deque<String> deque = Deque.withLength(6);
        deque.head = 1;
        deque.tail = 4;
        String element = "xyz";

        // when
        Chapter10.headEnqueue(deque, element);

        // then
        assertEquals(deque.length, deque.head);
        assertEquals(element, deque.at(deque.head));
    }

    @Test
    public void shouldRemoveFromBeginningOfDeque() {
        // given
        Deque<String> deque = Deque.withLength(6);
        deque.set(2, "xyz");
        deque.head = 2;
        deque.tail = 4;
        int headBeforeInserting = deque.head;
        String expectedElement = "xyz";

        // when
        String actualElement = Chapter10.headDequeue(deque);

        // then
        assertEquals(headBeforeInserting + 1, deque.head);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldRemoveFromBeginningOfDequeWithHeadEqualsLength() {
        // given
        Deque<String> deque = Deque.withLength(6);
        deque.set(6, "xyz");
        deque.head = 6;
        deque.tail = 4;
        String expectedElement = "xyz";

        // when
        String actualElement = Chapter10.headDequeue(deque);

        // then
        assertEquals(1, deque.head);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldInsertToEndOfDeque() {
        // given
        Deque<String> deque = Deque.withLength(6);
        deque.head = 2;
        deque.tail = 4;
        int tailBeforeInserting = deque.tail;
        String element = "xyz";

        // when
        Chapter10.tailEnqueue(deque, element);

        // then
        assertEquals(tailBeforeInserting + 1, deque.tail);
        assertEquals(element, deque.at(deque.tail - 1));
    }

    @Test
    public void shouldInsertToEndOfDequeWithTailEqualsLength() {
        // given
        Deque<String> deque = Deque.withLength(6);
        deque.head = 2;
        deque.tail = 6;
        String element = "xyz";

        // when
        Chapter10.tailEnqueue(deque, element);

        // then
        assertEquals(1, deque.tail);
        assertEquals(element, deque.at(deque.length));
    }

    @Test
    public void shouldRemoveFromEndOfDeque() {
        // given
        Deque<String> deque = Deque.withLength(6);
        deque.set(3, "xyz");
        deque.head = 2;
        deque.tail = 4;
        int tailBeforeInserting = deque.tail;
        String expectedElement = "xyz";

        // when
        String actualElement = Chapter10.tailDequeue(deque);

        // then
        assertEquals(tailBeforeInserting - 1, deque.tail);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldRemoveFromEndOfDequeWithTailEquals1() {
        // given
        Deque<String> deque = Deque.withLength(6);
        deque.set(6, "xyz");
        deque.head = 2;
        deque.tail = 1;
        String expectedElement = "xyz";

        // when
        String actualElement = Chapter10.tailDequeue(deque);

        // then
        assertEquals(deque.length, deque.tail);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldInsertToQueueOnStacks() {
        // given
        Stack<String> stack1 = Stack.withLength(4);
        Stack<String> stack2 = Stack.withLength(4);
        stack1.top = 2;
        int topBeforeInserting = stack1.top;
        String element = "xyz";

        // when
        Chapter10.enqueueOnStacks(stack1, stack2, element);

        // then
        assertEquals(topBeforeInserting + 1, stack1.top);
        assertEquals(element, stack1.at(stack1.top));
    }

    @Test
    public void shouldRemoveFromQueueOnStacks() {
        // given
        Stack<String> stack1 = Stack.withLength(4);
        Stack<String> stack2 = Stack.withLength(4);
        stack1.set(1, "xyz");
        stack1.top = 2;
        int topBeforeInserting = stack1.top;
        String expectedElement = "xyz";

        // when
        String actualElement = Chapter10.dequeueOnStacks(stack1, stack2);

        // then
        assertEquals(topBeforeInserting - 1, stack1.top);
        assertEquals(expectedElement, actualElement);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingFromEmptyQueueOnStacks() {
        // given
        Stack<String> stack1 = Stack.withLength(4);
        Stack<String> stack2 = Stack.withLength(4);

        try {
            // when
            Chapter10.dequeueOnStacks(stack1, stack2);
        } catch (RuntimeException e) {
            // then
            assertEquals("underflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldInsertToStackOnQueues() {
        // given
        Queue<String> queue1 = Queue.withLength(4);
        Queue<String> queue2 = Queue.withLength(4);
        queue1.head = 4;
        queue1.tail = 2;
        int tailBeforeInserting = queue1.tail;
        String element = "xyz";

        // when
        Chapter10.pushOnQueues(queue1, queue2, element);

        // then
        assertEquals(tailBeforeInserting + 1, queue1.tail);
        assertEquals(element, queue1.at(queue1.tail - 1));
    }

    @Test
    public void shouldRemoveFromStackOnQueues() {
        // given
        Queue<String> queue1 = Queue.withLength(4);
        Queue<String> queue2 = Queue.withLength(4);
        queue1.set(1, "xyz");
        queue1.head = 4;
        queue1.tail = 2;
        int tailBeforeInserting = queue1.tail;
        int stackSize = queue1.tail - queue1.head + queue1.length;
        String expectedElement = "xyz";

        // when
        String actualElement = Chapter10.popOnQueues(queue1, queue2);

        // then
        assertEquals(tailBeforeInserting, queue1.head);
        assertEquals(tailBeforeInserting + stackSize - 1, queue1.tail);
        assertEquals(expectedElement, actualElement);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingFromEmptyStackOnQueues() {
        // given
        Queue<String> queue1 = Queue.withLength(4);
        Queue<String> queue2 = Queue.withLength(4);

        try {
            // when
            Chapter10.popOnQueues(queue1, queue2);
        } catch (RuntimeException e) {
            // then
            assertEquals("underflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldFindKeyOnList() {
        // given
        List<Integer> list = new List<>(5,7,9,2,6,1,6,6,3,1,7,8);
        int keyToFind = 6;

        // when
        List<Integer>.Node actualNode = Chapter10.listSearch(list, keyToFind);

        // then
        assertNotNull(actualNode);
        assertEquals(new Integer(keyToFind), actualNode.key);
    }

    @Test
    public void shouldNotFindNonexistentKeyOnList() {
        // given
        List<Integer> list = new List<>(5,7,9,2,6,1,6,6,3,1,7,8);
        int keyToFind = 4;

        // when
        List<Integer>.Node actualNode = Chapter10.listSearch(list, keyToFind);

        // then
        assertNull(actualNode);
    }

    @Test
    public void shouldInsertElementOntoList() {
        // given
        List<Integer> list = new List<>(5,7,9,2,6,1,6,6,3,1,7,8);
        List<Integer> original = new List<>(list);
        int keyToInsert = 3;

        // when
        Chapter10.listInsert(list, list.new Node(keyToInsert));

        // then
        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertElementInsertedIntoBeginningOfList(keyToInsert, listArray, originalArray);
    }

    @Test
    public void shouldDeleteElementFromList() {
        // given
        List<Integer> list = new List<>(5,7,9,2,6,1,6,6,3,1,7,8);
        List<Integer> original = new List<>(list);
        int keyToDelete = 6;
        List<Integer>.Node nodeToDelete = list.head.next.next.next.next; // first element on the list with key = 6

        // when
        Chapter10.listDelete(list, nodeToDelete);

        // then
        assertElementDeletedFromList(keyToDelete, list.toArray(), original.toArray());
    }

    private void assertElementDeletedFromList(int deletedKey, Array<Integer> listArray, Array<Integer> originalArray) {
        assertEquals(originalArray.length - 1, listArray.length);
        int i = 0;
        boolean elementsEqual = true;
        while (i + 1 <= listArray.length && elementsEqual) {
            i++;
            elementsEqual = listArray.at(i).equals(originalArray.at(i));
        }
        assertEquals(new Integer(deletedKey), originalArray.at(i));
        while (i <= listArray.length) {
            assertEquals(listArray.at(i), originalArray.at(i + 1));
            i++;
        }
    }

    @Test
    public void shouldDeleteElementFromListWithSentinel() {
        // given
        ListWithSentinel<Integer> list = new ListWithSentinel<>(5,7,9,2,6,1,6,6,3,1,7,8);
        ListWithSentinel<Integer> original = new ListWithSentinel<>(list);
        int keyToDelete = 2;
        ListWithSentinel<Integer>.Node nodeToDelete = list.nil.next.next.next.next; // element with key = 2

        // when
        Chapter10.listDelete_(list, nodeToDelete);

        // then
        assertElementDeletedFromList(keyToDelete, list.toArray(), original.toArray());
    }

    @Test
    public void shouldFindKeyOnListWithSentinel() {
        // given
        ListWithSentinel<Integer> list = new ListWithSentinel<>(5,7,9,2,6,1,6,6,3,1,7,8);
        int keyToFind = 6;

        // when
        ListWithSentinel<Integer>.Node actualNode = Chapter10.listSearch_(list, keyToFind);

        // then
        assertNotNull(actualNode);
        assertEquals(new Integer(keyToFind), actualNode.key);
    }

    @Test
    public void shouldNotFindNonexistentKeyOnListWithSentinel() {
        // given
        ListWithSentinel<Integer> list = new ListWithSentinel<>(5,7,9,2,6,1,6,6,3,1,7,8);
        int keyToFind = 4;

        // when
        ListWithSentinel<Integer>.Node actualNode = Chapter10.listSearch_(list, keyToFind);

        // then
        assertEquals(list.nil, actualNode);
    }

    @Test
    public void shouldInsertElementOntoListWithSentinel() {
        // given
        ListWithSentinel<Integer> list = new ListWithSentinel<>(5,7,9,2,6,1,6,6,3,1,7,8);
        ListWithSentinel<Integer> original = new ListWithSentinel<>(list);
        int keyToInsert = 3;

        // when
        Chapter10.listInsert_(list, list.new Node(keyToInsert));

        // then
        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertElementInsertedIntoBeginningOfList(keyToInsert, listArray, originalArray);
    }

    private void assertElementInsertedIntoBeginningOfList(int keyToInsert, Array<Integer> listArray, Array<Integer> originalArray) {
        assertEquals(originalArray.length + 1, listArray.length);
        assertEquals(new Integer(keyToInsert), listArray.at(1));
        for (int i = 2; i <= listArray.length; i++) {
            assertEquals(originalArray.at(i - 1), listArray.at(i));
        }
    }

    @Test
    public void shouldInsertElementOntoSinglyLinkedList() {
        // given
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(5,7,9,2,6,1,6,6,3,1,7,8);
        SinglyLinkedList<Integer> original = new SinglyLinkedList<>(list);
        int keyToInsert = 3;

        // when
        Chapter10.singlyLinkedListInsert(list, list.new Node(keyToInsert));

        // then
        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertElementInsertedIntoBeginningOfList(keyToInsert, listArray, originalArray);
    }

    @Test
    public void shouldDeleteElementFromSinglyLinkedList() {
        // given
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(5,7,9,2,6,1,6,6,3,1,7,8);
        SinglyLinkedList<Integer> original = new SinglyLinkedList<>(list);
        int keyToDelete = 6;
        SinglyLinkedList<Integer>.Node nodeToDelete = list.head.next.next.next.next; // first element on the list with key = 6

        // when
        Chapter10.singlyLinkedListDelete(list, nodeToDelete);

        // then
        assertElementDeletedFromList(keyToDelete, list.toArray(), original.toArray());
    }

    @Test
    public void shouldInsertElementIntoStackOnSinglyLinkedList() {
        // given
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(5,7,9,2,6,1,6,6,3,1,7,8);
        SinglyLinkedList<Integer> original = new SinglyLinkedList<>(list);
        int keyToInsert = 3;

        // when
        Chapter10.singlyLinkedListPush(list, keyToInsert);

        // then
        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertElementInsertedIntoBeginningOfList(keyToInsert, listArray, originalArray);
    }

    @Test
    public void shouldDeleteElementFromStackOnSinglyLinkedList() {
        // given
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(5,7,9,2,6,1,6,6,3,1,7,8);
        SinglyLinkedList<Integer> original = new SinglyLinkedList<>(list);
        int expectedElement = 5;

        // when
        Integer actualElement = Chapter10.singlyLinkedListPop(list);

        // then
        assertEquals(new Integer(expectedElement), actualElement);
        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertEquals(originalArray.length - 1, listArray.length);
        for (int i = 1; i <= listArray.length; i++) {
            assertEquals(originalArray.at(i + 1), listArray.at(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenDeletingFromEmptyStackOnSinglyLinkedList() {
        // given
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        try {
            // when
            Chapter10.singlyLinkedListPop(list);
        } catch (RuntimeException e) {
            // then
            assertEquals("underflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldInsertElementIntoQueueOnSinglyLinkedListWithTail() {
        // given
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>(5,7,9,2,6,1,6,6,3,1,7,8);
        SinglyLinkedListWithTail<Integer> original = new SinglyLinkedListWithTail<>(list);
        int keyToInsert = 3;

        // when
        Chapter10.singlyLinkedListEnqueue(list, keyToInsert);

        // then
        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertEquals(originalArray.length + 1, listArray.length);
        assertEquals(new Integer(keyToInsert), listArray.at(listArray.length));
        for (int i = 1; i <= listArray.length - 1; i++) {
            assertEquals(originalArray.at(i), listArray.at(i));
        }
    }

    @Test
    public void shouldDeleteElementFromQueueOnSinglyLinkedListWithTail() {
        // given
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>(5,7,9,2,6,1,6,6,3,1,7,8);
        SinglyLinkedListWithTail<Integer> original = new SinglyLinkedListWithTail<>(list);
        int expectedElement = 5;

        // when
        Integer actualElement = Chapter10.singlyLinkedListDequeue(list);

        // then
        assertEquals(new Integer(expectedElement), actualElement);
        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertEquals(originalArray.length - 1, listArray.length);
        for (int i = 1; i <= listArray.length; i++) {
            assertEquals(originalArray.at(i + 1), listArray.at(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenDeletingFromEmptyQueueOnSinglyLinkedListWithTail() {
        // given
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>();

        try {
            // when
            Chapter10.singlyLinkedListDequeue(list);
        } catch (RuntimeException e) {
            // then
            assertEquals("underflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldInsertElementOntoCircularList() {
        // given
        CircularList<Integer> list = new CircularList<>(5,7,9,2,6,1,6,6,3,1,7,8);
        CircularList<Integer> original = new CircularList<>(list);
        int keyToInsert = 3;

        // when
        Chapter10.circularListInsert(list, list.new Node(keyToInsert));

        // then
        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertEquals(originalArray.length + 1, listArray.length);
        assertEquals(originalArray.at(1), listArray.at(1));
        assertEquals(new Integer(keyToInsert), listArray.at(2));
        for (int i = 3; i <= listArray.length; i++) {
            assertEquals(originalArray.at(i - 1), listArray.at(i));
        }
    }

    @Test
    public void shouldDeleteElementFromCircularList() {
        // given
        CircularList<Integer> list = new CircularList<>(5,7,9,2,6,1,6,6,3,1,7,8);
        CircularList<Integer> original = new CircularList<>(list);
        int keyToDelete = 6;
        CircularList<Integer>.Node nodeToDelete = list.head.next.next.next.next; // first element on the list with key = 6

        // when
        Chapter10.circularListDelete(list, nodeToDelete);

        // then
        assertElementDeletedFromList(keyToDelete, list.toArray(), original.toArray());
    }

    @Test
    public void shouldFindKeyOnCircularList() {
        // given
        CircularList<Integer> list = new CircularList<>(5,7,9,2,6,1,6,6,3,1,7,8);
        int keyToFind = 6;

        // when
        CircularList<Integer>.Node actualNode = Chapter10.circularListSearch(list, keyToFind);

        // then
        assertNotNull(actualNode);
        assertEquals(new Integer(keyToFind), actualNode.key);
    }

    @Test
    public void shouldNotFindNonexistentKeyOnCircularList() {
        // given
        CircularList<Integer> list = new CircularList<>(5,7,9,2,6,1,6,6,3,1,7,8);
        int keyToFind = 4;

        // when
        CircularList<Integer>.Node actualNode = Chapter10.circularListSearch(list, keyToFind);

        // then
        assertNull(actualNode);
    }

    @Test
    public void shouldUnionCircularLists() {
        // given
        CircularList<Integer> list1 = new CircularList<>(12,44,26,20,67,4,21,66,35,51,13);
        CircularList<Integer> list2 = new CircularList<>(55,23,2,74,30,47);
        Array<Integer> originalArray1 = list1.toArray();
        Array<Integer> originalArray2 = list2.toArray();

        // when
        CircularList<Integer> actualMergedLists = Chapter10.circularListsUnion(list1, list2);

        // then
        Array<Integer> actualMergedArray = actualMergedLists.toArray();
        Array<Integer> expectedMergedArray = Array.withLength(originalArray1.length + originalArray2.length);
        for (int i = 1; i <= originalArray1.length; i++) {
            expectedMergedArray.set(i, originalArray1.at(i));
        }
        for (int i = 1; i <= originalArray2.length; i++) {
            expectedMergedArray.set(originalArray1.length + i, originalArray2.at(i));
        }
        assertShuffled(expectedMergedArray, actualMergedArray);
    }

    @Test
    public void shouldReverseSinglyLinkedList() {
        // given
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(5,7,9,2,6,1,6,6,3,1,7,8);
        SinglyLinkedList<Integer> original = new SinglyLinkedList<>(list);

        // when
        Chapter10.singlyLinkedListReverse(list);

        // then
        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertEquals(originalArray.length, listArray.length);
        int n = listArray.length;
        for (int i = 1; i <= n; i++) {
            assertEquals(originalArray.at(i), listArray.at(n - i + 1));
        }
    }

    @Test
    public void shouldAllocateObjectOnMultipleArrayList() {
        // given
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>();
        multipleArrayList.next = new Array<>(4,1,null,null,3);
        multipleArrayList.prev = new Array<>(2,null,5,1,null);
        multipleArrayList.L = 2;
        multipleArrayList.free = 5;

        // when
        int actualNewPosition = Chapter10.allocateObject(multipleArrayList);

        // then
        assertEquals(5, actualNewPosition);
        assertEquals(new Integer(3), multipleArrayList.free);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAllocateObjectOnFullMultipleArrayList() {
        // given
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>();
        multipleArrayList.next = new Array<>(4,1,null,5,3);
        multipleArrayList.prev = new Array<>(2,null,5,1,4);
        multipleArrayList.L = 2;
        multipleArrayList.free = null;

        try {
            // when
            Chapter10.allocateObject(multipleArrayList);
        } catch (RuntimeException e) {
            // then
            assertEquals("out of space", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldFreeObjectOnMultipleArrayList() {
        // given
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>();
        multipleArrayList.next = new Array<>(4,1,null,null,3);
        multipleArrayList.prev = new Array<>(2,null,5,1,null);
        multipleArrayList.L = 2;
        multipleArrayList.free = 5;

        // when
        Chapter10.freeObject(multipleArrayList, 1);

        // then
        assertEquals(new Integer(1), multipleArrayList.free);
        assertEquals(new Integer(5), multipleArrayList.next.at(1));
    }

    @Test
    public void shouldAllocateObjectOnSingleArrayList() {
        // given
        SingleArrayList singleArrayList = new SingleArrayList();
        singleArrayList.set(new Array<>(100,10,4,200,1,null,300,null,13,400,null,1,500,7,null));
        singleArrayList.L = 4;
        singleArrayList.free = 13;

        // when
        int actualNewPosition = Chapter10.singleArrayAllocateObject(singleArrayList);

        // then
        assertEquals(13, actualNewPosition);
        assertEquals(new Integer(7), singleArrayList.free);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAllocateObjectOnFullSingleArrayList() {
        // given
        SingleArrayList singleArrayList = new SingleArrayList();
        singleArrayList.set(new Array<>(100,10,4,200,1,null,300,null,13,400,13,1,500,7,10));
        singleArrayList.L = 4;
        singleArrayList.free = null;

        try {
            // when
            Chapter10.singleArrayAllocateObject(singleArrayList);
        } catch (RuntimeException e) {
            // then
            assertEquals("out of space", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldFreeObjectOnSingleArrayList() {
        // given
        SingleArrayList singleArrayList = new SingleArrayList();
        singleArrayList.set(new Array<>(100,10,4,200,1,null,300,null,13,400,null,1,500,7,null));
        singleArrayList.L = 4;
        singleArrayList.free = 13;

        // when
        Chapter10.singleArrayFreeObject(singleArrayList, 1);

        // then
        assertEquals(new Integer(1), singleArrayList.free);
        assertEquals(new Integer(13), singleArrayList.at(2));
    }

    @Test
    public void shouldAllocateObjectOnCompactList() {
        // given
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>();
        multipleArrayList.next = new Array<>(3,1,null,5,null);
        multipleArrayList.prev = new Array<>(2,null,1,null,null);
        multipleArrayList.L = 2;
        multipleArrayList.free = 4;

        // when
        int actualNewPosition = Chapter10.compactListAllocateObject(multipleArrayList);

        // then
        assertEquals(4, actualNewPosition);
        assertEquals(new Integer(5), multipleArrayList.free);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAllocateObjectOnFullCompactList() {
        // given
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>();
        multipleArrayList.next = new Array<>(3,1,5,null,4);
        multipleArrayList.prev = new Array<>(2,null,1,null,null);
        multipleArrayList.L = 2;
        multipleArrayList.free = null;

        try {
            // when
            Chapter10.compactListAllocateObject(multipleArrayList);
        } catch (RuntimeException e) {
            // then
            assertEquals("out of space", e.getMessage());
            throw e;
        }
    }

    @Ignore
    @Test
    public void shouldFreeObjectOnCompactList() {
        // given
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>();
        multipleArrayList.key  = new Array<>("aaa","bbb","ccc","ddd","eee");
        multipleArrayList.next = new Array<>(3,1,null,5,null);
        multipleArrayList.prev = new Array<>(2,null,1,null,null);
        multipleArrayList.L = 2;
        multipleArrayList.free = 4;

        // when
        Chapter10.compactListFreeObject(multipleArrayList, 1);

        // then
        assertEquals(new Integer(3), multipleArrayList.free);
        assertEquals(new Integer(4), multipleArrayList.next.at(3));
        assertEquals("ccc", multipleArrayList.key.at(1));
        assertNull(multipleArrayList.next.at(1));
    }

    @Ignore
    @Test
    public void shouldCompactifyList() {
        // given
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>();
        multipleArrayList.next = new Array<>(9,8,null,1,2,5,null,10,7,3);
        multipleArrayList.prev = new Array<>(null,5,10,null,6,null,null,2,null,8);
        multipleArrayList.key = Array.withLength(10);
        multipleArrayList.L = 6;
        multipleArrayList.free = 4;

        // when
        Chapter10.compactifyList(multipleArrayList);

        // then
        int m = multipleArrayList.getLength();
        Integer x = multipleArrayList.L;
        while (x != null) {
            assertTrue(x <= m);
            x = multipleArrayList.next.at(x);
        }
        x = multipleArrayList.free;
        while (x != null) {
            assertTrue(x > m);
            x = multipleArrayList.next.at(x);
        }
    }

    @Test
    public void shouldPrintOutEmptyTreeInPreorder() {
        // given

        // when
        Chapter10.iterativePreorderTreeWalk(new BinaryTree<>());

        // then
        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonEmptyTreeInPreorder() {
        // given
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree<Integer>.Node x1 = tree.new Node(10);
        BinaryTree<Integer>.Node x2 = tree.new Node(4);
        BinaryTree<Integer>.Node x3 = tree.new Node(14);
        BinaryTree<Integer>.Node x4 = tree.new Node(1);
        BinaryTree<Integer>.Node x5 = tree.new Node(11);
        BinaryTree<Integer>.Node x6 = tree.new Node(19);
        BinaryTree<Integer>.Node x7 = tree.new Node(20);
        tree.root = x1;
        x1.left = x2; x2.p = x1; x1.right = x3; x3.p = x1;
        x2.left = x4; x4.p = x2;
        x3.left = x5; x5.p = x3; x3.right = x6; x6.p = x3;
        x6.right = x7; x7.p = x6;

        // when
        Chapter10.iterativePreorderTreeWalk(tree);

        // then
        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"10","4","1","14","11","19","20"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldPrintOutEmptyMultiaryTree() {
        // given

        // when
        Chapter10.treeWalk(null);

        // then
        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonEmptyMultiaryTree() {
        // given
        MultiaryTree<Integer> tree = new MultiaryTree<>();
        MultiaryTree<Integer>.Node x1 = tree.new Node(1);
        MultiaryTree<Integer>.Node x2 = tree.new Node(2);
        MultiaryTree<Integer>.Node x3 = tree.new Node(3);
        MultiaryTree<Integer>.Node x4 = tree.new Node(4);
        MultiaryTree<Integer>.Node x5 = tree.new Node(5);
        MultiaryTree<Integer>.Node x6 = tree.new Node(6);
        MultiaryTree<Integer>.Node x7 = tree.new Node(7);
        MultiaryTree<Integer>.Node x8 = tree.new Node(8);
        MultiaryTree<Integer>.Node x9 = tree.new Node(9);
        MultiaryTree<Integer>.Node x10 = tree.new Node(10);
        tree.root = x1;
        x1.leftChild = x2; x2.p = x1;
        x2.leftChild = x5; x5.p = x2; x2.rightSibling = x3; x3.p = x1;
        x3.rightSibling = x4; x4.p = x1;
        x4.leftChild = x8; x8.p = x4;
        x5.rightSibling = x6; x6.p = x2;
        x6.leftChild = x10; x10.p = x6; x6.rightSibling = x7; x7.p = x2;
        x8.rightSibling = x9; x9.p = x4;

        // when
        Chapter10.treeWalk(tree.root);

        // then
        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1","2","5","6","10","7","3","4","8","9"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldPrintOutEmptyTreeInInorder() {
        // given

        // when
        Chapter10.stacklessInorderTreeWalk(new BinaryTree<>());

        // then
        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonEmptyTreeInInorder() {
        // given
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree<Integer>.Node x1 = tree.new Node(10);
        BinaryTree<Integer>.Node x2 = tree.new Node(4);
        BinaryTree<Integer>.Node x3 = tree.new Node(14);
        BinaryTree<Integer>.Node x4 = tree.new Node(1);
        BinaryTree<Integer>.Node x5 = tree.new Node(11);
        BinaryTree<Integer>.Node x6 = tree.new Node(19);
        BinaryTree<Integer>.Node x7 = tree.new Node(20);
        tree.root = x1;
        x1.left = x2; x2.p = x1; x1.right = x3; x3.p = x1;
        x2.left = x4; x4.p = x2;
        x3.left = x5; x5.p = x3; x3.right = x6; x6.p = x3;
        x6.right = x7; x7.p = x6;

        // when
        Chapter10.stacklessInorderTreeWalk(tree);

        // then
        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1","4","10","11","14","19","20"};
        Assert.assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldMakeNewHeapOnSortedLists() {
        // given

        // when
        List<Integer> actualHeap = Chapter10.sortedListMakeMinHeap();

        // then
        assertEquals(0, actualHeap.getLength());
    }

    @Test
    public void shouldInsertElementAtTheBeginningOfHeapOnSortedLists() {
        // given
        List<Integer> sortedList = new List<>(2,4,8,8,13,14,15);
        int key = 1;
        List<Integer> expectedAfterInsertion = new List<>(1,2,4,8,8,13,14,15);

        // when
        Chapter10.sortedListMinHeapInsert(sortedList, key);

        // then
        assertArrayEquals(expectedAfterInsertion.toArray(), sortedList.toArray());
    }

    @Test
    public void shouldInsertElementAtTheEndOfHeapOnSortedLists() {
        // given
        List<Integer> sortedList = new List<>(2,4,8,8,13,14,15);
        int key = 20;
        List<Integer> expectedAfterInsertion = new List<>(2,4,8,8,13,14,15,20);

        // when
        Chapter10.sortedListMinHeapInsert(sortedList, key);

        // then
        assertArrayEquals(expectedAfterInsertion.toArray(), sortedList.toArray());
    }

    @Test
    public void shouldInsertElementInTheMiddleOfHeapOnSortedLists() {
        // given
        List<Integer> sortedList = new List<>(2,4,8,8,13,14,15);
        int key = 12;
        List<Integer> expectedAfterInsertion = new List<>(2,4,8,8,12,13,14,15);

        // when
        Chapter10.sortedListMinHeapInsert(sortedList, key);

        // then
        assertArrayEquals(expectedAfterInsertion.toArray(), sortedList.toArray());
    }

    @Test
    public void shouldGetMinimumFromHeapOnSortedLists() {
        // given
        List<Integer> sortedList = new List<>(2,4,8,8,13,14,15);
        List<Integer> original = new List<>(sortedList);
        int expectedMinimum = 2;

        // when
        int actualMinimum = Chapter10.sortedListHeapMinimum(sortedList);

        // then
        assertEquals(expectedMinimum, actualMinimum);
        assertArrayEquals(original.toArray(), sortedList.toArray());
    }

    @Test
    public void shouldExtractMinimumFromHeapOnSortedLists() {
        // given
        List<Integer> sortedList = new List<>(2,4,8,8,13,14,15);
        List<Integer> expectedAfterExtraction = new List<>(4,8,8,13,14,15);
        int expectedMinimum = 2;

        // when
        int actualMinimum = Chapter10.sortedListHeapExtractMin(sortedList);

        // then
        assertEquals(expectedMinimum, actualMinimum);
        assertArrayEquals(expectedAfterExtraction.toArray(), sortedList.toArray());
    }

    @Test
    public void shouldMergeHeapsOnSortedLists() {
        // given
        List<Integer> sortedList1 = new List<>(2,4,8,8,13,14,15);
        List<Integer> sortedList2 = new List<>(1,2,5,6,8,12,14,14);
        List<Integer> expectedMerged = new List<>(1,2,2,4,5,6,8,8,8,12,13,14,14,14,15);

        // when
        List<Integer> actualMerged = Chapter10.sortedListMinHeapUnion(sortedList1, sortedList2);

        // then
        assertArrayEquals(expectedMerged.toArray(), actualMerged.toArray());
    }

    @Test
    public void shouldFindElementInCompactList() {
        // given
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>();
        multipleArrayList.next = new Array<>(6,4,null,5,1,3,8,9,10,11);
        multipleArrayList.prev = new Array<>(5,null,6,2,4,1,null,null,null,null);
        multipleArrayList.key  = new Array<>("ddd","aaa","fff","bbb","ccc","eee",null,null,null,null);
        multipleArrayList.L = 2;
        multipleArrayList.free = 7;

        // when
        Integer actualFoundPosition = Chapter10.compactListSearch(multipleArrayList, 6, "eee");

        // then
        assertEquals(new Integer(6), actualFoundPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementInCompactList() {
        // given
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>();
        multipleArrayList.next = new Array<>(6,4,null,5,1,3,8,9,10,11);
        multipleArrayList.prev = new Array<>(5,null,6,2,4,1,null,null,null,null);
        multipleArrayList.key  = new Array<>("ddd","aaa","fff","bbb","ccc","eee",null,null,null,null);
        multipleArrayList.L = 2;
        multipleArrayList.free = 7;

        // when
        Integer actualFoundPosition = Chapter10.compactListSearch(multipleArrayList, 6, "xxx");

        // then
        assertNull(actualFoundPosition);
    }

}
