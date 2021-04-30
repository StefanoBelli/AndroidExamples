package it.ssynx.urldownloader

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.widget.TextView

class DownloadLog {
    companion object {
        private fun baseLogger(c: Cursor, m: String, tv: TextView) {
            val colIdxDlSoFar = c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
            val colIdxTotalDl = c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
            val colIdxLocalUri = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
            val bytesDlSoFar = c.getInt(colIdxDlSoFar)
            val bytesTotalDl = c.getInt(colIdxTotalDl)
            val localUri = c.getString(colIdxLocalUri)
            tv.text = "${tv.text.toString()}$m: $localUri (${bytesDlSoFar}B/${bytesTotalDl}B)\n"
        }

        fun successful(c: Cursor, tv: TextView, ctx: Context) {
            baseLogger(c, "[${ctx.getString(R.string.finished)}]", tv)
        }

        fun failed(c: Cursor, tv: TextView, ctx: Context) {
            baseLogger(c, "[${ctx.getString(R.string.failed)}]", tv)
        }

        fun paused(c: Cursor, tv: TextView, ctx: Context) {
            baseLogger(c, ctx.getString(R.string.paused), tv)
        }

        fun pending(c: Cursor, tv: TextView, ctx: Context) {
            baseLogger(c, ctx.getString(R.string.pending), tv)
        }

        fun running(c: Cursor, tv: TextView, ctx: Context) {
            baseLogger(c, ctx.getString(R.string.still_running), tv)
        }
    }
}