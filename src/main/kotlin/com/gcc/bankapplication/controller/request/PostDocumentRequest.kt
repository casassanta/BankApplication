package com.gcc.bankapplication.controller.request

import com.gcc.bankapplication.model.Document
import com.gcc.bankapplication.model.enums.DocumentType

data class PostDocumentRequest(
    val type: DocumentType,
    val number: String
){

    fun toDocument(): Document{
        return Document(
            type = this.type,
            number = this.number
        )
    }
}