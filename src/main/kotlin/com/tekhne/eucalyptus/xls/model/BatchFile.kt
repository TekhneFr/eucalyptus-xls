package com.tekhne.eucalyptus.xls.model

import java.util.*

data class BatchFile (
        val uid: String,
        val name: String,
        val date: Date,
        val cases: List<Case>
)