package com.danslans.alertbuyers.presentation.mappers;

import com.danslans.alertbuyers.business.models.entities.NotificationEntity;
import com.danslans.alertbuyers.presentation.dto.ResponseNotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( componentModel = "spring" )
public interface INotificationMapper {

    INotificationMapper INSTANCE = Mappers.getMapper( INotificationMapper.class );
    List<ResponseNotificationDto> notificationEntityToResponseNotificationDto(List<NotificationEntity> notifications);
}
