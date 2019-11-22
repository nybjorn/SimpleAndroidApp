package com.example.simpleandroidapp

import android.app.Application
import android.content.Context
import com.example.simpleandroidapp.net.networkModule
import com.example.simpleandroidapp.repository.beerModule
import com.example.simpleandroidapp.repository.dao.ObjectBox
import com.example.simpleandroidapp.repository.dao.boxModule
import com.example.simpleandroidapp.ui.second.beerViewModule
import io.objectbox.BoxStore
import org.junit.Test
import org.koin.android.ext.koin.androidApplication
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mockito.mock

class MyTest : KoinTest {

    val mockedAndroidContext = module {
        single { mock(Application::class.java) }
    }
    val mockedContext = module {
        single { mock(Context::class.java)}
    }

    val boxModuleMock = module {
        //   BoxStore does things so lets mock it
        single { mock(BoxStore::class.java) }
    }


    @Test
    fun `checking modules`() {
        // use koinApplication instead of startKoin, to avoid having to close Koin after each test

        koinApplication {
            modules(
                listOf(
                    networkModule,
                    boxModuleMock,
                    beerModule,
                    beerViewModule,
                    mockedAndroidContext,
                    mockedContext
                )
            )
        }.checkModules()

    }
}
