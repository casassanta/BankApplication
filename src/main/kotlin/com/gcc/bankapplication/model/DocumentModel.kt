package com.gcc.bankapplication.model

import com.gcc.bankapplication.model.enums.DocumentType

data class DocumentModel(
    val type: DocumentType,
    val number: String
)