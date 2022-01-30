package com.gcc.bankapplication.controller.response

import com.gcc.bankapplication.model.Document


data class DocumentResponse(
    val type: Document.Type,
    val number: String
)