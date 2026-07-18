package com.homeassignment.vvoropai.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.homeassignment.vvoropai.data.datasource.GitHubDataSource
import com.homeassignment.vvoropai.data.datasource.IGitHubDataSource
import com.homeassignment.vvoropai.data.repositories.GitHubRepository
import com.homeassignment.vvoropai.domain.IGitHubRepository
import com.homeassignment.vvoropai.domain.usecases.GetUserByIdUseCase
import com.homeassignment.vvoropai.domain.usecases.GetUserRepoDetailsUseCase
import com.homeassignment.vvoropai.domain.usecases.IGetUserByIdUseCase
import com.homeassignment.vvoropai.domain.usecases.IGetUserRepoDetailsUseCase
import com.homeassignment.vvoropai.network.ApiService
import com.homeassignment.vvoropai.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

val nullOnEmptyConverterFactory = object : Converter.Factory() {
    fun converterFactory() = this
    override fun responseBodyConverter(
        type: Type, annotations: Array<out Annotation>, retrofit: Retrofit
    ) = object : Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter =
            retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

        override fun convert(value: ResponseBody) =
            if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    @Named("io")
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @Named("main")
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    @Named("default")
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideGitHubRepository(
        gitHubDataSource: IGitHubDataSource
    ): IGitHubRepository {
        return GitHubRepository(gitHubDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideGitHubDataSource(service: ApiService): IGitHubDataSource {
        return GitHubDataSource(service)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(gitHubRepository: IGitHubRepository): IGetUserByIdUseCase {
        return GetUserByIdUseCase(gitHubRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserRepoDetailsUseCase(gitHubRepository: IGitHubRepository): IGetUserRepoDetailsUseCase {
        return GetUserRepoDetailsUseCase(gitHubRepository)
    }
}