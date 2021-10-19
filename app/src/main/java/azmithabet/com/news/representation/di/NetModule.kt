package azmithabet.com.news.representation.di

import azmithabet.com.news.BuildConfig
import azmithabet.com.news.data.api.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor():HttpLoggingInterceptor{
      return  HttpLoggingInterceptor().apply {
            this.level=HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHTTPClint(interceptor:HttpLoggingInterceptor):OkHttpClient{
       return OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.connectTimeout(30, TimeUnit.SECONDS)
            this.readTimeout(20, TimeUnit.SECONDS)
            this.writeTimeout(25, TimeUnit.SECONDS)
        }.build()
    }
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiServices(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }
}