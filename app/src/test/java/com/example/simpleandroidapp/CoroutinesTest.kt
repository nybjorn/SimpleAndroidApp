package com.example.simpleandroidapp

import kotlinx.coroutines.*
import org.junit.Test

import kotlin.system.measureTimeMillis

/**
 * GRASP KOTLIN’S COROUTINES WITH THIS SHORT TUTORIAL
 * https://silica.io/understanding-kotlin-coroutines/
 */
class CoroutinesTest {
    @Test
    fun coroutines() {
        println("Starting a coroutine block...")
        runBlocking() {
            println(" Coroutine block started in ${Thread.currentThread().name}")
            launch {
                println("  1/ First coroutine start in ${Thread.currentThread().name}")
                delay(100)
                println("  1/ First coroutine end")
            }
            launch {
                println("  2/ Second coroutine start in ${Thread.currentThread().name}")
                delay(50)
                println("  2/ Second coroutine end")
            }
            println(" Two coroutines have been launched")
        }
        println("Back from the coroutine block")
    }

    @Test
    fun no_concurrency() {
        runBlocking {
            val launch = launch {
                println("First coroutine start, suspend for 50ms")
                delay(50)
                println("First coroutine : starting some computation for 100ms")
                val t0 = System.currentTimeMillis()
                while (System.currentTimeMillis() - t0 < 100) {
                    // Simulate a computation taking 100ms by wasting
                    // CPU cycles in this loop
                    // We could also use blocking_delay()
                }
                println("Computation ended")
            }
            launch {
                println("Second coroutine start, suspend for 100ms")
                val time = measureTimeMillis {
                    delay(100)
                }
                println("Second coroutine end after ${time}ms")
            }
        }
    }

    @Test
    fun yielding() {
        runBlocking {
            launch {
                println("First coroutine start, suspend for 50ms")
                delay(50)
                println("First coroutine : starting some yielding computation for 100ms")
                val t0 = System.currentTimeMillis()
                while (System.currentTimeMillis() - t0 < 100) {
                    // Simulate some work but yield thread
                    // execution as often as possible
                    blocking_delay(1)
                    yield()
                }
                println("Computation ended")
            }
            launch {
                println("Second coroutine start, suspend for 100ms")
                val time = measureTimeMillis {
                    delay(100)
                }
                println("Second coroutine end after ${time}ms")
            }
        }
    }

    @Test
    fun multi_threading() {
        repeat(2) { i ->
            println("#$i Starting a coroutine block from thread ${Thread.currentThread().name}")
            val time = measureTimeMillis {
                runBlocking(Dispatchers.Default) {
                    println(" #$i Coroutine block started in thread ${Thread.currentThread().name}")
                    launch {
                        println(" #$i First coroutine started in thread ${Thread.currentThread().name}")
                        blocking_delay(100) // Simulate some work
                        println(" #$i First coroutine end")
                    }
                    launch {
                        println(" #$i Second coroutine started in thread ${Thread.currentThread().name}")
                        blocking_delay(50) // Simulate some work
                        println(" #$i Second coroutine end")
                    }
                }
            }
            println("#$i Back from the coroutine block in thread ${Thread.currentThread().name} after ${time}ms")
        }
    }

    @Test
    fun context_switching() {  val uiContext = newSingleThreadContext("UI_Thread")
        println("Starting a coroutine block from thread ${Thread.currentThread().name}")
        runBlocking(Dispatchers.Default) {
            println(" Coroutine block started in thread ${Thread.currentThread().name}")
            launch {
                println("  First coroutine started in thread ${Thread.currentThread().name}")
                blocking_delay(100) // Simulate some work
                val computationResult = 29
                launch(uiContext) {
                    println("   First coroutine reporting result=$computationResult to the UI in thread ${Thread.currentThread().name}")
                }
                println("  First coroutine end")
            }
            launch {
                println("  Second coroutine started in thread ${Thread.currentThread().name}")
                blocking_delay(50) // Simulate some work
                val computationResult = 13
                launch(uiContext) {
                    println("   Second coroutine reporting result=$computationResult to the UI in thread ${Thread.currentThread().name}")
                }
                println("  Second coroutine end")
            }
        }
        println(" Back from the coroutine block in thread ${Thread.currentThread().name}")
        uiContext.close()
    }

    @Test
    fun context_switching_sync() {  val uiContext = newSingleThreadContext("UI_Thread")
        println("Starting a coroutine block from thread ${Thread.currentThread().name}")
        runBlocking(Dispatchers.Default) {
            println(" Coroutine block started in thread ${Thread.currentThread().name}")
            launch {
                println("  First coroutine started in thread ${Thread.currentThread().name}")
                blocking_delay(100) // Simulate some work
                val computationResult = 29
                withContext(uiContext) {
                    println("   First coroutine reporting result=$computationResult to the UI in thread ${Thread.currentThread().name}")
                }
                println("  First coroutine end")
            }
            launch {
                println("  Second coroutine started in thread ${Thread.currentThread().name}")
                blocking_delay(50) // Simulate some work
                val computationResult = 13
                withContext(uiContext) {
                    println("   Second coroutine reporting result=$computationResult to the UI in thread ${Thread.currentThread().name}")
                }
                println("  Second coroutine end")
            }
        }
        println(" Back from the coroutine block in thread ${Thread.currentThread().name}")
        uiContext.close()
    }

    private fun blocking_delay(delay_in_millis : Long) {
        Thread.sleep(delay_in_millis)
    }

    @Test
    fun sub_scope() {
        runBlocking {
            println("Doing something...")
            println("Creating a new coroutine scope containing two coroutines")
            coroutineScope {
                launch {
                    firstSubroutine()
                }
                launch {
                    println(" 2/ Second coroutine start")
                    delay(50)
                    println(" 2/ Second coroutine end")
                }
            }
            println("End of the new coroutine scope")
            println("Doing something else...")
        }
    }

    private suspend fun firstSubroutine() {
        println(" 1/ First coroutine start")
        delay(100)
        println(" 1/ First coroutine end")
    }

    @Test
    fun coroutines3() {
        println("Starting a coroutine block...")
        runBlocking {
            println(" Coroutine block started")
            val launch_1 = async {
                println("  1/ First coroutine start")
                delay(100)
                println("  1/ First coroutine end")
                3
            }
            val launch_2 = async {
                println("  2/ Second coroutine start")
                delay(50)
                println("  2/ Second coroutine end")
                4
            }
            val sum = launch_1.await() + launch_2.await()
            println(" Two coroutines have been launched $sum")
        }
        println("Back from the coroutine block")
    }

}
