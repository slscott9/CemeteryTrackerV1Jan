package com.sscott.cemeterytrackerv1.data.remote

import com.sscott.cemeterytrackerv1.other.Constants.IGNORE_AUTH_URLS
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

/*
    This class is available in the app module as a singletion
    Once user logs in succesfully we can set email and username properties and every
    network request will send username and password in the Authorization headers to be authenticated
    by the rest api


 */
class BasicAuthInterceptor : Interceptor {



    var userName : String? = null
    var password: String? = null


    /*
        If the http request is one of the IGNORE_AUTH_URLS then we dont authenticate that request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if(request.url.encodedPath in IGNORE_AUTH_URLS){
            return chain.proceed(request)
        }
        //if request was not one of the IGNORE we need to add auth header to request
        //Authorization means are you allowed to make that request
        //Authentication means making sure you are  actually you
        //If peter makes request he must be Authorized and Athenticated

        val authenticatedRequest = request.newBuilder()
            .header("Authorization", Credentials.basic(userName ?: "", password ?: ""))
            .build()
        return chain.proceed(authenticatedRequest)
    }
}