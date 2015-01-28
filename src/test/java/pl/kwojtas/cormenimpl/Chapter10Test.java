package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.CircularList;
import pl.kwojtas.cormenimpl.util.Deque;
import pl.kwojtas.cormenimpl.util.DoubleStack;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.ListWithSentinel;
import pl.kwojtas.cormenimpl.util.Pair;
import pl.kwojtas.cormenimpl.util.Queue;
import pl.kwojtas.cormenimpl.util.SinglyLinkedList;
import pl.kwojtas.cormenimpl.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class Chapter10Test {

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
    public <T> void shouldFindKeyUsingListSearch_(ListWithSentinel<T> list, T key) {
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
    public <T> void shouldNotFindKeyUsingListSearchUsingListSearch_(ListWithSentinel<T> list, T key) {
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
    public <T> void shouldFindKeyUsingCircularListSearch(CircularList<T> list, T key) {
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
    public <T> void shouldNotFindKeyUsingListSearchUsingCircularListSearch(CircularList<T> list, T key) {
        // given

        // when
        CircularList<T>.Node actualNode = Chapter10.circularListSearch(list, key);

        // then
        assertNull(actualNode);
    }

}
