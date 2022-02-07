package com.rohit.luasapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rohit.luasapp.model.LoadedForecastData
import com.rohit.luasapp.model.StopInfo
import com.rohit.luasapp.repository.forecast.ForecastRepository
import com.rohit.luasapp.ui.forecast.LuasViewModel
import com.rohit.luasapp.util.Response
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito.mockStatic
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalTime


@RunWith(MockitoJUnitRunner::class)
class ForecastViewModelTest {

    private lateinit var viewModel: LuasViewModel
    private lateinit var beforeMoonTime: LocalTime
    private lateinit var afterMoonTime: LocalTime

    @Mock
    private lateinit var repository: ForecastRepository

    @Mock
    lateinit var mockLiveDataObserver: Observer<Response<StopInfo>>

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    private lateinit var localTime: MockedStatic<LocalTime>

    @Before
    fun setup() {
        beforeMoonTime = LocalTime.of(11,0,0)
        afterMoonTime = LocalTime.of(13,0,0)

        localTime = mockStatic(LocalTime::class.java)

        viewModel = LuasViewModel(repository)
        viewModel.currentForecast.observeForever(mockLiveDataObserver)
    }

    @After
    fun close() {
        localTime.close()
    }

    @Test
    fun `should refresh forecast for the STILLORGAN stop when time is after 12h00 and before 00h00`() {
        // Given
        whenever(repository.forecast).thenReturn(Observable.just(LoadedForecastData(StopInfo())))
        whenever(repository.loadForecast(any())).thenReturn(Completable.complete())

        whenever(LocalTime.now()).thenReturn(afterMoonTime)

        // When
        viewModel.initialize()
        viewModel.refreshForecast()

        // Then
        verify(repository, times(1)).loadForecast(eq("STI"))
    }

    @Test
    fun `should refresh forecast for the MARLBOROUGH stop when time is after 00h00 and before 12h00`() {
        // Given
        whenever(repository.forecast).thenReturn(Observable.just(LoadedForecastData(StopInfo())))
        whenever(repository.loadForecast(any())).thenReturn(Completable.complete())

        whenever(LocalTime.now()).thenReturn(beforeMoonTime)

        // When
        viewModel.initialize()
        viewModel.refreshForecast()

        // Then
        verify(repository, times(1)).loadForecast(eq("MAR"))
    }

}