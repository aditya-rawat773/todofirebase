package com.example.todo

class Notes{


        companion object Factory {
            fun create(): Notes = Notes()
        }
        var UId: String? = null
        var itemText: String? = null
        var done: Boolean? = false


    }


