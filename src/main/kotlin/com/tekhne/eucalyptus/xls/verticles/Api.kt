package com.tekhne.eucalyptus.xls.verticles

import com.tekhne.eucalyptus.xls.handler.GetCaseHandler
import com.tekhne.eucalyptus.xls.handler.SaveCaseHandler
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory
import io.vertx.kotlin.core.http.httpServerOptionsOf
import org.slf4j.LoggerFactory

class Api : AbstractVerticle() {

    private val logger = LoggerFactory.getLogger(Api::class.java)

    override fun start() {
        logger.info("Starting Api verticle")
        OpenAPI3RouterFactory.create(vertx, "api/xlsapi.yaml") {result ->
            val routerFactory = result.result()
            routerFactory.addHandlerByOperationId("addCase", SaveCaseHandler())
            routerFactory.addFailureHandlerByOperationId("addCase") {
                logger.error(it.failure().message)
                it.response().end(it.failure().message)
            }
            routerFactory.addHandlerByOperationId("getCase", GetCaseHandler())
            routerFactory.addFailureHandlerByOperationId("getCase") {
                logger.error("${it.failure()} ${it.failure().message}")
                it.response().setStatusCode(it.statusCode()).end(it.failure().message)
            }
            vertx.createHttpServer(
                httpServerOptionsOf(
                        port = 8080
                )
            ).requestHandler(routerFactory.router).listen()
        }

        super.start()
    }
}