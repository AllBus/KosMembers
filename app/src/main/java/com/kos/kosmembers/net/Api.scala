package com.kos.kosmembers.net

object Api {
	val SERVER = "https://kosmembers.herokuapp.com/"
	val PROTO_SERVER: String = SERVER + "proto/"

	val TEST  = PROTO_SERVER+"test"
	val SUGGEST = PROTO_SERVER+"members/codes/books"
}
