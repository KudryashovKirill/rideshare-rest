package com.example.rideshare_rest.graphql.fetcher;

import com.example.rideshare_api_contract.dto.PagedResponse;
import com.example.rideshare_api_contract.dto.RideResponse;
import com.example.rideshare_rest.graphql.types.BookingConnectionGql;
import com.example.rideshare_rest.graphql.types.PageInfoGql;
import com.example.rideshare_rest.service.BookingService;
import com.example.rideshare_rest.service.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class RideRelationDataFetcher {
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public RideRelationDataFetcher(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    //todo доделать и в контракте добавить
//    @DgsData(parentType = "Ride", field = "bookings")
//    public BookingConnectionGql bookings(DgsDataFetchingEnvironment dfe,
//                                         @InputArgument Integer page,
//                                         @InputArgument Integer size) {
//        RideResponse ride = dfe.getSource();
//        PagedResponse paged = bookingService.(ride.getId(),
//                page != null ? page : 0,
//                size != null ? size : 20);
//        return new BookingConnectionGql(
//                paged.content(),
//                new PageInfoGql(paged.pageNumber(), paged.pageSize(), paged.totalPages(), paged.last()),
//                (int) paged.totalElements()
//        );
//    }
}
