import kotlincommon.permutations
import kotlincommon.swap
import kotlincommon.test.shouldEqual
import org.junit.Test

class HeapSortTests {
    @Test fun `sort a list of integers`() {
        fun List<Int>.canBeSorted() =
            permutations().forEach {
                it.heapSort() shouldEqual this
            }

        listOf<Int>().canBeSorted()
        listOf(1).canBeSorted()
        listOf(1, 2).canBeSorted()
        listOf(1, 2, 3).canBeSorted()
        listOf(1, 2, 3, 4).canBeSorted()
        listOf(1, 2, 3, 4, 5).canBeSorted()
        listOf(1, 2, 3, 4, 5, 6).canBeSorted()
    }

    @Test fun `array-based binary min heap`() {
        val heap = Heap()

        heap.add(3)
        heap.add(2)
        heap.add(1)
        heap.add(4)

        heap.remove() shouldEqual 1
        heap.remove() shouldEqual 2
        heap.remove() shouldEqual 3
        heap.remove() shouldEqual 4
    }
}

fun List<Int>.heapSort(): List<Int> {
    val heap = Heap()
    forEach { element -> heap.add(element) }
    val result = ArrayList<Int>()
    while (heap.size > 0) {
        result.add(heap.remove())
    }
    return result
}


class Heap {
    private val array = arrayOf(0, 0, 0, 0, 0, 0, 0, 0)
    var size = 0

    fun add(element: Int) {
        array[size++] = element
        pullUp(size - 1)
    }

    private fun pullUp(index: Int) {
        if (index == 0) return
        val parentIndex = (index - 1) / 2
        if (array[parentIndex] > array[index]) {
            array.swap(parentIndex, index)
            pullUp(parentIndex)
        }
    }

    fun remove(): Int {
        val result = array[0]
        array[0] = array[size - 1]
        size--
        pushDown(0)
        return result
    }

    private fun pushDown(index: Int) {
        val leftChild = index * 2 + 1
        val rightChild = index * 2 + 2
        val minIndex = listOf(index, leftChild, rightChild)
            .filter { it < size }
            .minBy { array[it] } ?: return

        if (minIndex != index) {
            array.swap(minIndex, index)
            pushDown(minIndex)
        }
    }
}
