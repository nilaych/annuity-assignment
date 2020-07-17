package com.lendico.assignment.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class AmountSerializer extends JsonSerializer<Double> {
    private static DecimalFormat formatter = new DecimalFormat("##.##");

    @Override
    public void serialize(Double value,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeObject(formatter.format(value));
    }
}