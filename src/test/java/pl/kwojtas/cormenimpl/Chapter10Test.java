package pl.kwojtas.cormenimpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Chapter5.class})
public class Chapter10Test {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter10> constructor = Chapter10.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldDetectEmptyStack() {
        Stack<String> stack = Stack.withLength(6);

        boolean actualEmpty = Chapter10.stackEmpty(stack);

        assertTrue(actualEmpty);
    }

    @Test
    public void shouldDetectNonemptyStack() {
        Stack<String> stack = Stack.withLength(6);
        stack.top = 4;

        boolean actualEmpty = Chapter10.stackEmpty(stack);

        assertFalse(actualEmpty);
    }

    @Test
    public void shouldInsertToStack() {
        Stack<String> stack = Stack.withLength(6);
        stack.top = 4;
        String element = "xyz";
        int topBeforeInserting = stack.top;

        Chapter10.push(stack, element);

        assertEquals(topBeforeInserting + 1, stack.top);
        assertEquals(element, stack.at(stack.top));
    }

    @Test
    public void shouldDeleteFromStack() {
        Stack<String> stack = Stack.withLength(6);
        stack.set(4, "xyz");
        stack.top = 4;
        int topBeforeDeleting = stack.top;
        String expectedElement = "xyz";

        String actualElement = Chapter10.pop(stack);

        assertEquals(topBeforeDeleting - 1, stack.top);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingFromEmptyStack() {
        Stack<String> stack = Stack.withLength(6);

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("underflow");
        Chapter10.pop(stack);
    }

    @Test
    public void shouldInsertToQueue() {
        Queue<String> queue = Queue.withLength(6);
        queue.head = 4;
        queue.tail = 2;
        String element = "xyz";
        int tailBeforeInserting = queue.tail;

        Chapter10.enqueue(queue, element);

        assertEquals(tailBeforeInserting + 1, queue.tail);
        assertEquals(element, queue.at(queue.tail - 1));
    }

    @Test
    public void shouldInsertToQueueWithTailEqualsLength() {
        Queue<String> queue = Queue.withLength(6);
        queue.head = 4;
        queue.tail = 6;
        String element = "xyz";

        Chapter10.enqueue(queue, element);

        assertEquals(1, queue.tail);
        assertEquals(element, queue.at(queue.length));
    }

    @Test
    public void shouldThrowExceptionWhenInsertingToFullQueue() {
        Queue<String> queue = Queue.withLength(6);
        queue.head = 3;
        queue.tail = 2;
        String element = "xyz";

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("overflow");
        Chapter10.enqueue(queue, element);
    }

    @Test
    public void shouldThrowExceptionWhenInsertingToFullQueueWithTailEqualsLength() {
        Queue<String> queue = Queue.withLength(6);
        queue.head = 1;
        queue.tail = 6;
        String element = "xyz";

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("overflow");
        Chapter10.enqueue(queue, element);
    }

    @Test
    public void shouldDeleteFromQueue() {
        Queue<String> queue = Queue.withLength(6);
        queue.set(4, "xyz");
        queue.head = 4;
        queue.tail = 6;
        int headBeforeDeleting = queue.head;
        String expectedElement = "xyz";

        String actualElement = Chapter10.dequeue(queue);

        assertEquals(headBeforeDeleting + 1, queue.head);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingFromEmptyQueue() {
        Queue<String> queue = Queue.withLength(6);

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("underflow");
        Chapter10.dequeue(queue);
    }

    @Test
    public void shouldDetectEmptyQueue() {
        Queue<String> queue = Queue.withLength(6);

        boolean actualEmpty = Chapter10.queueEmpty(queue);

        assertTrue(actualEmpty);
    }

    @Test
    public void shouldDetectNonemptyQueue() {
        Queue<String> queue = Queue.withLength(6);
        queue.head = 2;
        queue.tail = 5;

        boolean actualEmpty = Chapter10.queueEmpty(queue);

        assertFalse(actualEmpty);
    }

    @Test
    public void shouldInsertToFirstStackOfDoubleStack() {
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.leftTop = 2;
        doubleStack.rightTop = 4;
        String element = "xyz";
        int top1BeforeInserting = doubleStack.leftTop;

        Chapter10.firstStackPush(doubleStack, element);

        assertEquals(top1BeforeInserting + 1, doubleStack.leftTop);
        assertEquals(element, doubleStack.at(doubleStack.leftTop));
    }

    @Test
    public void shouldDeleteFromFirstStackOfDoubleStack() {
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.set(2, "xyz");
        doubleStack.leftTop = 2;
        doubleStack.rightTop = 4;
        int top1BeforeDeleting = doubleStack.leftTop;
        String expectedElement = "xyz";

        String actualElement = Chapter10.firstStackPop(doubleStack);

        assertEquals(top1BeforeDeleting - 1, doubleStack.leftTop);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingFromEmptyFirstStackOfDoubleStack() {
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.rightTop = 3;

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("underflow");
        Chapter10.firstStackPop(doubleStack);
    }

    @Test
    public void shouldDetectEmptyFirstStackOfDoubleStack() {
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);

        boolean actualEmpty = Chapter10.firstStackEmpty(doubleStack);

        assertTrue(actualEmpty);
    }

    @Test
    public void shouldDetectNonemptyFirstStackOfDoubleStack() {
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.leftTop = 2;

        boolean actualEmpty = Chapter10.firstStackEmpty(doubleStack);

        assertFalse(actualEmpty);
    }

    @Test
    public void shouldInsertToSecondStackOfDoubleStack() {
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.leftTop = 2;
        doubleStack.rightTop = 4;
        String element = "xyz";
        int top2BeforeInserting = doubleStack.rightTop;

        Chapter10.secondStackPush(doubleStack, element);

        assertEquals(top2BeforeInserting - 1, doubleStack.rightTop);
        assertEquals(element, doubleStack.at(doubleStack.rightTop));
    }

    @Test
    public void shouldDeleteFromSecondStackOfDoubleStack() {
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.set(4, "xyz");
        doubleStack.leftTop = 2;
        doubleStack.rightTop = 4;
        int top2BeforeDeleting = doubleStack.rightTop;
        String expectedElement = "xyz";

        String actualElement = Chapter10.secondStackPop(doubleStack);

        assertEquals(top2BeforeDeleting + 1, doubleStack.rightTop);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingFromEmptySecondStackOfDoubleStack() {
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.leftTop = 4;

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("underflow");
        Chapter10.secondStackPop(doubleStack);
    }

    @Test
    public void shouldDetectEmptySecondStackOfDoubleStack() {
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);

        boolean actualEmpty = Chapter10.secondStackEmpty(doubleStack);

        assertTrue(actualEmpty);
    }

    @Test
    public void shouldDetectNonemptySecondStackOfDoubleStack() {
        DoubleStack<String> doubleStack = DoubleStack.withLength(6);
        doubleStack.rightTop = 3;

        boolean actualEmpty = Chapter10.secondStackEmpty(doubleStack);

        assertFalse(actualEmpty);
    }

    @Test
    public void shouldInsertToBeginningOfDeque() {
        Deque<String> deque = Deque.withLength(6);
        deque.head = 2;
        deque.tail = 4;
        int headBeforeInserting = deque.head;
        String element = "xyz";

        Chapter10.headEnqueue(deque, element);

        assertEquals(headBeforeInserting - 1, deque.head);
        assertEquals(element, deque.at(deque.head));
    }

    @Test
    public void shouldInsertToBeginningOfDequeWithHeadEquals1() {
        Deque<String> deque = Deque.withLength(6);
        deque.head = 1;
        deque.tail = 4;
        String element = "xyz";

        Chapter10.headEnqueue(deque, element);

        assertEquals(deque.length, deque.head);
        assertEquals(element, deque.at(deque.head));
    }

    @Test
    public void shouldDeleteFromBeginningOfDeque() {
        Deque<String> deque = Deque.withLength(6);
        deque.set(2, "xyz");
        deque.head = 2;
        deque.tail = 4;
        int headBeforeInserting = deque.head;
        String expectedElement = "xyz";

        String actualElement = Chapter10.headDequeue(deque);

        assertEquals(headBeforeInserting + 1, deque.head);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldDeleteFromBeginningOfDequeWithHeadEqualsLength() {
        Deque<String> deque = Deque.withLength(6);
        deque.set(6, "xyz");
        deque.head = 6;
        deque.tail = 4;
        String expectedElement = "xyz";

        String actualElement = Chapter10.headDequeue(deque);

        assertEquals(1, deque.head);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldInsertToEndOfDeque() {
        Deque<String> deque = Deque.withLength(6);
        deque.head = 2;
        deque.tail = 4;
        int tailBeforeInserting = deque.tail;
        String element = "xyz";

        Chapter10.tailEnqueue(deque, element);

        assertEquals(tailBeforeInserting + 1, deque.tail);
        assertEquals(element, deque.at(deque.tail - 1));
    }

    @Test
    public void shouldInsertToEndOfDequeWithTailEqualsLength() {
        Deque<String> deque = Deque.withLength(6);
        deque.head = 2;
        deque.tail = 6;
        String element = "xyz";

        Chapter10.tailEnqueue(deque, element);

        assertEquals(1, deque.tail);
        assertEquals(element, deque.at(deque.length));
    }

    @Test
    public void shouldDeleteFromEndOfDeque() {
        Deque<String> deque = Deque.withLength(6);
        deque.set(3, "xyz");
        deque.head = 2;
        deque.tail = 4;
        int tailBeforeInserting = deque.tail;
        String expectedElement = "xyz";

        String actualElement = Chapter10.tailDequeue(deque);

        assertEquals(tailBeforeInserting - 1, deque.tail);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldDeleteFromEndOfDequeWithTailEquals1() {
        Deque<String> deque = Deque.withLength(6);
        deque.set(6, "xyz");
        deque.head = 2;
        deque.tail = 1;
        String expectedElement = "xyz";

        String actualElement = Chapter10.tailDequeue(deque);

        assertEquals(deque.length, deque.tail);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldInsertToQueueOnStacks() {
        Stack<String> stack1 = Stack.withLength(4);
        Stack<String> stack2 = Stack.withLength(4);
        stack1.top = 2;
        int topBeforeInserting = stack1.top;
        String element = "xyz";

        Chapter10.enqueueOnStacks(stack1, stack2, element);

        assertEquals(topBeforeInserting + 1, stack1.top);
        assertEquals(element, stack1.at(stack1.top));
    }

    @Test
    public void shouldDeleteFromQueueOnStacks() {
        Stack<String> stack1 = Stack.withLength(4);
        Stack<String> stack2 = Stack.withLength(4);
        stack1.set(1, "xyz");
        stack1.top = 2;
        int topBeforeInserting = stack1.top;
        String expectedElement = "xyz";

        String actualElement = Chapter10.dequeueOnStacks(stack1, stack2);

        assertEquals(topBeforeInserting - 1, stack1.top);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingFromEmptyQueueOnStacks() {
        Stack<String> stack1 = Stack.withLength(4);
        Stack<String> stack2 = Stack.withLength(4);

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("underflow");
        Chapter10.dequeueOnStacks(stack1, stack2);
    }

    @Test
    public void shouldInsertToStackOnQueues() {
        Queue<String> queue1 = Queue.withLength(4);
        Queue<String> queue2 = Queue.withLength(4);
        queue1.head = 4;
        queue1.tail = 2;
        int tailBeforeInserting = queue1.tail;
        String element = "xyz";

        Chapter10.pushOnQueues(queue1, queue2, element);

        assertEquals(tailBeforeInserting + 1, queue1.tail);
        assertEquals(element, queue1.at(queue1.tail - 1));
    }

    @Test
    public void shouldDeleteFromStackOnQueues() {
        Queue<String> queue1 = Queue.withLength(4);
        Queue<String> queue2 = Queue.withLength(4);
        queue1.set(1, "xyz");
        queue1.head = 4;
        queue1.tail = 2;
        int tailBeforeInserting = queue1.tail;
        int stackSize = queue1.tail - queue1.head + queue1.length;
        String expectedElement = "xyz";

        String actualElement = Chapter10.popOnQueues(queue1, queue2);

        assertEquals(tailBeforeInserting, queue1.head);
        assertEquals(tailBeforeInserting + stackSize - 1, queue1.tail);
        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingFromEmptyStackOnQueues() {
        Queue<String> queue1 = Queue.withLength(4);
        Queue<String> queue2 = Queue.withLength(4);

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("underflow");
        Chapter10.popOnQueues(queue1, queue2);
    }

    @Test
    public void shouldFindKeyOnList() {
        List<Integer> list = new List<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int keyToFind = 6;

        List.Node<Integer> actualNode = Chapter10.listSearch(list, keyToFind);

        assertNotNull(actualNode);
        assertEquals(Integer.valueOf(keyToFind), actualNode.key);
    }

    @Test
    public void shouldNotFindNonexistentKeyOnList() {
        List<Integer> list = new List<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int keyToFind = 4;

        List.Node<Integer> actualNode = Chapter10.listSearch(list, keyToFind);

        assertNull(actualNode);
    }

    @Test
    public void shouldInsertOntoList() {
        List<Integer> list = new List<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        List<Integer> original = new List<>(list);
        int keyToInsert = 3;

        Chapter10.listInsert(list, new List.Node<>(keyToInsert));

        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertElementInsertedIntoBeginningOfList(keyToInsert, listArray, originalArray);
    }

    @Test
    public void shouldDeleteElementInTheMiddleOfList() {
        List<Integer> list = new List<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        List<Integer> original = new List<>(list);
        int keyToDelete = 6;
        List.Node<Integer> nodeToDelete = list.head.next.next.next.next; // first element on the list with key = 6

        Chapter10.listDelete(list, nodeToDelete);

        assertElementDeletedFromList(keyToDelete, list.toArray(), original.toArray());
    }

    @Test
    public void shouldDeleteHeadFromList() {
        List<Integer> list = new List<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        List<Integer> original = new List<>(list);
        int keyToDelete = 5;
        List.Node<Integer> nodeToDelete = list.head;

        Chapter10.listDelete(list, nodeToDelete);

        assertElementDeletedFromList(keyToDelete, list.toArray(), original.toArray());
    }

    private <E> void assertElementDeletedFromList(E deletedKey, Array<E> listArray, Array<E> originalArray) {
        assertEquals(originalArray.length - 1, listArray.length);
        int i = 0;
        boolean elementsEqual = true;
        while (i + 1 <= listArray.length && elementsEqual) {
            i++;
            elementsEqual = listArray.at(i).equals(originalArray.at(i));
        }
        if (elementsEqual) {
            assertEquals(deletedKey, originalArray.at(i + 1));
            return;
        }
        assertEquals(deletedKey, originalArray.at(i));
        while (i <= listArray.length) {
            assertEquals(listArray.at(i), originalArray.at(i + 1));
            i++;
        }
    }

    @Test
    public void shouldDeleteFromListWithSentinel() {
        ListWithSentinel<Integer> list = new ListWithSentinel<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        ListWithSentinel<Integer> original = new ListWithSentinel<>(list);
        int keyToDelete = 2;
        ListWithSentinel.Node<Integer> nodeToDelete = list.nil.next.next.next.next; // element with key = 2

        Chapter10.listDelete_(list, nodeToDelete);

        assertElementDeletedFromList(keyToDelete, list.toArray(), original.toArray());
    }

    @Test
    public void shouldFindKeyOnListWithSentinel() {
        ListWithSentinel<Integer> list = new ListWithSentinel<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int keyToFind = 6;

        ListWithSentinel.Node<Integer> actualNode = Chapter10.listSearch_(list, keyToFind);

        assertNotNull(actualNode);
        assertEquals(Integer.valueOf(keyToFind), actualNode.key);
    }

    @Test
    public void shouldNotFindNonexistentKeyOnListWithSentinel() {
        ListWithSentinel<Integer> list = new ListWithSentinel<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int keyToFind = 4;

        ListWithSentinel.Node<Integer> actualNode = Chapter10.listSearch_(list, keyToFind);

        assertEquals(list.nil, actualNode);
    }

    @Test
    public void shouldInsertOntoListWithSentinel() {
        ListWithSentinel<Integer> list = new ListWithSentinel<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        ListWithSentinel<Integer> original = new ListWithSentinel<>(list);
        int keyToInsert = 3;

        Chapter10.listInsert_(list, new ListWithSentinel.Node<>(keyToInsert));

        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertElementInsertedIntoBeginningOfList(keyToInsert, listArray, originalArray);
    }

    private <E> void assertElementInsertedIntoBeginningOfList(E keyToInsert, Array<E> listArray, Array<E> originalArray) {
        assertEquals(originalArray.length + 1, listArray.length);
        assertEquals(keyToInsert, listArray.at(1));
        for (int i = 2; i <= listArray.length; i++) {
            assertEquals(originalArray.at(i - 1), listArray.at(i));
        }
    }

    @Test
    public void shouldInsertOntoSinglyLinkedList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        SinglyLinkedList<Integer> original = new SinglyLinkedList<>(list);
        int keyToInsert = 3;

        Chapter10.singlyLinkedListInsert(list, new SinglyLinkedList.Node<>(keyToInsert));

        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertElementInsertedIntoBeginningOfList(keyToInsert, listArray, originalArray);
    }

    @Test
    public void shouldDeleteFromSinglyLinkedList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        SinglyLinkedList<Integer> original = new SinglyLinkedList<>(list);
        int keyToDelete = 6;
        SinglyLinkedList.Node<Integer> nodeToDelete = list.head.next.next.next.next; // first element on the list with key = 6

        Chapter10.singlyLinkedListDelete(list, nodeToDelete);

        assertElementDeletedFromList(keyToDelete, list.toArray(), original.toArray());
    }

    @Test
    public void shouldInsertIntoStackOnSinglyLinkedList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        SinglyLinkedList<Integer> original = new SinglyLinkedList<>(list);
        int keyToInsert = 3;

        Chapter10.singlyLinkedListPush(list, keyToInsert);

        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertElementInsertedIntoBeginningOfList(keyToInsert, listArray, originalArray);
    }

    @Test
    public void shouldDeleteFromStackOnSinglyLinkedList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        SinglyLinkedList<Integer> original = new SinglyLinkedList<>(list);
        int expectedElement = 5;

        Integer actualElement = Chapter10.singlyLinkedListPop(list);

        assertEquals(Integer.valueOf(expectedElement), actualElement);
        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertEquals(originalArray.length - 1, listArray.length);
        for (int i = 1; i <= listArray.length; i++) {
            assertEquals(originalArray.at(i + 1), listArray.at(i));
        }
    }

    @Test
    public void shouldThrowExceptionWhenDeletingFromEmptyStackOnSinglyLinkedList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("underflow");
        Chapter10.singlyLinkedListPop(list);
    }

    @Test
    public void shouldInsertIntoQueueOnSinglyLinkedListWithTail() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        SinglyLinkedListWithTail<Integer> original = new SinglyLinkedListWithTail<>(list);
        int keyToInsert = 3;

        Chapter10.singlyLinkedListEnqueue(list, keyToInsert);

        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertEquals(originalArray.length + 1, listArray.length);
        assertEquals(Integer.valueOf(keyToInsert), listArray.at(listArray.length));
        for (int i = 1; i <= listArray.length - 1; i++) {
            assertEquals(originalArray.at(i), listArray.at(i));
        }
    }

    @Test
    public void shouldInsertIntoEmptyQueueOnSinglyLinkedListWithTail() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>();
        int keyToInsert = 3;

        Chapter10.singlyLinkedListEnqueue(list, keyToInsert);

        assertNull(list.head.next);
        assertEquals(Integer.valueOf(keyToInsert), list.head.key);
    }

    @Test
    public void shouldDeleteFromQueueOnSinglyLinkedListWithTail() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        SinglyLinkedListWithTail<Integer> original = new SinglyLinkedListWithTail<>(list);
        int expectedElement = 5;

        Integer actualElement = Chapter10.singlyLinkedListDequeue(list);

        assertEquals(Integer.valueOf(expectedElement), actualElement);
        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertEquals(originalArray.length - 1, listArray.length);
        for (int i = 1; i <= listArray.length; i++) {
            assertEquals(originalArray.at(i + 1), listArray.at(i));
        }
    }

    @Test
    public void shouldDeleteTheOnlyElementFromQueueOnSinglyLinkedListWithTail() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>(5);
        int expectedElement = 5;

        Integer actualElement = Chapter10.singlyLinkedListDequeue(list);

        assertEquals(Integer.valueOf(expectedElement), actualElement);
        assertNull(list.head);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingFromEmptyQueueOnSinglyLinkedListWithTail() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("underflow");
        Chapter10.singlyLinkedListDequeue(list);
    }

    @Test
    public void shouldInsertOntoCircularList() {
        CircularList<Integer> list = new CircularList<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        CircularList<Integer> original = new CircularList<>(list);
        int keyToInsert = 3;

        Chapter10.circularListInsert(list, new CircularList.Node<>(keyToInsert));

        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertEquals(originalArray.length + 1, listArray.length);
        assertEquals(originalArray.at(1), listArray.at(1));
        assertEquals(Integer.valueOf(keyToInsert), listArray.at(2));
        for (int i = 3; i <= listArray.length; i++) {
            assertEquals(originalArray.at(i - 1), listArray.at(i));
        }
    }

    @Test
    public void shouldInsertOntoEmptyCircularList() {
        CircularList<Integer> list = new CircularList<>();
        int keyToInsert = 3;

        Chapter10.circularListInsert(list, new CircularList.Node<>(keyToInsert));

        assertEquals(Integer.valueOf(keyToInsert), list.head.key);
        assertEquals(list.head, list.head.next);
    }

    @Test
    public void shouldDeleteFromCircularList() {
        CircularList<Integer> list = new CircularList<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        CircularList<Integer> original = new CircularList<>(list);
        int keyToDelete = 6;
        CircularList.Node<Integer> nodeToDelete = list.head.next.next.next.next; // first element on the list with key = 6

        Chapter10.circularListDelete(list, nodeToDelete);

        assertElementDeletedFromList(keyToDelete, list.toArray(), original.toArray());
    }

    @Test
    public void shouldDeleteHeadFromCircularList() {
        CircularList<Integer> list = new CircularList<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        CircularList<Integer> original = new CircularList<>(list);
        int keyToDelete = 5;
        CircularList.Node<Integer> nodeToDelete = list.head;

        Chapter10.circularListDelete(list, nodeToDelete);

        assertElementDeletedFromList(keyToDelete, list.toArray(), original.toArray());
    }

    @Test
    public void shouldDeleteHeadFromCircularListWithOneElement() {
        CircularList<Integer> list = new CircularList<>(5);
        CircularList.Node<Integer> nodeToDelete = list.head;

        Chapter10.circularListDelete(list, nodeToDelete);

        assertNull(list.head);
    }

    @Test
    public void shouldFindKeyOnCircularList() {
        CircularList<Integer> list = new CircularList<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int keyToFind = 6;

        CircularList.Node<Integer> actualNode = Chapter10.circularListSearch(list, keyToFind);

        assertNotNull(actualNode);
        assertEquals(Integer.valueOf(keyToFind), actualNode.key);
    }

    @Test
    public void shouldFindKeyInCircularListHead() {
        CircularList<Integer> list = new CircularList<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int keyToFind = 5;

        CircularList.Node<Integer> actualNode = Chapter10.circularListSearch(list, keyToFind);

        assertNotNull(actualNode);
        assertEquals(Integer.valueOf(keyToFind), actualNode.key);
    }

    @Test
    public void shouldNotFindNonexistentKeyOnCircularList() {
        CircularList<Integer> list = new CircularList<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int keyToFind = 4;

        CircularList.Node<Integer> actualNode = Chapter10.circularListSearch(list, keyToFind);

        assertNull(actualNode);
    }

    @Test
    public void shouldNotFindKeyOnEmptyCircularList() {
        CircularList<Integer> list = new CircularList<>();
        int keyToFind = 4;

        CircularList.Node<Integer> actualNode = Chapter10.circularListSearch(list, keyToFind);

        assertNull(actualNode);
    }

    @Test
    public void shouldUnionCircularLists() {
        CircularList<Integer> list1 = new CircularList<>(12, 44, 26, 20, 67, 4, 21, 66, 35, 51, 13);
        CircularList<Integer> list2 = new CircularList<>(55, 23, 2, 74, 30, 47);
        Array<Integer> originalArray1 = list1.toArray();
        Array<Integer> originalArray2 = list2.toArray();

        CircularList<Integer> actualMergedLists = Chapter10.circularListsUnion(list1, list2);

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
    public void shouldUnionEmptyCircularLists() {
        CircularList<Integer> list1 = new CircularList<>();
        CircularList<Integer> list2 = new CircularList<>();

        CircularList<Integer> actualMergedLists = Chapter10.circularListsUnion(list1, list2);

        assertNull(actualMergedLists.head);
    }

    @Test
    public void shouldReverseSinglyLinkedList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        SinglyLinkedList<Integer> original = new SinglyLinkedList<>(list);

        Chapter10.singlyLinkedListReverse(list);

        Array<Integer> listArray = list.toArray();
        Array<Integer> originalArray = original.toArray();
        assertEquals(originalArray.length, listArray.length);
        int n = listArray.length;
        for (int i = 1; i <= n; i++) {
            assertEquals(originalArray.at(i), listArray.at(n - i + 1));
        }
    }

    @Test
    public void shouldFindElementInXorLinkedList() {
        XorLinkedList<String> xorLinkedList = getExemplaryXorLinkedList();
        String key = "ccc";

        XorLinkedList.Node<String> actualFound = Chapter10.xorLinkedListSearch(xorLinkedList, key);

        assertEquals(key, actualFound.key);
    }

    private XorLinkedList<String> getExemplaryXorLinkedList() {
        XorLinkedList<String> xorLinkedList = new XorLinkedList<>();
        XorLinkedList.Node<String> x1 = xorLinkedList.registerNode("aaa");
        XorLinkedList.Node<String> x2 = xorLinkedList.registerNode("bbb");
        XorLinkedList.Node<String> x3 = xorLinkedList.registerNode("ccc");
        XorLinkedList.Node<String> x4 = xorLinkedList.registerNode("ddd");
        x1.np = x2.address;
        x2.np = x1.address ^ x3.address;
        x3.np = x2.address ^ x4.address;
        x4.np = x3.address;
        xorLinkedList.head = x1;
        xorLinkedList.tail = x4;
        return xorLinkedList;
    }

    @Test
    public void shouldNotFindNonexistingElementInXorLinkedList() {
        XorLinkedList<String> xorLinkedList = getExemplaryXorLinkedList();
        String key = "xyz";

        XorLinkedList.Node<String> actualFound = Chapter10.xorLinkedListSearch(xorLinkedList, key);

        assertNull(actualFound);
    }

    @Test
    public void shouldInsertElementToEmptyXorLinkedList() {
        XorLinkedList<String> xorLinkedList = new XorLinkedList<>();
        XorLinkedList.Node<String> nodeToInsert = xorLinkedList.registerNode("xyz");

        Chapter10.xorLinkedListInsert(xorLinkedList, nodeToInsert);

        assertEquals(nodeToInsert, xorLinkedList.head);
        assertEquals(nodeToInsert, xorLinkedList.tail);
        assertEquals(0, nodeToInsert.np);
    }

    @Test
    public void shouldInsertElementToNonemptyXorLinkedList() {
        XorLinkedList<String> xorLinkedList = getExemplaryXorLinkedList();
        XorLinkedList<String> original = new XorLinkedList<>(xorLinkedList);
        String keyToInsert = "xyz";
        XorLinkedList.Node<String> nodeToInsert = xorLinkedList.registerNode(keyToInsert);

        Chapter10.xorLinkedListInsert(xorLinkedList, nodeToInsert);

        Array<String> listArray = xorLinkedList.toArray();
        Array<String> originalArray = original.toArray();
        assertElementInsertedIntoBeginningOfList(keyToInsert, listArray, originalArray);
    }

    @Test
    public void shouldDeleteFromBeginningOfXorLinkedList() {
        XorLinkedList<String> xorLinkedList = getExemplaryXorLinkedList();
        XorLinkedList<String> original = new XorLinkedList<>(xorLinkedList);
        String keyToDelete = xorLinkedList.head.key;

        Chapter10.xorLinkedListDelete(xorLinkedList, xorLinkedList.head);

        assertElementDeletedFromList(keyToDelete, xorLinkedList.toArray(), original.toArray());
    }

    @Test
    public void shouldDeleteFromEndOfXorLinkedList() {
        XorLinkedList<String> xorLinkedList = getExemplaryXorLinkedList();
        XorLinkedList<String> original = new XorLinkedList<>(xorLinkedList);
        XorLinkedList.Node<String> nodeToDelete = xorLinkedList.tail;
        String keyToDelete = xorLinkedList.tail.key;

        Chapter10.xorLinkedListDelete(xorLinkedList, nodeToDelete);

        assertElementDeletedFromList(keyToDelete, xorLinkedList.toArray(), original.toArray());
    }

    @Test
    public void shouldReverseXorLinkedList() {
        XorLinkedList<String> xorLinkedList = getExemplaryXorLinkedList();
        XorLinkedList<String> original = new XorLinkedList<>(xorLinkedList);

        Chapter10.xorLinkedListReverse(xorLinkedList);

        Array<String> listArray = xorLinkedList.toArray();
        Array<String> originalArray = original.toArray();
        assertEquals(originalArray.length, listArray.length);
        int n = listArray.length;
        for (int i = 1; i <= n; i++) {
            assertEquals(originalArray.at(i), listArray.at(n - i + 1));
        }
    }

    @Test
    public void shouldAllocateObjectOnMultipleArrayList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(4, 1, null, null, 3),
                null,
                new Array<>(2, null, 5, 1, null),
                2,
                5
        );

        int actualNewPosition = Chapter10.allocateObject(multipleArrayList);

        assertEquals(5, actualNewPosition);
        assertEquals(Integer.valueOf(3), multipleArrayList.free);
    }

    @Test
    public void shouldNotAllocateObjectOnFullMultipleArrayList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(4, 1, null, 5, 3),
                null,
                new Array<>(2, null, 5, 1, 4),
                2,
                null
        );

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("out of space");
        Chapter10.allocateObject(multipleArrayList);
    }

    @Test
    public void shouldFreeObjectOnMultipleArrayList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(4, 1, null, null, 3),
                null,
                new Array<>(2, null, 5, 1, null),
                2,
                5
        );

        Chapter10.freeObject(multipleArrayList, 1);

        assertEquals(Integer.valueOf(1), multipleArrayList.free);
        assertEquals(Integer.valueOf(5), multipleArrayList.next.at(1));
    }

    @Test
    public void shouldAllocateObjectOnSingleArrayList() {
        SingleArrayList singleArrayList = new SingleArrayList(
                new Array<>(100, 10, 4, 200, 1, null, 300, null, 13, 400, null, 1, 500, 7, null),
                4,
                13
        );

        int actualNewPosition = Chapter10.singleArrayAllocateObject(singleArrayList);

        assertEquals(13, actualNewPosition);
        assertEquals(Integer.valueOf(7), singleArrayList.free);
    }

    @Test
    public void shouldNotAllocateObjectOnFullSingleArrayList() {
        SingleArrayList singleArrayList = new SingleArrayList(
                new Array<>(100, 10, 4, 200, 1, null, 300, null, 13, 400, 13, 1, 500, 7, 10),
                4,
                null
        );

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("out of space");
        Chapter10.singleArrayAllocateObject(singleArrayList);
    }

    @Test
    public void shouldFreeObjectOnSingleArrayList() {
        SingleArrayList singleArrayList = new SingleArrayList(
                new Array<>(100, 10, 4, 200, 1, null, 300, null, 13, 400, null, 1, 500, 7, null),
                4,
                13
        );

        Chapter10.singleArrayFreeObject(singleArrayList, 1);

        assertEquals(Integer.valueOf(1), singleArrayList.free);
        assertEquals(Integer.valueOf(13), singleArrayList.at(2));
    }

    @Test
    public void shouldAllocateObjectOnCompactList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(3, 1, null, 5, null),
                new Array<>("aaa", "bbb", "ccc", "ddd", "eee"),
                new Array<>(2, null, 1, null, null),
                2,
                4
        );
        MultipleArrayList<String> original = new MultipleArrayList<>(multipleArrayList);

        int actualNewPosition = Chapter10.compactListAllocateObject(multipleArrayList);

        assertEquals(4, actualNewPosition);
        assertCompactList(multipleArrayList);
        assertArrayEquals(original.toArray(), multipleArrayList.toArray());
        assertEquals(original.getFreeListLength() - 1, multipleArrayList.getFreeListLength());
    }

    private void assertCompactList(MultipleArrayList<String> multipleArrayList) {
        int listLength = multipleArrayList.getLength();
        Integer x = multipleArrayList.L;
        while (x != null) {
            assertTrue(x <= listLength);
            x = multipleArrayList.next.at(x);
        }
        x = multipleArrayList.free;
        while (x != null) {
            assertTrue(x > listLength);
            x = multipleArrayList.next.at(x);
        }
    }

    @Test
    public void shouldNotAllocateObjectOnFullCompactList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(3, 1, 5, null, 4),
                new Array<>("aaa", "bbb", "ccc", "ddd", "eee"),
                new Array<>(2, null, 1, null, null),
                2,
                null
        );

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("out of space");
        Chapter10.compactListAllocateObject(multipleArrayList);
    }

    @Test
    public void shouldFreeObjectOnCompactList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(3, 1, null, 5, null),
                new Array<>("aaa", "bbb", "ccc", "ddd", "eee"),
                new Array<>(2, null, 1, null, null),
                2,
                4
        );
        MultipleArrayList<String> originalList = new MultipleArrayList<>(multipleArrayList);
        int elementIndexToFree = 1;
        String keyToDelete = "aaa";
        int freeListLength = multipleArrayList.getFreeListLength();

        Chapter10.compactListFreeObject(multipleArrayList, elementIndexToFree);

        assertCompactList(multipleArrayList);
        assertElementDeletedFromList(keyToDelete, multipleArrayList.toArray(), originalList.toArray());
        assertEquals(freeListLength + 1, multipleArrayList.getFreeListLength());
    }

    @Test
    public void shouldFreeObjectAtTheEndOfCompactList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(3, 1, null, 5, null),
                new Array<>("aaa", "bbb", "ccc", "ddd", "eee"),
                new Array<>(2, null, 1, null, null),
                2,
                4
        );
        MultipleArrayList<String> originalList = new MultipleArrayList<>(multipleArrayList);
        int elementIndexToFree = 3;
        String keyToDelete = "ccc";
        int freeListLength = multipleArrayList.getFreeListLength();

        Chapter10.compactListFreeObject(multipleArrayList, elementIndexToFree);

        assertCompactList(multipleArrayList);
        assertElementDeletedFromList(keyToDelete, multipleArrayList.toArray(), originalList.toArray());
        assertEquals(freeListLength + 1, multipleArrayList.getFreeListLength());
    }

    @Test
    public void shouldFreeObjectOnFullCompactList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(4, 1, null, 3, 2),
                new Array<>("aaa", "bbb", "ccc", "ddd", "eee"),
                new Array<>(2, 5, 4, 1, null),
                5,
                null
        );
        MultipleArrayList<String> originalList = new MultipleArrayList<>(multipleArrayList);
        int elementIndexToFree = 3;
        String keyToDelete = "ccc";
        int freeListLength = multipleArrayList.getFreeListLength();

        Chapter10.compactListFreeObject(multipleArrayList, elementIndexToFree);

        assertCompactList(multipleArrayList);
        assertElementDeletedFromList(keyToDelete, multipleArrayList.toArray(), originalList.toArray());
        assertEquals(freeListLength + 1, multipleArrayList.getFreeListLength());
    }

    @Test
    public void shouldCompactifyNonemptyList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(9, 8, null, 1, 2, null, 5, 10, 6, 3),
                Array.withLength(10),
                new Array<>(4, 5, 10, null, 7, 9, null, 2, 1, 8),
                7,
                4
        );
        int listLength = 6;

        Chapter10.compactifyList(multipleArrayList);

        assertCompactList(multipleArrayList);
        assertEquals(listLength, multipleArrayList.getLength());
        assertEquals(10 - listLength, multipleArrayList.getFreeListLength());
    }

    @Test
    public void shouldCompactifyEmptyList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(3, 1, 5, 2, null),
                Array.withLength(5),
                new Array<>(null, null, null, null, null),
                null,
                4
        );

        Chapter10.compactifyList(multipleArrayList);

        assertNull(multipleArrayList.L);
        assertEquals(5, multipleArrayList.getFreeListLength());
    }

    @Test
    public void shouldPrintOutEmptyTreeInPreorder() {

        Chapter10.iterativePreorderTreeWalk(new BinaryTree<>());

        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonEmptyTreeInPreorder() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree.Node<Integer> x1 = new BinaryTree.Node<>(10);
        BinaryTree.Node<Integer> x2 = new BinaryTree.Node<>(4);
        BinaryTree.Node<Integer> x3 = new BinaryTree.Node<>(14);
        BinaryTree.Node<Integer> x4 = new BinaryTree.Node<>(1);
        BinaryTree.Node<Integer> x5 = new BinaryTree.Node<>(11);
        BinaryTree.Node<Integer> x6 = new BinaryTree.Node<>(19);
        BinaryTree.Node<Integer> x7 = new BinaryTree.Node<>(20);
        tree.root = x1;
        x1.left = x2;
        x2.p = x1;
        x1.right = x3;
        x3.p = x1;
        x2.left = x4;
        x4.p = x2;
        x3.left = x5;
        x5.p = x3;
        x3.right = x6;
        x6.p = x3;
        x6.right = x7;
        x7.p = x6;

        Chapter10.iterativePreorderTreeWalk(tree);

        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"10", "4", "1", "14", "11", "19", "20"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldPrintOutEmptyMultiaryTree() {

        Chapter10.treeWalk(null);

        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonEmptyMultiaryTree() {
        MultiaryTree<Integer> tree = new MultiaryTree<>();
        MultiaryTree.Node<Integer> x1 = new MultiaryTree.Node<>(1);
        MultiaryTree.Node<Integer> x2 = new MultiaryTree.Node<>(2);
        MultiaryTree.Node<Integer> x3 = new MultiaryTree.Node<>(3);
        MultiaryTree.Node<Integer> x4 = new MultiaryTree.Node<>(4);
        MultiaryTree.Node<Integer> x5 = new MultiaryTree.Node<>(5);
        MultiaryTree.Node<Integer> x6 = new MultiaryTree.Node<>(6);
        MultiaryTree.Node<Integer> x7 = new MultiaryTree.Node<>(7);
        MultiaryTree.Node<Integer> x8 = new MultiaryTree.Node<>(8);
        MultiaryTree.Node<Integer> x9 = new MultiaryTree.Node<>(9);
        MultiaryTree.Node<Integer> x10 = new MultiaryTree.Node<>(10);
        tree.root = x1;
        x1.leftChild = x2;
        x2.p = x1;
        x2.leftChild = x5;
        x5.p = x2;
        x2.rightSibling = x3;
        x3.p = x1;
        x3.rightSibling = x4;
        x4.p = x1;
        x4.leftChild = x8;
        x8.p = x4;
        x5.rightSibling = x6;
        x6.p = x2;
        x6.leftChild = x10;
        x10.p = x6;
        x6.rightSibling = x7;
        x7.p = x2;
        x8.rightSibling = x9;
        x9.p = x4;

        Chapter10.treeWalk(tree.root);

        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1", "2", "5", "6", "10", "7", "3", "4", "8", "9"};
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldPrintOutEmptyTreeInInorder() {

        Chapter10.stacklessInorderTreeWalk(new BinaryTree<>());

        assertEquals(0, outContent.size());
    }

    @Test
    public void shouldPrintOutNonEmptyTreeInInorder() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        BinaryTree.Node<Integer> x1 = new BinaryTree.Node<>(10);
        BinaryTree.Node<Integer> x2 = new BinaryTree.Node<>(4);
        BinaryTree.Node<Integer> x3 = new BinaryTree.Node<>(14);
        BinaryTree.Node<Integer> x4 = new BinaryTree.Node<>(1);
        BinaryTree.Node<Integer> x5 = new BinaryTree.Node<>(11);
        BinaryTree.Node<Integer> x6 = new BinaryTree.Node<>(19);
        BinaryTree.Node<Integer> x7 = new BinaryTree.Node<>(20);
        tree.root = x1;
        x1.left = x2;
        x2.p = x1;
        x1.right = x3;
        x3.p = x1;
        x2.left = x4;
        x4.p = x2;
        x3.left = x5;
        x5.p = x3;
        x3.right = x6;
        x6.p = x3;
        x6.right = x7;
        x7.p = x6;

        Chapter10.stacklessInorderTreeWalk(tree);

        String[] actualOutput = splitOutContent();
        String[] expectedOutput = new String[]{"1", "4", "10", "11", "14", "19", "20"};
        Assert.assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldMakeNewHeapOnSortedList() {

        SinglyLinkedList<Integer> actualHeap = Chapter10.sortedListMakeMinHeap();

        assertEquals(0, actualHeap.getLength());
    }

    @Test
    public void shouldInsertToEmptyHeapOnSortedList() {
        SinglyLinkedList<Integer> sortedList = new SinglyLinkedList<>();
        int key = 12;
        SinglyLinkedList<Integer> expectedAfterInsertion = new SinglyLinkedList<>(12);

        Chapter10.sortedListMinHeapInsert(sortedList, key);

        assertArrayEquals(expectedAfterInsertion.toArray(), sortedList.toArray());
    }

    @Test
    public void shouldInsertAtTheBeginningOfHeapOnSortedList() {
        SinglyLinkedList<Integer> sortedList = new SinglyLinkedList<>(2, 4, 8, 13, 14, 15);
        int key = 1;
        SinglyLinkedList<Integer> expectedAfterInsertion = new SinglyLinkedList<>(1, 2, 4, 8, 13, 14, 15);

        Chapter10.sortedListMinHeapInsert(sortedList, key);

        assertArrayEquals(expectedAfterInsertion.toArray(), sortedList.toArray());
    }

    @Test
    public void shouldInsertAtTheEndOfHeapOnSortedList() {
        SinglyLinkedList<Integer> sortedList = new SinglyLinkedList<>(2, 4, 8, 13, 14, 15);
        int key = 20;
        SinglyLinkedList<Integer> expectedAfterInsertion = new SinglyLinkedList<>(2, 4, 8, 13, 14, 15, 20);

        Chapter10.sortedListMinHeapInsert(sortedList, key);

        assertArrayEquals(expectedAfterInsertion.toArray(), sortedList.toArray());
    }

    @Test
    public void shouldInsertInTheMiddleOfHeapOnSortedList() {
        SinglyLinkedList<Integer> sortedList = new SinglyLinkedList<>(2, 4, 8, 13, 14, 15);
        int key = 11;
        SinglyLinkedList<Integer> expectedAfterInsertion = new SinglyLinkedList<>(2, 4, 8, 11, 13, 14, 15);

        Chapter10.sortedListMinHeapInsert(sortedList, key);

        assertArrayEquals(expectedAfterInsertion.toArray(), sortedList.toArray());
    }

    @Test
    public void shouldGetMinimumFromHeapOnSortedList() {
        SinglyLinkedList<Integer> sortedList = new SinglyLinkedList<>(2, 4, 8, 13, 14, 15);
        SinglyLinkedList<Integer> original = new SinglyLinkedList<>(sortedList);
        int expectedMinimum = 2;

        int actualMinimum = Chapter10.sortedListHeapMinimum(sortedList);

        assertEquals(expectedMinimum, actualMinimum);
        assertArrayEquals(original.toArray(), sortedList.toArray());
    }

    @Test
    public void shouldThrowExceptionWhenExtractingMinimumFromEmptyHeapOnSortedList() {
        SinglyLinkedList<Integer> sortedList = new SinglyLinkedList<>();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("heap underflow");
        Chapter10.sortedListHeapExtractMin(sortedList);
    }

    @Test
    public void shouldExtractMinimumFromHeapOnSortedList() {
        SinglyLinkedList<Integer> sortedList = new SinglyLinkedList<>(2, 4, 8, 13, 14, 15);
        SinglyLinkedList<Integer> expectedAfterExtraction = new SinglyLinkedList<>(4, 8, 13, 14, 15);
        int expectedMinimum = 2;

        int actualMinimum = Chapter10.sortedListHeapExtractMin(sortedList);

        assertEquals(expectedMinimum, actualMinimum);
        assertArrayEquals(expectedAfterExtraction.toArray(), sortedList.toArray());
    }

    @Test
    public void shouldMergeHeapsOnSortedLists() {
        SinglyLinkedList<Integer> sortedList1 = new SinglyLinkedList<>(2, 4, 8, 13, 14, 15);
        SinglyLinkedList<Integer> sortedList2 = new SinglyLinkedList<>(1, 2, 5, 6, 8, 12, 14);
        SinglyLinkedList<Integer> expectedMerged = new SinglyLinkedList<>(1, 2, 4, 5, 6, 8, 12, 13, 14, 15);

        SinglyLinkedList<Integer> actualMerged = Chapter10.sortedListMinHeapUnion(sortedList1, sortedList2);

        assertArrayEquals(expectedMerged.toArray(), actualMerged.toArray());
    }

    @Test
    public void shouldMakeNewHeapOnList() {

        SinglyLinkedListWithTail<Integer> actualHeap = Chapter10.listMakeMinHeap();

        assertEquals(0, actualHeap.getLength());
    }

    @Test
    public void shouldInsertToEmptyHeapOnList() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>();
        int key = 12;
        SinglyLinkedListWithTail<Integer> expectedAfterInsertion = new SinglyLinkedListWithTail<>(12);

        Chapter10.listMinHeapInsert(list, key);

        assertArrayEquals(expectedAfterInsertion.toArray(), list.toArray());
    }

    @Test
    public void shouldInsertNewMinimumIntoHeapOnList() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>(2, 8, 12, 10, 6, 5, 3);
        int key = 1;
        SinglyLinkedListWithTail<Integer> expectedAfterInsertion = new SinglyLinkedListWithTail<>(1, 2, 8, 12, 10, 6, 5, 3);

        Chapter10.listMinHeapInsert(list, key);

        assertArrayEquals(expectedAfterInsertion.toArray(), list.toArray());
    }

    @Test
    public void shouldInsertElementNotBecomingMinimumIntoHeapOnList() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>(2, 8, 12, 10, 6, 5, 3);
        int key = 7;
        SinglyLinkedListWithTail<Integer> expectedAfterInsertion = new SinglyLinkedListWithTail<>(2, 7, 8, 12, 10, 6, 5, 3);

        Chapter10.listMinHeapInsert(list, key);

        assertArrayEquals(expectedAfterInsertion.toArray(), list.toArray());
    }

    @Test
    public void shouldGetMinimumFromHeapOnList() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>(2, 8, 12, 10, 6, 5, 3);
        SinglyLinkedListWithTail<Integer> original = new SinglyLinkedListWithTail<>(list);
        int expectedMinimum = 2;

        int actualMinimum = Chapter10.listHeapMinimum(list);

        assertEquals(expectedMinimum, actualMinimum);
        assertArrayEquals(original.toArray(), list.toArray());
    }

    @Test
    public void shouldThrowExceptionWhenExtractingMinimumFromEmptyHeapOnList() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("heap underflow");
        Chapter10.listHeapExtractMin(list);
    }

    @Test
    public void shouldExtractMinimumFromHeapOnList() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>(2, 8, 12, 10, 6, 5, 3);
        SinglyLinkedListWithTail<Integer> expectedAfterExtraction = new SinglyLinkedListWithTail<>(3, 8, 12, 10, 6, 5);
        int expectedMinimum = 2;

        int actualMinimum = Chapter10.listHeapExtractMin(list);

        assertEquals(expectedMinimum, actualMinimum);
        assertArrayEquals(expectedAfterExtraction.toArray(), list.toArray());
    }

    @Test
    public void shouldExtractMinimumFromHeapOnListContainingOneElement() {
        SinglyLinkedListWithTail<Integer> list = new SinglyLinkedListWithTail<>(13);
        int expectedMinimum = 13;

        int actualMinimum = Chapter10.listHeapExtractMin(list);

        assertEquals(expectedMinimum, actualMinimum);
        assertNull(list.head);
    }

    @Test
    public void shouldMergeHeapsOnListsWhereFirstHeapIsEmpty() {
        SinglyLinkedListWithTail<Integer> list1 = new SinglyLinkedListWithTail<>();
        SinglyLinkedListWithTail<Integer> list2 = new SinglyLinkedListWithTail<>(3, 7, 4, 10, 6, 9);

        SinglyLinkedList<Integer> actualMerged = Chapter10.listMinHeapUnion(list1, list2);

        assertArrayEquals(list2.toArray(), actualMerged.toArray());
    }

    @Test
    public void shouldMergeHeapsOnListsWhereSecondHeapIsEmpty() {
        SinglyLinkedListWithTail<Integer> list1 = new SinglyLinkedListWithTail<>(2, 8, 12, 10, 6, 5, 3);
        SinglyLinkedListWithTail<Integer> list2 = new SinglyLinkedListWithTail<>();

        SinglyLinkedList<Integer> actualMerged = Chapter10.listMinHeapUnion(list1, list2);

        assertArrayEquals(list1.toArray(), actualMerged.toArray());
    }

    @Test
    public void shouldMergeHeapsOnLists() {
        SinglyLinkedListWithTail<Integer> list1 = new SinglyLinkedListWithTail<>(2, 8, 12, 10, 6, 5, 3);
        SinglyLinkedListWithTail<Integer> list2 = new SinglyLinkedListWithTail<>(3, 7, 4, 10, 6, 9);
        SinglyLinkedListWithTail<Integer> expectedMerged = new SinglyLinkedListWithTail<>(2, 3, 4, 5, 6, 7, 8, 9, 10, 12);

        SinglyLinkedList<Integer> actualMerged = Chapter10.listMinHeapUnion(list1, list2);

        assertArrayEquals(expectedMerged.toArray(), actualMerged.toArray());
    }

    @Test
    public void shouldMergeDisjointHeapsOnListsWhereFirstHeapIsEmpty() {
        SinglyLinkedListWithTail<Integer> list1 = new SinglyLinkedListWithTail<>();
        SinglyLinkedListWithTail<Integer> list2 = new SinglyLinkedListWithTail<>(1, 13, 7, 17, 9, 11);

        SinglyLinkedList<Integer> actualMerged = Chapter10.listMinHeapDisjointUnion(list1, list2);

        assertArrayEquals(list2.toArray(), actualMerged.toArray());
    }

    @Test
    public void shouldMergeDisjointHeapsOnListsWhereSecondHeapIsEmpty() {
        SinglyLinkedListWithTail<Integer> list1 = new SinglyLinkedListWithTail<>(2, 8, 12, 10, 6, 5, 3);
        SinglyLinkedListWithTail<Integer> list2 = new SinglyLinkedListWithTail<>();

        SinglyLinkedList<Integer> actualMerged = Chapter10.listMinHeapDisjointUnion(list1, list2);

        assertArrayEquals(list1.toArray(), actualMerged.toArray());
    }

    @Test
    public void shouldMergeDisjointHeapsOnLists() {
        SinglyLinkedListWithTail<Integer> list1 = new SinglyLinkedListWithTail<>(2, 8, 12, 10, 6, 5, 3);
        SinglyLinkedListWithTail<Integer> list2 = new SinglyLinkedListWithTail<>(1, 13, 7, 17, 9, 11);
        SinglyLinkedListWithTail<Integer> expectedMerged = new SinglyLinkedListWithTail<>(1, 13, 7, 17, 9, 11, 2, 8, 12, 10, 6, 5, 3);

        SinglyLinkedList<Integer> actualMerged = Chapter10.listMinHeapDisjointUnion(list1, list2);

        assertArrayEquals(expectedMerged.toArray(), actualMerged.toArray());
    }

    @Test
    public void shouldMergeDisjointHeapsOnLists2() {
        SinglyLinkedListWithTail<Integer> list1 = new SinglyLinkedListWithTail<>(1, 13, 7, 17, 9, 11);
        SinglyLinkedListWithTail<Integer> list2 = new SinglyLinkedListWithTail<>(2, 8, 12, 10, 6, 5, 3);
        SinglyLinkedListWithTail<Integer> expectedMerged = new SinglyLinkedListWithTail<>(1, 13, 7, 17, 9, 11, 2, 8, 12, 10, 6, 5, 3);

        SinglyLinkedList<Integer> actualMerged = Chapter10.listMinHeapDisjointUnion(list1, list2);

        assertArrayEquals(expectedMerged.toArray(), actualMerged.toArray());
    }

    @Test
    public void shouldFindElementAfterJumpsInCompactList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(6, 4, null, 5, 1, 3, 8, 9, 10, 11),
                new Array<>("ddd", "aaa", "fff", "bbb", "ccc", "eee", null, null, null, null),
                new Array<>(5, null, 6, 2, 4, 1, null, null, null, null),
                2,
                7
        );
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, 6)).thenReturn(3, 1);

        Integer actualFoundPosition = Chapter10.compactListSearch(multipleArrayList, 6, "eee");

        assertEquals(Integer.valueOf(6), actualFoundPosition);
    }

    @Test
    public void shouldFindElementByJumpingOnItInCompactList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(6, 4, null, 5, 1, 3, 8, 9, 10, 11),
                new Array<>("ddd", "aaa", "fff", "bbb", "ccc", "eee", null, null, null, null),
                new Array<>(5, null, 6, 2, 4, 1, null, null, null, null),
                2,
                7
        );
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, 6)).thenReturn(6);

        Integer actualFoundPosition = Chapter10.compactListSearch(multipleArrayList, 6, "eee");

        assertEquals(Integer.valueOf(6), actualFoundPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementInCompactList() {
        MultipleArrayList<String> multipleArrayList = new MultipleArrayList<>(
                new Array<>(6, 4, null, 5, 1, 3, 8, 9, 10, 11),
                new Array<>("ddd", "aaa", "fff", "bbb", "ccc", "eee", null, null, null, null),
                new Array<>(5, null, 6, 2, 4, 1, null, null, null, null),
                2,
                7
        );

        Integer actualFoundPosition = Chapter10.compactListSearch(multipleArrayList, 6, "xxx");

        assertNull(actualFoundPosition);
    }

}
