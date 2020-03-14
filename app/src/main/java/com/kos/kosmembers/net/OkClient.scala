package com.kos.kosmembers.net

import java.util.concurrent.TimeUnit

import okhttp3.{OkHttpClient, Request, RequestBody}
import scalapb.{GeneratedMessage, GeneratedMessageCompanion}

import scala.concurrent.{ExecutionContext, Future}
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level

object OkClient {

	def createClient(): OkHttpClient = {

		val logging = new HttpLoggingInterceptor
		logging.setLevel(Level.BODY)

		val client = new OkHttpClient.Builder().
			readTimeout(10, TimeUnit.SECONDS)
			.writeTimeout(10, TimeUnit.SECONDS)
			.connectTimeout(10, TimeUnit.SECONDS)
			.addInterceptor(logging)
			.build()

		client
	}

	def requestTo[A <: GeneratedMessage](uri: String,
										 data: GeneratedMessage,
										 gm: GeneratedMessageCompanion[A])(implicit client: OkHttpClient, executor: ExecutionContext): Future[A] = {
		Future(syncRequestTo(uri, data, gm))
	}

	def syncRequestTo[A <: GeneratedMessage](uri: String,
											 data: GeneratedMessage,
											 gm: GeneratedMessageCompanion[A])(implicit client: OkHttpClient): A = {

		val body = RequestBody.create(data.toByteArray)
		val rb = new Request.Builder()
		rb.url(uri).method("POST", body)


		val request = rb.build()
		val ex = client.newCall(request).execute()
		gm.parseFrom(ex.body().bytes())

	}


}
