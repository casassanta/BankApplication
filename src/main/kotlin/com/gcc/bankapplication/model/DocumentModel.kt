package com.gcc.bankapplication.model

import com.gcc.bankapplication.model.enums.DocumentType
import javax.persistence.Embeddable
import javax.persistence.Enumerated

@Embeddable
data class DocumentModel(

    @Enumerated
    val type: DocumentType,
    val number: String
)