package com.tekhne.eucalyptus.xls.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions
import org.slf4j.LoggerFactory

class XlsBootstrap : AbstractVerticle() {


    private val logger = LoggerFactory.getLogger(XlsBootstrap::class.java)

    override fun start() {

        logger.debug(config().encodePrettily())
        super.start()
        vertx.deployVerticle(Api::class.java.canonicalName) {
            res ->
            when {
                res.succeeded() -> logger.debug("API is ready")
                res.failed() -> logger.error("API failed to start")
            }
        }
        vertx.deployVerticle(Database::class.java.canonicalName,
                DeploymentOptions().setConfig(config().getJsonObject("mongo"))){
            res ->
            when {
                res.succeeded() -> logger.debug("Database connection is ready")
                res.failed() -> logger.error("Database connection failed to start")
            }
        }
    }
}
