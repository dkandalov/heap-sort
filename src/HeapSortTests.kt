import kotlincommon.listOfInts
import kotlincommon.permutations
import kotlincommon.printed
import kotlincommon.swap
import kotlincommon.test.shouldEqual
import org.junit.Test
import kotlin.random.Random

class HeapSortTests {
    @Test fun `sort list of integers`() {
        emptyList<Int>().heapSort() shouldEqual emptyList()

        listOf(1, 2, 3).permutations().forEach {
            it.printed().heapSort() shouldEqual listOf(1, 2, 3)
        }

        listOf(1, 2, 3, 4).permutations().forEach {
            it.printed().heapSort() shouldEqual listOf(1, 2, 3, 4)
        }
        listOf(1, 2, 3, 4, 5).permutations().forEach {
            it.printed().heapSort() shouldEqual listOf(1, 2, 3, 4, 5)
        }

        val list = Random.listOfInts(
            sizeRange = 0..100,
            valuesRange = 0..100
        )
        list.heapSort().windowed(size = 2)
            .all { it[0] <= it[1] } shouldEqual true
    }

    @Test fun `array-backed binary heap`() {
        val heap = BinaryHeap()
        heap.add(4)
        heap.add(1)
        heap.add(2)
        heap.add(3)

        heap.remove() shouldEqual 1
        heap.size shouldEqual 3

        heap.remove() shouldEqual 2
        heap.size shouldEqual 2

        heap.remove() shouldEqual 3
        heap.size shouldEqual 1

        heap.remove() shouldEqual 4
        heap.size shouldEqual 0
    }
}

fun List<Int>.heapSort(): List<Int> = sequence {
    val heap = BinaryHeap()
    forEach { heap.add(it) }
    while (heap.size > 0) {
        yield(heap.remove())
    }
}.toList()

class BinaryHeap {
    private val array = Array(100, { 0 })
    var size = 0

    fun add(element: Int) {
        array[size++] = element
        siftUp(size - 1)
    }

    fun remove(): Int {
        val result = array[0]
        array[0] = array[size - 1]
        size--
        siftDown(0)
        return result
    }

    private fun siftUp(index: Int) {
        if (index == 0) return
        val parentIndex = (index - 1) / 2
        if (array[parentIndex] > array[index]) {
            array.swap(parentIndex, index)
            siftUp(parentIndex)
        }
    }

    private fun siftDown(index: Int) {
        val leftChild = index * 2 + 1
        val rightChild = index * 2 + 2
        val minIndex = listOf(index, leftChild, rightChild)
            .filter { it < size }
            .minBy { array[it] } ?: return

        if (minIndex != index) {
            array.swap(minIndex, index)
            siftDown(minIndex)
        }
    }
}
