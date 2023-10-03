package com.mjucow.eatda.common.vo.deseializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.mjucow.eatda.common.vo.PhoneNumber

class PhoneNumberDeserializer : JsonDeserializer<PhoneNumber?>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): PhoneNumber? {
        val value = p!!.valueAsString ?: return null
        return PhoneNumber(value = value)
    }
}
