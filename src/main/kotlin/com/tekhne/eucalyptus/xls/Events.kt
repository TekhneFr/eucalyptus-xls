package com.tekhne.eucalyptus.xls

enum class Events(val address: String) {
    DB_PERSIST_EVENT("eucalyptus.batch.persist"),
    DB_GET_BATCH_EVENT("eucalyptus.batch.get")
}