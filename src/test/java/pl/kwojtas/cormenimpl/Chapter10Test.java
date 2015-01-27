package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Deque;
import pl.kwojtas.cormenimpl.util.DoubleStack;
import pl.kwojtas.cormenimpl.util.Pair;
import pl.kwojtas.cormenimpl.util.Queue;
import pl.kwojtas.cormenimpl.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

}
