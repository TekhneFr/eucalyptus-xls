package com.tekhne.eucalyptus.xls.handler

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import java.io.ByteArrayInputStream

class CaseFileHandler: Handler<RoutingContext> {
    override fun handle(event: RoutingContext?) {
        val inputStream = ByteArrayInputStream(event?.body?.bytes)
    }
}