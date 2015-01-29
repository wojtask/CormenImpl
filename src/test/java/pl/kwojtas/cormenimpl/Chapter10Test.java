package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.BinaryTree;
import pl.kwojtas.cormenimpl.util.CircularList;
import pl.kwojtas.cormenimpl.util.Deque;
import pl.kwojtas.cormenimpl.util.DoubleStack;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.ListWithSentinel;
import pl.kwojtas.cormenimpl.util.MultiaryTree;
import pl.kwojtas.cormenimpl.util.Pair;
import pl.kwojtas.cormenimpl.util.Queue;
import pl.kwojtas.cormenimpl.util.SinglyLinkedList;
import pl.kwojtas.cormenimpl.util.SinglyLinkedListWithTail;
import pl.kwojtas.cormenimpl.util.Stack;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;

@RunWith(DataProviderRunner.class)
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

    @DataProvider
    public static Object[][] provideDataForStackOperations() {
        return new Object[][]{
                {new Array<>(34,null), new Array<>()},
                {new Array<>(4,1,3,null,8,null), new Array<>(1,4)},
                {new Array<>(3.14,-0.53,null,-1.54,null,2.23), new Array<>(2.23,3.14)},
                {new Array<>("eee",null,"ccc","aaa",null), new Array<>("ccc")}
        };
    }

    @Test
    @UseDataProvider("provideDataForStackOperations")
    public <T extends Comparable> void shouldPerformStackOperations(Array<T> contents, Array<T> expectedContents) {
        // given
        Stack<T> stack = Stack.withLength(5);

        // when
        for (int i = 1; i <= contents.length; i++) {
            if (contents.at(i) != null) {
                Chapter10.push(stack, contents.at(i));
            } else {
                Chapter10.pop(stack);
            }
        }

        // then
        if (expectedContents.length > 0) {
            assertFalse(Chapter10.stackEmpty(stack));
        }
        for (int i = 1; i <= expectedContents.length; i++) {
            assertEquals(expectedContents.at(i), Chapter10.pop(stack));
        }
        assertTrue(Chapter10.stackEmpty(stack));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingElementFromEmptyStack() {
        // given

        // when
        Chapter10.pop(Stack.withLength(5));

        // then
    }

    @DataProvider
    public static Object[][] provideDataForQueueOperations() {
        return new Object[][]{
                {new Array<>(34,null), new Array<>()},
                {new Array<>(4,1,3,null,8,null), new Array<>(3,8)},
                {new Array<>(4,1,3,5,null,8,null), new Array<>(3,5,8)},
                {new Array<>(3.14,-0.53,null,-1.54,null,2.23), new Array<>(-1.54,2.23)},
                {new Array<>("eee",null,"ccc","aaa",null), new Array<>("aaa")}
        };
    }

    @Test
    @UseDataProvider("provideDataForQueueOperations")
    public <T extends Comparable> void shouldPerformQueueOperations(Array<T> contents, Array<T> expectedContents) {
        // given
        Queue<T> queue = Queue.withLength(5);

        // when
        for (int i = 1; i <= contents.length; i++) {
            if (contents.at(i) != null) {
                Chapter10.enqueue(queue, contents.at(i));
            } else {
                Chapter10.dequeue(queue);
            }
        }

        // then
        if (expectedContents.length > 0) {
            assertFalse(Chapter10.queueEmpty(queue));
        }
        for (int i = 1; i <= expectedContents.length; i++) {
            assertEquals(expectedContents.at(i), Chapter10.dequeue(queue));
        }
        assertTrue(Chapter10.queueEmpty(queue));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInsertingElementToFullQueue() {
        // given
        Queue<Integer> queue = Queue.withLength(5);
        queue.head = 4;
        queue.tail = 3;

        // when
        Chapter10.enqueue(queue, 1);

        // then
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInsertingElementToFullQueue2() {
        // given
        Queue<Integer> queue = Queue.withLength(5);
        queue.head = 1;
        queue.tail = 5;

        // when
        Chapter10.enqueue(queue, 1);

        // then
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingElementFromEmptyQueue() {
        // given

        // when
        Chapter10.dequeue(Queue.withLength(5));

        // then
    }

    @DataProvider
    public static Object[][] provideDataForStackOperationsUsingTwoStacksOnSingleArray() {
        return new Object[][]{
                {new Array<>(new Pair<>(1,34),new Pair<>(1,null)), new Array<>(), new Array<>()},
                {new Array<>(new Pair<>(1,4),new Pair<>(2,6),new Pair<>(1,1),new Pair<>(1,3),new Pair<>(2,null),
                        new Pair<>(1,null),new Pair<>(2,8),new Pair<>(1,null)), new Array<>(4), new Array<>(8)},
                {new Array<>(new Pair<>(2,3.14),new Pair<>(2,-0.53),new Pair<>(2,null),new Pair<>(1,-1.54),new Pair<>(1,null),
                        new Pair<>(2,2.23)), new Array<>(), new Array<>(2.23,3.14)},
                {new Array<>(new Pair<>(2,"eee"),new Pair<>(2,null),new Pair<>(1,"ccc"),new Pair<>(2,"aaa"),new Pair<>(1,"bbb")),
                        new Array<>("bbb","ccc"), new Array<>("aaa")}
        };
    }

    @Test
    @UseDataProvider("provideDataForStackOperationsUsingTwoStacksOnSingleArray")
    public <T extends Comparable> void shouldPerformStackOperationsUsingTwoStacksOnSingleArray(
            Array<Pair<Integer, T>> contents, Array<T> expectedContents1, Array<T> expectedContents2) {
        // given
        DoubleStack<T> doubleStack = DoubleStack.withLength(5);

        // when
        for (int i = 1; i <= contents.length; i++) {
            if (contents.at(i).first == 1) {
                if (contents.at(i).second != null) {
                    Chapter10.firstStackPush(doubleStack, contents.at(i).second);
                } else {
                    Chapter10.firstStackPop(doubleStack);
                }
            } else {
                if (contents.at(i).second != null) {
                    Chapter10.secondStackPush(doubleStack, contents.at(i).second);
                } else {
                    Chapter10.secondStackPop(doubleStack);
                }
            }
        }

        // then
        for (int i = 1; i <= expectedContents1.length; i++) {
            assertEquals(expectedContents1.at(i), Chapter10.firstStackPop(doubleStack));
        }
        for (int i = 1; i <= expectedContents2.length; i++) {
            assertEquals(expectedContents2.at(i), Chapter10.secondStackPop(doubleStack));
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingElementFromEmptyFirstStackOnSingleArray() {
        // given

        // when
        Chapter10.firstStackPop(DoubleStack.withLength(5));

        // then
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingElementFromEmptySecondStackOnSingleArray() {
        // given

        // when
        Chapter10.secondStackPop(DoubleStack.withLength(5));

        // then
    }

    @DataProvider
    public static Object[][] provideDataForDequeOperations() {
        return new Object[][]{
                {new Array<>(new Pair<>(1,34),new Pair<>(1,null)), new Array<>()},
                {new Array<>(new Pair<>(1,4),new Pair<>(2,6),new Pair<>(1,1),new Pair<>(1,3),new Pair<>(2,null),
                        new Pair<>(1,null),new Pair<>(2,8),new Pair<>(1,null)), new Array<>(4,8)},
                {new Array<>(new Pair<>(2,3.14),new Pair<>(2,-0.53),new Pair<>(2,null),new Pair<>(1,-1.54),new Pair<>(1,null),
                        new Pair<>(2,2.23)), new Array<>(3.14,2.23)},
                {new Array<>(new Pair<>(2,"eee"),new Pair<>(2,null),new Pair<>(1,"ccc"),new Pair<>(2,"aaa"),new Pair<>(1,"bbb")),
                        new Array<>("bbb","ccc","aaa")}
        };
    }

    @Test
    @UseDataProvider("provideDataForDequeOperations")
    public <T extends Comparable> void shouldPerformDequeOperations(Array<Pair<Integer, T>> contents, Array<T> expectedContents) {
        // given
        Deque<T> deque = Deque.withLength(5);

        // when
        for (int i = 1; i <= contents.length; i++) {
            if (contents.at(i).first == 1) {
                if (contents.at(i).second != null) {
                    Chapter10.headEnqueue(deque, contents.at(i).second);
                } else {
                    Chapter10.headDequeue(deque);
                }
            } else {
                if (contents.at(i).second != null) {
                    Chapter10.tailEnqueue(deque, contents.at(i).second);
                } else {
                    Chapter10.tailDequeue(deque);
                }
            }
        }

        // then
        for (int i = 1; i <= expectedContents.length; i++) {
            assertEquals(expectedContents.at(i), Chapter10.headDequeue(deque));
        }
    }

    @Test
    @UseDataProvider("provideDataForStackOperations")
    public <T extends Comparable> void shouldPerformStackOperationsOnQueues(Array<T> contents, Array<T> expectedContents) {
        // given
        Queue<T> queue1 = Queue.withLength(5);
        Queue<T> queue2 = Queue.withLength(5);

        // when
        for (int i = 1; i <= contents.length; i++) {
            if (contents.at(i) != null) {
                Chapter10.pushOnQueues(queue1, queue2, contents.at(i));
            } else {
                Chapter10.popOnQueues(queue1, queue2);
            }
        }

        // then
        for (int i = 1; i <= expectedContents.length; i++) {
            assertEquals(expectedContents.at(i), Chapter10.popOnQueues(queue1, queue2));
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingElementFromEmptyStackOnQueues() {
        // given

        // when
        Chapter10.popOnQueues(Queue.withLength(5), Queue.withLength(5));

        // then
    }

    @Test
    @UseDataProvider("provideDataForQueueOperations")
    public <T extends Comparable> void shouldPerformQueueOperationsOnStacks(Array<T> contents, Array<T> expectedContents) {
        // given
        Stack<T> stack1 = Stack.withLength(5);
        Stack<T> stack2 = Stack.withLength(5);

        // when
        for (int i = 1; i <= contents.length; i++) {
            if (contents.at(i) != null) {
                Chapter10.enqueueOnStacks(stack1, stack2, contents.at(i));
            } else {
                Chapter10.dequeueOnStacks(stack1, stack2);
            }
        }

        // then
        for (int i = 1; i <= expectedContents.length; i++) {
            assertEquals(expectedContents.at(i), Chapter10.dequeueOnStacks(stack1, stack2));
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenRemovingElementFromEmptyQueueOnStacks() {
        // given

        // when
        Chapter10.dequeueOnStacks(Stack.withLength(5), Stack.withLength(5));

        // then
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulListSearch() {
        return new Object[][]{
                {new List<>(34), 34},
                {new List<>(5,7,9,2,6,1,6,6,3,1,7,8), 6},
                {new List<>(5,7,9,2,6,1,6,6,3,1,7,8), 8},
                {new List<>(5.0,-2.3,-1.3,-1.9,-2.3), -2.3},
                {new List<>("aaa","bbb","aaa","ccc"), "ccc"}
        };
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulListSearch")
    public <T> void shouldFindKeyUsingListSearch(List<T> list, T key) {
        // given

        // when
        List<T>.Node actualNode = Chapter10.listSearch(list, key);

        // then
        assertNotNull(actualNode);
        assertEquals(key, actualNode.key);
        List<T>.Node x = list.head;
        boolean found = false;
        while (x != null && !found) {
            found = x.key.equals(actualNode.key);
            x = x.next;
        }
        assertTrue(found);
    }

    @DataProvider
    public static Object[][] provideDataForUnsuccessfulListSearch() {
        return new Object[][]{
                {new List<>(34), 35},
                {new List<>(5,7,9,2,6,1,6,6,3,1,7,8), 4},
                {new List<>(5.0,-2.3,-1.3,-1.9,-2.3), 2.3},
                {new List<>("aaa","bbb","aaa","ccc"), "xyz"}
        };
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulListSearch")
    public <T> void shouldNotFindKeyUsingListSearch(List<T> list, T key) {
        // given

        // when
        List<T>.Node actualNode = Chapter10.listSearch(list, key);

        // then
        assertNull(actualNode);
    }

    @DataProvider
    public static Object[][] provideDataForInsertingElementOnList() {
        return new Object[][]{
                {new List<>(), 35},
                {new List<>(34), 35},
                {new List<>(5,7,9,2,6,1,6,6,3,1,7,8), 3},
                {new List<>(5.0,-2.3,-1.3,-1.9,-2.3), 2.3},
                {new List<>("aaa","bbb","aaa","ccc"), "xyz"}
        };
    }

    @Test
    @UseDataProvider("provideDataForInsertingElementOnList")
    public <T> void shouldInsertElementOnList(List<T> list, T key) {
        // given
        List<T> original = new List<>(list);

        // when
        Chapter10.listInsert(list, list.new Node(key));

        // then
        assertNotNull(list.head);
        assertEquals(key, list.head.key);
        List<T>.Node x = original.head;
        List<T>.Node y = list.head.next;
        while (x != null) {
            assertEquals(x.key, y.key);
            x = x.next;
            y = y.next;
        }
    }

    @DataProvider
    public static Object[][] provideDataForDeletingElementFromList() {
        return new Object[][]{
                {new List<>(34), 1},
                {new List<>(5,7,9,2,6,1,6,6,3,1,7,8), 4},
                {new List<>(5.0,-2.3,-1.3,-1.9,-2.3), 4},
                {new List<>("aaa","bbb","aaa","ccc"), 4}
        };
    }

    @Test
    @UseDataProvider("provideDataForDeletingElementFromList")
    public <T> void shouldDeleteElementFromList(List<T> list, int elementPosition) {
        // given
        List<T>.Node nodeToDelete = list.head;
        for (int i = 2; i <= elementPosition; i++) {
            nodeToDelete = nodeToDelete.next;
        }
        List<T> original = new List<>(list);

        // when
        Chapter10.listDelete(list, nodeToDelete);

        // then
        List<T>.Node x = original.head;
        List<T>.Node y = list.head;
        while (x != null) {
            if (y != null && x.key.equals(y.key)) {
                x = x.next;
                y = y.next;
            } else {
                assertEquals(x.key, nodeToDelete.key);
                x = x.next;
            }
        }
    }

    @DataProvider
    public static Object[][] provideDataForDeletingElementFromListWithSentinel() {
        return new Object[][]{
                {new ListWithSentinel<>(34), 1},
                {new ListWithSentinel<>(5,7,9,2,6,1,6,6,3,1,7,8), 4},
                {new ListWithSentinel<>(5.0,-2.3,-1.3,-1.9,-2.3), 4},
                {new ListWithSentinel<>("aaa","bbb","aaa","ccc"), 4}
        };
    }

    @Test
    @UseDataProvider("provideDataForDeletingElementFromListWithSentinel")
    public <T> void shouldDeleteElementFromListWithSentinel(ListWithSentinel<T> list, int elementPosition) {
        // given
        ListWithSentinel<T>.Node nodeToDelete = list.nil;
        for (int i = 1; i <= elementPosition; i++) {
            nodeToDelete = nodeToDelete.next;
        }
        ListWithSentinel<T> original = new ListWithSentinel<>(list);

        // when
        Chapter10.listDelete_(list, nodeToDelete);

        // then
        ListWithSentinel<T>.Node x = original.nil.next;
        ListWithSentinel<T>.Node y = list.nil.next;
        while (x != original.nil) {
            if (y != list.nil && x.key.equals(y.key)) {
                x = x.next;
                y = y.next;
            } else {
                assertEquals(x.key, nodeToDelete.key);
                x = x.next;
            }
        }
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulListWithSentinelSearch() {
        return new Object[][]{
                {new ListWithSentinel<>(34), 34},
                {new ListWithSentinel<>(5,7,9,2,6,1,6,6,3,1,7,8), 6},
                {new ListWithSentinel<>(5,7,9,2,6,1,6,6,3,1,7,8), 8},
                {new ListWithSentinel<>(5.0,-2.3,-1.3,-1.9,-2.3), -2.3},
                {new ListWithSentinel<>("aaa","bbb","aaa","ccc"), "ccc"}
        };
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulListWithSentinelSearch")
    public <T> void shouldFindKeyOnListWithSentinel(ListWithSentinel<T> list, T key) {
        // given

        // when
        ListWithSentinel<T>.Node actualNode = Chapter10.listSearch_(list, key);

        // then
        assertNotNull(actualNode);
        assertEquals(key, actualNode.key);
        ListWithSentinel<T>.Node x = list.nil.next;
        boolean found = false;
        while (x != list.nil && !found) {
            found = x.key.equals(actualNode.key);
            x = x.next;
        }
        assertTrue(found);
    }

    @DataProvider
    public static Object[][] provideDataForUnsuccessfulListWithSentinelSearch() {
        return new Object[][]{
                {new ListWithSentinel<>(34), 35},
                {new ListWithSentinel<>(5,7,9,2,6,1,6,6,3,1,7,8), 4},
                {new ListWithSentinel<>(5.0,-2.3,-1.3,-1.9,-2.3), 2.3},
                {new ListWithSentinel<>("aaa","bbb","aaa","ccc"), "xyz"}
        };
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulListWithSentinelSearch")
    public <T> void shouldNotFindKeyOnListWithSentinel(ListWithSentinel<T> list, T key) {
        // given

        // when
        ListWithSentinel<T>.Node actualNode = Chapter10.listSearch_(list, key);

        // then
        assertNotNull(actualNode);
        assertEquals(list.nil, actualNode);
    }

    @DataProvider
    public static Object[][] provideDataForInsertingElementOnListWithSentinel() {
        return new Object[][]{
                {new ListWithSentinel<>(), 35},
                {new ListWithSentinel<>(34), 35},
                {new ListWithSentinel<>(5,7,9,2,6,1,6,6,3,1,7,8), 3},
                {new ListWithSentinel<>(5.0,-2.3,-1.3,-1.9,-2.3), 2.3},
                {new ListWithSentinel<>("aaa","bbb","aaa","ccc"), "xyz"}
        };
    }

    @Test
    @UseDataProvider("provideDataForInsertingElementOnListWithSentinel")
    public <T> void shouldInsertElementOnListWithSentinel(ListWithSentinel<T> list, T key) {
        // given
        ListWithSentinel<T> original = new ListWithSentinel<>(list);

        // when
        Chapter10.listInsert_(list, list.new Node(key));

        // then
        assertNotEquals(list.nil.next, list.nil);
        assertEquals(key, list.nil.next.key);
        ListWithSentinel<T>.Node x = original.nil.next;
        ListWithSentinel<T>.Node y = list.nil.next.next;
        while (x != original.nil) {
            assertEquals(x.key, y.key);
            x = x.next;
            y = y.next;
        }
    }

    @DataProvider
    public static Object[][] provideDataForInsertingElementOnSinglyLinkedList() {
        return new Object[][]{
                {new SinglyLinkedList<>(), 35},
                {new SinglyLinkedList<>(34), 35},
                {new SinglyLinkedList<>(5,7,9,2,6,1,6,6,3,1,7,8), 3},
                {new SinglyLinkedList<>(5.0,-2.3,-1.3,-1.9,-2.3), 2.3},
                {new SinglyLinkedList<>("aaa","bbb","aaa","ccc"), "xyz"}
        };
    }

    @Test
    @UseDataProvider("provideDataForInsertingElementOnSinglyLinkedList")
    public <T> void shouldInsertElementOnSinglyLinkedList(SinglyLinkedList<T> list, T key) {
        // given
        SinglyLinkedList<T> original = new SinglyLinkedList<>(list);

        // when
        Chapter10.singlyLinkedListInsert(list, list.new Node(key));

        // then
        assertNotNull(list.head);
        assertEquals(key, list.head.key);
        SinglyLinkedList<T>.Node x = original.head;
        SinglyLinkedList<T>.Node y = list.head.next;
        while (x != null) {
            assertEquals(x.key, y.key);
            x = x.next;
            y = y.next;
        }
    }

    @DataProvider
    public static Object[][] provideDataForDeletingElementFromSinglyLinkedList() {
        return new Object[][]{
                {new SinglyLinkedList<>(34), 1},
                {new SinglyLinkedList<>(5,7,9,2,6,1,6,6,3,1,7,8), 4},
                {new SinglyLinkedList<>(5.0,-2.3,-1.3,-1.9,-2.3), 4},
                {new SinglyLinkedList<>("aaa","bbb","aaa","ccc"), 4}
        };
    }

    @Test
    @UseDataProvider("provideDataForDeletingElementFromSinglyLinkedList")
    public <T> void shouldDeleteElementFromSinglyLinkedList(SinglyLinkedList<T> list, int elementPosition) {
        // given
        SinglyLinkedList<T>.Node nodeToDelete = list.head;
        for (int i = 2; i <= elementPosition; i++) {
            nodeToDelete = nodeToDelete.next;
        }
        SinglyLinkedList<T> original = new SinglyLinkedList<>(list);

        // when
        Chapter10.singlyLinkedListDelete(list, nodeToDelete);

        // then
        SinglyLinkedList<T>.Node x = original.head;
        SinglyLinkedList<T>.Node y = list.head;
        while (x != null) {
            if (y != null && x.key.equals(y.key)) {
                x = x.next;
                y = y.next;
            } else {
                assertEquals(x.key, nodeToDelete.key);
                x = x.next;
            }
        }
    }

    @Test
    @UseDataProvider("provideDataForInsertingElementOnSinglyLinkedList")
    public <T> void shouldPushElementOnSinglyLinkedList(SinglyLinkedList<T> list, T key) {
        // given
        SinglyLinkedList<T> original = new SinglyLinkedList<>(list);

        // when
        Chapter10.singlyLinkedListPush(list, key);

        // then
        assertNotNull(list.head);
        assertEquals(key, list.head.key);
        SinglyLinkedList<T>.Node x = original.head;
        SinglyLinkedList<T>.Node y = list.head.next;
        while (x != null) {
            assertEquals(x.key, y.key);
            x = x.next;
            y = y.next;
        }
    }

    @DataProvider
    public static Object[][] provideDataForPerformingPopOnSinglyLinkedList() {
        return new Object[][]{
                {new SinglyLinkedList<>(34)},
                {new SinglyLinkedList<>(5,7,9,2,6,1,6,6,3,1,7,8)},
                {new SinglyLinkedList<>(5.0,-2.3,-1.3,-1.9,-2.3)},
                {new SinglyLinkedList<>("aaa","bbb","aaa","ccc")}
        };
    }

    @Test
    @UseDataProvider("provideDataForPerformingPopOnSinglyLinkedList")
    public <T> void shouldPopElementFromSinglyLinkedList(SinglyLinkedList<T> list) {
        // given
        SinglyLinkedList<T> original = new SinglyLinkedList<>(list);

        // when
        Chapter10.singlyLinkedListPop(list);

        // then
        SinglyLinkedList<T>.Node x = original.head.next;
        SinglyLinkedList<T>.Node y = list.head;
        while (x != null) {
            assertEquals(x.key, y.key);
            x = x.next;
            y = y.next;
        }
    }

    @DataProvider
    public static Object[][] provideDataForPerformingEnqueueOnSinglyLinkedListWithTail() {
        return new Object[][]{
                {new SinglyLinkedListWithTail<>(), 35},
                {new SinglyLinkedListWithTail<>(34), 35},
                {new SinglyLinkedListWithTail<>(5,7,9,2,6,1,6,6,3,1,7,8), 3},
                {new SinglyLinkedListWithTail<>(5.0,-2.3,-1.3,-1.9,-2.3), 2.3},
                {new SinglyLinkedListWithTail<>("aaa","bbb","aaa","ccc"), "xyz"}
        };
    }

    @Test
    @UseDataProvider("provideDataForPerformingEnqueueOnSinglyLinkedListWithTail")
    public <T> void shouldEnqueueElementOnSinglyLinkedList(SinglyLinkedListWithTail<T> list, T key) {
        // given
        SinglyLinkedListWithTail<T> original = new SinglyLinkedListWithTail<>(list);

        // when
        Chapter10.singlyLinkedListEnqueue(list, key);

        // then
        assertNotNull(list.head);
        SinglyLinkedListWithTail<T>.Node x = original.head;
        SinglyLinkedListWithTail<T>.Node y = list.head;
        while (x != original.tail) {
            assertEquals(x.key, y.key);
            x = x.next;
            y = y.next;
        }
        assertEquals(key, list.tail.key);
    }

    @DataProvider
    public static Object[][] provideDataForPerformingDequeueOnSinglyLinkedListWithTail() {
        return new Object[][]{
                {new SinglyLinkedListWithTail<>(34)},
                {new SinglyLinkedListWithTail<>(5,7,9,2,6,1,6,6,3,1,7,8)},
                {new SinglyLinkedListWithTail<>(5.0,-2.3,-1.3,-1.9,-2.3)},
                {new SinglyLinkedListWithTail<>("aaa","bbb","aaa","ccc")}
        };
    }

    @Test
    @UseDataProvider("provideDataForPerformingDequeueOnSinglyLinkedListWithTail")
    public <T> void shouldDequeueElementFromSinglyLinkedListWithTail(SinglyLinkedListWithTail<T> list) {
        // given
        SinglyLinkedListWithTail<T> original = new SinglyLinkedListWithTail<>(list);

        // when
        Chapter10.singlyLinkedListDequeue(list);

        // then
        SinglyLinkedList<T>.Node x = original.head.next;
        SinglyLinkedList<T>.Node y = list.head;
        while (x != null) {
            assertEquals(x.key, y.key);
            x = x.next;
            y = y.next;
        }
    }

    @DataProvider
    public static Object[][] provideDataForInsertingElementOnCircularList() {
        return new Object[][]{
                {new CircularList<>(), 35},
                {new CircularList<>(34), 35},
                {new CircularList<>(5,7,9,2,6,1,6,6,3,1,7,8), 3},
                {new CircularList<>(5.0,-2.3,-1.3,-1.9,-2.3), 2.3},
                {new CircularList<>("aaa","bbb","aaa","ccc"), "xyz"}
        };
    }

    @Test
    @UseDataProvider("provideDataForInsertingElementOnCircularList")
    public <T> void shouldInsertElementOnCircularList(CircularList<T> list, T key) {
        // given
        CircularList<T> original = new CircularList<>(list);

        // when
        Chapter10.circularListInsert(list, list.new Node(key));

        // then
        assertNotNull(list.head);
        assertEquals(key, list.head.next.key);
        if (original.head == null) {
            return;
        }
        CircularList<T>.Node x = original.head.next;
        CircularList<T>.Node y = list.head.next.next;
        while (x != original.head) {
            assertEquals(x.key, y.key);
            x = x.next;
            y = y.next;
        }
    }

    @DataProvider
    public static Object[][] provideDataForDeletingElementFromCircularList() {
        return new Object[][]{
                {new CircularList<>(34), 1},
                {new CircularList<>(5,7,9,2,6,1,6,6,3,1,7,8), 4},
                {new CircularList<>(5.0,-2.3,-1.3,-1.9,-2.3), 4},
                {new CircularList<>("aaa","bbb","aaa","ccc"), 4}
        };
    }

    @Test
    @UseDataProvider("provideDataForDeletingElementFromCircularList")
    public <T> void shouldDeleteElementFromCircularList(CircularList<T> list, int elementPosition) {
        // given
        CircularList<T>.Node nodeToDelete = list.head;
        for (int i = 2; i <= elementPosition; i++) {
            nodeToDelete = nodeToDelete.next;
        }
        CircularList<T> original = new CircularList<>(list);

        // when
        Chapter10.circularListDelete(list, nodeToDelete);

        // then
        if (list.head == null) {
            return;
        }
        if (!original.head.key.equals(list.head.key)) {
            assertEquals(original.head.key, nodeToDelete.key);
        }
        CircularList<T>.Node x = original.head.next;
        CircularList<T>.Node y = list.head.next;
        while (x != original.head) {
            if (y != original.head && x.key.equals(y.key)) {
                x = x.next;
                y = y.next;
            } else {
                assertEquals(x.key, nodeToDelete.key);
                x = x.next;
            }
        }
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulListSearchUsingCircularListSearch() {
        return new Object[][]{
                {new CircularList<>(34), 34},
                {new CircularList<>(5,7,9,2,6,1,6,6,3,1,7,8), 6},
                {new CircularList<>(5,7,9,2,6,1,6,6,3,1,7,8), 8},
                {new CircularList<>(5.0,-2.3,-1.3,-1.9,-2.3), -2.3},
                {new CircularList<>("aaa","bbb","aaa","ccc"), "ccc"}
        };
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulListSearchUsingCircularListSearch")
    public <T> void shouldFindKeyOnCircularList(CircularList<T> list, T key) {
        // given

        // when
        CircularList<T>.Node actualNode = Chapter10.circularListSearch(list, key);

        // then
        assertNotNull(actualNode);
        assertEquals(key, actualNode.key);
        assertNotNull(list.head);
        boolean found = list.head.key.equals(actualNode.key);
        CircularList<T>.Node x = list.head.next;
        while (x != list.head && !found) {
            found = x.key.equals(actualNode.key);
            x = x.next;
        }
        assertTrue(found);
    }

    @DataProvider
    public static Object[][] provideDataForUnsuccessfulListSearchUsingCircularListSearch() {
        return new Object[][]{
                {new CircularList<>(34), 35},
                {new CircularList<>(5,7,9,2,6,1,6,6,3,1,7,8), 4},
                {new CircularList<>(5.0,-2.3,-1.3,-1.9,-2.3), 2.3},
                {new CircularList<>("aaa","bbb","aaa","ccc"), "xyz"}
        };
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulListSearchUsingCircularListSearch")
    public <T> void shouldNotFindKeyOnCircularList(CircularList<T> list, T key) {
        // given

        // when
        CircularList<T>.Node actualNode = Chapter10.circularListSearch(list, key);

        // then
        assertNull(actualNode);
    }

    @DataProvider
    public static Object[][] provideDataForCircularListsUnion() {
        return new Object[][]{
                {new CircularList<>(),new CircularList<>()},
                {new CircularList<>(1),new CircularList<>()},
                {new CircularList<>(12,44,26,20,67,4,21,66,35,51,13),new CircularList<>(55,23,2,74,30,47)}
        };
    }

    @Test
    @UseDataProvider("provideDataForCircularListsUnion")
    public <T> void shouldUnionCircularLists(CircularList<T> list1, CircularList<T> list2) {
        // given
        Array<T> originalArray1 = list1.toArray();
        Array<T> originalArray2 = list2.toArray();

        // when
        CircularList<T> actualMergedLists = Chapter10.circularListsUnion(list1, list2);

        // then
        Array<T> actualMergedArray = actualMergedLists.toArray();
        Array<T> expectedMergedArray = Array.withLength(originalArray1.length + originalArray2.length);
        for (int i = 1; i <= originalArray1.length; i++) {
            expectedMergedArray.set(i, originalArray1.at(i));
        }
        for (int i = 1; i <= originalArray2.length; i++) {
            expectedMergedArray.set(originalArray1.length + i, originalArray2.at(i));
        }
        assertShuffled(expectedMergedArray, actualMergedArray);
    }

    @DataProvider
    public static Object[][] provideDataForReversingSinglyLinkedList() {
        return new Object[][]{
                {new SinglyLinkedList<>()},
                {new SinglyLinkedList<>(34)},
                {new SinglyLinkedList<>(5,7,9,2,6,1,6,6,3,1,7,8)},
                {new SinglyLinkedList<>(5.0,-2.3,-1.3,-1.9,-2.3)},
                {new SinglyLinkedList<>("aaa","bbb","aaa","ccc")}
        };
    }

    @Test
    @UseDataProvider("provideDataForReversingSinglyLinkedList")
    public <T> void shouldReverseSinglyLinkedList(SinglyLinkedList<T> list) {
        // given
        SinglyLinkedList<T> original = new SinglyLinkedList<>(list);

        // when
        Chapter10.singlyLinkedListReverse(list);

        // then
        SinglyLinkedList<T> expectedReversed = new SinglyLinkedList<>();
        SinglyLinkedList<T>.Node x = original.head;
        SinglyLinkedList<T>.Node y;
        while (x != null) {
            y = expectedReversed.new Node(x.key);
            y.next = expectedReversed.head;
            expectedReversed.head = y;
            x = x.next;
        }
        x = list.head;
        y = expectedReversed.head;
        while (x != null) {
            assertEquals(y.key, x.key);
            x = x.next;
            y = y.next;
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
        assertArrayEquals(expectedOutput, actualOutput);
    }

}
