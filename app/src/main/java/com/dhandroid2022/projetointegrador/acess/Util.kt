package com.dhandroid2022.projetointegrador.acess

import android.util.Patterns

object StringUtils {
    fun isValidEmail(givenEmail: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(givenEmail).matches()

    fun isValidPassword(givenPassword: String): Boolean = givenPassword.length >= 8

}