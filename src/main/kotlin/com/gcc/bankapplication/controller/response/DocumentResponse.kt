package com.gcc.bankapplication.controller.response

import com.gcc.bankapplication.model.enums.DocumentType

data class DocumentResponse(
    val type: DocumentType,
    val number: String
)