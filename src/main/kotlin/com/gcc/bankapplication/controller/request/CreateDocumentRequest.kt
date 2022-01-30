package com.gcc.bankapplication.controller.request

import com.gcc.bankapplication.model.Document
import javax.validation.constraints.Pattern

data class CreateDocumentRequest(

    val type: Document.Type,

    @field:Pattern( regexp = "^[a-zA-Z0-9 ]+\$", message = "Invalid characters.")
    val number: String
){

    fun toDocument(): Document{
        return Document(
            type = this.type,
            number = this.number
        )
    }
}