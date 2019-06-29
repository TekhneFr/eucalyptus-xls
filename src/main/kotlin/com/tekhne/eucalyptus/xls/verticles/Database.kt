package com.tekhne.eucalyptus.xls.verticles

import com.tekhne.eucalyptus.xls.Events
import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import org.slf4j.LoggerFactory

class Database : AbstractVerticle() {


    private val logger = LoggerFactory.getLogger(Database::class.java)

    override fun start() {
        super.start()
        val client = MongoClient.createShared(vertx, config())
        vertx.eventBus().consumer<Any>(Events.DB_PERSIST_EVENT.address) { event ->
            client.insert("batch", JsonObject(event.body().toString())) { result ->
                when {
                    result.succeeded() -> logger.debug("Batch inserted with id ${result.result()}")
                    result.failed() -> logger.error("Batch insertion failed")
                }
            }
        }
    }

}