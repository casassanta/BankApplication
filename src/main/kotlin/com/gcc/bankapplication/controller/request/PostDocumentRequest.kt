package com.gcc.bankapplication.controller.request

import com.gcc.bankapplication.model.Document
import com.gcc.bankapplication.model.enums.DocumentType
import javax.validation.Valid
import javax.validation.constraints.Pattern

data class PostDocumentRequest(

    val type: DocumentType,

    @field:Pattern( regexp = "^[a-zA-Z0-9 ]+\$", message = "Invalid characters for document.number.")
    val number: String
){

    fun toDocument(): Document{
        return Document(
            type = this.type,
            number = this.number
        )
    }
}