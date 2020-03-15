package com.kos.kosmembers.viewmodels

import java.util.concurrent.Executors

import android.app.Application
import androidx.lifecycle.{AndroidViewModel, LiveData, MutableLiveData}
import com.kos.kit.protobufio.OkClient
import com.kos.kosmembers.R
import com.kos.kosmembers.helpers.DeviceHelper
import com.kos.kosmembers.net.{Api, GRPCChannel}
import members.proto.members.{DeviceInfo, SuggestBook, SuggestResponse, TestRequest, TestResponse}
import okhttp3.OkHttpClient

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.util.{Failure, Success}

class MembersViewModel(app: Application)  extends AndroidViewModel(app: Application) {



	implicit protected val executorContext: ExecutionContextExecutor = 	GRPCChannel.executionContext
	implicit protected val okclient: OkHttpClient = OkClient.createClient()

	private val _checkServer: MutableLiveData[Boolean] = new MutableLiveData[Boolean](false)
	private val _sendServer: MutableLiveData[Int] = new MutableLiveData[Int](0)

	def checkServer : LiveData[Boolean] = _checkServer
	def sendServer : LiveData[Int] = _sendServer
	def resetAnswer() = _sendServer.setValue(0)


	val deviceInfo = Option(DeviceInfo(
		DeviceHelper.getDeviceName,
		DeviceHelper.getAndroidVersion,
		DeviceHelper.getApplicationName(app),
		DeviceHelper.getApplicationVersion(app)
	))

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
				_sendServer.postValue(R.string.sendSuggestionSuccess)
			case Failure(exception) ⇒
				exception.printStackTrace()
				_checkServer.postValue(true)
				_sendServer.postValue(R.string.sendSuggestionFailure)
		}
	}
}
