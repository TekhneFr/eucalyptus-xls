package com.tekhne.eucalyptus.xls.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.ext.mongo.MongoClient

class Database : AbstractVerticle() {
    override fun start() {
        super.start()
        val client = MongoClient.createShared(vertx, config())
    }
}