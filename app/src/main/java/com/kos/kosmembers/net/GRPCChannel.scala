package com.kos.kosmembers.net

import java.util.concurrent.Executors

import io.grpc.ManagedChannelBuilder

import scala.concurrent.ExecutionContext

object GRPCChannel {

	val executionContext = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
}
