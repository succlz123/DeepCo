package org.succlz123.lib.file

expect val HomeFolder: File

interface File {
    val name: String
    val absolutePath: String
    val isDirectory: Boolean
    val children: List<File>
    val hasChildren: Boolean
}