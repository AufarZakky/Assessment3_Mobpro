package org.d3if3109.mobpro1.model

data class OpStatus <T>(
    val statusCode: Int,
    val data: T,
    val message: String?
)
