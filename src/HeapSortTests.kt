import kotlincommon.permutations
import kotlincommon.swap
import kotlincommon.test.shouldEqual
import org.junit.Test

class HeapSortTests {
    @Test fun `sort a list of integers`() {
        emptyList<Int>().heapSort() shouldEqual emptyList()
        listOf(1).heapSort() shouldEqual listOf(1)

        listOf(1, 2, 3).permutations().forEach {
            it.heapSort() shouldEqual listOf(1, 2, 3)
        }
        listOf(1, 2, 3, 4).permutations().forEach {
            it.heapSort() shouldEqual listOf(1, 2, 3, 4)
        }
    }

    @Test fun `array-backed binary heap`() {
        val heap = BinaryHeap()

        heap.add(3)
        heap.add(2)
        heap.add(1)

        heap.remove() shouldEqual 1
        heap.size shouldEqual 2
        heap.remove() shouldEqual 2
        heap.size shouldEqual 1
        heap.remove() shouldEqual 3
        heap.size shouldEqual 0
    }
}

private fun List<Int>.heapSort(): List<Int> {
    val result = ArrayList<Int>()
    val heap = BinaryHeap(this)
    while (heap.size > 0) {
        result.add(heap.remove())
    }
    return result
}

class BinaryHeap {
    private val array = arrayOf(0, 0, 0, 0)
    var size = 0

    constructor(list: List<Int> = emptyList()) {
        list.forEach { add(it) }
    }

    fun add(element: Int) {
        array[size] = element
        siftUp(size)
        size++
    }

    fun remove(): Int {
        val result = array[0]
        size--
        array[0] = array[size]
        siftDown(0)
        return result
    }

    private fun siftUp(index: Int) {
        val parentIndex = if (index == 0) -1 else (index - 1) / 2
        if (parentIndex != -1 && array[parentIndex] > array[index]) {
            array.swap(parentIndex, index)
            siftUp(parentIndex)
        }
    }

    private fun siftDown(index: Int) {
        val childIndex1 = index * 2 + 1
        val childIndex2 = index * 2 + 2
        var minIndex = index
        if (childIndex1 < size && array[childIndex1] < array[minIndex]) {
            minIndex = childIndex1
        }
        else if (childIndex2 < size && array[childIndex2] < array[minIndex]) {
            minIndex = childIndex2
        }
        if (minIndex != index) {
            array.swap(minIndex, index)
            siftDown(minIndex)
        }
    }
}
