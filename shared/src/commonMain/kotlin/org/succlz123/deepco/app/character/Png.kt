package org.succlz123.deepco.app.character


import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.zip.CRC32
import java.util.Base64

object Png {
    private val crc32 = CRC32()

    data class TextChunk(val keyword: String, val text: String)
    data class PngChunk(val type: String, val data: ByteArray, val crc: Int = 0)

    fun decodeText(data: ByteArray): TextChunk {
        var naming = true
        val keyword = StringBuilder()
        val text = StringBuilder()

        for (b in data) {
            when {
                naming -> {
                    if (b.toInt() == 0) {
                        naming = false
                    } else {
                        keyword.append(b.toInt().toChar())
                    }
                }

                else -> {
                    if (b.toInt() == 0) throw IllegalArgumentException("Invalid NULL in tEXt chunk")
                    text.append(b.toInt().toChar())
                }
            }
        }

        return TextChunk(keyword.toString(), text.toString())
    }

    fun encodeText(keyword: String, text: String): ByteArray {
        require(keyword.all { it.code in 0x00..0xFF }) { "Invalid keyword characters" }
        require(text.all { it.code in 0x00..0xFF }) { "Invalid text characters" }
        require(keyword.length <= 79) { "Keyword too long" }

        return ByteArray(keyword.length + text.length + 1).apply {
            keyword.mapIndexed { i, c -> this[i] = c.code.toByte() }
            this[keyword.length] = 0
            text.mapIndexed { i, c -> this[keyword.length + 1 + i] = c.code.toByte() }
        }
    }

    fun readChunk(data: ByteArray, offset: Int): Pair<PngChunk, Int> {
        val buffer = ByteBuffer.wrap(data, offset, data.size - offset)
            .order(ByteOrder.BIG_ENDIAN)

        val length = buffer.int
        val typeBytes = ByteArray(4).apply { buffer.get(this) }
        val type = String(typeBytes, Charsets.ISO_8859_1)

        val chunkData = ByteArray(length).apply { buffer.get(this) }
        val crcStored = buffer.int

        crc32.reset()
        crc32.update(typeBytes)
        crc32.update(chunkData)
        val crcCalculated = crc32.value.toInt()

        if (crcStored != crcCalculated) throw IllegalArgumentException("CRC mismatch for $type")

        return PngChunk(type, chunkData, crcStored) to buffer.position()
    }

    fun readChunks(data: ByteArray): List<PngChunk> {
        require(
            data.size >= 8 && data[0] == 0x89.toByte()
                    && data.sliceArray(1..7).contentEquals(
                byteArrayOf(
                    0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A
                )
            )
        ) { "Invalid PNG header" }

        val chunks = mutableListOf<PngChunk>()
        var offset = 8

        while (offset < data.size) {
            val (chunk, newOffset) = readChunk(data, offset)
            chunks.add(chunk)
            offset = newOffset
        }

        require(chunks.isNotEmpty() && chunks.first().type == "IHDR") { "Missing IHDR" }
        require(chunks.last().type == "IEND") { "Missing IEND" }

        return chunks
    }

    fun encodeChunks(chunks: List<PngChunk>): ByteArray {
        val buffer = ByteArrayOutputStream().apply {
            write(byteArrayOf(0x89.toByte(), 0x50.toByte(), 0x4E.toByte(), 0x47.toByte(), 0x0D.toByte(), 0x0A.toByte(), 0x1A.toByte(), 0x0A.toByte()))
        }

        chunks.forEach { chunk ->
            ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).apply {
                putInt(chunk.data.size)
                buffer.write(array())
            }

            buffer.write(chunk.type.toByteArray(Charsets.ISO_8859_1))
            buffer.write(chunk.data)

            crc32.reset()
            crc32.update(chunk.type.toByteArray(Charsets.ISO_8859_1))
            crc32.update(chunk.data)
            val crc = crc32.value.toInt()

            ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).apply {
                putInt(crc)
                buffer.write(array())
            }
        }

        return buffer.toByteArray()
    }

    fun parse(arrayBuffer: ByteArray): String {
        val chunks = readChunks(arrayBuffer)
        val textChunks = chunks.filter { it.type == "tEXt" }.map { decodeText(it.data) }
        val charaChunk = textChunks.find { it.keyword == "chara" }
            ?: throw IllegalArgumentException("Missing chara chunk")

        return try {
            Base64.getDecoder().decode(charaChunk.text).toString(Charsets.UTF_8)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid base64 in chara chunk", e)
        }
    }

    fun generate(arrayBuffer: ByteArray, text: String): ByteArray {
        val chunks = readChunks(arrayBuffer)
            .filterNot { it.type == "tEXt" }
            .toMutableList()

        val charaData = Base64.getEncoder().encodeToString(text.toByteArray(Charsets.UTF_8))
        val textChunk = PngChunk(
            "tEXt",
            encodeText("chara", charaData)
        )

        chunks.add(chunks.lastIndex, textChunk)
        return encodeChunks(chunks)
    }
}
