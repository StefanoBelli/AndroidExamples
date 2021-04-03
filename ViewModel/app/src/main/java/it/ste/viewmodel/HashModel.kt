package it.ste.viewmodel

import java.math.BigInteger
import java.security.MessageDigest

class HashModel(private val clearText: String){
    private val hashed = BigInteger(
        1, MessageDigest.getInstance("MD5").digest(
            clearText.toByteArray())).toString(16)

    override fun toString(): String {
        return hashed
    }
}