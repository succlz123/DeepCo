package org.succlz123.lib.thread

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import kotlin.coroutines.cancellation.CancellationException

class TaskManager<T>(private val threadCount: Int = 3) {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val executor = Executors.newFixedThreadPool(threadCount)
    private val taskMap = ConcurrentHashMap<String, Deferred<T>>()

    suspend fun execute(key: String, task: suspend () -> T): T {
        // 检查是否有相同 key 的任务正在执行
        val existingTask = taskMap[key]
        if (existingTask != null) {
            println("已经有任务在执行")
            return try {
                existingTask.await()
            } catch (e: CancellationException) {
                throw e
            } finally {
                println("上一个相同任务结束 - 移除")
                taskMap.remove(key)
            }
        }

        // 创建新任务
        val deferred = scope.async(executor.asCoroutineDispatcher()) {
            try {
                println("Task start: ")
                task()
            } finally {
                println("任务结束 - 移除")
                taskMap.remove(key)
            }
        }

        taskMap[key] = deferred

        return try {
            deferred.await()
        } catch (e: CancellationException) {
            println("cancel task")
            deferred.cancel()
            throw e
        } finally {
            taskMap.remove(key)
        }
    }

    fun cancelTask(key: String) {
        taskMap[key]?.cancel()
    }

    fun cancelAll() {
        taskMap.forEach { (_, deferred) -> deferred.cancel() }
    }

    fun shutdown() {
        cancelAll()
        executor.shutdown()
        scope.cancel()
    }
}