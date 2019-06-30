package com.tekhne.eucalyptus.xls.handler

import com.tekhne.eucalyptus.xls.Events
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import org.slf4j.LoggerFactory

class GetCaseHandler : Handler<RoutingContext> {
    private val logger = LoggerFactory.getLogger(GetCaseHandler::class.java)
    override fun handle(event: RoutingContext?) {
        event?.vertx()?.eventBus()?.send<String>(Events.DB_GET_BATCH_EVENT.address, event.pathParam("uid")) { ar ->
            when {
                ar.succeeded() -> event.response()?.
                        putHeader("content-type", "application/json; charset=utf-8")?.end(ar.result().body())
                ar.failed() -> event.fail(ar.cause())
            }
        }
    }
}