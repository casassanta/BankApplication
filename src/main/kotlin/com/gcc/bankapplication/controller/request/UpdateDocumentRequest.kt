package com.gcc.bankapplication.controller.request

import com.gcc.bankapplication.controller.response.DocumentResponse
import com.gcc.bankapplication.model.Document
import javax.validation.constraints.Pattern

data class UpdateDocumentRequest(

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

    fun toDocumentResponse(): DocumentResponse{
        return DocumentResponse(
            type = this.type,
            number = this.number
        )
    }
}