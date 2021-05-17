package com.teamounce.ounce.util

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object ImageUrlHelper {

    suspend fun getBitmapUri(context: Context, uri: Uri): String? {

        var bitmap: Bitmap? = null

        kotlin.runCatching {
            if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        }.onSuccess {
            bitmap = it
        }.onFailure {
            it.printStackTrace()
            return null
        }

        if (bitmap != null) {
            var bitmapWidth = bitmap!!.width.toFloat()
            var bitmapHeight = bitmap!!.height.toFloat()

            if (bitmapHeight > 1024) {
                val percent = bitmapHeight / 100
                val scale = 1024 / percent
                bitmapWidth *= scale / 100
                bitmapHeight *= scale / 100
            }

            bitmap =
                Bitmap.createScaledBitmap(bitmap!!, bitmapWidth.toInt(), bitmapHeight.toInt(), true)


            // temp file의 이름을 정합니다.
            // 확장자_YYYYMMDDHHMMSS


            // temp file의 이름을 정합니다.
            // 확장자_YYYYMMDDHHMMSS
            val time = System.currentTimeMillis()
            val dayTime = SimpleDateFormat("yyyymmddhhmmss")
            val fileName = "JPEG_" + dayTime.format(Date(time)) + "_" + time.toString()

            // 임시 파일 저장 경로

            // 임시 파일 저장 경로
            val tempStorage = context.cacheDir
            val tempFileName = "$fileName.jpeg"
            val tempFile = File(tempStorage, tempFileName)

            kotlin.runCatching {
                // 파일을 생성합니다.
                tempFile.createNewFile()
                val outputStream = FileOutputStream(tempFile)
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 75, outputStream)
                outputStream.close()

            }.onFailure {
                it.printStackTrace()
            }

            return tempFile.absolutePath

        }
        else {
            return null
        }

    }

    suspend fun getAbsolutePath(context: Context, uri: Uri): String? {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return context.getExternalFilesDir(Environment.DIRECTORY_DCIM)
                        .toString() + "/" + split[1]
                }

            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
                return getDataColumn(
                    context,
                    contentUri,
                    null,
                    null
                )
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                when (type) {
                    "image" -> {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    "video" -> {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    "audio" -> {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(
                    context,
                    contentUri,
                    selection,
                    selectionArgs
                )
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }

        return null
    }

    private suspend fun getDataColumn(
        context: Context,
        contentUri: Uri?,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )

        try {
            cursor = context.contentResolver.query(
                contentUri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private suspend fun isDownloadsDocument(uri: Uri): Boolean =
        "com.android.providers.downloads.documents" == uri.authority


    private suspend fun isExternalStorageDocument(uri: Uri): Boolean =
        "com.android.externalstorage.documents" == uri.authority

    private suspend fun isMediaDocument(uri: Uri): Boolean =
        "com.android.providers.media.documents" == uri.authority

}