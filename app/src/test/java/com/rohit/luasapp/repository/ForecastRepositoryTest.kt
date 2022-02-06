package com.rohit.luasapp.repository

import com.rohit.luasapp.api.LuasService
import com.rohit.luasapp.model.ErrorForecastData
import com.rohit.luasapp.model.LoadedForecastData
import com.rohit.luasapp.model.LoadingForecastData
import com.rohit.luasapp.model.StopInfo
import com.rohit.luasapp.repository.forecast.ForecastRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.xml.stream.XMLStreamException

@RunWith(MockitoJUnitRunner::class)
class ForecastRepositoryTest {

    private lateinit var repository: ForecastRepository

    @Mock
    private lateinit var luasService: LuasService

    @Before
    fun setup() {
        repository = ForecastRepository(luasService)
    }

    @Test
    fun `should return success on loadForecast call`() {
        // Given
        whenever(luasService.loadForecast(any())).thenReturn(Single.just(StopInfo()))

        // When
        val forecastTestObserver = repository.forecast.test()
        val testObserver = repository.loadForecast("sti").test()

        // Then
        testObserver.await()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        verify(luasService, atLeastOnce()).loadForecast(any())

        forecastTestObserver.assertValueAt(1) { data -> data is LoadingForecastData }
        forecastTestObserver.assertValueAt(2) { data -> data is LoadedForecastData }

        testObserver.dispose()
        forecastTestObserver.dispose()
    }

    @Test
    fun `should return error on loadForecast exception`() {
        // Given
        val exception = Exception()
        whenever(luasService.loadForecast(any())).thenReturn(Single.error(exception))

        // When
        val forecastTestObserver = repository.forecast.test()
        val testObserver = repository.loadForecast("sti").test()

        // Then
        testObserver.await()
        testObserver.assertNotComplete()
        testObserver.assertError(exception)
        verify(luasService, atLeastOnce()).loadForecast(any())

        forecastTestObserver.assertValueAt(1) { data -> data is LoadingForecastData }
        forecastTestObserver.assertValueAt(2) { data -> data is ErrorForecastData }

        testObserver.dispose()
        forecastTestObserver.dispose()
    }

    @Test
    fun `should return error on request loadForecast with wrong a non existing stop name`() {
        // Given
        val exception = XMLStreamException()
        whenever(luasService.loadForecast("")).thenReturn(Single.error(exception))

        // When
        val forecastTestObserver = repository.forecast.test()
        val testObserver = repository.loadForecast("").test()

        // Then
        testObserver.await()
        testObserver.assertNotComplete()
        testObserver.assertError(exception)
        verify(luasService, atLeastOnce()).loadForecast(any())

        forecastTestObserver.assertValueAt(1) { data -> data is LoadingForecastData }
        forecastTestObserver.assertValueAt(2) { data -> data is ErrorForecastData }

        testObserver.dispose()
        forecastTestObserver.dispose()
    }

    @Test
    fun `should forecast subject emit multiple objects on a sequential call`() {
        // Given
        whenever(luasService.loadForecast("loa")).thenReturn(Single.just(StopInfo()))

        val xmlException = XMLStreamException()
        whenever(luasService.loadForecast("")).thenReturn(Single.error(xmlException))

        val exception = Exception()
        whenever(luasService.loadForecast("sti")).thenReturn(Single.error(exception))

        // When
        val forecastTestObserver = repository.forecast.test()
        var testObserver = repository.loadForecast("loa").test()
        testObserver = repository.loadForecast("").test()
        testObserver = repository.loadForecast("sti").test()


        forecastTestObserver.assertValueCount(7)
        forecastTestObserver.assertValueAt(1) { data -> data is LoadingForecastData }
        forecastTestObserver.assertValueAt(2) { data -> data is LoadedForecastData }
        forecastTestObserver.assertValueAt(3) { data -> data is LoadingForecastData }
        forecastTestObserver.assertValueAt(4) { data -> data is ErrorForecastData }
        forecastTestObserver.assertValueAt(5) { data -> data is LoadingForecastData }
        forecastTestObserver.assertValueAt(6) { data -> data is ErrorForecastData }

        testObserver.dispose()
        forecastTestObserver.dispose()
    }

}