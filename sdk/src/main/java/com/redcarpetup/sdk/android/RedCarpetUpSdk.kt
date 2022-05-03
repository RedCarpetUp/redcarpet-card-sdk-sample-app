package com.redcarpetup.sdk.android

import android.app.Activity
import android.content.Context
import com.redcarpetup.sdk.Pigeon
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

object RedCarpetUpSdk {
    private lateinit var flutterEngine: FlutterEngine
    private lateinit var api: Pigeon.Api

    fun initialize(context: Context) {
        if (RedCarpetUpSdk::flutterEngine.isInitialized) return
        flutterEngine = FlutterEngine(context.applicationContext)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        FlutterEngineCache.getInstance().put("main", flutterEngine)
        api = Pigeon.Api(flutterEngine.dartExecutor.binaryMessenger)
    }

    suspend fun call(method: String,dataMap: Map<Any?,Any?>): String = suspendCoroutine { cont ->
        api.call(method,dataMap) { cont.resume(it.message) }
    }


    fun startActivity(activity: Activity) {
        activity.startActivity(FlutterActivity.withCachedEngine("main").build(activity))
    }
}

private fun <T> Continuation<T>.resume(value: T?) {

}
