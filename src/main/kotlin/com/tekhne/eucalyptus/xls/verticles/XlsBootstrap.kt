package com.tekhne.eucalyptus.xls.verticles

import io.vertx.core.AbstractVerticle

class XlsBootstrap : AbstractVerticle() {

    override fun start() {
        super.start()
        vertx.deployVerticle(Api::class.java.canonicalName)
    }
}
