package com.sscott.cemeterytrackerv1.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.sscott.cemeterytrackerv1.data.local.CemeteryDatabase
import com.sscott.cemeterytrackerv1.data.local.datasource.CemeteryDao
import com.sscott.cemeterytrackerv1.data.local.datasource.LocalDataSource
import com.sscott.cemeterytrackerv1.data.local.datasource.LocalDataSourceImpl
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.Cemetery
import com.sscott.cemeterytrackerv1.data.models.entities.cemetery.CemeteryMapper
import com.sscott.cemeterytrackerv1.data.models.mapper.DomainMapper
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDto
import com.sscott.cemeterytrackerv1.data.models.network.cemdto.CemeteryDtoMapper
import com.sscott.cemeterytrackerv1.data.remote.BasicAuthInterceptor
import com.sscott.cemeterytrackerv1.data.remote.datasource.CemeteryApi
import com.sscott.cemeterytrackerv1.data.remote.datasource.RemoteDataSource
import com.sscott.cemeterytrackerv1.data.remote.datasource.RemoteDataSourceImpl
import com.sscott.cemeterytrackerv1.data.repository.Repository
import com.sscott.cemeterytrackerv1.data.repository.RepositoryImpl
import com.sscott.cemeterytrackerv1.other.Constants.BASE_URL
import com.sscott.cemeterytrackerv1.other.Constants.DATABASE_NAME
import com.sscott.cemeterytrackerv1.other.Constants.ENCRYPTED_SHARED_PREF_NAME
import com.sscott.cemeterytrackerv1.other.ResponseHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDao(db : CemeteryDatabase) = db.cemeteryDao()

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext appContext : Context) =
        Room.databaseBuilder(
            appContext,
            CemeteryDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()



    @Singleton
    @Provides
    fun provideRemoteDataSource(cemApi : CemeteryApi): RemoteDataSource = RemoteDataSourceImpl(cemApi)

    @Singleton
    @Provides
    fun provideLocalDataSource(dao: CemeteryDao) : LocalDataSource = LocalDataSourceImpl(dao)

    @Provides
    fun provideResponseHandler() = ResponseHandler()

    @Singleton
    @Provides
    fun provideRepo(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        responseHandler: ResponseHandler,
        context : Application,

    ): Repository = RepositoryImpl(
            remoteDataSource,
            localDataSource,
            responseHandler,
            context

    )


    @Singleton
    @Provides
    fun provideBasicAuthInterceptor(sharedPreferences: SharedPreferences) = BasicAuthInterceptor(sharedPreferences)

    @Singleton
    @Provides
    fun provideHttlClient() : OkHttpClient.Builder {
        val trustAllCertificates: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                    //no op
                }

                override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                    //no op
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf() //trust manager trusts all certificates
                }
            }
        )
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCertificates, SecureRandom())

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCertificates[0] as X509TrustManager)
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
    }

    @Singleton
    @Provides
    fun provideCemeteryApi(okHttpClient: OkHttpClient.Builder, basicAuthInterceptor: BasicAuthInterceptor) : CemeteryApi {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = okHttpClient
            .addInterceptor(basicAuthInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CemeteryApi::class.java)

    }

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context) : SharedPreferences {
        //create master key for android key store
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM) //entrypts data
            .build()
        return EncryptedSharedPreferences.create(
            context,
            ENCRYPTED_SHARED_PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }



}