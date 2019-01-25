import kotlincommon.permutations
import kotlincommon.printed
import kotlincommon.swap
import kotlincommon.test.shouldEqual
import org.junit.Test
import java.util.*

class HeapSortTests {
    @Test fun `sort list of integers`() {
        fun List<Int>.canBeSorted() =
            this.permutations().forEach {
                it.printed().heapSort() shouldEqual this
            }

        emptyList<Int>().canBeSorted()
        listOf(1, 2, 3).canBeSorted()
        listOf(1, 2, 3, 4).canBeSorted()
        listOf(1, 2, 3, 4, 5).canBeSorted()
        listOf(1, 2, 3, 4, 5, 6).canBeSorted()
    }

    @Test fun `array-based binary heap`() {
        val heap = BinaryHeap()

        heap.add(4)
        heap.add(3)
        heap.add(2)
        heap.add(1)

        heap.remove() shouldEqual 1
        heap.remove() shouldEqual 2
        heap.remove() shouldEqual 3
        heap.remove() shouldEqual 4
    }
}

fun List<Int>.heapSort(): List<Int> {
    val heap = BinaryHeap()
    forEach { heap.add(it) }

    val result = ArrayList<Int>()
    while (heap.isNotEmpty()) {
        result.add(heap.remove())
    }
    return result
}

class BinaryHeap {
    private val array = Array(100, { 0 })
    private var size = 0

    fun add(element: Int) {
        array[size++] = element
        pullUp(size - 1)
    }

    fun remove(): Int {
        val result = array[0]
        array[0] = array[size - 1]
        size--
        pushDown(0)
        return result
    }

    private fun pushDown(index: Int) {
        val minIndex = listOf(index, index * 2 + 1, index * 2 + 2)
            .filter { it < size }
            .minBy { array[it] } ?: return

        if (minIndex != index) {
            array.swap(minIndex, index)
            pushDown(minIndex)
        }
    }

    private fun pullUp(index: Int) {
        if (index == 0) return
        val parentIndex = (index - 1) / 2
        if (array[parentIndex] > array[index]) {
            array.swap(parentIndex, index)
            pullUp(parentIndex)
        }
    }

    fun isNotEmpty(): Boolean {
        return size > 0
    }
}
