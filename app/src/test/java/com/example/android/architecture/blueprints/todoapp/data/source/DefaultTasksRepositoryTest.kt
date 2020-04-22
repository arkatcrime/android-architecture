package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DefaultTasksRepositoryTest{

    private val task1 = Task("Title1","Desciption1")
    private val task2 = Task("Title2","Desciption2")
    private val task3 = Task("Title3","Desciption3")

    private val remoteTasks:List<Task> = listOf(task1, task2).sortedBy { it.id }

    private val localTask:List<Task> = listOf(task3).sortedBy { it.id }

    private val newTask:List<Task> = listOf(task3).sortedBy { it.id }

    //datasources
    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var tasksLocalDataSource: FakeDataSource


    //class under test
    private lateinit var tasksRepository: DefaultTasksRepository

    @Before
    fun createRepository(){
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        tasksLocalDataSource = FakeDataSource(localTask.toMutableList())

        tasksRepository = DefaultTasksRepository(
                tasksRemoteDataSource,
                tasksLocalDataSource,
                Dispatchers.Unconfined)
    }

    @Test
    fun getTask_requestsAllTasksFromRemoteDataSource() = runBlockingTest{
        // When tasks are requested from the tasks repository
        val tasks = tasksRepository.getTasks(true) as Result.Success

        // Then tasks are loaded from the remote data source
        assertThat(tasks.data, IsEqual(remoteTasks))
    }

}