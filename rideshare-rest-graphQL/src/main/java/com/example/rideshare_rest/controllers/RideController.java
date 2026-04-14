package com.example.rideshare_rest.controllers;

import com.example.rideshare_api_contract.dto.*;
import com.example.rideshare_api_contract.endpoints.RideApi;
import com.example.rideshare_rest.assemblers.RideModelAssembler;
import com.example.rideshare_rest.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideController implements RideApi {
    private final RideService rideService;
    private final RideModelAssembler rideModelAssembler;
    private final PagedResourcesAssembler<RideResponse> pagedResourcesAssembler;

    @Autowired
    public RideController(RideService rideService,
                          RideModelAssembler rideModelAssembler,
                          PagedResourcesAssembler<RideResponse> pagedResourcesAssembler) {
        this.rideService = rideService;
        this.rideModelAssembler = rideModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public PagedModel<EntityModel<RideResponse>> getAllRides(Long driverId, String departureCity, String arrivalCity,
                                                             Integer freeSeats, int page, int size) {
        PagedResponse<RideResponse> paged = rideService.getAllRides(driverId, departureCity, arrivalCity,
                freeSeats, page, size);
        Page<RideResponse> springPage = new PageImpl<>(
                paged.content(),
                PageRequest.of(paged.pageNumber(), paged.pageSize()),
                paged.totalElements()
        );
        return pagedResourcesAssembler.toModel(springPage, rideModelAssembler);
    }

    @Override
    public EntityModel<RideResponse> getRideById(Long id) {
        return rideModelAssembler.toModel(rideService.getRideById(id));
    }

    @Override
    public ResponseEntity<EntityModel<RideResponse>> createRide(RideRequest request) {
        RideResponse created = rideService.create(request);
        EntityModel<RideResponse> model = rideModelAssembler.toModel(created);
        return ResponseEntity
                .created(model.getRequiredLink("self").toUri())
                .body(model);
    }

    @Override
    public EntityModel<RideResponse> updateRide(Long id, UpdateRideRequest request) {
        return rideModelAssembler.toModel(rideService.updateRide(id, request));
    }

    @Override
    public EntityModel<RideResponse> patchRide(Long id, PatchRideRequest request) {
        return rideModelAssembler.toModel(rideService.patchRide(id, request));
    }

    @Override
    public EntityModel<RideResponse> patchRideStatus(Long id, RideStatus status) {
        return rideModelAssembler.toModel(rideService.patchRideStatus(id, status));
    }

    @Override
    public void deleteRide(Long id) {
        rideService.delete(id);
    }
}
