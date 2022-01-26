package com.gcc.bankapplication.model

import com.gcc.bankapplication.model.enums.DocumentType
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class Document(

    @Enumerated(EnumType.STRING)
    val type: DocumentType,
    val number: String
)