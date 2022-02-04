package com.gcc.bankapplication.controller.request

import com.gcc.bankapplication.controller.response.DocumentResponse
import com.gcc.bankapplication.model.Document
import com.gcc.bankapplication.validation.DocumentTypeValidation
import javax.validation.constraints.Pattern

data class UpdateDocumentRequest(

    @field:DocumentTypeValidation
    val type: String,

    @field:Pattern( regexp = "^[a-zA-Z0-9 ]+\$", message = "Invalid characters.")
    val number: String
){

    fun toDocument(): Document{
        return Document(
            type = Document.Type.valueOf(this.type),
            number = this.number
        )
    }

    fun toDocumentResponse(): DocumentResponse{
        return DocumentResponse(
            type = Document.Type.valueOf(this.type),
            number = this.number
        )
    }
}