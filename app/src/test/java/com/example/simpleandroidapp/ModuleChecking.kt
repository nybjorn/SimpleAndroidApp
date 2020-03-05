package com.example.simpleandroidapp

import android.app.Application
import android.content.Context
import android.net.Network
import com.example.simpleandroidapp.net.BeerFetcherNet
import com.example.simpleandroidapp.net.networkModule
import com.example.simpleandroidapp.net.provideBeers
import com.example.simpleandroidapp.net.provideOkHttpClient
import com.example.simpleandroidapp.net.provideRetroFit
import com.example.simpleandroidapp.repository.beerModule
import com.example.simpleandroidapp.repository.dao.ObjectBox
import com.example.simpleandroidapp.repository.dao.boxModule
import com.example.simpleandroidapp.ui.second.beerViewModule
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import io.objectbox.BoxStore
import okhttp3.OkHttpClient
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.android.ext.koin.androidApplication
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito

@Category(CheckModuleTest::class)
class MyTest : KoinTest {

    val networkModuleMock = module {
        single{ declareMock<BeerFetcherNet> {  }}
    }

    val boxModuleMock = module {
        //   BoxStore does things so lets mock it
        single { declareMock<BoxStore> {} }
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Ignore("Mocking with koin 2.1.2 and not sure how to handle Flipper")
    @Test
    fun `checking modules`() {
        checkModules {
            modules(
                listOf(
                    networkModuleMock,
                    boxModuleMock,
                    beerModule,
                    beerViewModule
                )
            )
        }
    }
}
