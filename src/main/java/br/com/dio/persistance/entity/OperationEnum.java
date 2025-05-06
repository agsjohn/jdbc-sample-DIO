package br.com.dio.persistance.entity;


import java.util.stream.Stream;

public enum OperationEnum {

    UPDATE,
    INSERT,
    DELETE;

    public static OperationEnum getByDbOperation(final String dbOperation){
        return Stream.of(OperationEnum.values())
                .filter(o -> o.name().startsWith(dbOperation.toUpperCase()))
                .findFirst().orElseThrow();
    }
}
