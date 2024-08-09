package com.example.demo.models.mapinterface;

import com.example.demo.entities.Patron;
import com.example.demo.models.mappinginterface.PatronMapper;
import com.example.demo.models.request.PatronRequestModel;
import com.example.demo.models.response.PatronResponseModel;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-09T02:02:13+0300",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240725-1906, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class PatronMapperImpl implements PatronMapper {

    @Override
    public PatronResponseModel mapToPatronResponseModel(Patron patron) {
        if ( patron == null ) {
            return null;
        }

        PatronResponseModel patronResponseModel = new PatronResponseModel();

        patronResponseModel.setAddress( patron.getAddress() );
        patronResponseModel.setEmail( patron.getEmail() );
        patronResponseModel.setId( patron.getId() );
        patronResponseModel.setMobile( patron.getMobile() );
        patronResponseModel.setName( patron.getName() );

        return patronResponseModel;
    }

    @Override
    public List<PatronResponseModel> mapToPatronResponseModelList(List<Patron> patron) {
        if ( patron == null ) {
            return null;
        }

        List<PatronResponseModel> list = new ArrayList<PatronResponseModel>( patron.size() );
        for ( Patron patron1 : patron ) {
            list.add(mapToPatronResponseModel(patron1));
        }

        return list;
    }

    @Override
    public Patron mapToPatronRequestModel(PatronRequestModel patronRequestModel) {
        Patron patron = new Patron();
		patron.setEmail(patronRequestModel.getEmail());
		patron.setMobile(patronRequestModel.getMobile());
		patron.setName(patronRequestModel.getName());
		patron.setAddress(patronRequestModel.getAddress());
        return patron;
    }

    @Override
    public List<PatronRequestModel> mapToPatronRequestModelList(List<Patron> patrons) {
        if (patrons == null || patrons.isEmpty()) {
            return new ArrayList<>();
        }
    
        List<PatronRequestModel> patronRequestModels = new ArrayList<>();
        for (Patron patron : patrons) {
            PatronRequestModel patronRequestModel = new PatronRequestModel();
            patronRequestModel.setEmail(patron.getEmail());
            patronRequestModel.setMobile(patron.getMobile());
            patronRequestModel.setName(patron.getName());
            patronRequestModel.setAddress(patron.getAddress());
            patronRequestModels.add(patronRequestModel);
        }
        return patronRequestModels;
    }
}
