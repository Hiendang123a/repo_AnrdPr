package com.example.society.Mapper;

import com.example.society.Entity.Address;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ConverterMapper {

    @Named("stringToAddress")
    default Address stringToAddress(String address) {
        if (address == null || address.isEmpty()) {
            return new Address("", "", "");
        }
        String[] parts = address.split(", ");
        return new Address(
                parts.length > 0 ? parts[0] : "",
                parts.length > 1 ? parts[1] : "",
                parts.length > 2 ? parts[2] : ""
        );
    }

    @Named("addressToString")
    default String addressToString(Address address) {
        if (address == null) return "";
        return String.format("%s, %s, %s",
                address.getWard(), address.getDistrict(), address.getProvince());
    }

    @Named("stringToObjectID")
    default ObjectId stringToObjectID(String id){
        return new ObjectId(id);
    }

    @Named("objectIDToString")
    default String objectIDToString(ObjectId id){
        return id.toString();
    }
}
