package it.ste.viewmodel

import java.math.BigInteger
import java.security.MessageDigest

class HashModel(private val clearText: String){
    private val md5hash = BigInteger(
        1, MessageDigest.getInstance("MD5").digest(
            clearText.toByteArray())).toString(16)

    private val sha1hash = BigInteger(
            1, MessageDigest.getInstance("SHA1").digest(
            clearText.toByteArray())).toString(16)

    val md5 : String?
        get() = if(clearText == "") clearText else md5hash

    val sha1 : String?
        get() = if(clearText == "") clearText else sha1hash
}