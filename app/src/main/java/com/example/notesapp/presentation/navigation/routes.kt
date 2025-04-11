package com.example.notesapp.presentation.navigation

import kotlinx.serialization.Serializable

sealed class routes {

    @Serializable
    object Home: routes()


}