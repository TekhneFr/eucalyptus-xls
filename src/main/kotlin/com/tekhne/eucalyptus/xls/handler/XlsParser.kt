package com.tekhne.eucalyptus.xls.handler

import com.tekhne.eucalyptus.xls.model.BatchFile
import com.tekhne.eucalyptus.xls.model.Case
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.time.Instant
import java.util.*

object XlsParser {

    private val logger = LoggerFactory.getLogger(XlsParser::class.java)

    fun parse(input: InputStream, batchName: String): BatchFile {

        val longValue: (Cell) -> Long = {
            when(it.cellTypeEnum) {
                CellType.BLANK -> throw IllegalArgumentException("Cell is blank")
                CellType.STRING -> it.stringCellValue.toLong()
                CellType.NUMERIC -> it.numericCellValue.toLong()
                else -> {
                    throw IllegalArgumentException("Cell is not numeric")
                }
            }
        }

        val intValue: (Cell) -> Int = {
            when(it.cellTypeEnum) {
                CellType.BLANK -> throw IllegalArgumentException("Cell is blank")
                CellType.STRING -> it.stringCellValue.toInt()
                CellType.NUMERIC -> it.numericCellValue.toInt()
                else -> {
                    throw IllegalArgumentException("Cell is not numeric")
                }
            }
        }

        val nullableIntValue: (Cell) -> Int? = {
            when(it.cellTypeEnum) {
                CellType.BLANK -> null
                CellType.STRING -> it.stringCellValue.toInt()
                CellType.NUMERIC -> it.numericCellValue.toInt()
                else -> {
                    throw IllegalArgumentException("Cell is not numeric")
                }
            }
        }

        val stringValue: (Cell) -> String = {
            when(it.cellTypeEnum) {
                CellType.BLANK -> throw IllegalArgumentException("Cell is blank")
                CellType.NUMERIC -> "%d".format(it.numericCellValue.toInt())
                CellType.STRING -> it.stringCellValue
                else -> {
                    throw IllegalArgumentException("Cell is not String")
                }
            }
        }

        val nullableStringValue: (Cell) -> String? = {
            when(it.cellTypeEnum) {
                CellType.BLANK -> null
                CellType.NUMERIC -> it.numericCellValue.toString()
                CellType.STRING -> it.stringCellValue
                else -> {
                    throw IllegalArgumentException("Cell is not String")
                }
            }
        }

        val case: (Row) -> Case = {row ->
            val iterator = row.cellIterator()
            Case(
                    iterator.next().stringCellValue,
                    enumValueOf(iterator.next().stringCellValue),
                    enumValueOf(iterator.next().stringCellValue),
                    iterator.next().stringCellValue,
                    intValue(iterator.next()),
                    nullableStringValue(iterator.next()),
                    nullableIntValue(iterator.next()),
                    intValue(iterator.next()),
                    intValue(iterator.next()),
                    longValue(iterator.next()),
                    intValue(iterator.next()),
                    enumValueOf(iterator.next().stringCellValue),
                    enumValueOf(iterator.next().stringCellValue),
                    iterator.next().stringCellValue,
                    iterator.next().stringCellValue,
                    nullableStringValue(iterator.next()),
                    nullableStringValue(iterator.next()),
                    stringValue(iterator.next()),
                    iterator.next().stringCellValue,
                    nullableStringValue(iterator.next()),
                    nullableStringValue(iterator.next()),
                    nullableStringValue(iterator.next()),
                    nullableStringValue(iterator.next()),
                    nullableStringValue(iterator.next()),
                    nullableStringValue(iterator.next()),
                    nullableStringValue(iterator.next()),
                    nullableStringValue(iterator.next())
            )
        }

        val workbook = XSSFWorkbook(input)
        val sheet = workbook.getSheetAt(0)
        val cases = sheet.filterIndexed { i, _ -> i > 0 } .map(case)
        return BatchFile(UUID.randomUUID().toString(), batchName, Date.from(Instant.now()), cases)
    }
}