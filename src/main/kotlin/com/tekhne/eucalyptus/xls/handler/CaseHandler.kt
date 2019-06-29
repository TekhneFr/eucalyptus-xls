package com.tekhne.eucalyptus.xls.handler

import com.tekhne.eucalyptus.xls.Events
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import org.slf4j.LoggerFactory
import java.io.FileNotFoundException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

class CaseHandler : Handler<RoutingContext>{

    private val logger = LoggerFactory.getLogger(CaseHandler::class.java)


    private fun readFile(fileName : String): InputStream {
        logger.debug(fileName)
        val upFilePath = Paths.get(fileName)
        upFilePath.let { p ->
            if (Files.exists(p)) {
                return Files.newInputStream(p)
            }
            throw FileNotFoundException("file $upFilePath does not exists")
        }
    }

    override fun handle(event: RoutingContext?) {
        val uploads = event?.fileUploads()
        logger.debug("${uploads?.size}")
        when {
            uploads == null || uploads.isEmpty() -> event?.fail(IllegalArgumentException("No BatchFile file provided"))
        }
        val fileName = uploads!!.first().name()
        val batchFile = XlsParser.parse(readFile(uploads.first().uploadedFileName()), fileName)
        val responseStr = Json.encodePrettily(batchFile)
        event.vertx().eventBus().publish(Events.DB_PERSIST_EVENT.address, responseStr)
        event.response().putHeader("content-type", "application/json; charset=utf-8").end(responseStr)
    }
}