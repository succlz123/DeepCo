package org.succlz123.lib.file

import java.io.FileInputStream
import java.io.FilenameFilter
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

fun java.io.File.toProjectFile(): File = object : File {

    override val name: String
        get() = this@toProjectFile.name

    override val absolutePath: String
        get() = this@toProjectFile.absolutePath

    override val isDirectory: Boolean
        get() = this@toProjectFile.isDirectory

    override val children: List<File>
        get() = this@toProjectFile
            .listFiles(FilenameFilter { _, name -> !name.startsWith(".") })
            .orEmpty()
            .map { it.toProjectFile() }

    private val numberOfFiles
        get() = listFiles()?.size ?: 0

    override val hasChildren: Boolean
        get() = isDirectory && numberOfFiles > 0
}

// Backport slice from JDK 13
private fun ByteBuffer.slice(index: Int, length: Int): ByteBuffer {
    position(index)
    return slice().limit(length) as ByteBuffer
}

private fun java.io.File.readLinePositions(starts: IntList) {
    require(length() <= Int.MAX_VALUE) {
        "Files with size over ${Int.MAX_VALUE} aren't supported"
    }

    val averageLineLength = 200
    starts.clear(length().toInt() / averageLineLength)

    try {
        for (i in readLinePositions()) {
            starts.add(i)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        starts.clear(1)
        starts.add(0)
    }

    starts.compact()
}

private fun java.io.File.readLinePositions() = sequence {
    require(length() <= Int.MAX_VALUE) {
        "Files with size over ${Int.MAX_VALUE} aren't supported"
    }
    readBuffer {
        yield(position())
        while (hasRemaining()) {
            val byte = get()
            if (byte.isChar('\n')) {
                yield(position())
            }
        }
    }
}

private inline fun java.io.File.readBuffer(block: ByteBuffer.() -> Unit) {
    FileInputStream(this).use { stream ->
        stream.channel.use { channel ->
            channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size()).block()
        }
    }
}

private fun Byte.isChar(char: Char) = toInt().toChar() == char

/**
 * Compact version of List<Int> (without unboxing Int and using IntArray under the hood)
 */
private class IntList(initialCapacity: Int = 16) {
    @Volatile
    private var array = IntArray(initialCapacity)

    @Volatile
    var size: Int = 0
        private set

    fun clear(capacity: Int) {
        array = IntArray(capacity)
        size = 0
    }

    fun add(value: Int) {
        if (size == array.size) {
            doubleCapacity()
        }
        array[size++] = value
    }

    operator fun get(index: Int) = array[index]

    private fun doubleCapacity() {
        val newArray = IntArray(array.size * 2 + 1)
        System.arraycopy(array, 0, newArray, 0, size)
        array = newArray
    }

    fun compact() {
        array = array.copyOfRange(0, size)
    }
}
