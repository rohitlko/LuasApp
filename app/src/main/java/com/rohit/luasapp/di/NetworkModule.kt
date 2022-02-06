package com.rohit.luasapp.di

import com.rohit.luasapp.BuildConfig
import com.rohit.luasapp.api.LuasService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

/**
 * Module which provides all required dependencies about network
 */
@Module

// Indicates which component scope the module should be installed, in this case in the SingletonComponent
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides the OkHttpClient object, which is responsible for any low-level network operations,
     * caching, requests and responses manipulation.
     *
     * @return OkHttpClient
     */
    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()

            // Add a logging interceptor to log requests and responses in debugging mode
            .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
            .build()
    }

    /**
     * Provides the Retrofit object with a base URL.
     *
     * @param client instance of OkHttpClient
     * @return the Retrofit object
     */
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(provideClient())
            .baseUrl("https://luasforecasts.rpa.ie/xml/")

            // RxJava adapter to receive call results with custom handlers like RxJava Observers and Flowables.
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))

            // Inclusion of XML Converter as the data received from the server is in the XML format.
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    /**
     * Provides the Luas service implementation.
     *
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the LuasService implementation.
     */
    @Provides
    @Singleton
    fun provideLuasService(retrofit: Retrofit): LuasService {
        return retrofit.create(LuasService::class.java)
    }

}