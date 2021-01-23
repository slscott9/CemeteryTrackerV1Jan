package com.sscott.cemeterytrackerv1.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.sscott.cemeterytrackerv1.data.local.CemeteryDatabase
import com.sscott.cemeterytrackerv1.data.local.datasource.CemeteryDao
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import timber.log.Timber

@RunWith(JUnit4::class)
@SmallTest
class CemeteryDaoTest {

    private lateinit var database : CemeteryDatabase
    private lateinit var dao : CemeteryDao

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                CemeteryDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        dao = database.cemeteryDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun unsyncedCemeteries_returns_null() = runBlockingTest {
        val subject = dao.unsyncedCemeteries(false)

        Timber.i(subject.toString())
    }
}