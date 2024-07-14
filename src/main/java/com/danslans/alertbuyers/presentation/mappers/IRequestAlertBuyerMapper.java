package com.danslans.alertbuyers.presentation.mappers;

import com.danslans.alertbuyers.business.models.RequestAlertBuyer;
import com.danslans.alertbuyers.business.models.ResponseAlertBuyer;
import com.danslans.alertbuyers.presentation.dto.RequestAlertBuyerDto;
import com.danslans.alertbuyers.presentation.dto.ResponseAlertBuyerDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( componentModel = "spring" )
public interface IRequestAlertBuyerMapper {

    IRequestAlertBuyerMapper INSTANCE = Mappers.getMapper( IRequestAlertBuyerMapper.class );

    RequestAlertBuyer requestAlertBuyerDtoToRequestAlertBuyer(RequestAlertBuyerDto responseAlertBuyerDto);
    ResponseAlertBuyerDto responseAlertBuyerDtoToResponseAlertBuyer(ResponseAlertBuyer responseAlertBuyer);
}
