package com.gcc.bankapplication.model

import com.gcc.bankapplication.controller.response.DocumentResponse
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class Document(

    @Enumerated(EnumType.STRING)
    val type: Type,
    val number: String
){

    enum class Type {
        PASSPORT,
        ID
    }

    fun toDocumentResponse(): DocumentResponse{
        return DocumentResponse(
            type = this.type,
            number = this.number
        )
    }
}