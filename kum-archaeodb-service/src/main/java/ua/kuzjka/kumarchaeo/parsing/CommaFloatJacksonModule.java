package ua.kuzjka.kumarchaeo.parsing;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

public class CommaFloatJacksonModule extends SimpleModule {
    public CommaFloatJacksonModule() {
        super("CommaFloatDeserialization");
        addDeserializer(Float.class, new CommaFloatDeserializer());
        addDeserializer(Double.class, new CommaDoubleDeserializer());
//        addDeserializer(float.class, new CommaFloatDeserializer());
    }

    public static class CommaFloatDeserializer extends StdScalarDeserializer<Float> {
        protected CommaFloatDeserializer() {
            super(Float.class);
        }

        @Override
        public Float deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            String valueAsString = p.getValueAsString();
            if (valueAsString == null || valueAsString.isEmpty()) return null;
            try {
                return Float.parseFloat(valueAsString.replace(',', '.'));
            } catch (NumberFormatException e) {
                throw new InvalidFormatException(p, "cannot parse float", valueAsString, Float.class);
            }
        }
    }

    public static class CommaDoubleDeserializer extends StdScalarDeserializer<Double> {
        public CommaDoubleDeserializer() {
            super(Double.class);
        }

        @Override
        public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            String valueAsString = p.getValueAsString();
            if (valueAsString == null || valueAsString.isEmpty()) return null;
            try {
                return Double.parseDouble(valueAsString.replace(',', '.'));
            } catch (NumberFormatException e) {
                throw new InvalidFormatException(p, "cannot parse double", valueAsString, Double.class);
            }
        }
    }

//    public static class CommaFloatPrimitiveDeserializer extends StdScalarDeserializer<float> {
//        public CommaFloatPrimitiveDeserializer() {
//            super(float.class);
//        }
//
//        @Override
//        public float deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
//            String valueAsString = p.getValueAsString();
//            if (valueAsString == null || valueAsString.isEmpty())
//                throw new InvalidFormatException(p, "cannot parse float from empty or null string", valueAsString, float.class);
//            try {
//                return Float.parseFloat(valueAsString.replace(',', '.'));
//            } catch (NumberFormatException e) {
//                throw new InvalidFormatException(p, "cannot parse float", valueAsString, Float.class);
//            }
//        }
//
//
//    }
}
