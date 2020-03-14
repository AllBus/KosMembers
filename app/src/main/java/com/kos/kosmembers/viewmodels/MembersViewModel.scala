package com.kos.kosmembers.viewmodels

import java.util.concurrent.Executors

import android.app.Application
import androidx.lifecycle.{AndroidViewModel, LiveData, MutableLiveData}
import com.kos.kosmembers.net.{Api, GRPCChannel, OkClient}
import members.proto.members.{SuggestBook, SuggestResponse, TestRequest, TestResponse}
import okhttp3.OkHttpClient

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.util.{Failure, Success}

class MembersViewModel(app: Application)  extends AndroidViewModel(app: Application) {


	implicit protected val executorContext: ExecutionContextExecutor = 	GRPCChannel.executionContext
	implicit protected val okclient: OkHttpClient = OkClient.createClient()

	private val _checkServer: MutableLiveData[Boolean] = new MutableLiveData[Boolean](false)
	private val _sendServer: MutableLiveData[String] = new MutableLiveData[String](null)

	def checkServer : LiveData[Boolean] = _checkServer
	def sendServer : LiveData[String] = _sendServer
	def resetAnswer() = _sendServer.setValue(null)


	def test(): Unit ={

		val request = TestRequest()

		val future =
			OkClient.requestTo(Api.TEST, request, TestResponse)

		future.map { v ⇒
			v
		}.onComplete {
			case Success(data) =>
				_checkServer.postValue(true)
			case Failure(exception) ⇒
				exception.printStackTrace()
				_checkServer.postValue(true)
		}
	}

	def suggestBook(request:SuggestBook): Unit ={
		_checkServer.postValue(false)
		val future =
			OkClient.requestTo(Api.SUGGEST, request, SuggestResponse)

		future.map { v ⇒
			v
		}.onComplete {
			case Success(data) =>
				_checkServer.postValue(true)
				_sendServer.postValue("Ваше предложение принято")
			case Failure(exception) ⇒
				exception.printStackTrace()
				_checkServer.postValue(true)
				_sendServer.postValue("Ошибка при отправке данных")
		}
	}
}
